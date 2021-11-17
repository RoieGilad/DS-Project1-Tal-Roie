/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	private  IAVLNode node;
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
  	return node == null; // root == null iff the tree is empty
  }


 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k) {
	  return search_in(k, node);
  }

  public String search_in(int k, IAVLNode node){
	  if (node == null){
		  return null;
	  }

	  int root_key = node.getKey();

	  if (root_key == k){
		  return node.getValue();
	  }
	  if (root_key > k){
		  return search_in(k, node.getLeft());
	  } else {
		  return search_in(k, node.getRight());
	  }
  }

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
	  IAVLNode search = this.node;
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

  private void rotate_right(IAVLNode node){
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
	if (node == null) {
		node = new_node;
		min = new_node;
		max = new_node;
		Size = 1;
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
	Size = Size + 1;
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
   public int delete(int k)
   {
	   return 421;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   return (node == null) ? null : min.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   return (node == null) ? null : max.getValue();
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
   public IAVLNode getNode()
   {
	   return node;
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

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{
		static int getKey(IAVLNode root) {
			return root.getKey();
		}

		static String getValue(IAVLNode root) {
			return root.getValue();
		}

		static AVLTree getLeft(IAVLNode root) {
			return root.AVLTree.getLeft();
		}

		static AVLTree getRight(IAVLNode root) {
		}

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

	  private Integer key;
	  private String info;
	  private IAVLNode Left;
	  private IAVLNode Right;
	  private int Height;
	  private IAVLNode parent;
	  private static final IAVLNode VirtualNode; // same digital node for all real nodes

	  public AVLNode(Integer key, String info){
		  this.key = key;
		  this.info = info;
		  this.Left = VirtualNode;
		  this.Right = VirtualNode;

	  }
	   public AVLNode() { //default constructor for digital node
		   this.key = null;
		   this.info = null;
		   this.Left = null;
		   this.Right = null;
		   this.Height = -1;
	   }



		   public int getKey(){
	  		if (this.key == null){
	  			return -1;}
			else{
		   	return this.key;
		   }}

		public String getValue(){
			return this.info;		}

		public void setLeft(IAVLNode node){
	  		this.Left = node;

	  }

		public IAVLNode getLeft(){
	  		return this.Left;
		}


		public void setRight(IAVLNode node){
  			this.Right = node;
		}

		public IAVLNode getRight() {
			return this.Right;
		}


		public void setParent(IAVLNode node){
			this.parent = node;
		}
		public IAVLNode getParent(){
	  		return this.parent;
		}

		public boolean isRealNode(){
			if (this.key == -1){
				return false;
			}
			return true; // to be replaced by student code
		}

	    public void setHeight(int height){
	  		this.Height = height;
	    }

	    public int getHeight(){
	      return this.Height; // to be replaced by student code
	    }
  }

}
  
