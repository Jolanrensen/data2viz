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

package io.data2viz.time

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.time.ExperimentalTime

class IntervalTests : TestDate() {

    @Test
    fun interval_floor_offset_returns_a_custom_time_interval() {
        val interval = Interval(
                fun (date:LocalDateTime): LocalDateTime =
                    LocalDateTime(date.year, date.monthNumber, date.dayOfMonth, date.hour, 0, 0, 0),
                fun (date:LocalDateTime, step:Int): LocalDateTime = date + (DateTimeUnit.HOUR * step).duration,
        )

        val date1 = interval.floor(LocalDateTime(2015, 1, 1, 12, 34, 56, 789))
        val date2 = LocalDateTime(2015, 1, 1, 12, 0, 0, 0)
        date1 shouldBe date2
    }

    /**
    tape("timeInterval(floor, offset) does not define a count method", function(test) {
    var i = time.timeInterval(function(date) {
    date.setUTCMinutes(0, 0, 0);
    }, function(date, step) {
    date.setUTCHours(date.getUTCHours() + step);
    });
    test.ok(!("count" in i));
    test.end();
    });

    tape("timeInterval(floor, offset) floors the step before passing it to offset", function(test) {
    var steps = [], i = time.timeInterval(function(date) {
    date.setUTCMinutes(0, 0, 0);
    }, function(date, step) {
    steps.push(+step), date.setUTCHours(date.getUTCHours() + step);
    });
    test.deepEqual(i.offset(date.utc(2015, 0, 1, 12, 34, 56, 789), 1.5), date.utc(2015, 0, 1, 13, 34, 56, 789));
    test.deepEqual(i.range(date.utc(2015, 0, 1, 12), date.utc(2015, 0, 1, 15), 1.5), [date.utc(2015, 0, 1, 12), date.utc(2015, 0, 1, 13), date.utc(2015, 0, 1, 14)]);
    test.ok(steps.every(function(step) { return step === 1; }));
    test.end();
    });

    tape("timeInterval(floor, offset, count) defines a count method", function(test) {
    var i = time.timeInterval(function(date) {
    date.setUTCMinutes(0, 0, 0);
    }, function(date, step) {
    date.setUTCHours(date.getUTCHours() + step);
    }, function(start, end) {
    return (end - start) / 36e5;
    });
    test.equal(i.count(date.utc(2015, 0, 1, 12, 34), date.utc(2015, 0, 1, 15, 56)), 3);
    test.end();
    });

    tape("timeInterval(floor, offset, count) floors dates before passing them to count", function(test) {
    var dates = [], i = time.timeInterval(function(date) {
    date.setUTCMinutes(0, 0, 0);
    }, function(date, step) {
    date.setUTCHours(date.getUTCHours() + step);
    }, function(start, end) {
    return dates.push(new LocalDateTime(+start), new LocalDateTime(+end)), (end - start) / 36e5;
    });
    i.count(date.utc(2015, 0, 1, 12, 34), date.utc(2015, 0, 1, 15, 56));
    test.deepEqual(dates, [date.utc(2015, 0, 1, 12), date.utc(2015, 0, 1, 15)]);
    test.end();
    });

    tape("timeInterval.every(step) returns null if step is invalid", function(test) {
    test.equal(time.timeDay.every(), null);
    test.equal(time.timeMinute.every(null), null);
    test.equal(time.timeSecond.every(undefined), null);
    test.equal(time.timeDay.every(NaN), null);
    test.equal(time.timeMinute.every(0), null);
    test.equal(time.timeSecond.every(0.8), null);
    test.equal(time.timeHour.every(-1), null);
    test.end();
    });

    tape("timeInterval.every(step) returns interval if step is one", function(test) {
    test.equal(time.timeDay.every("1"), time.timeDay);
    test.equal(time.timeMinute.every(1), time.timeMinute);
    test.equal(time.timeSecond.every(1.8), time.timeSecond);
    test.end();
    });

    tape("timeInterval.every(step).range(invalid, invalid) returns the empty array", function(test) {
    test.deepEqual(time.timeMinute.every(15).range(NaN, NaN), []);
    test.end();
    });

     */

}