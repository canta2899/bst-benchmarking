class Node {
    Integer key;
    String value;
    Node left;
    Node right;
    Node parent; // for red black trees
    int height; // for AVL and red blak trees
    int color; // for red black trees

    // Overloading of constructors to suit nodes to every kind of implemented tree

    /**
     * Node for the binary search tree
     * @param key the key of the node. REQUIRED as an Integer value and not NULL
     * @param value the value associated to the key. REQUIRED as a String object
     */
    Node(Integer key, String value){
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }


    /**
     * Node for the AVL tree
     * @param key the key of the node. REQUIRED as an Integer value and not NULL
     * @param value the value associated to the key. REQUIRED as a String object
     * @param height the height of the node in the tree
     */
    Node(Integer key, String value, int height){
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = height;
    }


    /**
     * Node for a RedBlack Tree  (1 --> RED, 0 --> BLACK)
     * @param key the key of the node. REQUIRED as an Integer value and not NULL
     * @param value the value associated to the key. REQUIRED as a String object
     * @param rb identifier for rb overloading
     */
    Node(Integer key, String value, String rb){
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = 1;
    }
}
