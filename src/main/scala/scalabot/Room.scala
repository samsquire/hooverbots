package scalabot

import scala.math._
import scalabot._

class Room(size: Coord, dirt: List[Coord] = List()) {
  val (width: Int, height: Int) = (size._1 - 1, size._2 - 1)

  def south(pos: Coord) : Coord = (pos._1, max(pos._2 - 1, 0))
  def north(pos: Coord) : Coord = (pos._1, min(pos._2 + 1, height))
  def east(pos: Coord) : Coord = (min(pos._1 + 1, width), pos._2)
  def west(pos: Coord) : Coord = (max(pos._1 - 1, 0), pos._2)

}

// vim: set ts=2 sw=2 et sts=2:
