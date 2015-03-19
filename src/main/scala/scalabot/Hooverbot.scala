package scalabot
import types._

class Hooverbot(val room: Room, position: Coord, cleanedPositions: List[Coord] = List()) extends NESWCommandable[Hooverbot] {
  def moveSouth : Hooverbot = clean(room.south(position))
  def moveNorth : Hooverbot = clean(room.north(position))
  def moveEast : Hooverbot = clean(room.east(position))
  def moveWest : Hooverbot = clean(room.west(position))
  def pos : Coord = position
  def cleanedCount : Int = cleanedPositions.length
  def clean(target: Coord) : Hooverbot = {
    val (cleanedRoom, cleared) = room.clear(target)
    new Hooverbot(cleanedRoom, target, cleared ++ cleanedPositions)
  }
}

// vim: set ts=2 sw=2 et sts=2:
