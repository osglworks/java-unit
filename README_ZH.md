# OSGL Unit Test Tool

[![APL v2](https://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) 
[![Maven Central](https://img.shields.io/maven-central/v/org.osgl/osgl-ut.svg)](http://search.maven.org/#search%7Cga%7C1%7Cosgl-ut)
[![Build Status](https://travis-ci.org/osglworks/java-unit.svg?branch=master)](https://travis-ci.org/osglworks/java-unit)
[![codecov](https://codecov.io/gh/osglworks/java-unit/branch/master/graph/badge.svg)](https://codecov.io/gh/osglworks/java-unit)
[![Javadocs](http://www.javadoc.io/badge/org.osgl/osgl-ut.svg?color=red)](http://www.javadoc.io/doc/org.osgl/osgl-ut)

该项目为 Java 单元测试提供以下支持:

* 继承自 `org.junit.Assert` 的 `org.osgl.ut.TestBase` 类为用户测试类提供了简单便捷的断言方法

* 项目依赖中引入了完全的 [hamcrest-2.0.0.0](http://hamcrest.org/) 库, 提供了和 Junit 内置 hamcrest 库相比更加丰富的 [matchers](http://hamcrest.org/JavaHamcrest/javadoc/2.0.0.0/org/hamcrest/Matcher.html) 类型

* 项目依赖加入了 [mockito-2.9](http://site.mockito.org/) 库, 提供极好的 mocking 支持.

## 安装

将以下依赖加入到你项目的 `pom.xml` 文件中:

```xml
<dependency>
    <groupId>org.osgl</groupId>
    <artifactId>osgl-ut</artifactId>
    <version>${osgl-ut-version}</version>
</dependency>
```

## `TestBase` vs `Assert`

`org.junit.Assert` 提供了丰富的断言方法方便程序员创建声明型的单元测试. `org.osgl.ut.TestBase` 类从 `Assert` 类继承而来, 提供了简化命名的断言方法集. 另外对于带 `String message` 的断言方法, `TestBase` 将该参数从参数列表前面挪到了参数列表后面, 同时加上了 `Object... messageArgs` 参数, 方便开发者写需要格式化的断言消息, 例如:

```java
// Assert style
assertNotNull(String.format("some message with %s and %s", arg1, arg2), something);

// TestBase style
notNull(someting, "some message with %s and %s", arg1, arg2);
```  

`Assert` API 和 `TestBase` 相应 API 的对比 

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
| N/A | `isEmpty(String)` |
| N/A | `notEmpty(String)` |
| N/A | `isEmpty(Collection)` |
| N/A | `notEmpty(Collection)` |
| N/A | `isEmpty(Map)` |
| N/A | `notEmpty(Map)` |
| N/A | `isEmpty(Array)` |
| N/A | `notEmpty(Array)` |

## 代码示例

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
