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

package io.data2viz.geo.geojson

import io.data2viz.geo.geometry.polygonContains
import io.data2viz.geo.geojson.path.geoDistance
import io.data2viz.geo.stream.Stream
import io.data2viz.geojson.*
import io.data2viz.math.EPSILON
import io.data2viz.math.toRadians



public class Sphere : Geometry


/**
 *
 * For Point and MultiPoint geometries, an exact test is used; for a Sphere, true is always returned; for other geometries, an epsilon threshold is applied.
 * @param point must be specified as a two-element array [longitude, latitude] in degrees.
 * @return true if and only if the specified GeoJSON object contains the specified point, or false if the object does not contain the point.
 */
public fun GeoJsonObject.contains(point: Position): Boolean =
    when (this) {
        is Point                -> pos.contains(point)
        is MultiPoint           -> positions.any { it.contains(point) }
        is Polygon              -> lines.contains(point)
        is MultiPolygon         -> surface.any { it.contains(point) }
        is LineString           -> positions.contains(point)
        is MultiLineString      -> lines.any { it.contains(point) }
        is Sphere -> true
        is GeometryCollection   -> geometries.any { it.contains(point) }
        is FeatureCollection    -> features.any { it.contains(point) }
        is Feature              -> geometry.contains(point)
        else                    -> false
    }


private fun Lines.contains(point: Position): Boolean {
    val radiansCoordinates = map { it.map { toRadians(it) } }
    return polygonContains(radiansCoordinates, toRadians(point))
}

private fun Positions.contains(point: Position): Boolean {
    val ab = geoDistance(this[0], this[1])
    val ao = geoDistance(this[0], point)
    val ob = geoDistance(point, this[1])
    return ao + ob <= ab + EPSILON
}

private fun Position.contains(point: Position): Boolean = geoDistance(this, point) == .0


public val Point.pos: Position
    get() = coordinates

public val MultiPoint.positions: Positions
    get() = coordinates

public val LineString.positions: Positions
    get() = coordinates

public val Polygon.lines: Lines
    get() = coordinates

public val MultiLineString.lines: Lines
    get() = coordinates


public val MultiPolygon.surface: Surface
    get() = coordinates


internal val noop: () -> Unit = { }
internal val noop2: (Double, Double) -> Unit = { _, _ -> }
internal val noop3: (Double, Double, Double) -> Unit = { _, _, _ -> }

/**
 * Stream all children to [stream]
 */
public fun GeoJsonObject.stream(stream: Stream) {
    when (this) {
        is FeatureCollection    -> features.forEach { it.stream(stream) }
        is Feature              -> geometry.stream(stream)
        is GeometryCollection   -> geometries.forEach { streamGeometry(it, stream) }
        is Geometry             -> streamGeometry(this, stream)              // keep it last !
    }
}

private fun streamGeometry(geo: GeoJsonObject, stream: Stream) {
    when (geo) {
        is Point            -> streamPoint(geo.coordinates, stream)
        is LineString       -> streamLine(geo.coordinates, stream, false)
        is MultiPoint       -> geo.coordinates.forEach { streamPoint(it, stream) }
        is MultiPolygon     -> geo.coordinates.forEach { streamPolygon(it, stream) }
        is Polygon          -> streamPolygon(geo.coordinates, stream)
        is MultiLineString  -> geo.coordinates.forEach { streamLine(it, stream, false) }
        is Sphere -> streamSphere(stream)
    }
}

private fun streamSphere(stream: Stream) {
    stream.sphere()
}

private fun streamPoint(coordinates: Position, stream: Stream) {
    val z = coordinates.alt ?: .0
    stream.point(coordinates.lon, coordinates.lat, z)
}

private fun streamPolygon(coords: Lines, stream: Stream) {
    stream.polygonStart()
    coords.forEach {
        streamLine(it, stream, true)
    }
    stream.polygonEnd()
}

private fun streamLine(coords: Positions, stream: Stream, closed: Boolean) {
    val size = if (closed) coords.size - 1 else coords.size

    stream.lineStart()
    for (i in 0 until size) {
        val p = coords[i]
        stream.point(p[0], p[1], if (p.size > 2) p[2] else .0)
    }
    stream.lineEnd()
}

/**
 * Convert spherical [position] to cartesian doubleArray
 */
public fun toRadians(position: Position): DoubleArray {
    return position.map { it.toRadians() }.toDoubleArray()
}