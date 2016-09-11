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
			int row = 0;
			while ((line = reader.readLine()) != null) {
				for (int col = 0; col < line.length(); col++) {
					Integer value = Character.getNumericValue(line.charAt(col));
					Cell cell = cells.get(row).get(col);
					Set<Integer> candidatesToRemove = new HashSet<Integer>();
					Set<Integer> candidatesCurrent = cell.getCandidates();
					candidatesCurrent.remove(value);
					candidatesToRemove.addAll(candidatesCurrent);
					if (cell.removeCandidates(candidatesToRemove)) {
						resolve(row, col);
					}
				}
				row++;
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void getComplement(int rowNumber, int colNumber) {
		Set<Cell> complement = new HashSet<Cell>();
		
		ArrayList<Cell> cellsRow = cells.get(rowNumber);
		ArrayList<Cell> cellsCol = new ArrayList<Cell>();
		ArrayList<Cell> cellsBox = new ArrayList<Cell>();
		
		for (int row = 0; row < 9; row++) {
			cellsCol.add(cells.get(row).get(colNumber));
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				cellsBox.add(cells.get(rowNumber + i))
			}
		}
		
	}
	
	private void resolve(int row, int column) {
		
	}
	
	private boolean isSolved() {
		for (ArrayList<Cell> row : cells) {
			for (Cell cell : row) {
				if (!cell.isSolved()) {
					return false;
				}
			}
		}
		return true;
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
