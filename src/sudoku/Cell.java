package sudoku;

public class Cell {
	/*A Cell represents a single square on the Sudoku Game Board. 
	 * It knows it's number - 0 means it is not solved.
	 * It knows the potential numbers that it could have from 1-9.
	 * The Sudoku game board is sub-divided into 9 smaller 3x3 sections that I will call a box. 
	 * These boxes will be numbered from left to right, top to bottom, from 1 to 9.  Each cell
	 * will know which box it belongs in.
	 */
	
	private int number; // This is the solved value of the cell.
	private boolean[] potential = {false, true, true, true, true, true, true, true, true, true};
	
	/*This array represents the potential of the cell to be each of the given index numbers.  Index [0] is not used since
	 * the cell cannot be solved for 0. 
	 */
	private int boxID;//The boxID is the box to which the cell belongs.
	
	//USEFUL METHODS:
	///TODO: canBe 
	//This method returns TRUE or False depending on whether the cell has the potential to be number
	public boolean canBe (int w)
	{
		
			return potential[w];
		
			
		
	}
	

	///TODO: cantBe
	//This sets the potential array to be false for a given number
	public boolean cantBe(int number)
	{
		return potential[number] = false;
		
	}
	
	///TODO: numberOfPotentials
	//This method returns a count of the number of potential numbers that the cell could be.
	public int numberOfPotentials()
	{
		return number;
		
	}
	
	///TODO: getFirstPotential
	//This method will return the first number that a cell can possibly be.
	public int getFirstPotential()
	{
		
		for (int c = 1; c < 10; c++) {
			if(potential[c]) {
			
				return c;
			}
			
		}
		//System.out.println(counter);
		return -1;
				
		
	}
	
	
	public int getSecondPotential()
	{
		
		for (int c = getFirstPotential() + 1; c < 10; c++) {
			if(potential[c]) {
			
				return c;
			}
			
		}
		//System.out.println(counter);
		return -1;		
	}
		
	
	
	public int possible() {
		int counter = 0;
		for (int c = 1; c < 10; c++) {
			if(potential[c]) {
			
				counter++;
			}
			
		}
		//System.out.println(counter);
		return counter;
				
	}
	
	
	//GETTERS AND SETTERS
	public int getNumber() {
		return number;
	}
	
	///TODO: setNumber
	// This method sets the number for the cell but also sets all of the potentials for the cell (except for the solved number)
	//		to be false
	public void setNumber(int number) {
		
		this.number = number;
		for (int x = 1; x < 10; x++) {
			if (x != number) {
				potential[x] = false; 
			}
			potential[number] = true; 
			
		}
	}
	/*public int getSecondPotential()
    {
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            if (potential[i] == true && counter == 0)
                counter++;
            else
                return i;
        }
        return -1;
    }
    */
	public boolean[] getPotential() {
		return potential;
	}
	public void setPotential(boolean[] potential) {
		this.potential = potential;
	}
	public int getBoxID() {
		return boxID;
	}
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
}
