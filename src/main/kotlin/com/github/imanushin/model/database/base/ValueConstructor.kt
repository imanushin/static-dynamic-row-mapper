package com.github.imanushin.model.database.base

import java.sql.ResultSet

interface SingleValueMapper<out TValue> {
    fun getValue(resultSet: ResultSet, rowIndex: Int): TValue
}

interface DoubleValuesMapper<out TValue> {
    fun getValue(resultSet: ResultSet, rowIndex1: Int, rowIndex2: Int): TValue
}