import java.util.HashSet;
import java.util.Set;

public class Cell {
	private Set<Integer> candidates;
	
	public Cell() {
		
		// Add candidates
		candidates = new HashSet<Integer>();
		for (int i = 1; i <= 9; i++) {
			candidates.add(new Integer(i));
		}
		
	}
	
	public Set<Integer> getCandidates() {
		Set<Integer> set = new HashSet<Integer>(candidates);
		return set;
	}
	
	public boolean removeCandidates(Set<Integer> candidates) {
		
		return this.candidates.removeAll(candidates) && isSolved();
	}
	
	public boolean isSolved() {
		return candidates.size() == 1;
	}
	
}
