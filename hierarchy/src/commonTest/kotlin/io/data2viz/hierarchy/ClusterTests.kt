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

package io.data2viz.hierarchy

import io.data2viz.test.TestBase
import kotlin.test.Test

class ClusterTests : TestBase() {

    // DO NOT CHANGE VALUES
    val width = 500.0
    val height = 400.0

    data class Hierarchical(
        val value: Int,
        val x: Double = .0,
        val y: Double = .0,
        val subElements: List<Hierarchical>? = null
    )

    val testTreeLight = Hierarchical(
        1, .0, .0, listOf(
            Hierarchical(11, .0, .0),
            Hierarchical(12, .0, .0)
        )
    )

    val testTreeMid =
        Hierarchical(
            1, 263.02083333333, .0, subElements = listOf(
                Hierarchical(
                    11, 119.79166666666667, 133.33333333333334, subElements = listOf(
                        Hierarchical(111, 41.666666666666664, 400.0),
                        Hierarchical(112, 83.33333333333333, 400.0),
                        Hierarchical(113, 125.0, 400.0),
                        Hierarchical(
                            114, 229.16666666666666, 266.6666666666667, subElements = listOf(
                                Hierarchical(1141, 208.33333333333334, 400.0),
                                Hierarchical(1142, 250.0, 400.0)
                            )
                        )
                    )
                ),
                Hierarchical(
                    12, 406.25, 133.33333333333334, subElements = listOf(
                        Hierarchical(
                            121, 354.1666666666667, 266.6666666666667, subElements = listOf(
                                Hierarchical(1211, 333.3333333333333, 400.0),
                                Hierarchical(1212, 375.0, 400.0)
                            )
                        ),
                        Hierarchical(122, 458.3333333333333, 400.0)
                    )
                )
            )
        )

    @Test
    fun buildCluster() {
        val hierarchy = hierarchy(testTreeMid, { it.subElements })
        val clusterLayout = ClusterLayout()
        clusterLayout.size(width, height)

        val cluster = clusterLayout.cluster(hierarchy)
        cluster.children.size shouldBe 2
        cluster.depth shouldBe 0
        cluster.height shouldBe 3
        cluster.each { clusterNode ->
            clusterNode.x shouldBeClose clusterNode.data.x
            clusterNode.y shouldBeClose clusterNode.data.y
        }
    }

    @Test
    fun buildClusterWithSizeNode() {
        // TODO
        true shouldBe true
    }
}
