# Match Fixture Generator

A basic terminal Java program. No libraries or build tools are required.

## Run

Open a terminal in this folder:

```text
.\run.bat
```

Or compile and run manually:

```text
javac -cp . Main.java Input.java Player.java RoundRobin.java Knockout.java
java -cp . Main
```

In VS Code, Code Runner is configured to run in the Terminal so you can type
answers. If an older run is still waiting in the Output panel, stop it and run
`Main.java` again. The Output panel is read-only and cannot accept input.

Open `Main.java` before clicking Code Runner's Run button. If you see "Code
language not supported or defined", check that the active tab is `Main.java`
and its language mode (bottom right) says Java.

You can also press F5 and select **Run Fixture Generator**, or press
Ctrl+Shift+B to compile and run using the included task. Both use the Terminal.

## Files

- `Main.java`: participant registration, tournament menu and final results.
- `Input.java`: reads and checks whole-number input.
- `Player.java`: stores a serial number and accumulated points.
- `RoundRobin.java`: participant changes, fixtures, scoring and tie-breaks.
- `Knockout.java`: pairings, byes and first/second place.

## Rules used

- Initial serial numbers are 1 through the participant count.
- Before each main round robin, enter the absent players and any new players. A new player must have a positive serial number never registered in this tournament. New players start with zero points.
- Each round robin plays every pair once. Win, draw and loss points are nonnegative whole numbers.
- Advancement uses accumulated main round-robin points, including previous main rounds.
- For top N, only a tie crossing the last qualifying place requires extra matches. The tied players play a separate round robin. If necessary, the players still tied at the cutoff repeat until the required places are filled.
- Tie-break points start at zero each time and never change tournament points. Equal win/draw/loss scoring requires different tie-break scoring if the tie persists. Repeated draws can require further tie-breaks.
- After a main round robin, choose another round robin, start knockout, or finish. If nobody qualifies, another round robin allows new players to be added.
- Knockout asks for random or ascending serial order before every round. Adjacent players are paired; the last player gets a bye when the count is odd. The final match determines first and second place and ends the tournament.
- Finishing through the menu displays the remaining players and all registered players' accumulated points. It declares a champion only if one player remains.
