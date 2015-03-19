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

  val scenarioParser = new ScenarioParser(new CommandParser())

  def testInput : Iterator[String] = Source.fromFile("input.txt").getLines()

  "Scenario parser" should "correctly interpret input.txt" in {
    val scenario = scenarioParser.parse(testInput)

    scenario.dimensions should be (5, 5)
    scenario.hooverPosition should be (1, 2)
    scenario.dirtyPositions should contain theSameElementsAs List[Coord]((1, 0), (2, 2), (2,3))
    scenario.commands should contain theSameElementsAs List[Command]('N','N','E','S','E','E','S','W','N','W','W')
  }

  "Hooverbot scenario runner" should "correctly interpret file" in {
    val scenario = scenarioParser.parse(testInput)
    val results = new ScenarioRunner(scenario).run()
    results.lastPosition should be (1, 3)
    results.cleanedCount should be (1)
  }

  "Output" should "output properly" in {
    val output = new Output((5, 5), 5, List()) 
    output.toString should equal ("5 5\n5\n")
  }
}

// vim: set ts=2 sw=2 et sts=2:
