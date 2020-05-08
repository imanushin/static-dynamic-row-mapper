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
import io.mockk.every
import io.mockk.mockk
import java.sql.ResultSet
import java.sql.ResultSetMetaData

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
            val metadata = mockk<ResultSetMetaData> {
                every { columnCount } returns 3
            }
            val resultSet = mockk<ResultSet> {
                var isFirstRow = true
                every { metaData } returns metadata
                every { getString(0) } returns "name1"
                every { getString(1) } returns "name-1"
                every { getString(2) } returns "github.com"
                every { findColumn("name") } returns 0
                every { findColumn("user_email_name") } returns 1
                every { findColumn("user_email_domain") } returns 2
                every { next() } answers {
                    isFirstRow.also {
                        isFirstRow = false
                    }
                }
            }

            // When
            val mapper = mapperFactory.createForType<DbUser>()
            val result = mapper.extractData(resultSet)

            // Then
            result shouldBe expectedResult
        }
    }
}