Irritating Crusader: The Report
====================

**Authors:**

Team: ProgMethMyFirstGame

1. Pipat Saengow (id: 6232024821)
2. Siri Thammarerkrit (id: 6232029021)

**Submitted for**

Programming Methodology I (id: 2110215) 

Semester II of 2019.

Final project presentation


Overview
--------------------

**Quick Note:** Jumps to [Game Manual] now! if you want to play the game.

**Irritating Crusader** ("The game") is a two-player, top-down, shooter game where
the player "disrupt" the opponent by shooting the "effect bullets", eventually
causing the other player to "confuse" and get killed by the "traps".

This report describe the game's gameplay for the players ("Game Manual")
and also the game's technical overview for developers ("Technical Description").


Game Manual
--------------------

### Introduction

**Irritating Crusader** is a simple game where there's only one goal, To kill *all* other player.

The best way to get introduced to this game is to actually start playing it.
Invite your friend (or find one) and follows **Quick Start** guide.

If you played it and somehow got confused, We also provides documentations on bullets, control, etc. below.


### Quick Start

#### Setting up

1. Grab `IrritatingCrusader.jar` from [here](todo).
2. Install JRE 11 from [here](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
3. If your friends aren't connected to the same local network, Install [Logmein Hamachi](https://www.vpn.net/) and follows
their guide on how to setup a local network.

#### Playing the game

1. Launch `IrritatingCrusader.jar`.

Have one player be the host and

1. Click **Create**
2. Announce his/her IP to every other players.

Have other players who are not the host do

1. Enter the host's IP and press **Join**

Once everyone joined the game.  
Press `W`, `A`, `S`, `D`, `Space`, `E` and see what it do.

### Controlling the player

This game is entirely controlled by the keyboard.

| Keys | Function |
|------|----------|
| W    | Walk north |
| A    | Walk west |
| S    | Walk south |
| D    | Walk east |
| Space | Shoot |
| E | Change bullet's type |

But don't walk too much though or you would fall into **traps**!

**Note:** There's one key we didn't mention. The `/` key opens a cheat console. If you somehow opened it, Close it using
`ESC` key. Don't cheat!.

### Traps

[TODO]

So now you know how to avoid them, But wouldn't it be good if you can use them! By shooting **effect bullets** you
can cause other player to fall into a trap!.


### Effect Bullets

[TODO]

### HUD status

[TODO]


Technical Description
--------------------


Copyright Material
--------------------

- **LibGDX** is an Apache2-licensed game engine by Bad Logic Games.
- **KryoNet** is a networking library by Nathan Sweet licensed under BSD-3-Clause.
- **PressStart2P** is a font by CodeMan38 licensed under Open Font License.
- **plain-james** is a scene2d ui skin by Raymond "Raeleus" Buckley licensed under CC BY 4.0
