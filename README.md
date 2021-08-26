# Pawns-Only Chess
Stage 2 of 5 for JetBrains Academy - Kotlin - [Pawns-Only Chess project](https://hyperskill.org/projects/182/stages/923/implement).   
This stage has us ask for the name of the two players and then loop asking each player their move until one of them types exit.
## Requirements
### Description
We need to make our program more interactive. First, it will ask players for their names. After that, the software will prompt each player to take turns and make a move. We are going to implement pawn moves later, so, for now, it doesn't matter which move is done at the current stage. However, the input should have the correct format. It should follow the `x0y1` format where `x0` is the coordinates of a pawn that the user wants to move, and `y1` are the coordinates of the final position. For example, at the current stage, the valid moves are `a2a4`, `d4d8`, `a1h8`, and so on. Moves like `j2j4`, `h0h4`, `a2a4a` are deemed to be invalid.

If a player prompts an invalid move, the game should print a warning and ask for another move. Terminate the program after the `exit` command.
### Objectives
After `Pawns-Only Chess` is printed, prompt each player for their names with `First Player's name:` and `Second Player's name:` holders. After that, print the chessboard (see Example 1).

The program should prompt each player for a move by printing `<first player name>'s turn:` or `<second player name>'s turn:` where `<first player name>` and `<second player name>` are the players' names. If the move follows the correct format, proceed to the next one. If the input doesn't follow the correct format, print `Invalid Input` and ask for another move (see Example 1).

If a player inputs `exit`, terminate the program and print `Bye!`
### Examples
The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.
#### Example 1:
```text
Pawns-Only Chess
First Player's name:
> John
Second Player's name:
> Amelia
  +---+---+---+---+---+---+---+---+
8 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
7 | B | B | B | B | B | B | B | B |
  +---+---+---+---+---+---+---+---+
6 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
5 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
4 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
3 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
2 | W | W | W | W | W | W | W | W |
  +---+---+---+---+---+---+---+---+
1 |   |   |   |   |   |   |   |   |
  +---+---+---+---+---+---+---+---+
    a   b   c   d   e   f   g   h

John's turn:
> a2a3
Amelia's turn:
> b7c4
John's turn:
> h8f2
Amelia's turn:
> b2b5
John's turn:
> s2s3
Invalid Input
John's turn:
> d9b2
Invalid Input
John's turn:
> s0f2
Invalid Input
John's turn:
> a2a3b
Invalid Input
John's turn:
> b2b3
Amelia's turn:
> exit
Bye!
```