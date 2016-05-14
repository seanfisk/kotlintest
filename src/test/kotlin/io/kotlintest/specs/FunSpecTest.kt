package io.kotlintest.specs

import io.kotlintest.ListStack
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class FunSpecTest : FunSpec() {

  val invocationCounter = AtomicInteger(0)
  val theadCounter = AtomicInteger(0)
  val singleTagShouldHaveRun = AtomicBoolean(false)
  val multiTagShouldHaveRun = AtomicBoolean(false)

  init {

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

    test("support invocation parameter") {
      // this test should run 5 times
      invocationCounter.incrementAndGet()
    }.params(invocations = 5)

    test("support ignored") {
      System.exit(1)
    }.params(ignored = true)

    System.setProperty("testTags", "bibble,fibble,foo")

    test("support single tag") {
      singleTagShouldHaveRun.set(true)
    }.params(tag = "foo")

    test("support multiple tags") {
      multiTagShouldHaveRun.set(true)
    }.params(tags = listOf("foo", "boo"))

    // if we have 100 threads, and each one sleeps for 1000 seconds, then the total time should still be
    // approx 1000. So we set the timeout an order of magnitude higher, and it should never hit
    test("support threads parameter") {
      // this test should timeout
      Thread.sleep(1000)
      theadCounter.incrementAndGet()
    }.params(timeout = 10000, threads = 100, invocations = 100)
  }

  override fun afterAll(): Unit {
    if (invocationCounter.get() != 5)
      throw RuntimeException()
    if (theadCounter.get() != 100)
      throw RuntimeException()
    if (singleTagShouldHaveRun.get() == false)
      throw RuntimeException()
    if (multiTagShouldHaveRun.get() == false)
      throw RuntimeException()
  }
}