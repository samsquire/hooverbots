package scalabot
import scalabot._
import scala.math._
import types._

class Room(size: Coord, dirt: List[Coord] = List()) {
  val (width: Int, height: Int) = (size._1 - 1, size._2 - 1)

  val bad = dirt.filterNot(valid)
  if (!bad.isEmpty) {
    throw new InvalidScenario("Dirt coordinates out of bounds: " + bad.mkString)
  }
  def valid(pos: Coord) : Boolean = (min(pos._1, width), min(pos._2, height)) == pos && pos == (max(pos._1, 0), max(pos._2, 0))
  def south(pos: Coord) : Coord = (pos._1, max(pos._2 - 1, 0))
  def north(pos: Coord) : Coord = (pos._1, min(pos._2 + 1, height))
  def east(pos: Coord) : Coord = (min(pos._1 + 1, width), pos._2)
  def west(pos: Coord) : Coord = (max(pos._1 - 1, 0), pos._2)

  def dirtCount: Int = dirt.length
  def clear(location: Coord): (Room, List[Coord]) = {
    val (cleaned, dirty) = dirt.partition(_ == location)
    (new Room(size, dirty), cleaned)
  }
}

// vim: set ts=2 sw=2 et sts=2:
