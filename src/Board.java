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
		
		// Create cells
		cells = new ArrayList<ArrayList<Cell>>();
		for (int row = 0; row < 9; row++) {
			cells.add(new ArrayList<Cell>());
		}
		
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				cells.get(row).add(new Cell());
			}
		}
				
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String line = "";
			int rowNumber = 0;
			while ((line = reader.readLine()) != null) {
				for (int colNumber = 0; colNumber < line.length(); colNumber++) {
					Integer value = Character.getNumericValue(line.charAt(colNumber));
					if (value != 0) {
						Cell cell = cells.get(rowNumber).get(colNumber);
						Set<Integer> candidatesToRemove = new HashSet<Integer>();
						Set<Integer> candidatesCurrent = cell.getCandidates();
						candidatesCurrent.remove(value);
						candidatesToRemove.addAll(candidatesCurrent);
						if (cell.removeCandidates(candidatesToRemove)) {
							resolve(cell);
						}
					}
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
	
	private void resolve(Cell cell) {
		Set<Cell> complement = getComplement(cell);
						
		for (Cell cellComp : complement) {
			if (cellComp.removeCandidates(cell.getCandidates())) {
				resolve(cell);
			}
		}
	}
	
	private int getRowNumber(Cell cell) {
		for (int rowNumber = 0; rowNumber < 9; rowNumber++) {
			if (cells.get(rowNumber).contains(cell)) {
				return rowNumber;
			}
		}
		return -1;
	}
	
	private int getColNumber(Cell cell) {
		for (int rowNumber = 0; rowNumber < 9; rowNumber++) {
			for (int colNumber = 0; colNumber < 9; colNumber++) {
				if (cells.get(rowNumber).get(colNumber).equals(cell)) {
					return colNumber;
				}
			}
		}
		return -1;
	}
	
	public String toString() {
		String str = "";
		int[] longest = new int[9];
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Cell cell = cells.get(row).get(column);
				str = cell.getCandidates().toString();
				int length = str.length();
				if (length > longest[column]) {
					longest[column] = length;
				}
			}
		}
		
		String strReturn = "";
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Cell cell = cells.get(row).get(column);
				str = cell.getCandidates().toString();
				strReturn += String.format("%1$-" + (longest[column] + 1) + "s", str);
			}
			strReturn += "\n";
		}
		
		return strReturn;
	}
	
}
