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

package io.data2viz.geo.projection

import io.data2viz.geo.geometry.clip.anglePreClip
import io.data2viz.geo.projection.common.*
import io.data2viz.math.EPSILON
import io.data2viz.math.deg
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin


public fun orthographicProjection(init: Projection.() -> Unit = {}): Projection =
    projection(OrthographicProjector()) {
        scale = 249.5
        anglePreClip = (90 + EPSILON).deg
        init()
    }


internal class OrthographicProjector : Projector {

    override fun project(lambda: Double, phi: Double) = doubleArrayOf(cos(phi) * sin(lambda), sin(phi))
    override fun invert(x: Double, y: Double) = azimuthalInvert(::asin)(x, y)

}
