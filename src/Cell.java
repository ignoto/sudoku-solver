import java.util.HashSet;
import java.util.Set;

public class Cell {
	private Integer value;
	private Set<Integer> candidates;
	private boolean updated;
	
	public Cell() {
		candidates = new HashSet<Integer>();
		for (int i = 1; i <= 9; i++) {
			candidates.add(new Integer(i));
		}
		value = 0;
		updated = false;
	}
	
	public void setValue(Integer value) {
		this.value = value;
		if (value != 0) {
			candidates.clear();
			candidates.add(value);
			updated = true;
		}
	}
	
	public Integer getValue() {
		return value;
	}
	
	public Set<Integer> getCandidates() {
		Set<Integer> set = new HashSet<Integer>(candidates);
		return set;
	}
	
	public void removeCandidate(Integer candidate) {
		candidates.remove(candidate);
		if (candidates.size() == 1) {
			setValue((Integer) candidates.toArray()[0]);
		}
	}
	
	public boolean isSolved() {
		return value != 0;
	}
	
	public boolean isUpdated() {
		return updated;
	}
	
	public void setUpdated(boolean value) {
		updated = value;
	}
}
