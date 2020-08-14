class BSNode{
    Integer key;
    String value;
    BSNode left;
    BSNode right;

    /**
     * Node for the binary search tree
     * @param key the key of the node. REQUIRED as an Integer value and not NULL
     * @param value the value associated to the key. REQUIRED as a String object
     */
    BSNode(Integer key, String value){
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
