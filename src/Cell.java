public class Cell {
	private int value;
	private boolean isSolved;
	
	public Cell(int value, boolean isSolved) {
		this.value = value;
		this.isSolved = isSolved;
	}
	
	public boolean getIsSolved() {
		return this.isSolved;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setIsSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}
	
}
