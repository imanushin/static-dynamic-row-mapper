package com.github.imanushin.model.database.base

import java.sql.ResultSet

interface SingleValueMapper<out TValue> {
    fun getValue(resultSet: ResultSet, columnIndex: Int): TValue
}

interface DoubleValuesMapper<out TValue> {
    fun getValue(resultSet: ResultSet, columnIndex1: Int, columnIndex2: Int): TValue
}