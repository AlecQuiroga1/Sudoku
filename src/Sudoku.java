import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * 
 * @author Alec Quiroga
 *1/19/20
 *Advanced Java COP-3331
 * 
 *This code solves sudoku problems but is currently incomplete
 */

public class Sudoku {
	
	public static int maxRows = 9;//setting the size of the array
	public static int maxColumns = 9;
	public int gridsGenerated = 0;
	
	public int[][] grid = new int[maxRows][maxColumns];
	
	/**
	 * reads in puzzle from file
	 * @param filePath
	 * @return grid array
	 * @throws FileNotFoundException
	 */
	
	public int[][] readPuzzle(String filePath) throws FileNotFoundException{
		
		
		// setting a string array to hold each row in the file
		String [] rows = new String[maxRows]; 
		
		
		String path = filePath;
		File filereader = new File(path);//setting the file that will  be used
		Scanner reader = new Scanner(filereader);

		
		for (int i = 0; i< rows.length; i++) {
			String data = reader.nextLine();// reads the line of the file and saves it to a variable
			rows[i] = data;// stores lines from file in array 
		}
		
		char num = '0';
		int column = 0;
		
		for (int j = 0; j< rows.length; j++) {
			String currentRow = rows[j];// takes the string from the first array
			
			for (int i = 0; i < currentRow.length(); i++) {
				
				
				if (i%2 == 0) {// gets rid of the spaces in the 
					num = currentRow.charAt(i);//takes the specific characters out of the string
					int number = Character.getNumericValue(num);//changes the character to an int
					grid[j][column] = number;//saves the integer in the 2d array
					if(column < 9) {
						column++;
					}
					//this resets the column counter
					if (column == 9) {
						column = 0;
					}
				}
			}	
		}
		
		return grid;
	}
	
	/**
	 * pulls numbers out of the arraylist if they are in the row or column
	 * @param row
	 * @param col
	 * @return
	 */
	
	public ArrayList <Integer> getCandidates(int row, int col){
		ArrayList <Integer> dictionary = new ArrayList<> ();
		
		for (int i = 1; i<10; i++) {
			dictionary.add(i);
		}
		
		for(int column = 0; column < grid.length; column++) {
			
			for(int j = 0; j < dictionary.size(); j++ )
			if (grid[row][column] == dictionary.get(j)) {
				dictionary.remove(j);
				j--;
			}
		}
		for(int currentRow = 0; currentRow < grid.length; currentRow++) {
			
			for(int j = 0; j < dictionary.size(); j++ )
			if (grid[currentRow][col] == dictionary.get(j)) {
				dictionary.remove(j);
				j--;
			}
		}
		
		
		return dictionary;
		
	}
	
	/**
	 * Checks if the row is solved properly
	 * 
	 * @param row
	 * @return
	 */
	
	public boolean isRowValid(int row) {
		boolean rowCheck = false;
		int space = 0;
		int rowTotal = 0;
		
		
		// these nested for loops check to see that 
		// each value 1-9 only comes once
			int [] dictionary = {1,2,3,4,5,6,7,8,9};
			for(int column = 0; column < grid.length; column++) {
				space = grid[row][column];
				for(int i = 0; i < dictionary.length; i++) {
					if (dictionary[i] == space) {
						dictionary[i] = 0;
					}
				}
			// adds up the total and if it is 0 then each number is only used once.
			for(int j = 0; j < dictionary.length; j++) {
				rowTotal += dictionary[j];
			}
			if(rowTotal == 0) {
				rowCheck = true;
			}else {
				rowCheck = false;
			}
		}
		return rowCheck;
		
	}
	
	
	/**
	 * checks the columns value and makes sure its correct
	 * @param col
	 * @return
	 */
	public boolean isColumnValid(int col) {
		boolean columnCheck = false;
		int space = 0;
		int columnTotal = 0;
		
		// these nested for loops check to see that 
		// each value 1-9 only comes once

			int [] dictionary = {1,2,3,4,5,6,7,8,9};
			
			for(int row = 0; row < grid.length; row++) {
				space = grid[row][col];
				
				for(int i = 0; i < dictionary.length; i++) {
					if (dictionary[i] == space) {
						dictionary[i] = 0;
					}
				}
			}
			
			// test to see if the column only used the 9 numbers
			for(int j = 0; j < dictionary.length; j++) {
				columnTotal += dictionary[j];
			}
			if(columnTotal == 0) {
				columnCheck = true;
			}else {
				columnCheck = false;
			}
		
		return columnCheck;
		
	}
	
	public boolean isSubGridValid(int row, int col) {
		
		return false;
		
	}
	
	/**
	 * solves the sudoku problem
	 * @return solved grid
	 */
	public int[][] solve() {
		
		int[][] resetArray = new int[maxRows][maxColumns];
		boolean rows = false;
		boolean col = false;
		ArrayList <Integer> dictionary = new ArrayList<> ();
		int num;
		Random rand = new Random();
		for (int i = 0; i < resetArray.length; i++) {
			for(int j = 0; j < resetArray.length; j++) {
				resetArray[i][j] = grid[i][j];
			}
		}	
		
		do {
			
			for (int i = 0; i < resetArray.length; i++) {
				for(int j = 0; j < resetArray.length; j++) {
					grid[i][j] = resetArray[i][j];
				}
			}	
			
			for(int row = 0; row < grid.length; row++) {
				for(int column = 0; column < grid.length; column++) {
					if (grid[row][column] == 0) {
						dictionary = getCandidates(row,column); // gets the possible numbers
						if(dictionary.size() == 0) {
							break;
						}
					
						num = rand.nextInt(dictionary.size());// gives a random integer
						grid[row][column] = dictionary.get(num);// slightly less random number in spot
					}
					if(dictionary.size() == 0) // breaks out of the loop if no options are available
						break;
					col = isColumnValid(column);
				}
				if(dictionary.size() == 0)
					break;
				rows = isRowValid(row);
			}	
			gridsGenerated++; // iterates so that we know how many grids are made
		}while (col == false && rows == false);

		
		return grid;
	}
	public String toString() {
		return null;
	}
	
}
