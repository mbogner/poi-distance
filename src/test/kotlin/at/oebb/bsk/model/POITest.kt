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

package at.oebb.bsk.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class POITest {

    @Test
    fun distanceFrom() {
        val poi = POI(
            id = "id1",
            name = "name1",
            coordinate = Coordinate(BigDecimal.ZERO, BigDecimal.ZERO),
        )
        val distance = poi.distanceFrom(Coordinate(BigDecimal.ONE, BigDecimal.ONE))
        // 1^2 + 1^2 = 2
        // sqrt(2) = 1.414213562373095
        assertThat(distance).isEqualTo(BigDecimal("1.41421356"))
    }

}