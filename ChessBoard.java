import java.util.LinkedList;
import java.util.Scanner;

public class ChessBoard {

	static LinkedList<ChessCell> board = new LinkedList<ChessCell>();
	static int turn = 0;

	public static void main(String[] args) {

		initializer();
		newGameSetter();

		int solved = 0; // 1 for player with black, 2 for player with white
		while (solved == 0) {
			turn++;
			printer();
			if (turn % 2 == 1) {
				System.out.print("Player 1, ");
			} else {
				System.out.print("Player 2, ");
			}
			System.out.print("enter your move:");
			positionCnangeScanner();
			solved = solvedValidator();
		}

		printer();
		if (solved == 1) {
			System.out.println("Player with BLACK pawns wins!");
		} else if (solved == 2) {
			System.out.println("Player with WHITE pawns wins!");
		}

	}

	// Validates if the user input is 4 letters long
	// and if the coordinates exist on the board
	private static String positionCnangeScanner() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String positionCnange = new String("");

		boolean valid = false;
		int i = 0;
		while (!valid) {
			if (i != 0) {
				System.out.print("Wrong input! Please enter again: ");
			}
			positionCnange = scan.nextLine();
			if (positionCnange.length() == 4) {
				if (positionCnange.charAt(0) >= 'a' && positionCnange.charAt(0) <= 'h'
						|| positionCnange.charAt(0) >= 'A' && positionCnange.charAt(0) <= 'H') {
					if (positionCnange.charAt(2) >= 'a' && positionCnange.charAt(2) <= 'h'
							|| positionCnange.charAt(2) >= 'A' && positionCnange.charAt(2) <= 'H') {
						if (positionCnange.charAt(1) >= '1' && positionCnange.charAt(1) <= '8') {
							if (positionCnange.charAt(3) >= '1' && positionCnange.charAt(3) <= '8') {
								valid = true;
							}
						}
					}
				}

			}
			i++;
			if (valid) {
				twoIntegers positionChangeTranslated = positionTranslator(positionCnange);// from a7a6 to index:8,16

				if (!pieceValidator(positionChangeTranslated)) {// can the piece actually do that move
					// printer();
					System.out.println("The seleted pawn cannot do the desired move!");
					valid = false;
				}
			}

		}

