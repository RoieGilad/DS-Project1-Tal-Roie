import java.util.Iterator;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree  {
	private  IAVLNode root;
	private IAVLNode min;
	private IAVLNode max;
	private int Height;
	private int size;

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

  	IAVLNode node = this.find(k); // find searching for node with key = k

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


  private IAVLNode place_to_insert(IAVLNode node) { //the last node to insert
	  IAVLNode search = this.root;
	  IAVLNode place = search;
	  while (search != null) {
		  place = search;
		  if (search.getKey() == node.getKey()){
			  return search;
		  }
		  if (search.getKey() > node.getKey()) {
			  search = search.getLeft();
		  } else  {
			  search = search.getRight();
		  }

	  }
	  return place;
  }


  private int rebalance(IAVLNode node, int d){
	  int left_dist = node.getHeight()-node.getLeft().getHeight();
	  int right_dist = node.getHeight()- node.getRight().getHeight();

	  if ((left_dist == 0 && right_dist == 1) || (left_dist == 1 && right_dist == 0))
	  {
		  node.setHeight(node.getHeight()+1); //promote
		  rebalance(node.getParent(), d+1);
	  }
	  else if (left_dist == 0 && right_dist == 2) {
		  int left_left_dist = node.getLeft().getHeight()-node.getLeft().getLeft().getHeight();
		  //int left_right_dist = node.getLeft().getHeight()- node.getLeft().getRight().getHeight();
		  if (left_left_dist == 1) { //then left right dist = 2{
			  node.setHeight(node.getHeight() - 1); //demote
			  rotate_right(node);
			  return d + 1;

		  } else { //left left dist =2, left right dist = 1
			  node.setHeight(node.getHeight() - 1); //demote z
			  node.getLeft().setHeight(node.getLeft().getHeight() - 1); //demote x
			  node.getLeft().getRight().setHeight(node.getLeft().getRight().getHeight() +1); //promote b
			  rotate_left(node.getLeft());
			  rotate_right(node);
			  return d+ 2;

		  }
	  }
	  else if (right_dist == 0 && left_dist == 2) {
		  int right_right_dist = node.getRight().getHeight() - node.getRight().getRight().getHeight();
		  if (right_right_dist == 1) { //then right left dist = 2{
			  node.setHeight(node.getHeight() - 1); //demote
			  rotate_left(node);
			  return d + 1;
		  } else { //right right dist =2, right left dist = 1
			  node.setHeight(node.getHeight() - 1); //demote z
			  node.getRight().setHeight(node.getRight().getHeight() - 1); //demote x
			  node.getRight().getLeft().setHeight(node.getRight().getLeft().getHeight() + 1); //promote b
			  rotate_right(node.getRight());
			  rotate_left(node);
			  return d + 2;

		  }
	  }

	  return 0;
  }

	private void rotate_left(IAVLNode node1 , IAVLNode node2) {
  		node1.setRight(node2.getLeft());
  		node1.getRight().setParent(node1);
  		node2.setLeft(node1);
  		if (node1.getParent() != null) {
  			if (node1.getParent().getKey() < node1.getKey()){
  				node1.getParent().setRight(node2);}
  			else{
  				node1.getParent().setLeft(node2);}}
			node2.setParent(node1.getParent());
			node1.setParent(node2);
		}



	private void rotate_right(IAVLNode node , IAVLNode node2){
	  IAVLNode left_child = node.getLeft();
	  node.setLeft(left_child.getRight());
	  node.getLeft().setParent(node);
	  if (node.getParent() != null){

		  if (node.getParent().getKey() > node.getKey()){
			  node.getParent().setLeft(left_child);
		  } else {
			  node.getParent().setRight(left_child);
		  }
	  }
	  left_child.setParent(node.getParent());
	  left_child.setRight(node);
	  node.setParent(left_child);

  }




  public int insert(int k, String i) {
	IAVLNode new_node = new AVLNode(k, i);
	if (root == null) {
		root = new_node;
		min = new_node;
		max = new_node;
		size = 1;
		return 0;
	}

	IAVLNode place_to_insert = place_to_insert(new_node);
	if (place_to_insert.getKey() == k){
		return -1;
	}
	new_node.setParent(place_to_insert);
	if (place_to_insert.getKey() > k){
		place_to_insert.setLeft(new_node);
	} else {
		place_to_insert.setRight(new_node);
	}
	if (k < min.getKey()){ //update min
		min = new_node;
	}
	if (k > max.getKey()){ //update max
		max = new_node;
	}
	size = size + 1;
	return rebalance(place_to_insert, 0);
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
		if (this.size() > 1 ){ // updating min or max if necessary
			if (nDelete.getKey() == this.min.getKey()){
				this.min = this.successor(nDelete);}
			if (nDelete.getKey() == this.max.getKey()){
				this.max = this.predecessor(nDelete);}
		}
		size -= 1;
		IAVLNode toBeRebalance = this.deleteRetrieve(nDelete); // delete nDelete and retrieve the node that rebalancing should start from

		if (toBeRebalance == null){return 0;}
		else {
		   return this.reBalanceDelete(toBeRebalance,0);}}
   }


