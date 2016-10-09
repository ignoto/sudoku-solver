import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String [] args) {
		Path file = Paths.get("../SudokuSolver/puzzles/sudoku_expert.txt");
		Board board = new Board(file.toAbsolutePath().normalize().toString());

		System.out.println("Original:");
		System.out.print(board.toString());
		board.solve(Algorithm.Backtrack);
		System.out.println("Solved:");
		System.out.print(board.toString());
	}
	
}
