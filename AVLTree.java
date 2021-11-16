/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	private  IAVLNode root;
	private IAVLNode min;
	private IAVLNode max;
	private int Height;
	private int Size;

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
  	return root == null ;// root == null iff the tree is empty
  }


 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k){

  	IAVLNode node = this.find(k); // find search for node with key = K

	  if (node != null){           // if found return the value of the key
  		return node.getValue();}
	  else{
  		return null;}}	// if not return null



  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	  return 420;	// to be replaced by student code
   }

  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k){
   	IAVLNode nDelete = this.find(k); // searching for node with key = K that is about to be deleted
   	if (nDelete == null){ return -1;} // if node wasn't found return -1

	else{
		IAVLNode toBeRebalance = this.deleteRetrieve(nDelete); // delete nDelete and retrieve the node that rebalancing should start from
		int cnt = this.reBalanceDelete(toBeRebalance,0); // counting rotates and demotes...
		return cnt;	}
   }

	private int reBalanceDelete(IAVLNode node, int i) {
   		int L = node.getRankLeft();
   		int R = node.getRankRight();

   		if (L== 3 && R == 1){
   			IAVLNode RightChild = node.getRight();
   			int RL = RightChild.getRankLeft();
   			int RR = RightChild.getRankRight();

   			if (RL == 1 && RR ==1){
   				i += reBalanceCase3111(node, RightChild, true , i);

			}

   			else if (RL == 2 && RR == 1){
   				i += reBalanceCase3121(node, RightChild , true , i);
   				return i;
				}

   			else if (RL == 1 && RR == 2){
					i += reBalanceCase3112(node,RightChild,RightChild.getLeft(),true, i);}
				return i;}


   		else if (L == 1 && R== 3 ){
			IAVLNode LeftChild = node.getLeft();
			int LL = LeftChild.getRankLeft();
			int LR = LeftChild.getRankRight();

			if (LL == 1 && LR ==1){}

			else if (LL == 2 && LR == 1){}

			else if (LL == 1 && LR == 2){}


		}
   		else if (L == 2 && R == 2){

		}
   		else {
   			return i ;}
	}

	private IAVLNode deleteRetrieve(IAVLNode nDelete) {
   		IAVLNode returnNode = null;
		if ((!nDelete.getLeft().isRealNode()) && (!nDelete.getRight().isRealNode())) { // true iff nDelete is Leaf
			returnNode = deleteRetrieveLeaf(nDelete);}

		else if ((nDelete.getLeft().isRealNode()) && (!nDelete.getRight().isRealNode())) { // unary node with only left node
			returnNode = deleteRetrieveLeft(nDelete);}

		else if ((nDelete.getLeft().isRealNode()) && (!nDelete.getRight().isRealNode())) { // unary node with only Right node
			returnNode = deleteRetrieveRight(nDelete);}

		else if ((nDelete.getLeft().isRealNode()) && (nDelete.getRight().isRealNode())) { // nDelete has two child (happy family!)
			returnNode = deleteRetrieveFamily(nDelete);}

		return returnNode;}

	private IAVLNode deleteRetrieveFamily(IAVLNode nDelete) { // delete & retrieve node to be balance for root with two children
		IAVLNode parent = nDelete.getParent();
		IAVLNode mySuccessor = this.successor(nDelete); //finds the successor that will replace the deleted node
		IAVLNode tmp = deleteRetrieve(mySuccessor);

		if (nDelete == this.root) {  // root special case
			this.root = mySuccessor;}
		else {							// updating my parent
			mySuccessor.setParent(parent);
			if (nDelete.getKey() < parent.getKey()) {
				parent.setLeft(mySuccessor);}
			else {
				parent.setRight(mySuccessor);}
		}
		//replacing nDelete with his successor
		mySuccessor.setLeft(nDelete.getLeft());
		mySuccessor.setRight(nDelete.getRight());
		mySuccessor.setHeightAlone(); // updating successor by the new sons
		return tmp;}

	private IAVLNode successor(IAVLNode node) { // finding the successor of a node

   		if ( node.getRight().isRealNode()){ // if I have a right child so my successor is on that sub tree
   			return  myMin(node.getRight());}

   		//else my successor is above me
   		IAVLNode parent = node.getParent();

   		while (parent != null && node == parent.getRight()){ // go up till you come from the left or you are in the root
   			node = parent;
   			parent = node.getParent(); }
   		return parent;}

	private IAVLNode myMin(IAVLNode node) { // finding the most minimum node in my sub tree
   		while (node.getLeft().isRealNode()){ // go left if you can
   			node = node.getLeft();}
   		return node;}


	private IAVLNode deleteRetrieveRight(IAVLNode nDelete) { // delete & retrieve node to be balance for root with one right children
		IAVLNode parent = nDelete.getParent();

		if (nDelete == this.root) {  // root special case
			this.root = nDelete.getRight();
			root.setHeightAlone(); // updating height due to new state after deletion
			return this.root;}
		else if (nDelete.getKey() < parent.getKey()) { // nDelete is left child of parent
			parent.setLeft(nDelete.getRight()); // byPass
			parent.setHeightAlone(); // updating height due to new state after deletion
			return parent;}
		else {        // nDelete is right child of parent
			parent.setRight(nDelete.getRight()); // byPass
			parent.setHeightAlone(); // updating height due to new state after deletion
			return parent;
		}}

	private IAVLNode deleteRetrieveLeft(IAVLNode nDelete) { // delete & retrieve node to be balance for root with one left children
		IAVLNode parent = nDelete.getParent();

		if (nDelete == this.root) {  // root special case
			this.root = nDelete.getLeft();
			parent.setHeightAlone(); // updating height due to new state after deletion
			return this.root;}

		else if (nDelete.getKey() < parent.getKey()) { // nDelete is left child of parent
			parent.setLeft(nDelete.getLeft()); // byPass
			parent.setHeightAlone(); // updating height due to new state after deletion
			return parent;}

		else {        // nDelete is right child of parent
			parent.setRight(nDelete.getLeft()); // byPass
			parent.setHeightAlone(); // updating height due to new state after deletion
			return parent;}}




	private IAVLNode deleteRetrieveLeaf(IAVLNode nDelete) { // delete & retrieve node to be balance for root that is a Leaf
		IAVLNode parent = nDelete.getParent();
		
		if (nDelete == this.root) {  // root special case
			this.root = null;
			return this.root;}

		else if (nDelete.getKey() < parent.getKey()) { // nDelete is left child of parent
			parent.setLeft(nDelete.getLeft()); // byPass
			parent.setHeightAlone(); // updating height due to new state after deletion
			return parent;}

		else {        // nDelete is right child of parent
			parent.setRight(nDelete.getRight()); // byPass
			parent.setHeightAlone(); // updating height due to new state after deletion
			return parent;
		}}


	/**
    * public StringBe min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   return "minDefaultString"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   return "maxDefaultString"; // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
        return new int[33]; // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
        return new String[55]; // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   return 422; // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return null;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   return null; 
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   return -1;
   }

   private IAVLNode find(int k){ // finding node with key = K
   	IAVLNode curr = this.root;

   	while (curr != null && curr.isRealNode()){ // check if dead end

   		if ( k== curr.getKey()){ //found node with key = K
   			return curr;}

   		else if (k < curr.getKey()){ // go left
   			curr = curr.getLeft();}

   		else { curr = curr.getRight();} // go right
	}
   	if (curr == null){return null;}
   	else if (curr.isRealNode()){ // if found real return the node
   		return curr;}
   	else { return null;}} // if not return null


	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
		public int getRankLeft();
		public int getRankRight();
		public void setHeightAlone();
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{

	  private int key;
	  private String info;
	  private IAVLNode Left;
	  private IAVLNode Right;
	  private int Height;
	  private IAVLNode parent;
	  private static final IAVLNode VirtualNode = new AVLNode();// same digital node for all real nodes

	  public AVLNode(Integer key, String info){
		  this.key = key;
		  this.info = info;
		  this.Left = VirtualNode;
		  this.Right = VirtualNode;

	  }
	   public AVLNode() { //default constructor for digital node
		   this.key = -1;
		   this.info = null;
		   this.Left = null;
		   this.Right = null;
		   this.Height = -1;
	   }



		   public int getKey(){
		   	return this.key;
		   }

		public String getValue(){
			return this.info;		}

		public void setLeft(IAVLNode node){
	  		this.Left = node;	}

		public IAVLNode getLeft(){
	  		return this.Left;
		}


		public void setRight(IAVLNode node){
  			this.Right = node;}

		public IAVLNode getRight() {
	  		return this.Right;}


		public void setParent(IAVLNode node){
			this.parent = node;
		}
		public IAVLNode getParent(){
	  		return this.parent;
		}

		public boolean isRealNode(){
	  		return this.key != -1; // node is real iff node.key != -1
		}

	    public void setHeight(int height){
	  		this.Height = height;
	    }

	    public int getHeight(){
	      return this.Height; // to be replaced by student code
	    }

	   @Override
	   public int getRankLeft() { // return the the difference rank from left
		   return this.Height - this.getLeft().getHeight();
	   }

	   @Override
	   public int getRankRight() { // return the the difference rank from right
		   return this.Height - this.getRight().getHeight();
	   }

	   @Override
	   public void setHeightAlone() { //updating the height of a node
		   this.Height = Math.max(this.Left.getHeight(), this.Right.getHeight()) +1;
	   }
   }

}
  
