public class RBNode{
    int key;
    String value;
    RBNode left;
    RBNode right;
    RBNode parent; // for red black trees
    int color; // for red black trees

    /**
     * Node for a RedBlack Tree  (1 --> RED, 0 --> BLACK)
     * @param key the key of the node. REQUIRED as an Integer value and not NULL
     * @param value the value associated to the key. REQUIRED as a String object
     */
    RBNode(int key, String value){
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = 1;
    }
}
