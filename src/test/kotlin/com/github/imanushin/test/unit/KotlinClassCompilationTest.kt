package com.github.imanushin.test.unit

import com.github.imanushin.KotlinClassCompilationImpl
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class KotlinClassCompilationTest : FreeSpec() {
    init {
        "should compile simple statement" {
            // Given
            val source = " \"123\" "
            val instance = KotlinClassCompilationImpl()

            // When
            val result = instance.compile<String>(source)

            // Then
            result shouldBe "123"
        }

        "should compile interface implementation" {
            // Given
            val source =
                    """
                    object : Lazy<String> {
                        private var isInitialized = false
                        override val value: String
                            get() {
                                isInitialized=true
        
                                return "123"
                            }
        
                        override fun isInitialized() = isInitialized
                    }
                    """.trimIndent()
            val instance = KotlinClassCompilationImpl()

            // When
            val result = instance.compile<Lazy<String>>(source)

            // Then
            result.isInitialized() shouldBe false
            result.value shouldBe "123"
            result.isInitialized() shouldBe true
        }
    }
}