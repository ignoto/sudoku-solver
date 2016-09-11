import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String [] args) {
		Path file = Paths.get("../SudokuSolver/puzzles/sudoku_beginner.txt");
		Board board = new Board(file.toAbsolutePath().normalize().toString());

		System.out.print(board.toString());
	}
	
}
