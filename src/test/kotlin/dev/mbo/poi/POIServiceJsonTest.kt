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
import dev.mbo.poi.util.RandomNumberUtil
import dev.mbo.poi.util.RessourceUtil
import dev.mbo.poi.util.RessourceUtilTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.Instant
import kotlin.math.roundToInt


fun testJacksonObjectMapper(): ObjectMapper = jsonMapper { addModule(kotlinModule()).addModule(JavaTimeModule()) }

internal class POIServiceJsonTest {

    private val jsonParser = testJacksonObjectMapper()

    companion object {
        private val log: Logger = LoggerFactory.getLogger(POIServiceJsonTest::class.java)

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            log.info("set UTC")
            System.setProperty("user.timezone", "UTC")
        }
    }

    @Test
    fun getPOIFromCpFile() {
        shared(serviceClasspathFile())
    }

    @Test
    fun getPOIFromAbsoluteFile() {
        shared(POIServiceJson(jsonParser, RessourceUtil.loadClasspathRessource(RessourceUtilTest.TEST_FILE).file))
    }

    private fun serviceClasspathFile(): POIService {
        return POIServiceJson(jsonParser, "classpath:${RessourceUtilTest.TEST_FILE}")
    }

    @Test
    fun massTest() {
        val size = 1_000
        val ignore = 20

        val service = serviceClasspathFile()
        service.update()
        val durations = mutableListOf<Long>()
        val randomCoordinates = mutableListOf<Coordinate>()

        for (i in 0..size) {
            randomCoordinates.add(RandomNumberUtil.createRandomValidCoordinate())
        }
        val now = Instant.now()

        for (i in 0..size) {
            val start = System.currentTimeMillis()
            val result = service.getPOI(randomCoordinates[i], now)
            durations.add(System.currentTimeMillis() - start)
            assertThat(result).isNotEmpty
        }
        durations.sort()
        val min = durations.min()
        val max = durations.max()

        // ignore first and last <ignore> entries
        val part = durations.toTypedArray().copyOfRange(ignore, size - ignore).asList()
        assertThat(part).hasSize(size - 2 * ignore)

        val avg = rounded(part.average())
        val median = rounded(median(part))

        log.debug(
            "average duration for {} requests; avg={}ms, min={}ms, max={}ms, median={}ms",
            size, avg, min, max, median
        )
    }

    private fun rounded(value: Double): Double {
        return (value * 100.0).roundToInt() / 100.0
    }

    private fun median(part: List<Long>): Double {
        val middle = part.size / 2
        return if (1 == part.size % 2) {
            part[middle].toDouble()
        } else {
            (part[middle - 1] + part[middle]) / 2.0
        }
    }

    private fun shared(service: POIService) {
        service.update()
        val features = service.getPOI(
            Coordinate(
                lat = BigDecimal("48.45718202101379"),
                lon = BigDecimal("13.434536595612848")
            ),
            Instant.now(),
            true
        )
        val size = service.size()
        assertThat(size).isEqualTo(1032)
        assertThat(features).hasSize(1)
        val entry = features.elementAt(0)
        assertThat(entry.properties.OBJECTID).isEqualTo(30)
    }

}