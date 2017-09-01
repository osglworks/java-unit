package org.osgl.ut;

/*-
 * #%L
 * Java Unit Test Tool
 * %%
 * Copyright (C) 2017 OSGL (Open Source General Library)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static org.hamcrest.Matchers.not;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.internal.ArrayComparisonFailure;
import org.junit.internal.ExactComparisonCriteria;
import org.junit.internal.InexactComparisonCriteria;

/**
 * The `TestBase` provides simplified assertion methods.
 */
public abstract class TestBase extends Assert {

    /**
     * Asserts that a condition is `true`. If it isn't then throws an
     * {@link AssertionError} with the given message.
     *
     * @param condition
     *              condition to be checked
     * @param message
     *              The error message. `null` Okay
     * @param messageArgs
     *              the error message arguments
     */
    public static void yes(boolean condition, String message, Object ... messageArgs) {
        assertTrue(fmt(message, messageArgs), condition);
    }

    /**
     * Alias of {@link #assertTrue(boolean)}.
     *
     * @param condition condition to be checked
     */
    public static void yes(boolean condition) {
        assertTrue(condition);
    }

    /**
     * Alias of {@link #assertThat(Object, Matcher)}.
     *
     * @param actual
     *              the computed value being compared
     * @param matcher
     *              an expression, built of {@link Matcher}s, specifying allowed values
     * @param <T>
     *              the static type accepted by the matcher (this can flag obvious
     *              compile-time problems such as `yes(1, is("a"))`
     *
     * @see org.hamcrest.CoreMatchers
     * @see org.junit.matchers.JUnitMatchers
     */
    public static <T> void yes(T actual, Matcher<T> matcher) {
        assertThat(actual, matcher);
    }

    /**
     * Require `actual` satisfied the condition specified by `matcher`. If not
     * an {@link AssertionError} is thrown with the reason string and information
     * about the matcher and failing value. Example:
     *
     * ```
     * int n = 0;
     * yes(n, is(not(1))) // passes
     * yes(n, is(1), "Help! Integers don't work"); // fails:
     * // failure message:
     * // Help! Integers don't work
     * // expected: is <1>
     * // got value: <0>
     * ```
     * @param actual the computed value being compared
     * @param matcher an expression, built of {@link Matcher}s, specifying allowed values
     * @param message
     *              additional information about the error
     * @param messageArgs
     *              message arguments
     * @param <T>
     *              the static type accepted by the matcher (this can flag obvious
     *              compile-time problems such as `yes(1, is("a"))`
     *
     * @see org.hamcrest.CoreMatchers
     * @see org.junit.matchers.JUnitMatchers
     */
    public static <T> void yes(T actual, Matcher<T> matcher, String message, Object... messageArgs) {
        if (!matcher.matches(actual)) {
            assertThat(fmt(message, messageArgs), actual, matcher);
        }
    }

    /**
     * Alias of {@link #assertFalse(boolean)}.
     *
     * @param condition condition to be checked
     */
    public static void no(boolean condition) {
        assertFalse(condition);
    }

    /**
     * Asserts that a condition is `false`. If it isn't then throws an
     * {@link AssertionError} with the given message.
     *
     * @param condition
     *              condition to be checked
     * @param message
     *              The error message. `null` Okay
     * @param messageArgs
     *              the error message arguments
     */
    public static void no(boolean condition, String message, Object... messageArgs) {
        assertTrue(String.format(message, messageArgs), !condition);
    }

    /**
     * Require `actual` **NOT** satisfied the condition specified by `matcher`. Otherwise
     * an {@link AssertionError} is thrown with the reason string and information
     * about the matcher and failing value. Example:
     *
     * ```
     * int n = 0;
     * no(n, is(1)) // passes
     * no(n, is(0)); // fails:
     * // failure message:
     * // expected: not is <0>
     * // got value: <0>
     * ```
     *
     * @param actual
     *              the computed value being compared
     * @param matcher
     *              an expression, built of {@link Matcher}s, specifying disallowed values
     * @param <T>
     *              the static type accepted by the matcher (this can flag obvious
     *              compile-time problems such as `no(1, is("a"))`
     *
     * @see org.hamcrest.CoreMatchers
     * @see org.junit.matchers.JUnitMatchers
     */
    public static <T> void no(T actual, Matcher<T> matcher) {
        assertThat(actual, not(matcher));
    }