		// scan.close();
		return positionCnange;

	}

	// from user coordinates (a7a6) to 2 board (int 8, int 16) indexes
	public static twoIntegers positionTranslator(String positionCnange) {

		int letter1 = 0, letter2 = 0;
		int number1 = 0, number2 = 0;
		int index1 = 0, index2 = 0;

		if (positionCnange.charAt(0) >= 'a' && positionCnange.charAt(0) <= 'h') {
			letter1 = positionCnange.charAt(0) - 'a';
		} else if (positionCnange.charAt(0) >= 'A' && positionCnange.charAt(0) <= 'H') {
			letter1 = positionCnange.charAt(0) - 'A';
		}
		if (positionCnange.charAt(2) >= 'a' && positionCnange.charAt(2) <= 'h') {
			letter2 = positionCnange.charAt(2) - 'a';
		} else if (positionCnange.charAt(2) >= 'A' && positionCnange.charAt(2) <= 'H') {
			letter2 = positionCnange.charAt(2) - 'A';
		}

		number1 = Integer.parseInt("" + positionCnange.charAt(1));
		number2 = Integer.parseInt("" + positionCnange.charAt(3));

		index1 = letter1 + 8 * (8 - number1);
		index2 = letter2 + 8 * (8 - number2);

		twoIntegers result = new twoIntegers(index1, index2);

		return result;
	}

	public static boolean pieceValidator(twoIntegers positionChangeTranslated) {
		int indexA = positionChangeTranslated.getA();
		int indexB = positionChangeTranslated.getB();
		String piece = board.get(indexA).getPiece();
		String target = board.get(indexB).getPiece();

		if (piece == "") {
			System.out.println("You have NOT selected a pawn!");
		} else if (piece == "♟") {
			if (indexA >= 8 && indexA < 16) { // starting position - 1 or 2 ahead
				if (target == "") {
					if (indexB == indexA + 8 || indexB == indexA + 16) {
						if (indexB == indexA + 8 && target == "") {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						} else if (indexB == indexA + 16 && board.get(indexA + 8).getPiece() == "") {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					}
				} else if (blackOrWhite(indexB) == 2) {
					if (indexA % 8 == 0) {// left side (A column)
						if (indexB == indexA + 9) {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					} else if (indexA % 8 == 7) {// right side (B column)
						if (indexB == indexA + 7) {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					} else {// anywhere from B to G for a pawn
						if ((indexB == indexA + 7 || indexB == indexA + 9)) {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					}
				}

			} else if (indexB == indexA + 8 && target == "") { // other than starting position
				board.get(indexA).setPiece("");
				board.get(indexB).setPiece(piece);
				return true;
			} else if (blackOrWhite(indexB) == 2) {
				if (indexA % 8 == 0) {// left side (A column)
					if (indexB == indexA + 9) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA % 8 == 7) {// right side (B column)
					if (indexB == indexA + 7) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else {// anywhere from B to G for a pawn
					if ((indexB == indexA + 7 || indexB == indexA + 9)) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				}
			}
		} else if (piece == "♙") {
			if (indexA >= 48 && indexA < 56) { // starting position - 1 or 2 ahead
				if (target == "") {
					if (indexB == indexA - 8 || indexB == indexA - 16) {
						if (indexB == indexA - 8 && target == "") {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						} else if (indexB == indexA - 16 && board.get(indexA - 8).getPiece() == "") {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					}
				} else if (blackOrWhite(indexB) == 1) {
					if (indexA % 8 == 0) {// left side (A column)
						if (indexB == indexA - 7) {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					} else if (indexA % 8 == 7) {// right side (B column)
						if (indexB == indexA - 9) {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					} else {// anywhere from B to G for a pawn
						if ((indexB == indexA - 7 || indexB == indexA - 9)) {
							board.get(indexA).setPiece("");
							board.get(indexB).setPiece(piece);
							return true;
						}
					}
				}

			} else if (indexB == indexA - 8 && target == "") { // other than starting position
				board.get(indexA).setPiece("");
				board.get(indexB).setPiece(piece);
				return true;
			} else if (blackOrWhite(indexB) == 1) {
				if (indexA % 8 == 0) {// left side (A column)
					if (indexB == indexA - 7) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA % 8 == 7) {// right side (B column)
					if (indexB == indexA - 9) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else {// anywhere from B to G for a pawn
					if ((indexB == indexA - 7 || indexB == indexA - 9)) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				}
			}
		} else if (piece == "♜") {
			if (target == "" || blackOrWhite(indexB) == 2) {
				if (indexA % 8 == indexB % 8) { // on the same column
					boolean flagObstacle = false;
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 8 == indexB) {
								break;
							}
							currentIndex += 8;
						} else if (currentIndex > indexB) {
							if (currentIndex - 8 == indexB) {
								break;
							}
							currentIndex -= 8;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA / 8 == indexB / 8) { // on the same line
					boolean flagObstacle = false;
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 1 == indexB) {
								break;
							}
							currentIndex++;
						} else if (currentIndex > indexB) {
							if (currentIndex - 1 == indexB) {
								break;
							}
							currentIndex--;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				}
			}
		} else if (piece == "♖") {
			if (target == "" || blackOrWhite(indexB) == 1) {
				if (indexA % 8 == indexB % 8) { // on the same column
					boolean flagObstacle = false;
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 8 == indexB) {
								break;
							}
							currentIndex += 8;
						} else if (currentIndex > indexB) {
							if (currentIndex - 8 == indexB) {
								break;
							}
							currentIndex -= 8;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA / 8 == indexB / 8) { // on the same line
					boolean flagObstacle = false;
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 1 == indexB) {
								break;
							}
							currentIndex++;
						} else if (currentIndex > indexB) {
							if (currentIndex - 1 == indexB) {
								break;
							}
							currentIndex--;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				}
			}
		} else if (piece == "♝") {
			if (target == "" || blackOrWhite(indexB) == 2) {
				boolean flagObstacle = false;
				if (indexB > indexA && indexB % 8 - indexA % 8 == indexB / 8 - indexA / 8) {// down right
					for (int currentIndex = indexA + 9; currentIndex + 9 != indexB
							&& currentIndex != indexB; currentIndex += 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexB > indexA && indexA % 8 - indexB % 8 == indexB / 8 - indexA / 8) {// down left
					for (int currentIndex = indexA + 7; currentIndex + 7 != indexB
							&& currentIndex != indexB; currentIndex += 7) {
						System.out.println(currentIndex);
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA > indexB && indexB % 8 - indexA % 8 == indexA / 8 - indexB / 8) {// up right
					for (int currentIndex = indexA - 7; currentIndex - 7 != indexB
							&& currentIndex != indexB; currentIndex -= 7) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA > indexB && indexA % 8 - indexB % 8 == indexA / 8 - indexB / 8) {// up left
					for (int currentIndex = indexA - 9; currentIndex - 9 != indexB
							&& currentIndex != indexB; currentIndex -= 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				}
			}
		} else if (piece == "♗") {
			if (target == "" || blackOrWhite(indexB) == 1) {
				boolean flagObstacle = false;
				if (indexB > indexA && indexB % 8 - indexA % 8 == indexB / 8 - indexA / 8) {// down right
					for (int currentIndex = indexA + 9; currentIndex + 9 != indexB
							&& currentIndex != indexB; currentIndex += 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexB > indexA && indexA % 8 - indexB % 8 == indexB / 8 - indexA / 8) {// down left
					for (int currentIndex = indexA + 7; currentIndex + 7 != indexB
							&& currentIndex != indexB; currentIndex += 7) {
						System.out.println(currentIndex);
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA > indexB && indexB % 8 - indexA % 8 == indexA / 8 - indexB / 8) {// up right
					for (int currentIndex = indexA - 7; currentIndex - 7 != indexB
							&& currentIndex != indexB; currentIndex -= 7) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				} else if (indexA > indexB && indexA % 8 - indexB % 8 == indexA / 8 - indexB / 8) {// up left
					for (int currentIndex = indexA - 9; currentIndex - 9 != indexB
							&& currentIndex != indexB; currentIndex -= 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
					if (!flagObstacle) {
						board.get(indexA).setPiece("");
						board.get(indexB).setPiece(piece);
						return true;
					}
				}
			}
		} else if (piece == "♚") {
			if (target == "" || blackOrWhite(indexB) == 2) {
				if (indexB == indexA + 1 || indexB == indexA - 1 || indexB == indexA + 8 || indexB == indexA - 8
						|| indexB == indexA + 7 || indexB == indexA - 7 || indexB == indexA + 9
						|| indexB == indexA - 9) {
					board.get(indexA).setPiece("");
					board.get(indexB).setPiece(piece);
					return true;
				}
			}
		} else if (piece == "♔") {
			if (target == "" || blackOrWhite(indexB) == 1) {
				if (indexB == indexA + 1 || indexB == indexA - 1 || indexB == indexA + 8 || indexB == indexA - 8
						|| indexB == indexA + 7 || indexB == indexA - 7 || indexB == indexA + 9
						|| indexB == indexA - 9) {
					board.get(indexA).setPiece("");
					board.get(indexB).setPiece(piece);
					return true;
				}
			}
		} else if (piece == "♛") { // black queen
			if (target == "" || blackOrWhite(indexB) == 2) {
				boolean flagObstacle = false;
				if (indexA % 8 == indexB % 8) { // on the same column
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 8 == indexB) {
								break;
							}
							currentIndex += 8;
						} else if (currentIndex > indexB) {
							if (currentIndex - 8 == indexB) {
								break;
							}
							currentIndex -= 8;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexA / 8 == indexB / 8) { // on the same line
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 1 == indexB) {
								break;
							}
							currentIndex++;
						} else if (currentIndex > indexB) {
							if (currentIndex - 1 == indexB) {
								break;
							}
							currentIndex--;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexB > indexA && indexB % 8 - indexA % 8 == indexB / 8 - indexA / 8) {// down right
					for (int currentIndex = indexA + 9; currentIndex + 9 != indexB
							&& currentIndex != indexB; currentIndex += 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexB > indexA && indexA % 8 - indexB % 8 == indexB / 8 - indexA / 8) {// down left
					for (int currentIndex = indexA + 7; currentIndex + 7 != indexB
							&& currentIndex != indexB; currentIndex += 7) {
						System.out.println(currentIndex);
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexA > indexB && indexB % 8 - indexA % 8 == indexA / 8 - indexB / 8) {// up right
					for (int currentIndex = indexA - 7; currentIndex - 7 != indexB
							&& currentIndex != indexB; currentIndex -= 7) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexA > indexB && indexA % 8 - indexB % 8 == indexA / 8 - indexB / 8) {// up left
					for (int currentIndex = indexA - 9; currentIndex - 9 != indexB
							&& currentIndex != indexB; currentIndex -= 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				}
				if (!flagObstacle) {
					board.get(indexA).setPiece("");
					board.get(indexB).setPiece(piece);
					return true;
				}
			}
		} else if (piece == "♕") { //white queen
			if (target == "" || blackOrWhite(indexB) == 1) {
				boolean flagObstacle = false;
				if (indexA % 8 == indexB % 8) { // on the same column
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 8 == indexB) {
								break;
							}
							currentIndex += 8;
						} else if (currentIndex > indexB) {
							if (currentIndex - 8 == indexB) {
								break;
							}
							currentIndex -= 8;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexA / 8 == indexB / 8) { // on the same line
					for (int currentIndex = indexA; currentIndex != indexB;) {
						if (currentIndex < indexB) {
							if (currentIndex + 1 == indexB) {
								break;
							}
							currentIndex++;
						} else if (currentIndex > indexB) {
							if (currentIndex - 1 == indexB) {
								break;
							}
							currentIndex--;
						}
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexB > indexA && indexB % 8 - indexA % 8 == indexB / 8 - indexA / 8) {// down right
					for (int currentIndex = indexA + 9; currentIndex + 9 != indexB
							&& currentIndex != indexB; currentIndex += 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexB > indexA && indexA % 8 - indexB % 8 == indexB / 8 - indexA / 8) {// down left
					for (int currentIndex = indexA + 7; currentIndex + 7 != indexB
							&& currentIndex != indexB; currentIndex += 7) {
						System.out.println(currentIndex);
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexA > indexB && indexB % 8 - indexA % 8 == indexA / 8 - indexB / 8) {// up right
					for (int currentIndex = indexA - 7; currentIndex - 7 != indexB
							&& currentIndex != indexB; currentIndex -= 7) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				} else if (indexA > indexB && indexA % 8 - indexB % 8 == indexA / 8 - indexB / 8) {// up left
					for (int currentIndex = indexA - 9; currentIndex - 9 != indexB
							&& currentIndex != indexB; currentIndex -= 9) {
						if (blackOrWhite(currentIndex) == 1 || blackOrWhite(currentIndex) == 2) {
							flagObstacle = true;
							break;
						}
					}
				}
				if (!flagObstacle) {
					board.get(indexA).setPiece("");
					board.get(indexB).setPiece(piece);
					return true;
				}
			}
		} else if (piece == "♞") {
			if (target == "" || blackOrWhite(indexB) == 2) {
				// TO BE CONTINUED + PAWNS ANDESSANTS SPECIAL MOVE!!!
			}
		}

		// everything that is not in the IFs fails
		return false;
	}

	// returns 1 if the piece is black, 2 if white, 0 if empty
	private static int blackOrWhite(int index) {
		String piece = board.get(index).getPiece();
		if (piece == "♙" || piece == "♖" || piece == "♘" || piece == "♗" || piece == "♕" || piece == "♔") {
			return 2;
		} else if (piece == "♟" || piece == "♜" || piece == "♞" || piece == "♝" || piece == "♛" || piece == "♚") {
			return 1;
		}

		return 0;
	}

	// creates an empty LinkedList of 64 chess cells
	private static void initializer() {
		for (int i = 0; i < 64; i++) {
			ChessCell cell = new ChessCell();
			board.add(cell);
		}
	}

	// sets the chess pieces for the starting position
	private static void newGameSetter() {
		for (int i = 8; i < 16; i++) {
			board.get(i).setPiece("♟");
		}

		for (int i = 48; i < 56; i++) {
			board.get(i).setPiece("♙");
		}

		board.get(0).setPiece("♜");
		board.get(7).setPiece("♜");
		board.get(1).setPiece("♞");
		board.get(6).setPiece("♞");
		board.get(2).setPiece("♝");
		board.get(5).setPiece("♝");
		board.get(3).setPiece("♛");
		board.get(4).setPiece("♚");

		board.get(0 + 56).setPiece("♖");
		board.get(7 + 56).setPiece("♖");
		board.get(1 + 56).setPiece("♘");
		board.get(6 + 56).setPiece("♘");
		board.get(2 + 56).setPiece("♗");
		board.get(5 + 56).setPiece("♗");
		board.get(3 + 56).setPiece("♕");
		board.get(4 + 56).setPiece("♔");
	}

	// prints the current state of the chess board
	private static void printer() {
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		printerLineInBetween();
		System.out.println("|    | A  | B  | C  | D  | E  | F  | G  | H  |");

		for (int l = 8; l > 0; l--) {
			printerLineInBetween();
			System.out.print("| " + l + "  | ");
			for (int i = 0; i < 8; i++) {
				if (board.get(i + ((8 - l) * 8)).getPiece() == "") {
					System.out.print(" " + "  | ");
				} else {
					if (board.get(i + ((8 - l) * 8)).getPiece() == "♟") {
						System.out.print(board.get(i + ((8 - l) * 8)).getPiece() + "   | ");
					} else {
						System.out.print(board.get(i + ((8 - l) * 8)).getPiece() + "     | ");
					}

				}

			}
			System.out.println();
		}
		printerLineInBetween();
	}

	// helper for the printer
	private static void printerLineInBetween() {
		System.out.println("+————+————+————+————+————+————+————+————+————+");
	}

	// validates if there are any (same colored) pieces left
	// if no - the game is finished; the player with the remaining pieces wins
	private static int solvedValidator() {
		boolean blackKing = false;
		boolean whiteKing = false;

		for (int i = 0; i < 64; i++) {
			if (board.get(i).getPiece() == "♚") {
				blackKing = true;
			}

			if (board.get(i).getPiece() == "♔") {
				whiteKing = true;
			}
		}

		if (!blackKing) {
			return 2; // white wins
		} else if (!whiteKing) {
			return 1; // black wins
		}

		return 0;
	}

}