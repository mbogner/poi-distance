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

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.util.Date

@JsonIgnoreProperties(ignoreUnknown = true)
@Suppress("SpellCheckingInspection")
data class FeatureProperties(
    val OBJECTID: Long,
    val X_KOORDINATE: BigDecimal,
    val Y_KOORDINATE: BigDecimal,
    val NAME_SNNB: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    val VALIDFROM: Date,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    val VALIDTO: Date,
)