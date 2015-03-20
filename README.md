Hooverbots
=

This is an immutable robot simulation. There are two domain main models:

 * **Hooverbot** Holds its position and a reference to the state of the
   room it is in. Can be directed to move. Moving creates a new instance
   of itself and instructs the room to update itself too.
   
 * **Room** The room maintains a set of dimensions and a list of dirty
   locations. It also can be given a coordinate and it will determine a valid coordinate that it
   is North, East, West or South to it.

Other support models are:

 * **Scenario** all the data to describe a particular scenario involving
   the state of a room, the position of a hooverbot and a set of
   commands for the robot
* **ScenarioRunner** takes a scenario and coordinates the Hooverbot via
  commands. Will generate an **Output**
* **Output** holds all the output data for a given scenario. Since the
  models are immutable, this also contains a copy of every `tick` within
  the simulation. This allows us to replay in the future.
* **Runner** imports all of the above and uses the API to drive a
  command line application.

I have tried to decouple small details such as:

 * **command dispatch** - A hooverbot may not be the only thing that is
   commandable so the commands are kept as a trait (an interface) to
  avoid coupling
 * **parsing** creates intermediary container objects such as
   **Scenario** so that they could be independently changed
 * **API** By keeping everything decoupled, we could create different
   interfaces in the future, such as a web based interface that
   interprets Output and creates an animation of the robot.

Thoughts
=

I feel that the Robot coordinating through the Room rather than the Room coordinating its contents as being different to how this problem is usually solved. This has the positive effect that the Room does not need to know about Hooverbots.

Running
=

1. Clone the repository
1. Make sure you have Scala, scala-sbt and make installed
1. Run make. This will compile, test and print the **Output** for input.txt
which is a test **Scenario**
