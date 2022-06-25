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

import dev.mbo.poi.model.POIModel
import dev.mbo.poi.util.RessourceUtil
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Implementation for POIService with JSON-file backed data.
 */
class POIServiceJson(
    private val json: ObjectMapper,
    private val filename: String,
) : AbstractPOIService() {

    /**
     * @see POIService.update
     */
    override fun update() {
        model = json.readValue(RessourceUtil.loadClasspathRessource(filename), POIModel::class.java)
        if (null == model) {
            throw IllegalStateException("reading $filename resulted in empty model")
        }
        if (model!!.pois.isEmpty()) {
            throw IllegalStateException("no pois imported from $filename")
        }
    }

}