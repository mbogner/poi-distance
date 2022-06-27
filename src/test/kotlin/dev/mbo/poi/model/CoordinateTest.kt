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

package dev.mbo.poi.model

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CoordinateTest {

    @Test
    fun checkRange() {
        val diff = BigDecimal("0.001")
        Coordinate(lat = BigDecimal.ZERO, lon = BigDecimal.ZERO)
        Coordinate(lat = BigDecimal.TEN, lon = BigDecimal.TEN)
        assertThrows(IllegalArgumentException::class.java) {
            Coordinate(lat = Coordinate.LAT_MIN.subtract(diff), lon = BigDecimal.ZERO)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Coordinate(lat = Coordinate.LAT_MAX.add(diff), lon = BigDecimal.ZERO)
        }
        assertThrows(IllegalArgumentException::class.java) {
            Coordinate(lat = BigDecimal.ZERO, lon = Coordinate.LON_MIN.subtract(diff))
        }
        assertThrows(IllegalArgumentException::class.java) {
            Coordinate(lat = BigDecimal.ZERO, lon = Coordinate.LON_MAX.add(diff))
        }
    }

}