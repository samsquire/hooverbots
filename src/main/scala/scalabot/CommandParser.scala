package scalabot
import scalabot.types._

class CommandParser() {
  def parse(stream: String) : List[Command] = stream.toList
}

trait NESWCommandable[T] {
  def east():T
  def north():T
  def west():T
  def south():T
}

class NESWDispatcher[C](commanded: NESWCommandable[C]) {
  def process(commands: List[Command]) : List[Option[C]] = {
    commands.map((command) => command match  {
        case 'N' => Some(commanded.north())
        case 'E' => Some(commanded.east())
        case 'W' => Some(commanded.west())
        case 'S' => Some(commanded.south())
        case _ => None
      })
  }
}

// vim: set ts=2 sw=2 et sts=2:
