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

import dev.mbo.poi.model.Coordinate
import dev.mbo.poi.model.Feature
import dev.mbo.poi.model.Model
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.time.Instant
import java.util.Date
import java.util.stream.Collectors

/**
 * Base class for calculating
 */
abstract class AbstractPOIService : POIService {

    private val log: Logger = LoggerFactory.getLogger(javaClass)
    protected var model: Model? = null

    /**
     * @see POIService.getPOI
     */
    override fun getPOI(coordinate: Coordinate, timestamp: Instant, logDuration: Boolean): Set<Feature> {
        val start = System.currentTimeMillis()
        if (null == model) {
            log.warn("implicitly run update")
            update()
        }

        val today = Date()
        val validFeatures = model!!.features.filter {
            today.after(it.properties.VALIDFROM) && today.before(it.properties.VALIDTO)
        }

        validFeatures.forEach {
            it.distance = it.distanceFrom(coordinate)
        }

        val minimumDistancePoi = validFeatures.stream().min(Comparator.comparing(Feature::distance))
        if (minimumDistancePoi.isEmpty) {
            return emptySet()
        }
        val result = validFeatures.stream()
            .filter { it.distance == minimumDistancePoi.get().distance }
            .collect(Collectors.toSet())
        val duration = System.currentTimeMillis() - start
        if (logDuration) {
            log.debug("getPOI({},{}) took {}ms", coordinate.lat, coordinate.lon, duration)
        }
        return result
    }

    /**
     * @see POIService.size
     */
    override fun size(): Int {
        return model?.features?.size ?: 0
    }

    protected abstract fun getCacheFile(): File

}