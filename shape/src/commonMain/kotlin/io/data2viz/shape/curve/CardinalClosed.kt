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

package io.data2viz.shape.curve

import io.data2viz.geom.Path
import io.data2viz.shape.Curve

public class CardinalClosed(override val path: Path, tension: Double = 0.0) : Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0
    private var x2 = -1.0
    private var y2 = -1.0
    private var x3 = -1.0
    private var y3 = -1.0
    private var x4 = -1.0
    private var y4 = -1.0
    private var x5 = -1.0
    private var y5 = -1.0

    private var lineStatus = -1
    private var pointStatus = -1

    private val k = (1.0 - tension) / 6.0

    override fun areaStart() {}

    override fun areaEnd() {}

    override fun lineStart() {
        x0 = -1.0
        y0 = -1.0
        x1 = -1.0
        y1 = -1.0
        x2 = -1.0
        y2 = -1.0
        x3 = -1.0
        y3 = -1.0
        x4 = -1.0
        y4 = -1.0
        x5 = -1.0
        y5 = -1.0
        pointStatus = 0
    }

    override fun lineEnd() {
        when (pointStatus) {
            1 -> {
                path.moveTo(x3, y3)
                path.closePath()
            }
            2 -> {
                path.lineTo(x3, y3)
                path.closePath()
            }
            3 -> {
                point(x3, y3)
                point(x4, y4)
                point(x5, y5)
            }
        }
        if (lineStatus > 0) path.closePath()
        lineStatus = 1 - lineStatus
    }

    // TODO : non specific, inherit from Cardinal
    private fun curve(x: Double, y: Double) {
        path.bezierCurveTo(
                x1 + k * (x2 - x0),
                y1 + k * (y2 - y0),
                x2 + k * (x1 - x),
                y2 + k * (y1 - y),
                x2,
                y2
        )
    }

    override fun point(x: Double, y: Double) {
        when (pointStatus) {
            0 -> {
                pointStatus = 1
                x3 = x
                y3 = y
            }
            1 -> {
                pointStatus = 2
                x4 = x
                y4 = y
                path.moveTo(x4, y4)
            }
            2 -> {
                pointStatus = 3
                x5 = x
                y5 = y
            }
            else -> curve(x, y)
        }
        x0 = x1
        x1 = x2
        x2 = x
        y0 = y1
        y1 = y2
        y2 = y
    }
}