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

package io.data2viz.shape.stack

import io.data2viz.shape.const

public data class StackSpace<T>(
        var from: Double,
        var to: Double,
        val paramIndex: Int,
        val data: T
)

public data class StackParam<T>(
        val stackedValues: MutableList<StackSpace<T>>,
//        val data: T,
        var index: Int
)

// TODO : use "serieIndex" and "dataIndex" to help understand the algorithm

public fun <T> stack(init: StackGenerator<T>.() -> Unit): StackGenerator<T> = StackGenerator<T>().apply(init)


public class StackGenerator<T> {

    public var series: (T) -> Array<Double> = const(arrayOf(.0))

    public var order: StackOrder = StackOrder.NONE

    public var offset: StackOffset = StackOffset.NONE

    public fun stack(data: List<T>): Array<StackParam<T>> {
        val ret = mutableListOf<StackParam<T>>()

        // BUILDING : build the StackParam and StackSpace that function will return

        // TODO : maybe it is better to set the number of stackes needed if there is not always all values for each series

        // fix #202 : take the series with most elements (so series did not need to have a value for each category)
        val totalSeriesNum: Int = data.map { series(it).size }.maxOrNull()!!
        (0 until totalSeriesNum).map { ret.add(StackParam(mutableListOf(), it)) }

        data.forEachIndexed { index1, element ->
            series(element).forEachIndexed { index2, serie ->
                val stack = ret[index2]
                stack.stackedValues.add(StackSpace(.0, serie, index1, data[index1]))
            }
        }

        // ORDERING : order series depending on its sum
        val indexes = order.sort(ret)

        indexes.forEachIndexed { realIndex, oldIndex ->
            ret[oldIndex].index = realIndex
        }

        // OFFSETTING : place values along the baseline and normalize them if needed
        offset.offset(ret)

        return ret.toTypedArray()
    }
}
