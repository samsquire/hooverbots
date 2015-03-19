package scalabot

import scala.io.Source
import scalabot._

object Runner extends App {

  override def main(args: Array[String]) {
    val filename = args(0)
    val scenario = new ScenarioParser(new CommandParser()).parse(Source.fromFile(filename).getLines())
    print(new ScenarioRunner(scenario).run())
  }

}

// vim: set ts=2 sw=2 et sts=2:
