import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Board {

	private ArrayList<ArrayList<Cell>> cells;
	
	public Board(String fileName) {
		cells = new ArrayList<ArrayList<Cell>>();
				
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String line;
			int rowNumber = 0;
			while ((line = reader.readLine()) != null) {
				cells.add(new ArrayList<Cell>());
				for (int colNumber = 0; colNumber < line.length(); colNumber++) {
					int value = Character.getNumericValue(line.charAt(colNumber));
					cells.get(rowNumber).add(new Cell(value, value > 0 && value <= 9));
				}
				rowNumber++;
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private Set<Cell> getComplement(Cell cell) {
		Set<Cell> complement = new HashSet<Cell>();
		int thisRowNumber = getRowNumber(cell);
		int thisColNumber = getColNumber(cell);
		
		ArrayList<Cell> cellsRow = cells.get(thisRowNumber);
		ArrayList<Cell> cellsCol = new ArrayList<Cell>();
		ArrayList<Cell> cellsBox = new ArrayList<Cell>();
		
		for (int rowNumber = 0; rowNumber < 9; rowNumber++) {
			cellsCol.add(cells.get(rowNumber).get(thisColNumber));
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cellsBox.add(cells.get(3*(thisRowNumber/3) + i%3).get(3*(thisColNumber/3) + j%3));
			}
		}
		
		complement.addAll(cellsRow);
		complement.addAll(cellsCol);
		complement.addAll(cellsBox);
		complement.remove(cell);
		
		return complement;
	}
	
	private int getRowNumber(Cell cell) {
		for (int rowNumber = 0; rowNumber < 9; rowNumber++)
			if (cells.get(rowNumber).contains(cell))
				return rowNumber;
		return -1;
	}
	
	private int getColNumber(Cell cell) {
		for (int rowNumber = 0; rowNumber < 9; rowNumber++)
			for (int colNumber = 0; colNumber < 9; colNumber++)
				if (cells.get(rowNumber).get(colNumber).equals(cell))
					return colNumber;
		return -1;
	}
	
	private boolean inComplement(Cell cell, int number) {
		Set<Cell> complement = getComplement(cell);
		
		for (Cell cellCurrent : complement) {
			if (cellCurrent.getValue() == number) {
				return true;
			}
		}
		return false;
	}
	
	private void backtrack() {
		int nx = 0, ny = 0;
		
		boolean backtrack = false;
		while (nx < 9) {
			Cell cell  = cells.get(ny).get(nx);
			if (!cell.getIsSolved()) {
				backtrack = true;
				for (int testNumber = Math.max(cell.getValue()+1, 1); testNumber <= 9; testNumber++) {
					if (!inComplement(cell, testNumber)) {
						cell.setValue(testNumber);
						backtrack = false;
						break;
					}
				}
				if (backtrack) {
					cell.setValue(0);
					if (nx == 0) {
						nx = 8;
						--ny;
					}
					else {
						--nx;
					}
				}
				else {
					if (nx == 8) {
						nx = ny != 8 ? 0 : ++nx;
						++ny;
					}
					else {
						 ++nx;
					}
				}
			}
			else {
				if (backtrack) {
					if (nx == 0) {
						nx = 8;
						--ny;
					}
					else {
						--nx;
					}
				}
				else {
					if (nx == 8) {
						nx = ny != 8 ? 0 : ++nx;
						++ny;
					}
					else {
						 ++nx;
					}
				}
			}
		}
	}
	
	public void solve(Algorithm algorithm) {
		if (algorithm == Algorithm.Backtrack) {
			backtrack();
		}
	}
	
	public String toString() {
		String str = "";
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Cell cell = cells.get(row).get(column);
				str += (cell.getValue() != 0 ? cell.getValue() : "-") + " ";
			}
			str += "\n";
		}
		
		return str;
	}
	
}
