package com.github.imanushin.model.database.base

import com.github.imanushin.model.application.base.NonEmptyText
import com.github.imanushin.model.application.base.NonEmptyTextConstructor
import java.sql.ResultSet

abstract class NonEmptyTextValueMapper<out TResult : NonEmptyText>(
        private val textConstructor: NonEmptyTextConstructor<TResult>
) : SingleValueMapper<TResult> {
    override fun getValue(resultSet: ResultSet, rowIndex: Int): TResult {
        return textConstructor.create(resultSet.getString(rowIndex))
    }
}