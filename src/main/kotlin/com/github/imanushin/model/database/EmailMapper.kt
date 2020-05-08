package com.github.imanushin.model.database

import com.github.imanushin.model.application.Email
import com.github.imanushin.model.application.EmailDomain
import com.github.imanushin.model.application.EmailUser
import com.github.imanushin.model.application.UserName
import com.github.imanushin.model.database.base.NonEmptyTextValueMapper
import com.github.imanushin.model.database.base.TwoMappersValueMapper

object EmailUserMapper : NonEmptyTextValueMapper<EmailUser>(EmailUser)
object EmailDomainMapper : NonEmptyTextValueMapper<EmailDomain>(EmailDomain)

object EmailMapper : TwoMappersValueMapper<Email, EmailUser, EmailDomain>(EmailUserMapper, EmailDomainMapper) {
    override fun create(parameter1: EmailUser, parameter2: EmailDomain): Email {
        return Email(parameter1, parameter2)
    }
}