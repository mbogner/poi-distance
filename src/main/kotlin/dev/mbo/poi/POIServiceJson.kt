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
import dev.mbo.poi.model.Model
import dev.mbo.poi.util.RessourceUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Implementation for POIService with JSON-file backed data.
 */
class POIServiceJson(
    private val json: ObjectMapper,
    private val filename: String,
) : AbstractPOIService() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * @see POIService.update
     */
    override fun update() {
        val start = System.currentTimeMillis()
        model = json.readValue(RessourceUtil.loadClasspathRessource(filename), Model::class.java)
        val duration = System.currentTimeMillis() - start
        log.debug("parsed $filename in $duration ms")
        if (null == model) {
            throw IllegalStateException("reading $filename resulted in empty model")
        }
        if (model!!.features.isEmpty()) {
            throw IllegalStateException("no features imported from $filename")
        }
    }

}