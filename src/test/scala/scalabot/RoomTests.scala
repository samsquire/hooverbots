import scalabot.types._
import scalabot._
import scalabot.Test

import org.scalatest._

class RoomSpec extends Test {
  val corners = List[Coord](
    (0, 0), (4, 4), (4, 0), (0, 4)
  )

  "Dirtyroom" should "be cleanable" in {
    val (room, cleaned) = new Room((5, 5), corners).clear((0,0))
    cleaned should have length (1)
    cleaned should contain((0, 0))
    room.dirtCount should be (3)
  }

  "Invalid dirt" should "not be creatable" in {
    an [InvalidScenario] should be thrownBy new Room((3, 3), List[Coord]((8, 8)))
  }

}
// vim: set ts=2 sw=2 et sts=2:
