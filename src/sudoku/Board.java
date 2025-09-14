package sudoku;


import java.io.File;
import java.util.Scanner;

public class Board {

	/*
	 * The Sudoku Board is made of 9x9 cells for a total of 81 cells. In this
	 * program we will be representing the Board using a 2D Array of cells.
	 * 
	 */

	private static Cell[][] board = new Cell[9][9];

	// The variable "level" records the level of the puzzle being solved.
	private String level = "hard";

	/// TODO: CONSTRUCTOR
	// This must initialize every cell on the board with a generic cell. It must
	/// also assign all of the boxIDs to the cells
	public Board() {
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++) {
				board[x][y] = new Cell();
				board[x][y].setBoxID(3 * (x / 3) + (y) / 3 + 1);
			}
	}

	/// TODO: loadPuzzle
	/*
	 * This method will take a single String as a parameter. The String must be
	 * either "easy", "medium" or "hard" If it is none of these, the method will set
	 * the String to "easy". The method will set each of the 9x9 grid of cells by
	 * accessing either "easyPuzzle.txt", "mediumPuzzle.txt" or "hardPuzzle.txt" and
	 * setting the Cell.number to the number given in the file.
	 * 
	 * This must also set the "level" variable TIP: Remember that setting a cell's
	 * number affects the other cells on the board.
	 */
	public void loadPuzzle(String level) throws Exception {
		this.level = level;
		String fileName = "easy.txt";
		if (level.contentEquals("medium"))
			fileName = "mediumPuzzle.txt";
		else if (level.contentEquals("hard"))
			fileName = "hardPuzzle.txt";

		Scanner input = new Scanner(new File(fileName));

		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++) {
				int number = input.nextInt();
				if (number != 0)
					solve(x, y, number);
			}

		input.close();

	}

	/// TODO: isSolved
	/*
	 * This method scans the board and returns TRUE if every cell has been solved.
	 * Otherwise it returns FALSE
	 * 
	 */
	public boolean isSolved() {
		// int number[][] = new int [0][0];

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {

				if (board[x][y].getNumber() == 0) {
					return false;
				}

			}
		}
		return true;
	}

	/// TODO: DISPLAY
	/*
	 * This method displays the board neatly to the screen. It must have dividing
	 * lines to show where the box boundaries are as well as lines indicating the
	 * outer border of the puzzle
	 */
	public void display() {
		System.out.println("------------------");
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				System.out.print("|" + board[x][y].getNumber());

			}
			System.out.print("|");
			System.out.println("\n ------------------");
		}
	}

	/// TODO: solve
	/*
	 * This method solves a single cell at x,y for number. It also must adjust the
	 * potentials of the remaining cells in the same row, column, and box.
	 */

	public void solve(int x, int y, int number) {

		for (int i = 0; i < 9; i++) {
			board[x][i].cantBe(number);
			for (int j = 0; j < 9; j++) {
				board[j][y].cantBe(number);

				// .getbox ID

				if (board[x][y].getBoxID() == board[j][i].getBoxID()) {
					board[j][i].cantBe(number);

				}
			}
			board[x][y].setNumber(number);
		}
	}

	// logicCycles() continuously cycles through the different logic algorithms
	// until no more changes are being made.
	public void logicCycles() throws Exception {

		while (isSolved() == false) {
			int changesMade = 0;
			do {
				changesMade = 0;
				changesMade += logic1();
				changesMade += logic2();

				changesMade += logic3();
				changesMade += logic4();
				changesMade += logic5();
				display();
				if (errorFound()) {
					break;
				}
			} while (changesMade != 0);

		}

	}

	/// TODO: logic1
	/*
	 * This method searches each row of the puzzle and looks for cells that only
	 * have one potential. If it finds a cell like this, it solves the cell for that
	 * number. This also tracks the number of cells that it solved as it traversed
	 * the board and returns that number.
	 */
	public int logic1() {

		int changesMade = 0;

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (board[x][y].possible() == 1 && board[x][y].getNumber() == 0) { // does not get past ?
					// System.out.println("still no work");

					solve(x, y, board[x][y].getFirstPotential());
					changesMade++;

				}
			}

		}

		return changesMade;
	}

	public int logic2() {
		int changesMade = 0;
		// rows
		for (int i = 0; i < board.length; i++) {
			for (int num = 1; num < 10; num++) {
				int counter = 0;
				int saved = -1;
				for (int j = 0; j < board.length; j++) {
					if (board[i][j].getPotential()[num]) {
						counter++;
						saved = j;
					}
				}
				if (counter == 1) {
					solve(i, saved, num);
					changesMade++;
				}
			}
		}
		// columns

		for (int i = 0; i < board.length; i++) {
			for (int num = 1; num < 10; num++) {
				int counter = 0;
				int saved = -1;
				for (int j = 0; j < board.length; j++) {
					if (board[j][i].getPotential()[num]) {
						counter++;
						saved = j;
					}
				}
				if (counter == 1) {
					solve(saved, i, num);
					changesMade++;
				}
			}
		}
		// display();
		return changesMade;
	}

	/// TODO: logic3
	/*
	 * This method searches each box for a cell that is the only cell that has the
	 * potential to be a given number. If it finds such a cell and it is not already
	 * solved, it solves the cell. This also tracks the number of cells that it
	 * solved as it traversed the board and returns that number.
	 */
	public int logic3() {

		int changesMade = 0;

		int counter;
		int xPos = 0;
		int yPos = 0;

		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				counter = 0;
				for (int x = 0; x < 9; x++) {
					for (int y = 0; y < 0; y++) {
						if (board[x][y].getBoxID() == i) {
							if (board[x][y].getPotential()[j]) {
								counter++;
								xPos = x;
								yPos = y;
							}
						}
					}
				}
				if (counter == 1) {
					solve(xPos, yPos, j);
					changesMade++;
				}
			}
		}

		return changesMade;
	}

	public static int logic4() {
		int changesMade = 0;

		// first, looking for a cell with two potentials
		for (int x = 0; x < 9; x++)
			for (int y = 0; y < 9; y++)
				if (board[x][y].numberOfPotentials() == 2) {
					// found a 2-potential cell - looking for another in the same box

					for (int i = 0; i < 9; i++)
						for (int j = 0; j < 9; j++) {
							if (i == x && j == y)
								continue;
							if (board[i][j].numberOfPotentials() == 2
									&& board[i][j].getBoxID() == board[x][y].getBoxID()
									&& board[i][j].getFirstPotential() == board[x][y].getFirstPotential()
									&& board[i][j].getFirstPotential() == board[x][y].getFirstPotential()) {
							
								for (int a = 0; a < 9; a++)
									for (int b = 0; b < 9; b++)
										if (board[a][b].getBoxID() == board[x][y].getBoxID())
											if (!((a == x && b == y) || (a == i && b == j))) {

												if (board[a][b].canBe(board[x][y].getFirstPotential())) {
													board[a][b].cantBe(board[x][y].getFirstPotential());
													changesMade++;
												}
												if (board[a][b].canBe(board[x][y].getSecondPotential())) {
													board[a][b].cantBe(board[x][y].getSecondPotential());
													changesMade++;
												}

											}

							}

						}

				}

		return changesMade;
	}

	public static boolean hasSamePossibles(Cell a, Cell b) {
		for (int n = 1; n < 10; n++)
			if (a.canBe(n) != b.canBe(n))
				return false;
		return true;
	}

	// This is for logic 5 but only works with rows - a modified algorithm would be
	// needed for columns and boxes
	// but would follow the same logic.
	public static int logic5() {
		int changesMade = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int Possible = board[i][j].numberOfPotentials();
				int same = 1;
				for (int y = j + 1; y < 9; y++)
					if (hasSamePossibles(board[i][j], board[i][y])) {
						same++;
					}
				
				if (same == Possible)// the situation exists
				{
					for (int y = 0; y < 9; y++)
						if (hasSamePossibles(board[i][j], board[i][y]) == false) {
							
							for (int n = 1; n < 10; n++)
								if ((board[i][y].canBe(n) && board[i][j].canBe(n))) {
									board[i][y].cantBe(n);
									changesMade++;
								}
						}
				}
			}
		}

		return changesMade;
	}

	/// TODO: errorFound
	/*
	 * This method scans the board to see if any logical errors have been made. It
	 * can detect this by looking for a cell that no longer has the potential to be
	 * any number.
	 */
	public boolean errorFound() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j].numberOfPotentials() == 0) {
					return false;
				}
			}
		}
		return true;
	}

}
