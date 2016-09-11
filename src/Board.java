import java.util.ArrayList;
import java.util.Set;

public class Board {

	private ArrayList<ArrayList<Cell>> cells;
	
	public Board() {
		cells = new ArrayList<ArrayList<Cell>>();
		for (int row = 0; row < 9; row++) {
			cells.add(new ArrayList<Cell>());
		}
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				cells.get(row).add(new Cell());
			}
		}
	}
	
	public int getRowCount() {
		return cells.size();
	}
	
	public int getColumnCount() {
		return cells.get(0).size();
	}
	
	public void setValue(int row, int column, Integer value) {
		cells.get(row).get(column).setValue(value);
	}
	
	private void removeCandidateRow(int index, Integer candidate) {
		for (Cell cell : cells.get(index)) {
			cell.removeCandidate(candidate);
		}
	}
	
	private void removeCandidateColumn(int index, Integer candidate) {
		for (ArrayList<Cell> row : cells) {
			row.get(index).removeCandidate(candidate);
		}
	}
	
	private void removeCandidateBox(int indexRow, int indexColumn, Integer candidate) {
		int rowStart = indexRow - indexRow % 3;
		int columnStart = indexColumn - indexColumn % 3;
		for (int row = rowStart; row < rowStart + 3; row++) {
			for (int column = columnStart; column < columnStart + 3; column++) {
				cells.get(row).get(column).removeCandidate(candidate);
			}
		}
	}
	
	private void validateRows() {
		for (int value = 1; value <= 9; value++) {
			for (int row = 0; row < 9; row++) {
				int count = 0;
				for (Cell cell : cells.get(row)) {
					count += cell.getValue() == value ? 1 : 0;
				}
				if (count > 1) {
					System.out.println("Row " + row + ": " + value + "(" + count + ")");
				}
			}
		}
	}
	
	private void validateColumns() {
		for (int value = 1; value <= 9; value++) {
			for (int column = 0; column < 9; column++) {
				int count = 0;
				for (ArrayList<Cell> row : cells) {
					count += row.get(column).getValue() == value ? 1 : 0;
				}
				if (count > 1) {
					System.out.println("Column " + column + ": " + value + "(" + count + ")");
				}
			}
		}
	}
	
	private void validateBoxes() {
		ArrayList<ArrayList<Cell>> boxes = new ArrayList<ArrayList<Cell>>();
		for (int i = 0; i < 9; i++) {
			boxes.add(new ArrayList<Cell>());
		}
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				boxes.get(3 * (row/3) + column/3).add(cells.get(row).get(column));
			}
		}
		for (int value = 1; value <= 9; value++) {
			for (int box = 0; box < 9; box++) {
				int count = 0;
				for (Cell cell : boxes.get(box)) {
					count += cell.getValue() == value ? 1 : 0;
				}
				if (count > 1) {
					System.out.println("Box " + box + ": " + value + "(" + count + ")");
				}
			}
		}
	}
	
	private int getIndexRow(Cell cell) {
		for (int row = 0; row < 9; row++) {
			if (cells.get(row).contains(cell)) {
				return row;
			}
		}
		return -1;
	}
	
	private int getIndexColumn(Cell cell) {
		for (int row = 0; row < 9; row++) {
			if (cells.get(row).contains(cell)) {
				return cells.get(row).indexOf(cell);
			}
		}
		return -1;
	}
	
	private int getHiddenSingleRow(Cell cell) {
		int indexRow = getIndexRow(cell);
		ArrayList<Cell> row = new ArrayList<Cell>(cells.get(indexRow));
		row.remove(cell);
		Set<Integer> candidates = cell.getCandidates();
		
		for (Cell cellCurrent : row) {
			candidates.removeAll(cellCurrent.getCandidates());
		}
		
		return candidates.size() == 1 ? (int) candidates.toArray()[0] : -1;
	}
	
	private int getHiddenSingleColumn(Cell cell) {
		int indexColumn = getIndexColumn(cell);
		
		ArrayList<Cell> column = new ArrayList<Cell>();
		for (int row = 0; row < 9; row++) {
			column.add(cells.get(row).get(indexColumn));
		}
		
		column.remove(cell);
		
		Set<Integer> candidates = cell.getCandidates();
		
		for (Cell cellCurrent : column) {
			candidates.removeAll(cellCurrent.getCandidates());
		}
		
		return candidates.size() == 1 ? (int) candidates.toArray()[0] : -1;
	}
	
	private int getHiddenSingleBox(Cell cell) {
		int indexRow = getIndexRow(cell);
		int indexColumn = getIndexColumn(cell);
		
		int rowStart = indexRow - indexRow % 3;
		int columnStart = indexColumn - indexColumn % 3;
		
		ArrayList<Cell> box = new ArrayList<Cell>();
		for (int row = rowStart; row < rowStart + 3; row++) {
			for (int column = columnStart; column < columnStart + 3; column++) {
				box.add(cells.get(row).get(column));
			}
		}
		
		box.remove(cell);
		
		Set<Integer> candidates = cell.getCandidates();
		
		for (Cell cellCurrent : box) {
			candidates.removeAll(cellCurrent.getCandidates());
		}
		
		return candidates.size() == 1 ? (int) candidates.toArray()[0] : -1;
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
	
	private void applySinglesRule() {
		boolean success = true;
		while (success) {
			success = false;
			for (int row = 0; row < 9; row++) {
				for (int column = 0; column < 9; column++) {
					Cell cell = cells.get(row).get(column);
					if (cell.isUpdated() && cell.isSolved()) {
						removeCandidateRow(row, cell.getValue());
						removeCandidateColumn(column, cell.getValue());
						removeCandidateBox(row, column, cell.getValue());
						cell.setUpdated(false);
						success = true;
					}
				}
			}
		}
	}
	
	private void applyHiddenSinglesRule() {
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Cell cell = cells.get(row).get(column);
				if (!cell.isSolved()) {
					int singleRow;
					int singleColumn;
					int singleBox;
					if ((singleRow = getHiddenSingleRow(cell)) != -1) {
						setValue(row, column, singleRow);
					}
					else if ((singleColumn = getHiddenSingleColumn(cell)) != -1) {
						setValue(row, column, singleColumn);
					}
					else if ((singleBox = getHiddenSingleBox(cell)) != -1) {
						setValue(row, column, singleBox);
					}
				}
			}
		}
	}
	
	public void solve() {
		int nIterations = 0;
		while (nIterations < 100 && !isSolved()) {
			
			applySinglesRule();
			applyHiddenSinglesRule();
			
			nIterations++;
		}
	}
	
	public void validate() {
		validateRows();
		validateColumns();
		validateBoxes();
	}
	
	public String toString() {
		String str = "";
		int[] longest = new int[9];
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				Cell cell = cells.get(row).get(column);
				str = cell.getValue() == 0 ? cell.getCandidates().toString() : cell.getValue().toString();
				str += cell.isUpdated() ? "M" : "";
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
				str = cell.getValue() == 0 ? cell.getCandidates().toString() : cell.getValue().toString();
				str += cell.isUpdated() ? "M" : "";
				strReturn += String.format("%1$-" + (longest[column] + 1) + "s", str);
			}
			strReturn += "\n";
		}
		
		return strReturn;
	}
	
}
