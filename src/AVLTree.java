public class AVLTree {

    /**
     * Insertion of a newNode in the AVL maintaining AVL properties (performs rotations)
     * @param root the root node of the AVL
     * @param newNode the new node to be inserted
     * @return the root node of the AVL after the insertion
     */
    static AVLNode insert(AVLNode root, AVLNode newNode) {
        AVLNode curr = root;
        AVLNode prev = null;

        while (curr != null) {
            prev = curr;
            if (newNode.key < curr.key) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        if (prev == null) {
            root = newNode;
        } else {
            newNode.parent = prev;
            if (newNode.key < prev.key) {
                prev.left = newNode;
            } else {
                prev.right = newNode;
            }
        }

        return AVLFix(root, newNode);
    }

    static AVLNode AVLFix(AVLNode root, AVLNode newNode){
        int key;
        AVLNode prev = newNode.parent;
        while (prev != null) {
            prev.height = 1 + Math.max(getHeight(prev.left), getHeight(prev.right));
            int bal = getBalanceFactor(prev);
            key = prev.key;
            if(Math.abs(bal) > 1) {
                if (bal > 1) {
                    if (newNode.key > prev.left.key) {
                        // Reassigning left child with left rotation and rotating right root
                        prev.left = leftRotate(prev.left);
                        prev = rightRotate(prev);
                    } else {   // Left left case
                        prev = rightRotate(prev);
                    }
                } else if (bal < -1) {
                    // Right left case
                    if (newNode.key < prev.right.key) {
                        // Reassigning right child with right rotation and rotating left root
                        prev.right = rightRotate(prev.right);
                        prev = leftRotate(prev);
                    } else {
                        // Left rotation
                        prev = leftRotate(prev);
                    }
                }
                // Updating parent of rotated subtree in case of rotations
                if(prev.parent == null)
                    root = prev;
                else if(key > prev.parent.key)
                    prev.parent.right = prev;
                else
                    prev.parent.left = prev;
            }
            // updating prev
            prev = prev.parent;
        }
        return root;
    }

    /**
     * Finding the node of the given key in the AVL
     * @param root the root node of the AVL
     * @param key the key of the node to be searched. REQUIRED as a key of an existent node
     * @return the node of the given key
     */
    static AVLNode find(AVLNode root, int key){
        while(root != null){
            if(root.key == key){
                return root;
            }else if(root.key < key){
                root = root.right;
            }else{
                root = root.left;
            }
        }
        return null;
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
        AVLNode p = node.parent;
        AVLNode r = node.right;
        node.right = r.left;
        if(r.left != null)
            r.left.parent = node;
        r.left = node;
        node.parent = r;
        r.parent = p;
        if(p != null){
            if(p.right == node)
                p.right = r;
            else
                p.left = r;
        }
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        r.height = Math.max(getHeight(r.left), getHeight(r.right)) + 1;
        return r;
    }


    /**
     * Performs right rotation algorithm to maintain AVL tree balance property
     * @param node the node that will be rotated
     * @return node after rotation
     */
     private static AVLNode rightRotate(AVLNode node) {
        AVLNode p = node.parent;
        AVLNode l = node.left;
        node.left = l.right;
        if(l.right != null)
            l.right.parent = node;
        l.right = node;
        node.parent = l;
        l.parent = p;
        if(p != null){
            if(p.left == node)
                p.left = l;
            else
                p.right = l;
        }
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        l.height = Math.max(getHeight(l.left), getHeight(l.right)) + 1;
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
            return getHeight(node.left) - getHeight(node.right);

    }


    /**
     * Computes the height of a given node in constant time (height is a node's class variable)
     * @param node the node
     * @return 0 if the node is null, its height otherwise
     */
    static int getHeight(AVLNode node) {
        if (node == null)
            return 0;
        else
            return node.height;
    }
}
