/*
 * Copyright (c) 2018-2021. data2viz sàrl.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package io.data2viz.shape

import io.data2viz.format.formatter
import io.data2viz.test.TestBase
import kotlin.test.Test
import kotlin.test.assertEquals

class PieTest : TestBase() {

    val round = formatter(".6f")

    data class MyObject(
            val index: Int,
            val score: Double
    )

    val data = arrayOf(
            MyObject(0, 10.0),
            MyObject(1, 20.0),
            MyObject(2, 5.0),
            MyObject(3, 30.0),
            MyObject(4, 25.0)
    )

    val data2 = arrayOf(
            MyObject(0, 10.0),
            MyObject(1, 10.0)
    )

    val pieLayout = pie<MyObject> {
        value = { it.score }
        startAngle = { pi / 3 }
        endAngle = { pi }
    }

    val pieLayout2 = pie<MyObject> {
        value = { it.score }
        padAngle = { pi / 20 }
    }

    @Test
    fun pie_layout_start_end_angles() {
        val result = pieLayout.render(data)
        assertEquals(pi / 3, result[0].startAngle)
        assertEquals(round(pi), round(result[result.size - 1].endAngle))
    }

    /*@Test
    fun pie_layout_pad_angles() {
        val result = pieLayout2.render(data2)
        assertEquals(pi / 20, result[1].startAngle - result[0].endAngle)
        assertEquals(pi / 20, result[0].startAngle - result[1].endAngle)
    }*/


    /*@Test
    fun pie_draw() {
        val params = pieLayout.render(data)

        val arcGenerator = arc<ArcParams<MyObject>> {
            startAngle = { it.startAngle }
            endAngle = { it.endAngle }
        }

    }*/
}
