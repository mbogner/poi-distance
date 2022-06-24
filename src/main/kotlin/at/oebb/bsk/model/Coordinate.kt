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

package at.oebb.bsk.model

import java.math.BigDecimal

// https://en.wikipedia.org/wiki/Geographic_coordinate_system
data class Coordinate(
    val lat: BigDecimal, // y
    val lon: BigDecimal, // x
) {
    companion object {
        val LAT_MIN = BigDecimal("-90")
        val LAT_MAX: BigDecimal = LAT_MIN.abs()

        val LON_MIN = BigDecimal("-180")
        val LON_MAX: BigDecimal = LAT_MIN.abs()

        fun checkRange(value: BigDecimal, name: String, min: BigDecimal, max: BigDecimal) {
            if (value < min || value > max) {
                throw IllegalArgumentException("$name out of range ($min <= $value <= $max)")
            }
        }
    }

    init {
        checkRange(lat, "lat", LAT_MIN, LAT_MAX)
        checkRange(lon, "lon", LON_MIN, LON_MAX)
    }
}