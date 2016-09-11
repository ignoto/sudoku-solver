import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

	public static void main(String [] args) {
		Board board = new Board();
		board.solve();
		System.out.print(board.toString());
	}
	
}