    /**
     * Require `actual` **NOT** satisfied the condition specified by `matcher`. Otherwise
     * an {@link AssertionError} is thrown with the reason string and information
     * about the matcher and failing value. Example:
     *
     * ```
     * int n = 0;
     * no(n, is(1)) // passes
     * no(n, is(0), "Help! Integers don't work"); // fails:
     * // failure message:
     * // Help! Integers don't work
     * // expected: not is <0>
     * // got value: <0>
     * ```
     * @param actual the computed value being compared
     * @param matcher an expression, built of {@link Matcher}s, specifying disallowed values
     * @param message
     *              additional information about the error
     * @param messageArgs
     *              message arguments
     * @param <T>
     *              the static type accepted by the matcher (this can flag obvious
     *              compile-time problems such as `no(1, is("a"))`
     *
     * @see org.hamcrest.CoreMatchers
     * @see org.junit.matchers.JUnitMatchers
     */
    public static <T> void no(T actual, Matcher<T> matcher, String message, Object... messageArgs) {
        if (matcher.matches(actual)) {
            assertThat(fmt(message, messageArgs), actual, not(matcher));
        }
    }

    /**
     * Fails a test with the given message.
     *
     * @param message
     *              the failure message
     * @param args
     *              the failure message arguments
     * @see AssertionError
     */
    public static void fail(String message, Object ... args) {
        fail(fmt(message, args));
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message. If
     * `expected` and `actual` are `null`, they are considered equal.
     *
     * @param expected
     *              expected value, could be any object including array
     * @param actual
     *              actual value
     * @param message
     *              the failure message. `null` Okay
     * @param messageArgs
     *              the failure message arguments
     */
    public static void eq(Object expected, Object actual, String message, Object ... messageArgs) {
        if (null == expected) {
            beNull(actual, message, messageArgs);
            return;
        }
        Class<?> expectedClass = expected.getClass();
        String userMessage = fmt(message, messageArgs);
        if (expectedClass.isArray()) {
            Class<?> actualClass = actual.getClass();
            if (!expectedClass.equals(actualClass)) {
                if (null == message || "".equals(message.trim())) {
                    fail("arrays type differed\nExpected: %s\nActual  : %s",
                            expectedClass.getName(), actualClass.getName());
                } else {
                    fail("%s: arrays type differed\nExpected: %s\nActual  : %s",
                            userMessage, expectedClass.getName(), actualClass.getName());
                }
            }
            new ExactComparisonCriteria().arrayEquals(fmt(message, messageArgs), expected, actual);
        } else {
            assertEquals(fmt(message, messageArgs), expected, actual);
        }
    }

    /**
     * Alias of {@link #assertEquals(Object, Object)}.
     *
     * @param expected
     *              expected value
     * @param actual
     *              the value to check against `expected`
     */
    public static void eq(Object expected, Object actual) {
        if (null == expected) {
            beNull(actual);
            return;
        }
        Class<?> expectedClass = expected.getClass();
        if (expectedClass.isArray()) {
            Class<?> actualClass = actual.getClass();
            if (!expectedClass.equals(actualClass)) {
                fail("arrays type differed\nExpected: %s\nActual  : %s", expectedClass.getName(), actualClass.getName());
            }
            new ExactComparisonCriteria().arrayEquals(null, expected, actual);
        } else {
            assertEquals(null, expected, actual);
        }
    }

    /**
     * Asserts that two double arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param expecteds
     *              double array with expected values.
     * @param actuals
     *              double array with actual values
     * @param delta
     *              the maximum delta between `expected` and `actual`
     *              for which both numbers are still considered equal.
     * @param message
     *              the identifying message for the {@link AssertionError} (`null` okay)
     * @param messageArgs
     *              the failure message arguments
     */
    public static void eq(double[] expecteds, double[] actuals, double delta,
                          String message, Object ... messageArgs) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(fmt(message, messageArgs), expecteds, actuals);
    }

    /**
     * Alias of {@link #assertArrayEquals(double[], double[], double)}.
     *
     * @param expecteds
     *              double array with expected values.
     * @param actuals
     *              double array with actual values
     * @param delta
     *              the maximum delta between `expected` and `actual`
     *              for which both numbers are still considered equal.
     */
    public static void eq(double[] expecteds, double[] actuals, double delta) throws ArrayComparisonFailure {
        assertArrayEquals(expecteds, actuals, delta);
    }

