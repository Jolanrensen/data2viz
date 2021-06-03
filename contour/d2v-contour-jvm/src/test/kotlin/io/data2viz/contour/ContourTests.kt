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

package io.data2viz.contour

import io.data2viz.test.matchers.Matchers
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test

class ContourTests : Matchers {

    fun p(x: Number, y: Number) = arrayOf(x.toDouble(), y.toDouble())

    inline fun <reified T> lineOf(vararg elements: T): Array<T> = Array(elements.size) {elements[it]}

    @Test
    fun area() {
        doubleArea(arrayOf(p(0, 0), p(1, 0), p(1, 1), p(0, 1))) shouldBe -2.0
        doubleArea(arrayOf(p(0, 0), p(0, 1), p(1, 1), p(1, 0))) shouldBe 2.0

        doubleArea(arrayOf(p(1, 1), p(1, 2), p(2, 2), p(2, 1))) shouldBe 2.0
        doubleArea(arrayOf(p(1, 1), p(1, 2), p(3, 2), p(3, 1))) shouldBe 4.0
        doubleArea(arrayOf(p(1, 1), p(1, 2), p(4, 2), p(4, 1))) shouldBe 6.0
        doubleArea(arrayOf(p(1, 1), p(1, 3), p(4, 3), p(4, 1))) shouldBe 12.0
        doubleArea(arrayOf(p(2, 0), p(3, 1), p(2, 2), p(1, 1))) shouldBe -4.0
    }

    @Test
    fun collinear() {
        collinear(p(0, 0), p(1, 1), p(2, 2)) shouldBe true
        collinear(p(0, 0), p(1, 1), p(2, 1)) shouldBe false
        collinear(p(0, 0), p(2, 2), p(1, 1)) shouldBe true
    }

    @Test
    fun segmentContains() {
        segmentContains(p(0, 0), p(1, 1), p(2, 2)) shouldBe false
        segmentContains(p(0, 0), p(2, 2), p(0, 0)) shouldBe true
        segmentContains(p(0, 0), p(2, 2), p(1, 1)) shouldBe true
        segmentContains(p(0, 0), p(2, 2), p(2, 2)) shouldBe true
        segmentContains(p(0, 0), p(2, 2), p(2.0000000001, 2.0000000001)) shouldBe false
        segmentContains(p(0, 0), p(0, 2), p(0, 1)) shouldBe true
    }

    @Test
    fun `ring contains`() {
        val diamond = listOf(pt(2, 0), p(3, 1), p(2, 2), p(1, 1))
        ringContains(diamond, p(2, 1)) shouldBe 1
        ringContains(diamond, p(2, 0.1)) shouldBe 1
        ringContains(diamond, p(2, 0)) shouldBe 0
        ringContains(diamond, p(0, 0)) shouldBe -1
        ringContains(diamond, p(3, 1)) shouldBe 0
    }

    @Test
    fun `contours 1111`() {

        val result = contour(2).contours(
                values(
                        1, 1,
                        1, 1
                ))[0].coordinates[0][0]


        val expected = lineOf(
                arrayOf(2.0, 1.5),
                arrayOf(2.0, 0.5),
                arrayOf(1.5, 0.0),
                arrayOf(0.5, 0.0),
                arrayOf(0.0, 0.5),
                arrayOf(0.0, 1.5),
                arrayOf(0.5, 2.0),
                arrayOf(1.5, 2.0),
                arrayOf(2.0, 1.5)
        )

        checkValues(expected, result)
    }
    @Test
    fun `contours(values) returns the expected result for an empty polygon`() {

        val result = contour(2).contours(
                values(
                        0, 1,
                        0, 1
                ))[0].coordinates[0][0]


        val expected = lineOf(
                arrayOf(2.0, 1.5),
                arrayOf(2.0, 0.5),
                arrayOf(1.5, 0.0),
                arrayOf(1.0, 0.5),
                arrayOf(1.0, 1.5),
                arrayOf(1.5, 2.0),
                arrayOf(2.0, 1.5)
        )

        checkValues(expected, result)
    }

