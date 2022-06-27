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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@JsonIgnoreProperties(ignoreUnknown = true)
data class Feature(
    val type: String,
    val properties: FeatureProperties,
) : Comparable<Feature> {
    @Volatile
    var distance: BigDecimal? = null

    fun distanceFrom(coordinate: Coordinate): BigDecimal {
        // sqrt(x^2 + y^2) rounded to COORDINATE_SCALE with rounding mode half_up
        return (
                this.properties.X_KOORDINATE.minus(coordinate.lon).pow(2) +
                        this.properties.Y_KOORDINATE.minus(coordinate.lat).pow(2)
                )
            .sqrt(MathContext.DECIMAL64)
            .setScale(Model.COORDINATE_SCALE, RoundingMode.HALF_UP)
    }

    override fun compareTo(other: Feature): Int {
        return properties.OBJECTID.compareTo(other.properties.OBJECTID)
    }
}