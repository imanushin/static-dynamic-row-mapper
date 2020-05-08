package com.github.imanushin.test.unit

import com.github.imanushin.DynamicResultSetMapperFactoryImpl
import com.github.imanushin.KotlinClassCompilationImpl
import com.github.imanushin.createForType
import com.github.imanushin.model.application.Email
import com.github.imanushin.model.application.EmailDomain
import com.github.imanushin.model.application.EmailUser
import com.github.imanushin.model.application.UserName
import com.github.imanushin.model.database.DbUser
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import java.sql.ResultSet

/**
 * This isn't right unit test, because it operates with several services at one time.
 *
 * Right implementation should be based on SpringIoC, etc. and should be named "integration"
 *
 * However for example simplification
 */
class ResultSetMapperTest : FreeSpec() {
    init {
        "user should be parsed" {
            // Given
            val expectedResult = listOf(
                    DbUser(
                            UserName("name1"),
                            Email(
                                    EmailUser("name-1"),
                                    EmailDomain("github.com")
                            )
                    )
            )

            val compiler = KotlinClassCompilationImpl()
            val mapperFactory = DynamicResultSetMapperFactoryImpl(compiler)
            val resultSet = mockk<ResultSet> {

            }

            // When
            val mapper = mapperFactory.createForType<DbUser>()
            val result = mapper.extractData(resultSet)

            // Then
            result shouldBe expectedResult
        }
    }
}