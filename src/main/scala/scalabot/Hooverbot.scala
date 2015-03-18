package scalabot

import scalabot._

class Hooverbot(room: Room, position: Coord) {
  def south : Hooverbot = new Hooverbot(room, room.south(position))
  def north : Hooverbot = new Hooverbot(room, room.north(position))
  def east : Hooverbot = new Hooverbot(room, room.east(position))
  def west : Hooverbot = new Hooverbot(room, room.west(position))

  def pos : Coord = position

}

// vim: set ts=2 sw=2 et sts=2:
