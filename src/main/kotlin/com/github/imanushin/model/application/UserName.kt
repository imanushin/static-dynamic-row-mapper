package com.github.imanushin.model.application

import com.github.imanushin.model.application.base.NonEmptyText
import com.github.imanushin.model.application.base.NonEmptyTextConstructor

class UserName(value: String) : NonEmptyText(value) {
    companion object : NonEmptyTextConstructor<UserName> {
        override fun create(value: String) = UserName(value)
    }
}