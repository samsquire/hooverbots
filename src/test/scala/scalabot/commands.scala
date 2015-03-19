package scalabot

import scalabot.types._
import scalabot._
import scala.io.Source

class CommandSpec extends Test {
  val parser = new CommandParser()
  "Coordinate parser" should "create a stream of commands from directions" in {
    parser.parse("NNESEESWNWW") should contain theSameElementsAs List[Char](
      'N', 'N', 'E', 'S', 'E', 'E', 'S', 'W', 'N', 'W', 'W'
    )
  }
}

class FakeCommandable(direction: Char = ' ') extends NESWCommandable[FakeCommandable] {
  def moveEast():FakeCommandable = new FakeCommandable('E')
  def moveNorth():FakeCommandable = new FakeCommandable('N')
  def moveWest():FakeCommandable = new FakeCommandable('W')
  def moveSouth():FakeCommandable = new FakeCommandable('S')
  override def toString():String = direction.toString
}


class CommandDispatcherSpec extends Test {
  val fakeDispatcher = new NESWDispatcher[FakeCommandable]()

  "Command dispatcher" should "maps basic commands to a commandable" in {
    val bot = new FakeCommandable()
    val results = List[Command]('N', 'E', 'S', 'W').map(fakeDispatcher.process(bot, _))
    results should not contain None
    results.flatten.map(_.toString) should contain theSameElementsAs List[String]("N", "E", "S", "W")
  }

  it should "not accept invalid commands" in {
    val bot = new FakeCommandable()
    val results = List[Command]('N', 'X', 'Y', 'Z').map(fakeDispatcher.process(bot, _))
    results should contain (None)
  }
}

class ScenarioSpec extends Test {

  val parser = new ScenarioParser(new CommandParser())

  "Scenario parser" should "correctly interpret input.txt" in {
    val scenario = parser.parse(Source.fromFile("input.txt").getLines())

    scenario.dimensions should be (5, 5)
  }

}

// vim: set ts=2 sw=2 et sts=2:
