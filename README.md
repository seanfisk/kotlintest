# kotlintest

[![Build Status](https://travis-ci.org/kotlintest/kotlintest.svg?branch=master)](https://travis-ci.org/kotlintest/kotlintest) [<img src="https://img.shields.io/maven-central/v/io.kotlintest/kotlintest*.svg?label=latest%20release"/>](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22kotlintest)

Kotlin Test is a flexible and comprehensive testing tool for the [Kotlin](https://kotlinlang.org/) ecosystem based on and heavily inspired by the superb [Scalatest](http://www.scalatest.org/). KTest offers KTest offers several styles of test layout so that your team can pick the style they are most happy with.

### Testing Styles

You can choose a testing style by extending FlatSpec, WordSpec, FunSpec or FreeSpec in your test class, and writing your tests inside an init {} block. _In ScalaTest, the body of the class is the constructor, so you write tests directly in the class body. The KotlinTest equivalent is the init block._

```kotlin
class MyTests : WordSpec {
  init {
    // tests here
  }
}
```

#### Flat Spec

Word spec offers the keywords `should`, and `with`, and allows those to be used inline, as such:

```kotlin
"ListStack.pop" should "return the last element from stack" with {
  val stack = ListStack<String>()
  stack.push("hello")
  stack.push("world")
  stack.pop() shouldBe "world"
}
"ListStack.pop" should "remove the element from the stack" with {
  val stack = ListStack<String>()
  stack.push("hello")
  stack.push("world")
  stack.pop()
  stack.size() shouldBe 1
}
```

#### Fun Spec

Fun spec allows you to create tests similar to the junit style. You invoke a method called test, with a parameter to describe the test, and then the test itself:

```kotlin
test("ListStack.pop should remove the last element from stack") {
  val stack = ListStack<String>()
  stack.push("hello")
  stack.push("world")
  stack.size() shouldBe 2
  stack.pop() shouldBe "world"
  stack.size() shouldBe 1
}

test("ListStack.peek should leave the stack unmodified") {
  val stack = ListStack<String>()
  stack.push("hello")
  stack.push("world")
  stack.size() shouldBe 2
  stack.peek() shouldBe "world"
  stack.size() shouldBe 2
}
```

#### Word Spec

Word spec offers the keywords `should`, and `with`, and allows those to be nested, as such:

```kotlin
"ListStack.pop" should {
  "return the last element from stack" with {
    val stack = ListStack<String>()
    stack.push("hello")
    stack.push("world")
    stack.pop() shouldBe "world"
  }
  "remove the element from the stack" with {
    val stack = ListStack<String>()
    stack.push("hello")
    stack.push("world")
    stack.pop()
    stack.size() shouldBe 1
  }
}
```

#### Flat Spec

Flat spec allows you to nest arbitary levels of depth using the keywords `-` (minus) and `with`, as such:

```kotlin
"given a ListStack" - {
   "then pop" - {
     "should return the last element from stack" with {
        val stack = ListStack<String>()
        stack.push("hello")
        stack.push("world")
        stack.pop() shouldBe "world"
      }
      "should remove the element from the stack" with {
        val stack = ListStack<String>()
        stack.push("hello")
        stack.push("world")
        stack.pop()
        stack.size() shouldBe 1
      }
    }
  }
}
```

### How to use

KotlinTest is published to maven central, so to use, simply add the dependency in test scope to your gradle build:

`testCompile 'io.kotlintest:kotlintest:xxx'`