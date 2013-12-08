package structures;

public class Neighbor {
	public int vnum;
	public Neighbor next;
	public Neighbor(int vnum, Neighbor neighbor) {
		this.vnum = vnum;
		next = neighbor;
	}
}