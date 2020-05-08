package com.github.imanushin.model.database

import com.github.imanushin.model.application.Email
import com.github.imanushin.model.application.UserName
import com.github.imanushin.model.database.base.DoubleMappingValuesAnnotation
import com.github.imanushin.model.database.base.SingleMappingValueAnnotation

data class DbUser(
        @SingleMappingValueAnnotation(
                UserNameMapper::class,
                columnName = "name")
        val name: UserName,
        @DoubleMappingValuesAnnotation(EmailMapper::class,
                columnName1 = "user_email_name",
                columnName2 = "user_email_domain")
        val email: Email
)