package com.rangjin.chatapi.presentation.advice.field

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJacksonValue
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class GlobalFieldFilteringAdvice(

    private val objectMapper: ObjectMapper

): ResponseBodyAdvice<Any> {

    private val filterId = "FieldFilter"

    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>?>
    ): Boolean =
        true

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if (body == null) return null

        // fields 파라미터 확인
        val requested =
            ((request as ServletServerHttpRequest).servletRequest.getAttribute("FIELDS_PARAM") as? Set<*>?)
                ?.filterIsInstance<String>()
                ?.toSet()
                ?: emptySet()
        if (requested.isEmpty()) {
            return MappingJacksonValue(body).apply {
                filters = SimpleFilterProvider()
                    .addFilter(filterId, SimpleBeanPropertyFilter.serializeAll())
                    .setFailOnUnknownId(false)
            }
        }

        // @JsonFilter("FieldFilter") 붙은 DTO 타입 확인
        val target = extractElementType(body) ?: return body
        val jsonFilter = target.getAnnotation(JsonFilter::class.java) ?: return body
        if (jsonFilter.value != filterId) return body

        // required field와 실제 필드의 교집합
        val allowed = beanProps(target)
        val effective = requested.intersect(allowed)
        if (effective.isEmpty()) return body

        // 필터 적용
        return MappingJacksonValue(body)
            .apply {
                this.filters = SimpleFilterProvider().addFilter(
                    filterId,
                    SimpleBeanPropertyFilter.filterOutAllExcept(effective)
                )
            }
    }

    private fun extractElementType(body: Any): Class<*>? =
        when (body) {
            is Collection<*> -> body.firstOrNull()?.javaClass
            is Array<*> -> body.firstOrNull()?.javaClass
            else -> body.javaClass
        }

    private fun beanProps(clazz: Class<*>): Set<String> =
        objectMapper.serializationConfig
            .introspect(objectMapper.constructType(clazz))
            .findProperties()
            .map { it.name }
            .toSet()

}