    @Test
    fun `contours(values) returns the expected result for a simple polygon`() {
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val result = contours[0].coordinates[0][0]

        val expected = arrayOf(
                pt(6.0, 7.5),
                pt(6.0, 6.5),
                pt(6.0, 5.5),
                pt(6.0, 4.5),
                pt(6.0, 3.5),
                pt(5.5, 3.0),
                pt(4.5, 3.0),
                pt(3.5, 3.0),
                pt(3.0, 3.5),
                pt(3.0, 4.5),
                pt(3.0, 5.5),
                pt(3.0, 6.5),
                pt(3.0, 7.5),
                pt(3.5, 8.0),
                pt(4.5, 8.0),
                pt(5.5, 8.0),
                pt(6.0, 7.5)
        )
        checkValues(expected, result)
    }

    @Test
    fun `contours smooth(false)(values) returns the expected result for a simple polygon`() {
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 2, 1, 2, 0, 0, 0, 0,
                        0, 0, 0, 2, 2, 2, 0, 0, 0, 0,
                        0, 0, 0, 1, 2, 1, 0, 0, 0, 0,
                        0, 0, 0, 2, 2, 2, 0, 0, 0, 0,
                        0, 0, 0, 2, 1, 2, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val result = contours[0].coordinates[0][0]

        val expected = arrayOf(
                pt(6.0, 7.5),
                pt(6.0, 6.5),
                pt(6.0, 5.5),
                pt(6.0, 4.5),
                pt(6.0, 3.5),
                pt(5.5, 3.0),
                pt(4.5, 3.0),
                pt(3.5, 3.0),
                pt(3.0, 3.5),
                pt(3.0, 4.5),
                pt(3.0, 5.5),
                pt(3.0, 6.5),
                pt(3.0, 7.5),
                pt(3.5, 8.0),
                pt(4.5, 8.0),
                pt(5.5, 8.0),
                pt(6.0, 7.5)
        )
        checkValues(expected, result)
    }


    @Test
    fun `contours(values) returns the expected result for a polygon with a hole`() {
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 0, 1, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 1, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val extern = contours[0].coordinates[0][0]
        val hole = contours[0].coordinates[0][1]

        val expected = arrayOf(
                pt(6.0, 7.5),
                pt(6.0, 6.5),
                pt(6.0, 5.5),
                pt(6.0, 4.5),
                pt(6.0, 3.5),
                pt(5.5, 3.0),
                pt(4.5, 3.0),
                pt(3.5, 3.0),
                pt(3.0, 3.5),
                pt(3.0, 4.5),
                pt(3.0, 5.5),
                pt(3.0, 6.5),
                pt(3.0, 7.5),
                pt(3.5, 8.0),
                pt(4.5, 8.0),
                pt(5.5, 8.0),
                pt(6.0, 7.5)
        )
        val expectedHole = arrayOf(
                pt(4.5, 7.0),
                pt(4.0, 6.5),
                pt(4.0, 5.5),
                pt(4.0, 4.5),
                pt(4.5, 4.0),
                pt(5.0, 4.5),
                pt(5.0, 5.5),
                pt(5.0, 6.5),
                pt(4.5, 7.0)
        )
        checkValues(expected, extern)
        checkValues(expectedHole, hole)
    }

    @Test
    fun `contours(values) returns the expected result for a multipolygon`() {
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val extern1 = contours[0].coordinates[0][0]
        val extern2 = contours[0].coordinates[1][0]

        val expected1 = arrayOf(
                p(5, 7.5),
                p(5, 6.5),
                p(5, 5.5),
                p(5, 4.5),
                p(5, 3.5),
                p(4.5, 3),
                p(3.5, 3),
                p(3, 3.5),
                p(3, 4.5),
                p(3, 5.5),
                p(3, 6.5),
                p(3, 7.5),
                p(3.5, 8),
                p(4.5, 8),
                p(5, 7.5)
        )

        val expected2 = arrayOf(
                p(7, 7.5),
                p(7, 6.5),
                p(7, 5.5),
                p(7, 4.5),
                p(7, 3.5),
                p(6.5, 3),
                p(6, 3.5),
                p(6, 4.5),
                p(6, 5.5),
                p(6, 6.5),
                p(6, 7.5),
                p(6.5, 8),
                p(7, 7.5)
        )
        checkValues(expected1, extern1)
        checkValues(expected2, extern2)
    }

