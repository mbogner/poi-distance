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
import java.io.File
import java.util.UUID

internal class RessourceUtilTest {

    companion object {
        const val TEST_FILE = "/GIP_PV_STOPS_EU-DEL-V-20220627.json"
    }

    @Test
    fun loadClasspathRessourceStream() {
        assertThat(RessourceUtil.loadClasspathRessourceStream(TEST_FILE)).isNotNull
    }

    @Test
    fun loadClasspathRessourceStreamUnknownFile() {
        assertThrows(IllegalStateException::class.java) {
            RessourceUtil.loadClasspathRessourceStream(UUID.randomUUID().toString())
        }
    }

    @Test
    fun loadClasspathRessource() {
        val url = RessourceUtil.loadClasspathRessource(TEST_FILE)
        assertThat(url).isNotNull
        assertThat(url.file).startsWith(File.separator)
        assertThat(url.file).endsWith(TEST_FILE)
    }

}