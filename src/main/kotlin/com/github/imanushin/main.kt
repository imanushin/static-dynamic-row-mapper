package com.github.imanushin

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

interface DynamicRowMapperFactory {
    fun <TMappingType> createForType(clazz: Class<TMappingType>): RowMapper<TMappingType>
}

inline fun <reified TMappingType> DynamicRowMapperFactory.createForType(): RowMapper<TMappingType> {
    return createForType(TMappingType::class.java)
}

@Service
internal class DynamicRowMapperFactoryImpl : DynamicRowMapperFactory {
    override fun <TMappingType> createForType(clazz: Class<TMappingType>): RowMapper<TMappingType> {
        TODO("Not yet implemented")
    }

}