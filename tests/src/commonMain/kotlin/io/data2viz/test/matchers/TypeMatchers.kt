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

package io.data2viz.test.matchers

import kotlin.reflect.KClass

public interface TypeMatchers {

    public infix fun BeWrapper<*>.a(expected: KClass<*>): Unit = an(expected)

    public infix fun BeWrapper<*>.an(expected: KClass<*>): Unit {
        if (!(expected.isInstance(value)))
            throw AssertionError("$value is not of type $expected")
    }

    public infix fun <T> BeWrapper<T>.theSameInstanceAs(ref: T): Unit {
        if (value !== ref)
            throw AssertionError("$value is not the same reference as $ref")
    }

    public infix fun <T> beTheSameInstanceAs(ref: T): Matcher<T> = object : Matcher<T> {
        override fun test(value: T) {
            if (value !== ref)
                throw AssertionError("$value is not the same reference as $ref")
        }
    }
}
