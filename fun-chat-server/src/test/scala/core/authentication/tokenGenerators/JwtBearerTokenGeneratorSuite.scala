package core.authentication.tokenGenerators

import base.TestSuite
import core.authentication.tokenGenerators.JwtBearerTokenGeneratorSuite._
import core.entities.{IntervalTimestamp, Timer, TokenContext}

class JwtBearerTokenGeneratorSuite extends TestSuite {

  var tokenGenerator: (Timer) => JwtBearerTokenGenerator = _

  override def beforeEach(): Unit = {
    super.beforeEach()

    tokenGenerator = (timer: Timer) => new JwtBearerTokenGenerator(() => SECRET_KEY, timer)
  }

  test("creating token, token is defined") {
    val ctx = TokenContext("user-id-xx", "test-user")

    val token = tokenGenerator(TIMER).create(ctx)

    assert(token.isDefined)
  }

  test("decoding token, context is returned correctly") {
    val generator = tokenGenerator(TIMER)
    val ctx       = TokenContext("user-id-xx", "test-user")
    val token     = generator.create(ctx)

    val result = generator.decode(token.get)

    assert(result.isDefined)
    assertResult(ctx)(result.get)
  }

  test("verifying token, is OK") {
    val generator = tokenGenerator(TIMER)
    val ctx       = TokenContext("user-id-xx", "test-user")
    val token     = generator.create(ctx)

    val result = generator.isValid(token.get)

    assert(result)
  }

  test("token issue date is before current time minus expiration interval, token invalid") {
    val testableTimer = new TestableTimer(System.currentTimeMillis - EXPIRATION * 3 * 1000, EXPIRATION)
    val generator     = tokenGenerator(testableTimer)
    val ctx           = TokenContext("user-id-xx", "test-user")
    val token         = generator.create(ctx)

    val result = generator.isValid(token.get)

    assert(!result)
  }

  test("token expiration date is after current time plus expiration interval, token invalid") {
    val testableTimer = new TestableTimer(System.currentTimeMillis + EXPIRATION * 3 * 1000, EXPIRATION)
    val generator     = tokenGenerator(testableTimer)
    val ctx           = TokenContext("user-id-xx", "test-user")
    val token         = generator.create(ctx)

    val result = generator.isValid(token.get)

    assert(!result)
  }

  class TestableTimer(timestamp: Long, intervalStep: Long) extends Timer(intervalStep) {
    override def freeze: IntervalTimestamp = IntervalTimestamp(timestamp, intervalStep)
  }
}

object JwtBearerTokenGeneratorSuite {
  val EXPIRATION: Long        = 120
  val TIMER                   = Timer(EXPIRATION)
  val SECRET_KEY: Array[Byte] = "^fun-ch@t-test-sec7et-k3y!".toCharArray.map(_.toByte)
}