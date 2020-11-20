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

package io.data2viz.geom


/**
 * A circle is defined by the position [x] and [y] of its center and
 * by its [radius]
 */
public interface Circle {

    /**
     * x position of the circle center
     */
    public var x: Double

    /**
     * x position of the circle center
     */
    public var y: Double

    /**
     * radius size of the circle
     */
    public var radius: Double

}
