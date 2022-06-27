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
import java.io.File

/**
 * Implementation for POIService with JSON-file backed data.
 */
class POIServiceJson(
    private val json: ObjectMapper,
    private val source: String,
) : AbstractPOIService() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun getCacheFile(): File {
        return File.createTempFile("poi", "json")
    }

    /**
     * @see POIService.update
     */
    override fun update() {
        val start = System.currentTimeMillis()
        model = if (source.startsWith("classpath:")) {
            val cpSource = source.replace("classpath:", "")
            log.debug("reading file from classpath@ {}", cpSource)
            json.readValue(RessourceUtil.loadClasspathRessourceStream(cpSource), Model::class.java)
        } else if (source.startsWith("/")) {
            log.debug("reading file from absolute path {}", source)
            json.readValue(File(source), Model::class.java)
        } else {
            throw IllegalArgumentException("invalid source: $source")
        }
        val duration = System.currentTimeMillis() - start
        if (null == model) {
            throw IllegalStateException("reading $source resulted in empty model")
        }
        val size = size()
        log.debug("parsed {} ({} entries) in {} ms", source, size, duration)
        if (size < 1) {
            throw IllegalStateException("no features imported from $source")
        }
    }

}