package com.github.imanushin

import com.github.imanushin.model.database.base.DoubleMappingValuesAnnotation
import com.github.imanushin.model.database.base.SingleMappingValueAnnotation
import mu.KLogging
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.stereotype.Service
import java.sql.ResultSet
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

interface ResultSetMapper<TMappingType> : ResultSetExtractor<List<TMappingType>>

interface DynamicResultSetMapperFactory {
    fun <TMappingType : Any> createForType(clazz: KClass<TMappingType>): ResultSetMapper<TMappingType>
}

inline fun <reified TMappingType : Any> DynamicResultSetMapperFactory.createForType(): ResultSetMapper<TMappingType> {
    return createForType(TMappingType::class)
}

@Service
internal class DynamicResultSetMapperFactoryImpl(
        private val compiler: KotlinClassCompilation
) : DynamicResultSetMapperFactory {
    private companion object : KLogging()

    override fun <TMappingType : Any> createForType(clazz: KClass<TMappingType>): ResultSetMapper<TMappingType> {
        val sourceCode = getMapperSourceCode(clazz)

        logger.debug {
            buildString {
                appendln("Compiling:")
                append(sourceCode)
            }
        }

        return compiler.compile(sourceCode)
    }

    private fun <TMappingType : Any> getMapperSourceCode(clazz: KClass<TMappingType>): String {
        return buildString {
            val className = clazz.qualifiedName!!
            val resultSetClassName = ResultSet::class.java.name

            val singleConstructor = clazz.constructors.single()
            val parameters = singleConstructor.parameters

            val annotations = parameters.flatMap { it.annotations.toList() }

            val columnNames = annotations.flatMap { getColumnNames(it) }.toSet()
            val columnNameToVariable = columnNames.mapIndexed { index, name -> name to "columnIndex$index" }.toMap()

            appendln("""
import com.github.imanushin.ResultSetMapper
object : com.github.imanushin.ResultSetMapper<$className> {
   override fun extractData(rs: $resultSetClassName): List<$className> {
      val queryMetadata = rs.metaData
      val queryColumnCount = queryMetadata.columnCount
      val mapperColumnCount = ${columnNameToVariable.size}
      require(queryColumnCount == mapperColumnCount) {
          val queryColumns = (0..queryColumnCount).joinToString { queryMetadata.getColumnName(it) }
          "Sql query has invalid columns: \${'$'}mapperColumnCount is expected, however \${'$'}queryColumnCount is returned. " +
              "Query has: \${'$'}queryColumns. Mapper has: ${columnNames.joinToString()}"
      }

""")

            columnNameToVariable.forEach { (columnName, variableName) ->
                appendln("      val $variableName = rs.findColumn(\"$columnName\")")
            }

            appendln("""
       val result = mutableListOf<$className>()
       while (rs.next()) {
""")

            parameters.forEach { parameter ->
                fillParameterConstructor(parameter, columnNameToVariable)
            }


            appendln("          val rowResult = $className(")
            appendln(
                    parameters.joinToString("," + System.lineSeparator()) { parameter ->
                        "              ${parameter.name} = ${parameter.name}"
                    }
            )

            appendln("""
          )
          result.add(rowResult)
      }
      return result
   }
}
""")
        }
    }

    private fun StringBuilder.fillParameterConstructor(parameter: KParameter, columnNameToVariable: Map<String, String>) {
        append("              val ${parameter.name} = ")

        // please note: double or missing annotations aren't covered here
        parameter.annotations.forEach { annotation ->
            when (annotation) {
                is DoubleMappingValuesAnnotation ->
                    appendln("${annotation.constructionClass.qualifiedName}.getValue(" +
                            "rs, " +
                            "${columnNameToVariable[annotation.columnName1]}, " +
                            "${columnNameToVariable[annotation.columnName2]})"
                    )
                is SingleMappingValueAnnotation ->
                    appendln("${annotation.constructionClass.qualifiedName}.getValue(" +
                            "rs, " +
                            "${columnNameToVariable[annotation.columnName]})"
                    )
            }
        }
    }

    private fun getColumnNames(annotation: Annotation): List<String> {
        return when (annotation) {
            is DoubleMappingValuesAnnotation -> listOf(annotation.columnName1, annotation.columnName2)
            is SingleMappingValueAnnotation -> listOf(annotation.columnName)
            else -> emptyList()
        }
    }
}