
public class QuickFind implements UnionFind{
	
	
	private int[] id;
	private int count; //the number of connected components
	
	public QuickFind(int n) {
		id = new int[n]; 
		count = n; //n number of connected componets
		
		initialize();
	}
	
	//id[0] = 0
	//id[1] = 1
	//id[2] = 2
	//complexity: N
	private void initialize() {
		for (int i = 0; i < id.length; i++) {
			id[i] = i;
		}
	}
	
	
	@Override
	public void union(int p, int q) {
		if(isConnected(p, q))
			return; 
		
		int pid = id[p];
		int qid = id[q];
		
		for (int i = 0; i < id.length; i++) {
			if(id[i]==pid)
				id[i] = qid;
		}
		
		count--;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return id[p] == id[q];
	}

	@Override
	public int find(int p) {
		return id[p];
	}

	@Override
	public int getConnectedComponentCount() {
		return count;
	}

}