    /**
     * Asserts that two float arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param expecteds
     *              double array with expected values.
     * @param actuals
     *              double array with actual values
     * @param delta
     *              the maximum delta between `expected` and `actual`
     *              for which both numbers are still considered equal.
     * @param message
     *              the failure message for the {@link AssertionError} (`null` okay)
     * @param messageArgs
     *              the failure message arguments
     */
    public static void eq(float[] expecteds, float[] actuals, float delta,
                          String message, Object ... messageArgs) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(fmt(message, messageArgs), expecteds, actuals);
    }


    /**
     * Asserts that two float arrays are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param expecteds
     *              double array with expected values.
     * @param actuals
     *              double array with actual values
     * @param delta
     *              the maximum delta between `expected` and `actual`
     *              for which both numbers are still considered equal.
     */
    public static void eq(float[] expecteds, float[] actuals, float delta) throws ArrayComparisonFailure {
        assertArrayEquals(expecteds, actuals, delta);
    }

    /**
     * Asserts that two doubles or floats are equal to within a positive delta.
     * If they are not, an {@link AssertionError} is thrown with the given
     * message. If the expected value is infinity then the delta value is
     * ignored. NaNs are considered equal:
     *
     * `assertEquals(Double.NaN, Double.NaN, *)` passes
     *
     * @param expected
     *              expected value
     * @param actual
     *              the value to check against `expected`
     * @param delta
     *              the maximum delta between `expected` and
     *              `actual` for which both numbers are still
     *              considered equal.
     * @param message the failure message for the {@link AssertionError} (`null` okay)
     * @param messageArgs the failure message arguments
     */
    public static void eq(double expected, double actual, double delta, String message, Object... messageArgs) {
        assertEquals(fmt(message, messageArgs), expected, actual, delta);
    }

    /**
     * Alias of {@link #assertEquals(double, double, double)}.
     *
     * @param expected
     *              expected value
     * @param actual
     *              the value to check against `expected`
     * @param delta
     *              the maximum delta between `expected` and
     *              `actual` for which both numbers are still
     *              considered equal.
     */
    public static void eq(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is
     * thrown with the given message.
     *
     * @param object
     *            Object to check or `null`
     * @param message
     *            the identifying message for the {@link AssertionError} (`null` okay)
     * @param messageArgs
     *            the message arguments
     */
    public static void notNull(Object object, String message, Object... messageArgs) {
        yes(object != null, message, messageArgs);
    }

    /**
     * Alias of {@link #assertNotNull(Object)}.
     *
     * @param object Object to check or `null`
     */
    public static void notNull(Object object) {
        assertNotNull(null, object);
    }

    /**
     * Asserts that an object is null. If it is not, an {@link AssertionError}
     * is thrown with the given message.
     *
     * @param object
     *              Object to check or `null`
     * @param message
     *              Failure message
     * @param messageArgs
     *              Failure message arguments
     */
    public static void beNull(Object object, String message, Object... messageArgs) {
        assertTrue(fmt(message, messageArgs), object == null);
    }

    /**
     * Alias of {@link #assertNull(Object)}.
     */
    public static void beNull(Object object) {
        assertNull(null, object);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param expected
     *              the expected object
     * @param actual
     *              the object to compare to `expected`
     * @param message
     *              Failure message
     * @param messageArgs
     *              the failure message arguments
     */
    public static void same(Object expected, Object actual, String message, Object... messageArgs) {
        assertSame(fmt(message, messageArgs) + ",", expected, actual);
    }

    /**
     * Alias of {@link #assertSame(Object, Object)}.
     *
     * @param expected
     *              the expected object
     * @param actual
     *              the object to compare to `expected`
     */
    public static void same(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do
     * refer to the same object, an {@link AssertionError} is thrown with the
     * given message.
     *
     * @param unexpected
     *              the object you don't expect
     * @param actual
     *              the object to compare to `unexpected`
     * @param message
     *              the failure message
     * @param messageArgs
     *              the failure message arguments
     */
    public static void notSame(Object unexpected, Object actual, String message, Object... messageArgs) {
        assertNotSame(fmt(message, messageArgs) + ",", unexpected, actual);
    }

    /**
     * Alias of {@link #assertNotSame(Object, Object)}.
     *
     * @param unexpected
     *              the object you don't expect
     * @param actual
     *              the object to compare to `unexpected`
     */
    public static void notSame(Object unexpected, Object actual) {
        assertNotSame(null, unexpected, actual);
    }

    /**
     * Format a message with message arguments using {@link String#format(String, Object...)}.
     * @param message
     *      the message, `null` okay
     * @param messageArgs
     *      the message arguments
     * @return
     *      empty string `""` if `messsage` is `null` or
     *      result of {@link String#format(String, Object...)}
     */
    private static String fmt(String message, Object... messageArgs) {
        if (null == message) {
            return "";
        }
        return String.format(message, messageArgs);
    }

}
