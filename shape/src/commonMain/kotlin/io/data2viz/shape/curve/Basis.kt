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


public class Basis(override val path: Path): Curve {

    private var x0 = -1.0
    private var y0 = -1.0
    private var x1 = -1.0
    private var y1 = -1.0

    private var lineStatus = -1
    private var pointStatus = -1

    override fun areaStart() {
        lineStatus = 0
    }

    override fun areaEnd() {
        lineStatus = -1
    }

    override fun lineStart() {
        x0 = -1.0
        y0 = -1.0
        x1 = -1.0
        y1 = -1.0
        pointStatus = 0
    }

    override fun lineEnd() {
        if (pointStatus == 3) {
            curve(x1,y1)
            path.lineTo(x1, y1)
        }
        else if (pointStatus == 2) path.lineTo(x1, y1)

        if (lineStatus > 0 || pointStatus == 1) {
            path.closePath()
        }
        if (lineStatus > -1) lineStatus = 1 - lineStatus
    }

    private fun curve(x: Double, y: Double){
        path.bezierCurveTo(
                (2 * x0 + x1) / 3,
                (2 * y0 + y1) / 3,
                (x0 + 2 * x1) / 3,
                (y0 + 2 * y1) / 3,
                (x0 + 4 * x1 + x) / 6,
                (y0 + 4 * y1 + y) / 6
        )
    }

    override fun point(x: Double, y: Double) {
        if (pointStatus == 0) {
            pointStatus = 1
            if (lineStatus > 0) path.lineTo(x, y) else path.moveTo(x, y)
        } else if (pointStatus == 1) {
            pointStatus = 2
        } else if (pointStatus == 2) {
            pointStatus = 3
            path.lineTo((5 * x0 + x1) / 6, (5 * y0 + y1) / 6)
            curve(x, y)
        } else {
            curve(x, y)
        }
        x0 = x1
        x1 = x
        y0 = y1
        y1 = y
    }
}

