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

package at.oebb.bsk

import at.oebb.bsk.model.Coordinate
import at.oebb.bsk.model.POI
import at.oebb.bsk.model.POIModel
import at.oebb.bsk.util.RessourceUtil
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Instant
import java.util.stream.Collectors

class POIServiceJson(
    private val json: ObjectMapper,
    private val filename: String,
) : POIService {

    private var model: POIModel? = null

    @OptIn(ExperimentalStdlibApi::class)
    override fun getPOI(coordinate: Coordinate, timestamp: Instant): Set<POI> {
        if (null == model) {
            update()
        }
        val validFrom = Instant.ofEpochSecond(model!!.validFrom)
        if (timestamp.isBefore(validFrom)) {
            throw IllegalStateException("model isn't valid yet")
        }
        model!!.pois.forEach {
            it.distance = it.distanceFrom(coordinate)
        }

        val minimumDistancePoi = model!!.pois.stream().min(Comparator.comparing(POI::distance))
        if (minimumDistancePoi.isEmpty) {
            return emptySet()
        }
        return model!!.pois.stream()
            .filter { it.distance == minimumDistancePoi.get().distance }
            .collect(Collectors.toSet())
            .toSortedSet(Comparator.comparing(POI::id))
    }

    override fun update() {
        model = json.readValue(RessourceUtil.loadClasspathRessource(filename), POIModel::class.java)
        if (null == model) {
            throw IllegalStateException("reading $filename resulted in empty model")
        }
        if (model!!.pois.isEmpty()) {
            throw IllegalStateException("no pois imported from $filename")
        }
    }

    override fun size(): Int {
        return model?.pois?.size ?: 0
    }

}