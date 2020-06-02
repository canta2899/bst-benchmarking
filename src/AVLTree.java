public class AVLTree {

    /**
     * Insertion of a newNode in the AVL maintaining AVL properties (performs rotations)
     * @param root the root node of the BST
     * @param newNode the new node to be inserted
     * @return the root node of the BST after the insertion
     */
    static Node insert(Node root, Node newNode){
        if(root == null){
            return newNode;
        }else if(root.key < newNode.key){
            root.right = insert(root.right, newNode);
        }else{
            root.left = insert(root.left, newNode);
        }

        root.height = Math.max(computeHeight(root.left), computeHeight(root.right))+1;
        int bal = getBalanceFactor(root);

        // Left right case
        if (bal > 1 && newNode.key > root.left.key){
            // Reassigning left child with left rotation and rotating right root
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right left case
        if (bal < -1 && newNode.key < root.right.key){
            // Reassigning right child with right rotation and rotating left root
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        // Left left case
        if (bal > 1 && newNode.key < root.left.key){
            // Right rotation
            return rightRotate(root);
        }

        if (bal < -1 && newNode.key > root.right.key){
            // Left rotation
            return leftRotate(root);
        }

        return root;
    }


    /**
     * Finding the node of the given key in the BST
     * @param root the root node of the BST
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
     * Visiting the BST in prefix notation printing each node as key:value
     * @param root the root node of the BST
     */
    static void show(Node root){
        if(root != null){
            // I could use a string builder but probably this is not gonna be used during time calculation
            // and, given that, i don't wanna decrease code's readability
            System.out.print(root.key + ":" + root.value + ":" + root.height + " ");
            show(root.left);
            show(root.right);
        }else{
            System.out.print("NULL ");
        }
    }


    /**
     * Performs left rotation algorithm to maintain AVL tree balance property
     * @param node the node that will be rotated
     * @return node after rotation
     */
    private static Node leftRotate(Node node) {
        Node r = node.right;
        Node l = r.left;
        r.left = node;
        node.right = l;
        node.height = Math.max(computeHeight(node.left), computeHeight(node.right)) + 1;
        r.height = Math.max(computeHeight(r.left), computeHeight(r.right)) + 1;
        return r;
    }


    /**
     * Performs right rotation algorithm to maintain AVL tree balance property
     * @param node the node that will be rotated
     * @return node after rotation
     */
    private static Node rightRotate(Node node) {
        Node l = node.left;
        Node r = l.right;
        l.right = node;
        node.left = r;
        node.height = Math.max(computeHeight(node.left), computeHeight(node.right)) + 1;
        l.height = Math.max(computeHeight(l.left), computeHeight(l.right)) + 1;
        return l;
    }


    /**
     * Computes balance factor for a given node according to leaf's heights
     * @param node the node
     * @return 0 if the node is null, balance factor value otherwise
     */
    private static int getBalanceFactor(Node node) {
        if(node == null)
            return 0;
        else
            return computeHeight(node.left) - computeHeight(node.right);

    }


    /**
     * Computes the height of a given node in constant time (height is a node's class variable)
     * @param node the node
     * @return 0 if the node is null, its height otherwise
     */
    static int computeHeight(Node node) {
        if (node == null)
            return 0;
        else
            return node.height;
    }
}
