package scalabot
import scalabot._

import org.scalatest._

class DirectionSpec extends FlatSpec with Matchers {
  "Hooverbot" should "move south" in {
    val bot = new Hooverbot(new Room((5,5)), (4, 4)).moveSouth
    bot.pos should be (4, 3)
  }
  it should "move north" in {
    val bot = new Hooverbot(new Room((5,5)), (0, 0)).moveNorth
    bot.pos should be (0, 1)
  }
  it should "move east" in {
    val bot = new Hooverbot(new Room((5,5)), (0, 0)).moveEast
    bot.pos should be (1, 0)
  }
  it should "move west" in {
    val bot = new Hooverbot(new Room((5,5)), (4, 4)).moveWest
    bot.pos should be (3, 4)
  }

  "Edge hooverbot" should "stay put moving south" in {
    val bot = new Hooverbot(new Room((5, 5)), (0, 0)).moveSouth
    bot.pos should be (0, 0)
  }
  it should "stay put moving north" in {
    val bot = new Hooverbot(new Room((5, 5)), (4, 4)).moveNorth
    bot.pos should be (4, 4)
  }
  it should "stay put moving east" in {
    val bot = new Hooverbot(new Room((5, 5)), (4, 4)).moveEast
    bot.pos should be (4, 4)
  }
  it should "stay put moving west" in {
    val bot = new Hooverbot(new Room((5, 5)), (0, 0)).moveWest
    bot.pos should be (0, 0)
  }

}
// vim: set ts=2 sw=2 et sts=2:
