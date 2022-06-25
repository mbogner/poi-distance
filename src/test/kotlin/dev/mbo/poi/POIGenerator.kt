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

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.mbo.poi.model.CoordinateTest
import dev.mbo.poi.model.POI
import dev.mbo.poi.model.POIModel
import java.nio.file.Paths
import java.time.Instant

class POIGenerator {

    fun run() {
        val pois: MutableSet<POI> = mutableSetOf()
        for (i in 1..10_000) {
            pois.add(
                POI(
                    id = "id$i",
                    name = "name$i",
                    coordinate = CoordinateTest.createRandomValidCoordinate()
                )
            )
        }
        val model = POIModel(Instant.now().epochSecond, pois)
        val out = Paths.get("generated.json").toFile()
        jacksonObjectMapper()
            .writerWithDefaultPrettyPrinter()
            .writeValue(out, model)
        println("done - written to ${out.absolutePath}")
    }

}

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    POIGenerator().run()
}