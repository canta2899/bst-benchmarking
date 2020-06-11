public class RBTree {
    // 1 --> RED
    // 0 --> BLACK

    static Node insert(Node root, Node newNode) {
        if (root == null) {
            return newNode;
        } else if (root.key < newNode.key) {
            root.right = insert(root.right, newNode);
            root.right.parent = root;
        } else {
            root.left = insert(root.left, newNode);
            root.left.parent = root;
        }

        return root;

        // Applying rotations and colour chaining in order to maintain RBT property
    }


    /**
     * Finding the node of the given key in the RBT
     * @param root the root node of the RBT
     * @param key the key of the node to be searched. REQUIRED as a key of an existent node
     * @return the node of the given key
     */
    static Node find(Node root, int key){
        if(root == null || root.key == key){
            return root;
        }else if(root.key < key){
            return find(root.right, key);
        }else{
            return find(root.left, key);
        }
    }


    /**
     * Visiting the RBT in prefix notation printing each node as key:value:height:color
     * @param root the root node of the RBT
     */
    static void show(Node root){
        if(root != null){
            // I could use a string builder but probably this is not gonna be used during time calculation
            // and, given that, i don't wanna decrease code's readability
            System.out.print(root.key + ":" + root.value + ":" + root.height + " " + root.color + " ");
            show(root.left);
            show(root.right);
        }else{
            System.out.print("NULL ");
        }
    }
}
