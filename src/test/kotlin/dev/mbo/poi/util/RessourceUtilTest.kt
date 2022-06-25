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

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.UUID

internal class RessourceUtilTest {

    companion object {
        const val TEST_FILE = "/generated.json"
    }

    @Test
    fun loadClasspathRessource() {
        assertThat(RessourceUtil.loadClasspathRessource(TEST_FILE)).isNotNull
    }

    @Test
    fun loadClasspathRessourceUnknownFile() {
        assertThrows(IllegalStateException::class.java) {
            RessourceUtil.loadClasspathRessource(UUID.randomUUID().toString())
        }
    }

}