# OSGL Unit Test

[![APL v2](https://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) 
[![Maven Central](https://img.shields.io/maven-central/v/org.osgl/osgl-ut.svg)](http://search.maven.org/#search%7Cga%7C1%7Cosgl-ut)
[![Build Status](https://travis-ci.org/osglworks/java-unit.svg?branch=master)](https://travis-ci.org/osglworks/java-unit)

The project provides supporting Java unit tests:

* The `org.osgl.ut.TestBase` class is provided to be extended by user's test class. The class itself extends `org.junit.Assert` with simplified assertion methods

* The project has added the full [hamcrest-2.0.0.0](http://hamcrest.org/) library into the dependency, which provides more [matchers](http://hamcrest.org/JavaHamcrest/javadoc/2.0.0.0/org/hamcrest/Matcher.html) for use

## Installation

Add the following dependency into your `pom.xml` file:

```xml
<dependency>
    <groupId>org.osgl</groupId>
    <artifactId>osgl-ut</artifactId>
    <version>${osgl-ut-version}</version>
</dependency>
```

## `TestBase` vs `Assert`

`org.junit.Assert` provides a lot of assertion method to support writting declarative unit tests. `org.osgl.ut.TestBase` inherited from `Assert` and provides simplified names for those assertion methods. More than that, `TestBase` has moved the `String message` parameter from the first position to the end of the parameter list for all APIs that has `message` parameter, and appended with `Object... messageArgs` parameter, to make developer easier to write formatted message, for example

```java
// Assert style
assertNotNull(String.format("some message with %s and %s", arg1, arg2), something);

// TestBase style
notNull(someting, "some message with %s and %s", arg1, arg2);
```  

List of `Assert` API and their `TestBase` counter part 

| `org.junit.Assert` | `org.osgl.ut.TestBase` |
| ------------------ | ----------------------|
| `assertTrue(condition)` | `yes(condition)` |
| `assertTrue(message, condition)` | `yes(condition, message, messageArgs)`|
| `assertFalse(condition)` | `no(condition)` |
| `assertFalse(message, condition)` | `no(condition, message, messageArgs)` |
| `fail(message)` | `fail(message, messageArgs)` |
| `assertEquals(expected, actual)` | `eq(expected, actual)` |
| `assertEquals(message, expected, actual)` | `eq(expected, actual, message, messageArgs)` |
| `assertArrayEquals(expecteds, actuals)` | `eq(expecteds, actuals)` |
| `assertArrayEquals(message, expecteds, actuals)` | `eq(expecteds, actuals, message, messageArgs)` |
| `assertEquals(expected, actual, delta)` | `eq(expected, actual, delta)` |
| `assertEquals(message, expected, actual, delta)` | `eq(expected, actual, delta, message, messageArgs)` |
| `assertNotNull(object)` | `notNull(object)` |
| `assertNotNull(message, object)` | `notNull(object, message, messageArgs)` |
| `assertNull(object)` | `beNull(object)` |
| `assertNull(message, object)` | `beNull(object, message, messageArgs)` |
| `assertSame(expected, actual)` | `same(expected, actual)` |
| `assertSame(message, expected, actual)` | `same(expected, actual, message, messageArgs)` |
| `assertNotSame(unexpected, actual)` | `notSame(unexpected, actual)` |
| `assertNotSame(message, unexpected, actual)` | `notSame(unexpected, actual, message, messageArgs)` |
| `assertThat(actual, matcher)` | `yes(actual, matcher)` |
| `assertThat(message, actual, matcher)` | `yes(actual, matcher, message, messageArgs)` |
| N/A | `no(actual, matcher)` |
| N/A | `no(actual, matcher, message, messageArgs)` |

## Sample code

```java
public class MyClass {
    public String foo() {
        return "foo";
    }
    
    public char[] fooChars() {
        return "foo".toCharArray();
    }
    
    public String fooish(String anyString) {
        return "foo" + anyString;
    }
}
```

```java
import static org.hamcrest.Matchers.*;
public class MyClassTest {
    
    protected MyClass target;
    
    public void prepareTestingTarget() {
        target = new MyClass();
    }
    
    @Test
    public void fooShallNotReturnNull() {
        notNull(target.foo());
    }
    
    @Test
    public void fooShallReturnFoo() {
        eq("foo", target.foo(), "%s.foo() must return \"foo\"", target.getClass());
    }
    
    @Test
    public void fooCharsShallReturnFooCharArray() {
        eq("foo".toCharArray(), target.fooChars());
    }
    
    @Test
    public void fooishShallDecorateWithFooPrefix() {
        yes(target.fooish("Hi"), startsWith("foo"));
    }
    
    @Test
    public void fooishIsNotBarish() {
        no(target.fooish("Hi"), startsWith("bar"));
    }
    
}
```
