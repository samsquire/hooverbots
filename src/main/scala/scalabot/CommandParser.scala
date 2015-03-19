package scalabot
import scalabot.types._

class CommandParser() {
  def parse(stream: String) : List[Command] = stream.toList
}

class Scenario(val dimensions: Coord,
  val hooverPosition: Coord,
  val dirt: List[Coord],
  val commands: List[Command]) { }

class ScenarioParser(commandParser : CommandParser) {
  def parseCoordinate(coord: String) : Coord = {
    val parts = coord.split(" ")
    (parts(0).toInt, parts(1).toInt)
  }

  def parse(lines: Iterator[String]) : Scenario = {
    val dimensions = parseCoordinate(lines.next())
    val hooverPosition = parseCoordinate(lines.next())
    val remainingLines = lines.toList()
    val dirt = remainingLines.dropRight(2).map(parseCoordinate)
    val commands = commandParser.parse(remainingLines.last)
    new Scenario(dimensions, hooverPosition, dirt, commands)
  }
}

trait NESWCommandable[T] {
  def moveEast():T
  def moveNorth():T
  def moveWest():T
  def moveSouth():T
}

class NESWDispatcher[T]() {
  def process(commanded: NESWCommandable[T], command: Command) : Option[T] = {
    command match  {
      case 'N' => Some(commanded.moveNorth())
      case 'E' => Some(commanded.moveEast())
      case 'W' => Some(commanded.moveWest())
      case 'S' => Some(commanded.moveSouth())
      case _ => None
    }
  }
}

// vim: set ts=2 sw=2 et sts=2:
