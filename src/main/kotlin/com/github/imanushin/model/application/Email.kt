package com.github.imanushin.model.application

import com.github.imanushin.model.application.base.NonEmptyText
import com.github.imanushin.model.application.base.NonEmptyTextConstructor

class EmailUser(value: String) : NonEmptyText(value) {
    companion object : NonEmptyTextConstructor<EmailUser> {
        override fun create(value: String) = EmailUser(value)
    }
}

class EmailDomain(value: String) : NonEmptyText(value) {
    companion object : NonEmptyTextConstructor<EmailDomain> {
        override fun create(value: String) = EmailDomain(value)
    }
}

data class Email(val user: EmailUser, val domain: EmailDomain)