import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

	public static void main(String [] args) {
		Board board = new Board();
		
		String line;
		try (
		    InputStream fis = new FileInputStream("C:/Users/Jesper/Desktop/SudokuPuzzle.txt");
		    InputStreamReader isr = new InputStreamReader(fis);
		    BufferedReader br = new BufferedReader(isr);
		) {
			int row = 0;
		    while ((line = br.readLine()) != null) {
		    	for (int column = 0; column < line.length(); column++) {
		    		Integer value = Integer.parseInt(line.substring(column, column + 1));
		    		board.setValue(row, column, value);
		    	}
		    	row++;
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		board.solve();
		board.validate();
		System.out.print(board.toString());
	}
	
}
