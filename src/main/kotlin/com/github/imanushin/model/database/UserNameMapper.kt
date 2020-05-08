package com.github.imanushin.model.database

import com.github.imanushin.model.application.UserName
import com.github.imanushin.model.database.base.NonEmptyTextValueMapper

object UserNameMapper : NonEmptyTextValueMapper<UserName>(UserName)