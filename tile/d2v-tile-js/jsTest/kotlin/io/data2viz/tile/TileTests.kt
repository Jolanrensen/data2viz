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

package io.data2viz.tile

import io.data2viz.geom.Point
import io.data2viz.test.namespace
import io.data2viz.math.Angle
import io.data2viz.math.PI_ANGLE
import io.data2viz.math.TAU_ANGLE
import io.data2viz.math.deg
import kotlin.browser.document
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.tan

//class TileTests {
//
//    data class GeoPoint(val latitude: Angle, val longitude: Angle)
//
//    val GENEVA = GeoPoint(latitude = 46.2.deg, longitude = 6.1667.deg)
//
//
//    /**
//     * http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
//     */
//    fun GeoPoint.long2tile(zoom: Int) = ((longitude + PI_ANGLE) / TAU_ANGLE) * 2.0.pow(zoom.toDouble())
//
//    fun GeoPoint.lat2tile(zoom: Int) = (1 - ln(tan(latitude.rad) + 1 / cos(latitude.rad)) / kotlin.math.PI) / 2 * 2.0.pow(zoom.toDouble())
//
//
//    fun shouldDisplayGeneva() {
//
//        //see http://bl.ocks.org/mbostock/94b9fd26e12c586f342d
//        val layout = tilesLayout {
//            width = 800.0
//            height = 200.0
//            tilesCount = 200_000.0
//        }
//        layout.translation = Point(
//                    (layout.tilesCount / 2) - GENEVA.long2tile(layout.zoom) * layout.tileSize,
//                    (layout.tilesCount / 2) - GENEVA.lat2tile(layout.zoom) * layout.tileSize)
//        drawmap(layout)
//    }
//}
//
//private fun drawmap(layout: TilesLayout) {
//
//
//    document.body?.appendChild(
//            document.createElementNS(namespace.svg, "svg").apply {
//                setAttribute("width", "${layout.width}")
//                setAttribute("height", "${layout.height}")
//                layout.tiles().forEach { tile ->
//                    appendChild(
//                            document.createElementNS(namespace.svg, "image").apply {
//                                setAttribute("href", "http://a.tile.openstreetmap.org/${tile.zoom}/${tile.tileX}/${tile.tileY}.png")
//                                setAttribute("x", "${tile.x}")
//                                setAttribute("y", "${tile.y}")
//                                setAttribute("width", "${layout.tileSize}")
//                                setAttribute("height", "${layout.tileSize}")
//                            }
//                    )
//                }
//            }
//    )
//}