    @Test
    fun `contours(values) returns the expected result for a multipolygon with holes`() {
        val contours = contour(10).contours(
                values(
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 1, 1, 1, 0, 1, 1, 1, 0, 0,
                        0, 1, 0, 1, 0, 1, 0, 1, 0, 0,
                        0, 1, 1, 1, 0, 1, 1, 1, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                ))

        val expected1 = arrayOf(
              p(4, 5.5),
              p(4, 4.5),
              p(4, 3.5),
              p(3.5, 3),
              p(2.5, 3),
              p(1.5, 3),
              p(1, 3.5),
              p(1, 4.5),
              p(1, 5.5),
              p(1.5, 6),
              p(2.5, 6),
              p(3.5, 6),
              p(4, 5.5)
        )

        val expectedHole1 = arrayOf(
                p(2.5, 5),
                p(2, 4.5),
                p(2.5, 4),
                p(3, 4.5),
                p(2.5, 5)
        )
        val expected2 = arrayOf(
                p(8, 5.5),
                p(8, 4.5),
                p(8, 3.5),
                p(7.5, 3),
                p(6.5, 3),
                p(5.5, 3),
                p(5, 3.5),
                p(5, 4.5),
                p(5, 5.5),
                p(5.5, 6),
                p(6.5, 6),
                p(7.5, 6),
                p(8, 5.5)
        )

        val expectedHole2 = arrayOf(
                p(6.5, 5),
                p(6, 4.5),
                p(6.5, 4),
                p(7, 4.5),
                p(6.5, 5)
        )

        val extern1 = contours[0].coordinates[0][0]
        val hole1 = contours[0].coordinates[0][1]
        val extern2 = contours[0].coordinates[1][0]
        val hole2 = contours[0].coordinates[1][1]

        checkValues(expected1, extern1)
        checkValues(expectedHole1, hole1)
        checkValues(expected2, extern2)
        checkValues(expectedHole2, hole2)
    }


    fun pt(x: Double, y: Double) = arrayOf(x, y)
    fun pt(x: Int, y: Int) = arrayOf(x.toDouble(), y.toDouble())


    private fun checkValues(expected: Array<Array<Double>>, result: Array<Array<Double>>) {
        val zip = expected.zip(result)
        zip.forEach {
            assertEquals(it.first[0], it.second[0], .000001)
            assertEquals(it.first[1], it.second[1], .000001)
        }
    }


    private fun values(vararg vals: Int) = vals
            .map { it.toDouble() }
            .toTypedArray()

    @Test
    fun `empty polygon`() {

    }


    private fun contour(size: Int) = contour {
        size(size, size)
        thresholds = { arrayOf(0.5) }
    }


    @Test
    fun `goldsteinPrice with 30000 values`(){


        fun goldsteinPrice(x : Double, y: Double): Double  =
                (1 + Math.pow(x + y + 1, 2.0) * (19 - 14 * x + 3 * x * x - 14 * y + 6 * x * x + 3 * y * y)) *
                        (30 + Math.pow(2 * x - 3 * y, 2.0) * (18 - 32 * x + 12 * x * x + 48 * y - 36 * x * y + 27 * y * y));


        val n = 240
        val m = 125
        var k = 0
        val values = arrayOfNulls<Double>(n*m)
        for (j in 0 until m)
            for (i in 0 until n){
                values[k++] = goldsteinPrice((i+ .5) / n * 4 - 2, 1 - (j+ .5) / m * 3)
            }


        val contours = contour {
            size(n, m)
            this.thresholds = {(1..21).map { Math.pow(2.0, it.toDouble()) }.toTypedArray()}
        }

        val ret = contours.contours(values as Array<Double>)

        println(ret)

    }

}
