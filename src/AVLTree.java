public class AVLTree {

    /**
     * Insertion of a newNode in the AVL maintaining AVL properties (performs rotations)
     * @param root the root node of the AVL
     * @param newNode the new node to be inserted
     * @return the root node of the AVL after the insertion
     */
    static AVLNode insert(AVLNode root, AVLNode newNode){
        AVLNode curr = root;
        AVLNode prev = null;

        while(curr != null && curr.key != newNode.key) {
            prev = curr;
            if(newNode.key < curr.key){
                curr = curr.left;
            }else{
                curr = curr.right;
            }
        }

        if(prev == null){
            root = newNode;
        }else{
            newNode.parent = prev;
            if(newNode.key < prev.key){
                prev.left = newNode;
            }else{
                prev.right = newNode;
            }
        }

        AVLFix(newNode.parent, newNode.key);
        return root;
    }

    static void AVLFix(AVLNode node, int key){
        if(node != null) {
            node.height = Math.max(computeHeight(node.left), computeHeight(node.right)) + 1;
            int bal = getBalanceFactor(node);

            AVLNode temp = node;

            // Left right case
            if (bal > 1 && node.left != null && key > node.left.key) {
                // Reassigning left child with left rotation and rotating right root
                temp = leftRotate(node.left);
                temp = rightRotate(node);
            }

            // Right left case
            if (bal < -1 && node.right != null && key < node.right.key) {
                // Reassigning right child with right rotation and rotating left root
                temp = rightRotate(node.right);
                temp = leftRotate(node);
            }

            // Left left case
            if (bal > 1 && node.left != null && key < node.left.key) {
                // Right rotation
                temp = rightRotate(node);
            }

            if (bal < -1 && node.right != null && key > node.right.key) {
                // Left rotation
                temp = leftRotate(node);
            }

            AVLFix(temp.parent, key);
        }
    }


    /**
     * Finding the node of the given key in the AVL
     * @param root the root node of the AVL
     * @param key the key of the node to be searched. REQUIRED as a key of an existent node
     * @return the node of the given key
     */
    static AVLNode find(AVLNode root, int key){
        if(root == null || root.key == key){
            return root;
        }else if(root.key < key){
            return find(root.right, key);
        }else{
            return find(root.left, key);
        }
    }



    /**
     * Visiting the AVL in prefix notation printing each node as key:value:height
     * @param root the root node of the AVL
     */
    static void show(AVLNode root){
        if(root != null){
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
    private static AVLNode leftRotate(AVLNode node) {
        AVLNode r = node.right;
        AVLNode l = r.left;
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
    private static AVLNode rightRotate(AVLNode node) {
        AVLNode l = node.left;
        AVLNode r = l.right;
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
    private static int getBalanceFactor(AVLNode node) {
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
    static int computeHeight(AVLNode node) {
        if (node == null)
            return 0;
        else
            return node.height;
    }
}
