
public class WeightedQuickUnion extends QuickUnion{
	
	private int[] size;
	
	public WeightedQuickUnion(int n) {
		super(n);
		
		size = new int[n];
		
		for (int i = 0; i < id.length; i++) {
			size[i] = 1;
		}
		
	}

	@Override
	public void union(int p, int q) {
		int pRoot = root(p);
		int qRoot = root(q);
		
		if(pRoot == qRoot)
			return;
		
		if(size[pRoot] < size[qRoot]) {
			id[pRoot] = qRoot;
			size[qRoot] += size[pRoot];
		}else {
			id[qRoot] = pRoot;
			size[pRoot] += size[qRoot];
		}
		
		count--;
		
		
	}

}
