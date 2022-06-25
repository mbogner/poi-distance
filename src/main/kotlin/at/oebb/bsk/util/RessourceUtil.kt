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

package at.oebb.bsk.util

import java.io.InputStream

class RessourceUtil {

    companion object {
        /**
         * Loads a resource from classpath.
         * @param path of the resource to load relative to class or absolute starting from classpath root. Prefixing
         *             with classpath: or file: is not necessary.
         */
        fun loadClasspathRessource(path: String): InputStream {
            return this::class.java.getResourceAsStream(path) ?: throw IllegalStateException(
                "could not load $path from classpath"
            )
        }
    }

}