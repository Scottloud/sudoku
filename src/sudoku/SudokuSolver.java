package sudoku;

public class SudokuSolver {
public static void main(String[] args)throws Exception {
		
		Board puzzle = new Board();
		puzzle.loadPuzzle("easy");
		puzzle.display();
		
		puzzle.logicCycles();
	
		puzzle.display();	System.out.println("______________");
		
		
	System.out.println(puzzle.errorFound());
	System.out.println(puzzle.isSolved());
		

	}


}
