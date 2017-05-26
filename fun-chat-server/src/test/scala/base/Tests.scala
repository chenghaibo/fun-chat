package base

import org.flywaydb.core.Flyway
import org.hamcrest.Matcher
import org.mockito.MockitoAnnotations
import org.mockito.verification.VerificationMode
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FlatSpecLike, FunSuiteLike, Matchers, WordSpecLike, fixture, Suite => iSuite, TestSuite => iTestSuite}
import scalikejdbc.config.DBsWithEnv
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{ConnectionPool, GlobalSettings}

trait TestSuite extends FunSuiteLike with MockitoSupport

trait TestWordSpec extends WordSpecLike with Matchers with MockitoSupport

trait TestFlatSpec extends FlatSpecLike with Matchers with MockitoSupport

trait FixtureTestSuite extends fixture.FunSuiteLike with AutoRollback with Matchers with MockitoSupport with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    super.beforeAll()
    GlobalSettings.jtaDataSourceCompatible = true
    DBsWithEnv("test").setupAll()

    val flyway = new Flyway()
    flyway.setDataSource(ConnectionPool().dataSource)
    flyway.clean()
    flyway.migrate()
  }

  override def afterAll(): Unit = {
    super.afterAll()
    DBsWithEnv("test").closeAll()
  }
}

trait MockitoSupport extends iSuite with iTestSuite with MockitoSugar with BeforeAndAfterEach {

  override def beforeEach(): Unit = {
    MockitoAnnotations.initMocks(this)
  }

  def eq[A](value: A) = {
    org.mockito.Matchers.eq[A](value)
  }

  def any[A] = {
    org.mockito.Matchers.any[A]
  }

  def argThat[A](matcher: Matcher[A]) = {
    org.mockito.Matchers.argThat[A](matcher)
  }

  def when[A](methodCall: A) = {
    org.mockito.Mockito.when[A](methodCall)
  }

  def verify[A](mock: A) = {
    org.mockito.Mockito.verify(mock: A)
  }

  def verify[A](mock: A, mode: VerificationMode) = {
    org.mockito.Mockito.verify(mock, mode)
  }

  def times(wantedNumberOfInvocations: Int) = {
    org.mockito.Mockito.times(wantedNumberOfInvocations)
  }
}
