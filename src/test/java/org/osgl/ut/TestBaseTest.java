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

import static org.hamcrest.Matchers.*;

import org.junit.Test;

import java.util.*;

/**
 * Test {@link TestBase}
 */
public class TestBaseTest extends TestBase {

    @Test
    public void yesShallNotComplainWhenConditionIsTrue() {
        yes(true, "any message");
        yes(true);
    }

    @Test(expected = AssertionError.class)
    public void yesShallShoutWhenConditionIsFalse() {
        yes(false);
    }

    @Test
    public void yesShallShoutWithMessageSpecifiedWhenConditionIsFalse() {
        try {
            yes(false, "Hello %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("Hello junit", error.getMessage());
        }
    }

    @Test
    public void noShallNotComplainWhenConditionIsFalse() {
        no(false, "any message");
        no(false);
    }

    @Test(expected = AssertionError.class)
    public void noShallShoutWhenConditionIsTrue() {
        no(true);
    }

    @Test
    public void noShallShoutWithMessageSpecifiedWhenConditionIsTrue() {
        try {
            no(true, "Hello %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("Hello junit", error.getMessage());
        }
    }

    @Test
    public void failWithMessageAndMessageArgumentsShallShoutWithFormattedMessage() {
        try {
            fail("Hello %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("Hello junit", error.getMessage());
        }
    }

    @Test
    public void eqWithTwoNullValuesShallPass() {
        eq(null, null);
        eq(null, null, "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void neWithTwoNullValuesShallShout() {
        ne(null, null);
    }

    @Test
    public void neWithTwoNullValuesShallShoutWithMessage() {
        try {
            ne(null, null, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test(expected = AssertionError.class)
    public void eqWithOnNullExpectedShallShout() {
        eq("Hi", null);
    }

    @Test
    public void neWithOnNullExpectedShallPass() {
        ne("Hi", null);
        ne("Hi", null, "hi %s", "junit");
    }

    @Test
    public void eqWithOnNullExpectedShallShoutWithMessage() {
        try {
            eq("Hi", null, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test(expected = AssertionError.class)
    public void eqWithOnNullValueShallShout() {
        eq(null, "Hi");
    }

    @Test
    public void neWithOnNullValueShallPass() {
        ne(null, "Hi");
        ne(null, "Hi", "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void eqWithOnNullValueShallShoutWithMessage() {
        eq(null, "Hi", "hi %s", "junit");
    }

    @Test
    public void eqWithEqualObjectsShallPass() {
        eq("Hi", "Hi");
        eq("Hi", "Hi", "message");
        eq(1, 1);
        eq(1, 1, "message");
        Foo foo1 = new Foo("hi", 5);
        Foo foo2 = new Foo("hi", 5);
        eq(foo1, foo2);
        eq(foo1, foo2, "message", 1);
    }

    @Test(expected = AssertionError.class)
    public void neWithEqualObjectsShallShout() {
        Foo foo1 = new Foo("hi", 5);
        Foo foo2 = new Foo("hi", 5);
        ne(foo1, foo2);
    }

    @Test
    public void neWithEqualObjectsShallShoutWithMessage() {
        Foo foo1 = new Foo("hi", 5);
        Foo foo2 = new Foo("hi", 5);
        try {
            ne(foo1, foo2, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test(expected = AssertionError.class)
    public void eqWithNotEqualStringsShallShout() {
        eq("Hi", "hi");
    }

    @Test
    public void eqWithNotEqualStringsShallShoutWithMessage() {
        try {
            eq("Hi", "hi", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void neWithNotEqualStringsShallPass() {
        ne("Hi", "hi");
        ne("Hi", "hi", "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void eqWithNotEqualNumberShallShout() {
        eq(1, 2);
    }

    @Test(expected = AssertionError.class)
    public void eqWithNotEqualPojoShallShout() {
        eq(new Foo("hi", 5), new Foo("Hi", 5));
    }

    @Test
    public void eqWithNotEqualObjectWithFailureMessageSpecified() {
        try {
            eq("Hi", "hi", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void eqWithEqualArrayShallPass() {
        int[] a1 = {1, 2};
        int[] a2 = {1, 2};
        eq(a1, a2);
        eq(a1, a2, "message");
    }

    @Test(expected = AssertionError.class)
    public void neWithEqualArrayShallShout() {
        int[] a1 = {1, 2};
        int[] a2 = {1, 2};
        ne(a1, a2);
    }

    @Test
    public void neWithEqualArrayShallShoutWithMessage() {
        int[] a1 = {1, 2};
        int[] a2 = {1, 2};
        try {
            ne(a1, a2, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void eqWithArrayLengthDiff() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2};
        try {
            eq(a1, a2);
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "array lengths differed");
        }
    }

    @Test
    public void eqWithArrayLengthDiffWithMessage() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2};
        try {
            eq(a1, a2, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit: array lengths differed");
        }
    }

    @Test
    public void neWithArrayLengthDiffShallPass() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 2};
        ne(a1, a2);
        ne(a1, a2, "hi %s", "junit");
    }

    @Test
    public void eqWithArrayElementDiff() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 3, 2};
        try {
            eq(a1, a2);
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "arrays first differed at element");
        }
    }

    @Test
    public void eqWithArrayElementDiffWithMessage() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 3, 2};
        try {
            eq(a1, a2, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit: arrays first differed at element");
        }
    }

    @Test
    public void neWithArrayElementDiffShallPass() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {1, 3, 2};
        ne(a1, a2);
        ne(a1, a2, "hi %s", "junit");
    }

    @Test
    public void eqWithArrayTypeDiff() {
        int[] a1 = {1};
        short[] a2 = {1};
        try {
            eq(a1, a2);
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "arrays type differed");
        }
    }

    @Test
    public void eqWithArrayTypeDiffWithMessage() {
        int[] a1 = {1};
        short[] a2 = {1};
        try {
            eq(a1, a2, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit: arrays type differed");
        }
    }

    @Test
    public void neWithArrayTypeDiffShallPass() {
        int[] a1 = {1};
        short[] a2 = {1};
        ne(a1, a2);
        ne(a1, a2, "");
        ne(a1, a2, null);
        ne(a1, a2, "hi %s", "junit");
    }

    @Test
    public void eqWithArrayTypeDiffWithEmptyMessage() {
        eqWithArrayTypeDiffWithInvalidMessage("");
    }

    @Test
    public void eqWithArrayTypeDiffWithBlankMessage() {
        eqWithArrayTypeDiffWithInvalidMessage(" ");
    }

    @Test
    public void eqWithArrayTypeDiffWithNullMessage() {
        eqWithArrayTypeDiffWithInvalidMessage(null);
    }

    @Test(expected = AssertionError.class)
    public void eqArrayWithObjectShallShout() {
        Object expected = new Object();
        Object actual = new Object[0];
        eq(expected, actual);
    }

    @Test
    public void eqArrayWithObjectShallShoutWithMessage() {
        Object expected = new Object();
        Object actual = new Object[0];
        try {
            eq(expected, actual, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit expected:");
        }
    }

    @Test(expected = AssertionError.class)
    public void eqObjectWithArrayShallShout() {
        Object expected = new Object[0];
        Object actual = new Object();
        eq(expected, actual);
    }

    @Test
    public void eqObjectWithArrayShallShoutWithMessage() {
        Object expected = new Object[0];
        Object actual = new Object();
        try {
            eq(expected, actual, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit: arrays type differed");
        }
    }

    private void eqWithArrayTypeDiffWithInvalidMessage(String invalidMessage) {
        int[] a1 = {1};
        short[] a2 = {1};
        try {
            eq(a1, a2, invalidMessage);
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "arrays type differed");
        }
    }

    @Test
    public void eqWithEmptyArrayTypeDiff() {
        int[] a1 = {};
        String[] a2 = {};
        try {
            eq(a1, a2);
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "arrays type differed");
        }
    }

    @Test
    public void eqWithArrayTypeDiffWithMessageSpecified() {
        int[] a1 = {1};
        String[] a2 = {"1"};
        try {
            eq(a1, a2, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit: arrays type differed");
        }
    }

    @Test
    public void neWithEmptyArrayTypeDiffShallPass() {
        int[] a1 = {};
        String[] a2 = {};
        ne(a1, a2);
        ne(a1, a2, "hi %s", "junit");
    }

    @Test
    public void eqDoubleArraysWithDelta() {
        double[] a1 = {0.01d, 1.23037d};
        double[] a2 = {0.010001d, 1.23036d};
        eq(a1, a2, 0.0002d);
        eq(a1, a2, 0.0002d, "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void neDoubleArraysWithDelta() {
        double[] a1 = {0.01d, 1.23037d};
        double[] a2 = {0.010001d, 1.23036d};
        ne(a1, a2, 0.0002d);
    }

    @Test
    public void neDoubleArraysWithDeltaWithMessage() {
        double[] a1 = {0.01d, 1.23037d};
        double[] a2 = {0.010001d, 1.23036d};
        try {
            ne(a1, a2, 0.0002d, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test(expected = AssertionError.class)
    public void eqDoubleArraysWithDeltaNotMatch() {
        double[] a1 = {0.01d, 1.23037d};
        double[] a2 = {0.010001d, 1.23036d};
        eq(a1, a2, 0.0000002d);
    }

    @Test
    public void eqDoubleArraysWithDeltaNotMatchWithMessage() {
        double[] a1 = {0.01d, 1.23037d};
        double[] a2 = {0.010001d, 1.23036d};
        try {
            eq(a1, a2, 0.0000002d, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit: arrays first differed at element");
        }
    }

    @Test
    public void neDoubleArraysWithDeltaNotMatch() {
        double[] a1 = {0.01d, 1.23037d};
        double[] a2 = {0.010001d, 1.23036d};
        ne(a1, a2, 0.0000002d);
        ne(a1, a2, 0.0000002d, "hi %s", "junit");
    }

    @Test
    public void eqDoubleWithDelta() {
        eq(0.01d, 0.010001d, 0.0002d);
        eq(0.01d, 0.010001d, 0.0002d, "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void neDoubleWithDelta() {
        ne(0.01d, 0.010001d, 0.0002d);
    }

    @Test
    public void neDoubleWithDeltaWithMessage() {
        try {
            ne(0.01d, 0.010001d, 0.0002d, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test(expected = AssertionError.class)
    public void eqDoubleWithDeltaNotMatch() {
        eq(0.01d, 0.010001d, 0.0000002d);
    }

    @Test
    public void eqDoubleWithDeltaWithMessage() {
        try {
            eq(0.01d, 0.010001d, 0.0000002d, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void neDoubleWithDeltaNotMatch() {
        ne(0.01d, 0.010001d, 0.0000002d);
        ne(0.01d, 0.010001d, 0.0000002d, "hi %s", "junit");
    }

    @Test
    public void eqFloatArraysWithDelta() {
        float[] a1 = {0.01f, 1.23037f};
        float[] a2 = {0.010001f, 1.23036f};
        eq(a1, a2, 0.0002f);
    }

    @Test(expected = AssertionError.class)
    public void eqFloatArraysWithDeltaNotMatch() {
        float[] a1 = {0.01f, 1.23037f};
        float[] a2 = {0.010001f, 1.23036f};
        eq(a1, a2, 0.0000002f);
    }

    @Test
    public void eqFloatArraysWithDeltaNotMatchWithMessage() {
        float[] a1 = {0.01f, 1.23037f};
        float[] a2 = {0.010001f, 1.23036f};
        try {
            eq(a1, a2, 0.0000002f, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit: arrays first differed at element");
        }
    }

    @Test(expected = AssertionError.class)
    public void neFloatArraysWithDelta() {
        double[] a1 = {0.1f, 1.23f};
        double[] a2 = {0.101f, 1.24f};
        ne(a1, a2, 0.1f);
    }

    @Test
    public void neFloatArraysWithDeltaWithMessage() {
        double[] a1 = {0.1f, 1.23f};
        double[] a2 = {0.101f, 1.24f};
        try {
            ne(a1, a2, 0.1f, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void notNullShallPassIfObjectIsNotNull() {
        notNull(new Object());
    }

    @Test
    public void notNullShallPassIfObjectIsNotNullWithMessage() {
        notNull(new Object(), "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void notNullShallShoutIfObjectIsNull() {
        notNull(null);
    }

    @Test
    public void notNullShallShoutIfObjectIsNullWtihMessage() {
        try {
            notNull(null, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test
    public void isNullShallPassIfObjectIsNull() {
        isNull(null);
    }

    @Test
    public void isNullShallPassIfObjectIsNullWithMessage() {
        isNull(null, "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void isNullShallShoutIfObjectIsNotNull() {
        isNull(new Object());
    }

    @Test
    public void isNullShallShoutIfObjectIsNotNullWtihMessage() {
        try {
            isNull(new Object(), "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test
    public void notEmptyShallPassIfStrIsNotEmpty() {
        notEmpty(" ");
        notEmpty("abcde");
    }

    @Test(expected = AssertionError.class)
    public void notEmptyShallShoutIfStrIsNull() {
        String s = null;
        notEmpty(s);
    }

    @Test
    public void notEmptyShallShoutIfStrIsNullWithMessage() {
        try {
            String s = null;
            notEmpty(s, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test(expected = AssertionError.class)
    public void notEmptyShallShoutIfStrIsEmpty() {
        notEmpty("");
    }

    @Test
    public void notEmptyShallShoutIfStrIsEmptyWithMessage() {
        try {
            notEmpty("", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test
    public void notEmptyShallPassIfCollectionIsNotEmpty() {
        Collection<String> col = new HashSet<String>();
        col.add("xyz");
        notEmpty(col);
        notEmpty(col, "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void notEmptyShallShoutIfCollectionisEmpty() {
        notEmpty(Collections.EMPTY_LIST);
    }

    @Test
    public void notEmptyShallShoutIfCollectionIsEmptyWithMessage() {
        try {
            notEmpty(Collections.EMPTY_SET, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void notEmptyShallFailIfCollectionIsNull() {
        Collection<?> col = null;
        notEmpty(col);
    }

    @Test
    public void notEmptyShallPassIfArrayIsNotEmpty() {
        String[] sa = {"hi"};
        notEmpty(sa);
        notEmpty(sa, "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void notEmptyShallShoutIfArrayIsEmpty() {
        Integer[] ia = new Integer[]{};
        notEmpty(ia);
    }

    @Test
    public void notEmptyShallShoutIfArrayIsEmptyWithMessage() {
        try {
            Integer[] ia = new Integer[]{};
            notEmpty(ia, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test(expected = NullPointerException.class)
    public void notEmptyShallFailIfArrayIsNull() {
        Boolean[] ba = null;
        notEmpty(ba);
    }

    @Test
    public void eqFloatWithDelta() {
        eq(0.1f, 0.102f, 0.01f);
        eq(0.1f, 0.102f, 0.01f, "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void neFloatWithDelta() {
        ne(0.1f, 0.102f, 0.01f);
    }

    @Test
    public void neFloatWithDeltaWithMessage() {
        try {
            ne(0.1f, 0.102f, 0.01f, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test(expected = AssertionError.class)
    public void eqFloatWithDeltaNotMatch() {
        eq(0.01f, 0.0102f, 0.0001f);
    }

    @Test
    public void eqFloatWithDeltaWithMessage() {
        try {
            eq(0.01f, 0.0102f, 0.0001f, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void neFloatWithDeltaNotMatch() {
        ne(0.01f, 0.0102f, 0.0001f);
        ne(0.01f, 0.0102f, 0.0001f, "hi %s", "junit");
    }

    @Test(expected = NullPointerException.class)
    public void notEmptyShallFailIfCollectionIsNullWithMessage() {
        Collection<?> col = null;
        notEmpty(col, "hi %s", "junit");
    }


    @Test
    public void isEmptyShallPassIfStrIsNullOrEmpty() {
        String s = null;
        isEmpty(s);
        isEmpty("");
    }

    @Test
    public void isEmptyShallPassIfStrIsNullOrEmptyWithMessage() {
        String s = null;
        isEmpty(s, "hi %s", "junit");
        isEmpty("", "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void isEmptyShallShoutIfStrIsNotEmpty() {
        isEmpty("ab");
    }

    @Test
    public void isEmptyShallShoutIfStrIsNotEmptyWithMessage() {
        try {
            isEmpty("ab", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test
    public void isEmptyShallPassIfCollectionIsEmpty() {
        isEmpty(Collections.EMPTY_SET);
        isEmpty(Collections.EMPTY_LIST);
        isEmpty(Collections.EMPTY_MAP);
        isEmpty(new HashSet<Integer>());
    }

    @Test(expected = NullPointerException.class)
    public void isEmptyShallErrorIfCollectionIsNull() {
        List<?> list = null;
        isEmpty(list);
    }

    @Test(expected = AssertionError.class)
    public void isEmptyShallShoutIfCollectionIsNotEmpty() {
        List<String> list = new ArrayList<String>();
        list.add("hi");
        isEmpty(list);
    }

    @Test
    public void isEmptyShallShoutIfCollectionIsNotEmptyWithMessage() {
        List<String> list = new ArrayList<String>();
        list.add("hi");
        try {
            isEmpty(list, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }


    @Test
    public void isEmptyShallPassIfArrayIsEmpty() {
        isEmpty(new String[0]);
        isEmpty(new TestBase[]{});
    }

    @Test(expected = NullPointerException.class)
    public void isEmptyShallErrorIfArrayIsNull() {
        Integer[] ia = null;
        isEmpty(ia);
    }

    @Test(expected = AssertionError.class)
    public void isEmptyShallShoutIfArrayIsNotEmpty() {
        isEmpty(new Integer[]{1});
    }

    @Test
    public void isEmptyShallShoutIfArrayIsNotEmptyWithMessage() {
        try {
            isEmpty(new Object[]{null}, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }


    @Test(expected = AssertionError.class)
    public void isEmptyShallShoutIfStrIsBlank() {
        isEmpty(" ");
    }

    @Test
    public void isEmptyShallShoutIfStrIsBlankWithMessage() {
        try {
            isEmpty("\t", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test
    public void notBlankShallPassIfStrIsNotBlank() {
        notBlank("abcde");
        notBlank(" x");
    }

    @Test(expected = AssertionError.class)
    public void notBlankShallShoutIfStrIsNull() {
        notBlank(null);
    }

    @Test
    public void notBlankShallShoutIfStrIsNullWithMessage() {
        try {
            notBlank(null, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test(expected = AssertionError.class)
    public void notBlankShallShoutIfStrIsEmpty() {
        notBlank("");
    }

    @Test
    public void notBlankShallShoutIfStrIsEmptyWithMessage() {
        try {
            notBlank("", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test(expected = AssertionError.class)
    public void notBlankShallShoutIfStrIsBlank() {
        notBlank(" \t \b");
    }

    @Test
    public void notBlankShallShoutIfStrIsBlankWithMessage() {
        try {
            notBlank(" ", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test
    public void isBlankShallPassIfStrIsNullOrEmptyOrBlank() {
        isBlank(null);
        isBlank("");
        isBlank(" ");
        isBlank("\t");
        isBlank("\b");
        isBlank("\u0000");
    }

    @Test
    public void isBlankShallPassIfStrIsNullOrEmptyOrBlankWithMessage() {
        isBlank(null, "hi %s", "junit");
        isBlank("", "hi %s", "junit");
        isBlank(" ", "hi %s", "junit");
        isBlank("\t", "hi %s", "junit");
        isBlank("\b", "hi %s", "junit");
        isBlank("\u0000", "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void isBlankShallShoutIfStrIsNotBlank() {
        isBlank("ab");
    }

    @Test
    public void isBlankShallShoutIfStrIsNotBlankWithMessage() {
        try {
            isBlank("ab", "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            assertEquals("hi junit", error.getMessage());
        }
    }

    @Test
    public void sameShallPassIfObjectsAreSame() {
        Foo foo = new Foo("foo", 3);
        same(foo, foo);
        same(1, 1);
        same("", "");
    }

    @Test
    public void sameShallPassIfObjectsAreSameWithMessage() {
        Foo foo = new Foo("foo", 3);
        same(foo, foo, "hi %s", "junit");
        same(1, 1, "hi %s", "junit");
        same("", "", "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void sameShallShoutIfObjectsAreNotSame() {
        Foo f1 = new Foo("foo", 3);
        Foo f2 = new Foo("foo", 3);
        same(f1, f2);
    }

    @Test
    public void sameShallShoutIfObjectsAreNotSameWithMessage() {
        Foo f1 = new Foo("foo", 3);
        Foo f2 = new Foo("foo", 3);
        try {
            same(f1, f2, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void notSameShallPassIfObjectsAreNotSame() {
        Foo foo = new Foo("foo", 3);
        notSame(foo, new Foo("foo", 3));
        notSame(1, 2);
        notSame("", " ");
    }

    @Test
    public void notSameShallPassIfObjectsAreNOtSameWithMessage() {
        Foo foo = new Foo("foo", 3);
        notSame(foo, new Foo("foo", 3), "hi %s", "junit");
        notSame(1, 2, "hi %s", "junit");
        notSame("", " ", "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void notSameShallShoutIfObjectsAreSame() {
        Foo f1 = new Foo("foo", 3);
        notSame(f1, f1);
    }

    @Test
    public void notSameShallShoutIfObjectsAreSameWithMessage() {
        Foo f1 = new Foo("foo", 3);
        try {
            notSame(f1, f1, "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void yesShallPassIfMatcherMatches() {
        yes(1, is(1));
        yes(1, instanceOf(Integer.class));
        yes("hi junit", startsWith("hi"));
    }

    @Test
    public void yesShallPassIfMatcherMatchesWithMessage() {
        yes(1, is(1), "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void yesShallShoutIfMatcherNotMatch() {
        yes(1, is(0));
    }

    @Test
    public void yesShallShoutIfMatcherNotMatchWithMessage() {
        try {
            yes(1, is(0), "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    @Test
    public void notShallPassIfMatcherNotMatches() {
        no(1, is(0));
        no(1, instanceOf(String.class));
        no("hi junit", startsWith("bye "));
    }

    @Test
    public void noShallPassIfMatcherNotMatchesWithMessage() {
        no("hi junit", startsWith("bye "), "hi %s", "junit");
    }

    @Test(expected = AssertionError.class)
    public void noShallShoutIfMatcherMatches() {
        no(1, is(1));
    }

    @Test
    public void noShallShoutIfMatcherMatchesWithMessage() {
        try {
            no(1, is(1), "hi %s", "junit");
            expectAssertionError();
        } catch (AssertionError error) {
            msgShallStartsWith(error, "hi junit");
        }
    }

    private void msgShallStartsWith(AssertionError error, String prefix) {
        assertThat(error.getMessage(), startsWith(prefix));
    }

    private static void expectAssertionError() {
        fail("It shall raise AssertionError here");
    }

}
