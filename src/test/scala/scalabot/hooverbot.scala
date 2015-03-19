package scalabot
import scala.collection.mutable._

import scalabot.types._
import scalabot._

class HooverbotScenarioSpec extends Test {
  val dirt = List[Coord]((0, 1), (1, 1), (1, 0))

  val hooverbotDispatcher = new NESWDispatcher[Hooverbot]()
  val simpleCommands = List[Command]('N', 'E', 'S', 'W')
  "Hooverbot" should "keep a score of cleaned dirt" in {
    var room = new Room((5, 5), dirt)
    var bot = new Hooverbot(room, (0,0))
    val results = simpleCommands.foldLeft(ListBuffer(bot))((previousMovements, command) =>
      previousMovements += (hooverbotDispatcher.process(previousMovements.last, command)).getOrElse(null))

    results.map(_.cleanedCount) should contain theSameElementsAs List[Int](0, 1, 2, 3, 3)

  }

  "Hooverbot's room" should "remove dirt as it is cleaned" in {
    var room = new Room((5, 5), dirt)
    var bot = new Hooverbot(room, (0,0))
    val results = simpleCommands.foldLeft(ListBuffer(bot))((previousMovements, command) =>
      previousMovements += (hooverbotDispatcher.process(previousMovements.last, command)).getOrElse(null))

    results.map(_.room.dirtCount) should contain theSameElementsInOrderAs List[Int](3, 2, 1, 0, 0)
  }
}
// vim: set ts=2 sw=2 et sts=2:
