package com.github.imanushin.model.database.base

import java.sql.ResultSet

abstract class TwoMappersValueMapper<out TResult, TParameter1, TParameter2>(
        private val parameterMapper1: SingleValueMapper<TParameter1>,
        private val parameterMapper2: SingleValueMapper<TParameter2>
) : DoubleValuesMapper<TResult> {
    override fun getValue(resultSet: ResultSet, rowIndex1: Int, rowIndex2: Int): TResult {
        return create(
                parameterMapper1.getValue(resultSet, rowIndex1),
                parameterMapper2.getValue(resultSet, rowIndex2)
        )
    }

    abstract fun create(parameter1: TParameter1, parameter2: TParameter2): TResult
}