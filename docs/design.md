# Design

## Game Rules
Link to official rules: https://bananagrams.com/pages/instructions

We'll play according to classic bananagrams rules.

1. When the game begins, each player is to draw _x_ number of tiles from a pile of _n_ tiles.
2. Player then begins to form valid words using the _x_ number of tiles available. Words can only be formed from left to right of from top to bottom. If player is unable to form any more words with the remaining tiles, player can opt to swap one tile from hand with three tiles in the pile. If there are less than three tiles remaining in the pile (meaning there are insufficient tiles for swapping), the player loses and the game exits.
3. Upon using all the tiles, player shouts _"peel!"_ and all players draw a tile. Repeat step 2.
4. The first player to finish crafting all the words and shouts _"peel!"_ when the pile is empty is the winner.

## Approach
At the start of the game, our objective should be to form the longest valid word given _x_ tiles. We could use breadth-first search (BFS) with backtracking.

For example, suppose we start with the letters `eilnst` (sorted).
```mermaid
graph LR;
    given_word(eilnst)-->possible_word_1[listen];
    given_word-->possible_word_2[enlist]
    given_word-->possible_word_3[silent]
    given_word-->possible_word_4[tinsel]
```

Data structure would be: 
```json
{ 
  "eilnst": [
    "listen",
    "enlist",
    "silent",
    "tinsel"
  ]
}
``` 