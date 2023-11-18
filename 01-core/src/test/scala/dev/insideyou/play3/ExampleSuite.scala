package dev.insideyou
package play3

final class ExampleSuite extends TestSuite:
  test("hello world"):
    forAll: (int: Int, string: String) =>
      expectEquals(int, int)
      expectEquals(string, string)
