# SAA_Chess

This program is a console chess game

## The class "ChessCell" represents a single cell in the chess game.

- An object of ChessCell has a String "piece" that holds which chess piece is on the cell. 
"" = empty cell

## The class "ChessBoard":
- main(String[]) - makes players take turns until there is one king left; says who wins
- initializer() - creates a LinkedList of 64 ChessCell objects which represent the board
- newGameSetter() - sets all the pieces for a new game
- printer() - displays the current state of the board LinkedList (uses printerLineInBetween() too) 
- positionCnangeScanner() - scans the user input and validates if it is on the board
- positionTranslator(String) - from a8a6 (String) to 0,16 (TwoIntegers) - makes math easier
- pieceValidator(TwoIntegers) - validates if the chosen piece can do the desired move
- blackOrWhite(int) - returns 1 if the piece on a given index is black; 2 if white; 0 if empty
- solvedValidator() - validate if both kings are still alive (1-black wins; 2-white wins; 0-not solved yet)

## The class "TwoIntegers" is created with the intent to return two coordinates
example: a8a6 which is translated in two integer values: 0 and 16

- int a - stores the index of the selected piece
- int b - stores the target index for the chess piece to move to (a to b)