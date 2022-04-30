public class WeightedQuickUnionPC extends WeightedQuickUnion{

   public WeightedQuickUnionPC(int n) {
      super(n);
   }
   
   @Override
   protected int root(int p) {
      int root = p;
      while(id[p] != root) {
			root = id[p];
		}
      while(p != root) {
         int temp = id[p];
         id[p] = root;
         p = temp;
      }
      return root;
   }
}