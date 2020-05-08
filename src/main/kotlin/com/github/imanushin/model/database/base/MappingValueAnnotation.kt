package com.github.imanushin.model.database.base

import kotlin.reflect.KClass

@Target(AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class SingleMappingValueAnnotation(
        val constructionClass: KClass<out SingleValueMapper<*>>,
        val columnName: String
)

@Target(AnnotationTarget.VALUE_PARAMETER)
@MustBeDocumented
annotation class DoubleMappingValuesAnnotation(
        val constructionClass: KClass<out DoubleValuesMapper<*>>,
        val columnName1: String,
        val columnName2: String
)