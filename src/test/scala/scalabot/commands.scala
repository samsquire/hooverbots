package scalabot

import scalabot.types._
import scalabot._

class CommandSpec extends Test {
  val parser = new CommandParser()
  "Coordinate parser" should "create a stream of commands from directions" in {
    parser.parse("NNESEESWNWW") should contain theSameElementsAs List[Char](
      'N', 'N', 'E', 'S', 'E', 'E', 'S', 'W', 'N', 'W', 'W'
    )
}
}

class FakeCommandable extends NESWCommandable[Char] {
  def east():Char = 'E'
  def north():Char = 'N'
  def west():Char = 'W'
  def south():Char = 'S'
}


class CommandDispatcherSpec extends Test {

  "Command dispatcher" should "map commands" in {
    val dispatcher = new NESWDispatcher[Char](new FakeCommandable()).process(List[Command]('N', 'E', 'S', 'W'))
    dispatcher.flatten should contain theSameElementsAs List[Char]('N', 'E', 'S', 'W')
  }

}

// vim: set ts=2 sw=2 et sts=2:
