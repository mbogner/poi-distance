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

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.mbo.poi.model.Coordinate
import dev.mbo.poi.model.POI
import dev.mbo.poi.util.RessourceUtilTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant

internal class POIServiceJsonTest {

    private lateinit var service: POIServiceJson

    @BeforeEach
    fun beforeEach() {
        service = POIServiceJson(jacksonObjectMapper(), RessourceUtilTest.TEST_FILE)
    }

    @Test
    fun getPOI() {
        service.update()
        val start = System.currentTimeMillis()
        val pois: Set<POI> = service.getPOI(Coordinate(BigDecimal.ZERO, BigDecimal.ZERO), Instant.now())
        val duration = System.currentTimeMillis() - start
        println("processed ${service.size()} in $duration ms")
        assertThat(pois).hasSize(2)
        assertThat(pois.elementAt(0).id).isEqualTo("id1")
        assertThat(pois.elementAt(1).id).isEqualTo("id2")
    }

}