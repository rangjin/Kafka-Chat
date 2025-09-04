package com.rangjin.chatapi.infrastructure.persistence.outbox.converter

import com.rangjin.chatapi.infrastructure.persistence.outbox.entity.AggregateType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class AggregateTypeConverter: AttributeConverter<AggregateType, String> {

    override fun convertToDatabaseColumn(p0: AggregateType?): String? =
        p0?.dbValue

    override fun convertToEntityAttribute(p0: String?): AggregateType? =
        p0?.let { AggregateType.fromDb(it) }

}