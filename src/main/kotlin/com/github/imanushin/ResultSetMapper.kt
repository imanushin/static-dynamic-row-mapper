package com.github.imanushin

import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Service
import java.sql.ResultSet

interface ResultSetMapper<TMappingType> : ResultSetExtractor<List<TMappingType>>

interface DynamicResultSetMapperFactory {
    fun <TMappingType> createForType(clazz: Class<TMappingType>): ResultSetMapper<TMappingType>
}

inline fun <reified TMappingType> DynamicResultSetMapperFactory.createForType(): ResultSetMapper<TMappingType> {
    return createForType(TMappingType::class.java)
}

@Service
internal class DynamicResultSetMapperFactoryImpl(
        private val compiler: KotlinClassCompilation
) : DynamicResultSetMapperFactory {
    override fun <TMappingType> createForType(clazz: Class<TMappingType>): ResultSetMapper<TMappingType> {
        val sourceCode = getMapperSourceCode(clazz)

        return compiler.compile(sourceCode)
    }

    private fun <TMappingType> getMapperSourceCode(clazz: Class<TMappingType>): String {
        return buildString {
            val className = clazz.name
            val resultSetClassName = ResultSet::class.java.name

            appendln("import com.github.imanushin.ResultSetMapper")

            appendln("object : com.github.imanushin.ResultSetMapper<$className> {")
            appendln("   override fun extractData(rs: $resultSetClassName): List<$className> {")
            appendln("      TODO()")
            appendln("   }")
            appendln("}")
        }
    }
}