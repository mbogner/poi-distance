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
import java.time.Instant

interface POIService {

    /**
     * Translates a given coordinate to the closest POI(s). If no appropriate POI can be found the result is empty.
     * @param coordinate The coordinates to get closest POI for.
     * @return Set of POIs with lowest distance.
     */
    fun getPOI(coordinate: Coordinate, timestamp: Instant): Set<Feature>

    /**
     * Updates POI list from source.
     */
    fun update()

    /**
     * Gives the overall number of POIs known at the moment.
     */
    fun size(): Int
}