package com.github.imanushin.model.application.base

abstract class NonEmptyText(val value: String) {
    init {
        require(value.isNotBlank()) {
            "Empty text is prohibited for ${this.javaClass.simpleName}. Actual value: $this"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NonEmptyText

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }
}

interface NonEmptyTextConstructor<out TResult : NonEmptyText> {
    fun create(value: String): TResult
}