/*
 * Copyright (c) 2018-2019. data2viz sàrl.
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

package io.data2viz.interpolate

import io.data2viz.math.Percent
import kotlin.math.floor

/**
 * http://alvyray.com/Memos/CG/Pixar/spline77.pdf
 */
public fun computeSpline(t1: Double, v0: Int, v1: Int, v2: Int, v3: Int): Double {
    val t2 = t1 * t1
    val t3 = t2 * t1
    return ((1 - 3 * t1 + 3 * t2 - t3) * v0
            + (4 - 6 * t2 + 3 * t3) * v1
            + (1 + 3 * t1 + 3 * t2 - 3 * t3) * v2
            + t3 * v3) / 6
}

/**
 * uniform nonrational B-spline interpolation
 */
public fun basis(values: List<Int>): Interpolator<Double> {
    val n = values.size - 1
    return fun(percent: Percent): Double {

        val currentIndex: Int = floor(percent * n).toInt().coerceIn(0 .. n-1)

        val v1 = values[currentIndex]
        val v2 = values[currentIndex + 1]
        val v0 = if (currentIndex > 0) values[currentIndex - 1] else 2 * v1 - v2
        val v3 = if (currentIndex < n - 1) values[currentIndex + 2] else 2 * v2 - v1

        return computeSpline((percent.coerceToDefault().value - currentIndex.toDouble() / n) * n, v0, v1, v2, v3)
    }
}

/**
 * uniform nonrational cyclical B-spline interpolation
 */
public fun basisClosed(values: List<Int>): Interpolator<Double> {
    val n = values.size
    return fun(percent: Percent): Double {

        val newT = if (percent.value < 0) percent.value % 1 else (percent.value % 1 + 1)
        val currentIndex = floor(newT * n).toInt()

        val v0 = values[(currentIndex + n - 1) % n]
        val v1 = values[currentIndex % n]
        val v2 = values[(currentIndex + 1) % n]
        val v3 = values[(currentIndex + 2) % n]

        return computeSpline((newT - currentIndex / n) * n, v0, v1, v2, v3)
    }
}