// from here if sub Function for the delete function
	/** sub Functions for delete
	 * private int reBalanceDelete(IAVLNode node)
	 * takes a node that maybe need to rebalance and rebalance it while counting rotates and promotes/demotes
	 * @return the count of rotates and promotes/demotes
	 */
	private int reBalanceDelete(IAVLNode node , int cnt) {
   		int L = node.getRankLeft();
   		int R = node.getRankRight();

   		if (L== 3 && R == 1){ // the node that is about to rebalance is in (3,1) condition
   			IAVLNode RightChild = node.getRight();
   			int RL = RightChild.getRankLeft();
   			int RR = RightChild.getRankRight();

   			if (RL == 1 && RR ==1){ // (3,1) condition and the left child is at (1,1) condiotion
				cnt += reBalanceCase3111(node, RightChild, true , cnt );}

   			else if (RL == 2 && RR == 1){ // (3,1) condition and the left child is at (2,1) condiotion
				cnt += reBalanceCase3121(node, RightChild , true , cnt );}

   			else if (RL == 1 && RR == 2){ // (3,1) condition and the left child is at (1,2) condiotion
				cnt += reBalanceCase3112(node,RightChild,RightChild.getLeft(),true , cnt );}
   		}

   		else if (L == 1 && R== 3 ){ // the node that is about to rebalance is in (1,3) condition
			IAVLNode LeftChild = node.getLeft();
			int LL = LeftChild.getRankLeft();
			int LR = LeftChild.getRankRight();

			if (LL == 1 && LR ==1){ // (1,3) condition and the left child is at (1,1) condiotion
				cnt += reBalanceCase3111(node, LeftChild, false , cnt );}

			else if (LL == 2 && LR == 1){ // (1,3) condition and the left child is at (2,1) condiotion
				cnt += reBalanceCase3121(node, LeftChild , false, cnt  );}

			else if (LL == 1 && LR == 2){  // (1,3) condition and the left child is at (1,2) condiotion
				cnt += reBalanceCase3112(node , LeftChild , LeftChild.getRight() ,false, cnt );}
		}

   		else if (L == 2 && R == 2){ // the node that is about to rebalance is in (2,2) condition
			cnt += reBalanceCase22(node, cnt );}
   		 // the node is balanced
   			return cnt ;}


	private int reBalanceCase22(IAVLNode node , int cnt) { //rebalance after delete (2,2) case
   		node.setHeightAlone(); // demote
		if (this.root == node){ // no need to go and rebalance parent
			return 1;}
		else{
			return  reBalanceDelete(node.getParent(), 1+ cnt);}} // check if parent if rebalanced


	private int reBalanceCase3112(IAVLNode z, IAVLNode y , IAVLNode a, boolean left , int cnt ) { //rebalance after delete 31 - 12 / 13 - 21 case
   		if (left){
   			rotate_right(a,y);	// rotate right on the (a,y) edge
			rotate_left(z,a);// roate left on the (z,a) edge
   		}

   		else if (!left) {
			rotate_left(y,a);// rotate left on (y,a) edge
			rotate_right(a,z);// rotate right on (a,z)
   		}
		z.setHeightAlone();// promotes and demotes
		y.setHeightAlone();
		a.setHeightAlone();

		if (this.root == a){ // there is no need to go up for rebalancing
   			return 6;}
		else { // go up and check if rebalanced
			return  this.reBalanceDelete(a.getParent() , 6 + cnt);}
		}

	private int reBalanceCase3121(IAVLNode z, IAVLNode y, boolean left, int cnt) { //rebalance after delete 31 - 21 / 13 - 12 case
		if (left){
			rotate_left(z,y);	// rotate left on (z,y) edge
		}
		else if (!left) {
			rotate_right(y,z);// rotate right (y,z) edge
		}
		 z.setHeightAlone(); // demote twice z
		if (this.root == y){ // there is no need to go up for rebalancing
			return 3;}
		else { // go up and check if rebalanced
			return  reBalanceDelete(y.getParent() , 3 + cnt);}
	}


	private int reBalanceCase3111(IAVLNode z, IAVLNode y, boolean left, int cnt ) { //rebalance after delete 31 - 11 / 13 - 11 case
		if (left) {
				rotate_left(z,y);	}// rotate left on the (z,y) edge

		else {
				rotate_right(y,z);	}// rotate right on the (y,z) edge
			z.setHeightAlone();// demote z
			y.setHeightAlone();// promote y
			return 3;}

			//




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

	private IAVLNode deleteRetrieveFamily(IAVLNode nDelete) { // delete & retrieve node to be balanced for node with two children
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


	private IAVLNode deleteRetrieveRight(IAVLNode nDelete) { // delete & retrieve node to be balanced for node with one right children
		IAVLNode parent = nDelete.getParent();

		if (nDelete == this.root) {  // root special case
			this.root = nDelete.getRight();
			return this.root;}
		else if (nDelete.getKey() < parent.getKey()) { // nDelete is left child of parent
			parent.setLeft(nDelete.getRight()); // byPass
			return parent;}
		else {        // nDelete is right child of parent
			parent.setRight(nDelete.getRight()); // byPass
			return parent;
		}}

	private IAVLNode deleteRetrieveLeft(IAVLNode nDelete) { // delete & retrieve node to be balance for root with one left children
		IAVLNode parent = nDelete.getParent();

		if (nDelete == this.root) {  // root special case
			this.root = nDelete.getLeft();
			return this.root;}

		else if (nDelete.getKey() < parent.getKey()) { // nDelete is left child of parent
			parent.setLeft(nDelete.getLeft()); // byPass
			return parent;}

		else {        // nDelete is right child of parent
			parent.setRight(nDelete.getLeft()); // byPass
			return parent;}}




	private IAVLNode deleteRetrieveLeaf(IAVLNode nDelete) { // delete & retrieve node to be balanced for node that is a Leaf
		IAVLNode parent = nDelete.getParent();

		if (nDelete == this.root) {  // root special case
			this.root = null;
			return null;}

		else if (nDelete.getKey() < parent.getKey()) { // nDelete is left child of parent
			parent.setLeft(nDelete.getLeft()); // byPass
			return parent;}

		else {        // nDelete is right child of parent
			parent.setRight(nDelete.getRight()); // byPass
			return parent;
		}}
// till here sub function for the delete function

	private IAVLNode successor(IAVLNode node) { // finding the successor of a node

		if ( node.getRight().isRealNode()){ // if I have a right child so my successor is on that sub tree
			return  myMin(node.getRight());}

		//else my successor is above me
		IAVLNode parent = node.getParent();

		while (parent != null && node == parent.getRight()){ // go up till you come from the left or you are in the root
			node = parent;
			parent = node.getParent(); }
		return parent;}

	private IAVLNode predecessor(IAVLNode nDelete) { // return the predecessor of node if doesnt exist return null

	return null ; }


	private IAVLNode myMin(IAVLNode node) { // finding the most minimum node in my sub tree
		while (node.getLeft().isRealNode()){ // go left if you can
			node = node.getLeft();}
		return node;}

	/**
    * public StringBe min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   return (root == null) ? null : min.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   return (root == null) ? null : max.getValue();
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
	   return Size; // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return root;
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
	  private  final IAVLNode VirtualNode = new AVLNode();// same digital node for all real nodes

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

	    public int getHeight(){return this.Height; }

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
  
