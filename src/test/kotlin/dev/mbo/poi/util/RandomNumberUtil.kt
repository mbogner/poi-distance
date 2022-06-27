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

package dev.mbo.poi.util

import dev.mbo.poi.model.Coordinate
import dev.mbo.poi.model.Model
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.ThreadLocalRandom

class RandomNumberUtil {

    companion object {
        private fun randomBigDecimal(min: BigDecimal, max: BigDecimal, scale: Int = Model.COORDINATE_SCALE): BigDecimal {
            return BigDecimal(
                ThreadLocalRandom.current().nextDouble(min.toDouble(), max.toDouble())
            ).setScale(scale, RoundingMode.HALF_UP)
        }

        fun createRandomValidCoordinate(): Coordinate {
            return Coordinate(lat = createRandomValidLat(), lon = createRandomValidLon())
        }

        private fun createRandomValidLat(): BigDecimal {
            return randomBigDecimal(Coordinate.LAT_MIN, Coordinate.LAT_MAX)
        }

        private fun createRandomValidLon(): BigDecimal {
            return randomBigDecimal(Coordinate.LON_MIN, Coordinate.LON_MAX)
        }
    }

}