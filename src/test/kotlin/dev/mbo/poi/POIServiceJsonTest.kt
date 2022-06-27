/*
 * Copyright 2022 mbo.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.mbo.poi

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import dev.mbo.poi.model.Coordinate
import dev.mbo.poi.model.Feature
import dev.mbo.poi.util.RessourceUtilTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.Instant

fun testJacksonObjectMapper(): ObjectMapper = jsonMapper { addModule(kotlinModule()).addModule(JavaTimeModule()) }

internal class POIServiceJsonTest {


    private lateinit var service: POIServiceJson

    companion object {
        private val log: Logger = LoggerFactory.getLogger(POIServiceJsonTest::class.java)

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            log.info("set UTC")
            System.setProperty("user.timezone", "UTC")
        }
    }

    @BeforeEach
    fun beforeEach() {
        service = POIServiceJson(testJacksonObjectMapper(), RessourceUtilTest.TEST_FILE)
    }

    @Test
    fun getPOI() {
        service.update()
        val start = System.currentTimeMillis()
        val features: Set<Feature> = service.getPOI(Coordinate(BigDecimal("48.45718202101379"), BigDecimal("13.434536595612848")), Instant.now())
        val duration = System.currentTimeMillis() - start
        log.debug("processed ${service.size()} in $duration ms")
        assertThat(features).hasSize(1)
        val entry = features.elementAt(0)
        assertThat(entry.properties.OBJECTID).isEqualTo(30)
    }

}