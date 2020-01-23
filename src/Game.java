import java.io.File;
import java.io.FileNotFoundException;
public class Game {

	 //else
	public static void main(String[] args) throws FileNotFoundException {
		Sudoku sudoku = new Sudoku();
		
		String file = args[0];
		
		sudoku.readPuzzle(file);
	}
	
	

}
