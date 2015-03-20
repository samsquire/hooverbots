package scalabot
import scala.collection.mutable._
import scalabot.types._

class InvalidScenario(message: String) extends Exception(message) { }

class CommandParser() {
  def parse(stream: String) : List[Command] = stream.toList
}

class Scenario(val dimensions: Coord,
  val hooverPosition: Coord,
  val dirtyPositions: List[Coord],
  val commands: List[Command]) { }

class ScenarioParser(commandParser : CommandParser) {
  def parseCoordinate(coord: String) : Coord = {
    val parts = coord.split(" ")
    (parts(0).toInt, parts(1).toInt)
  }

  def parse(lines: Iterator[String]) : Scenario = {
    try {
      val dimensions = parseCoordinate(lines.next())
      val hooverPosition = parseCoordinate(lines.next())
      val remainingLines = lines.toList
      val (dirt, commandStream) = remainingLines.partition("""\d* \d*""".r.pattern.matcher(_).matches)
      val commands = commandParser.parse(commandStream(0))
      new Scenario(dimensions, hooverPosition, dirt.map(parseCoordinate), commands)
    } catch {
      case e:Exception => throw new InvalidScenario("Could not parse scenario due to %s".format(e.toString()))
    }
  }
}

class Output(val lastPosition: Coord,
  val cleanedCount: Int,
  val movements: List[Hooverbot],
  val errors: List[String] = List[String]()) {
  override def toString() : String = "%s\n%d\n".format(coordinate(lastPosition), cleanedCount)
  def coordinate(coord: Coord) : String = { "%d %d".format(coord._1, coord._2) }
}

class ScenarioRunner(scenario: Scenario) {

  type Tick = (ListBuffer[Hooverbot], ListBuffer[String])
  val dispatcher = new NESWDispatcher[Hooverbot]()

  def initialState() : (Hooverbot, Room) = {
    val room = new Room(scenario.dimensions, scenario.dirtyPositions)
    (new Hooverbot(room, scenario.hooverPosition), room)
  }

  def scenarioTick(previous: Tick, command: Command): Tick = {
    val (previousMovements, errors) = previous
    dispatcher.process(previousMovements.last, command) match {
      case Some(success) => (previousMovements += success, errors)
      case None => (previousMovements, errors += ("ignoring bad command %s".format(command)))
    }
  }

  def run() : Output = {
    val (hooverbot, room) = initialState()
    val (movements, errors) = scenario.commands.foldLeft((ListBuffer[Hooverbot](hooverbot), ListBuffer[String]()))(scenarioTick)
    new Output(movements.last.pos, movements.last.cleanedCount, movements.toList, errors.toList)
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
