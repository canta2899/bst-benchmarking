public class AVLNode{
    int key;
    String value;
    int height;
    AVLNode left;
    AVLNode right;
    AVLNode parent;

    /**
     * Node for the AVL tree
     * @param key the key of the node. REQUIRED as an Integer value and not NULL
     * @param value the value associated to the key. REQUIRED as a String object
     * @param height the height of the node in the tree
     */
    AVLNode(int key, String value, int height){
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.height = height;
    }
}
