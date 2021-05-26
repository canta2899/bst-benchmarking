public class RBTree {
    // 1 --> RED
    // 0 --> BLACK

    /**
     * Performs the RBTree insertion by inserting a new node and fixing RBTree properties
     * @param root the root node of the tree
     * @param newNode the new node that will be inserted
     * @return the root node after the insertion and the RBTree fix process
     */
    static RBNode insert(RBNode root, RBNode newNode){
        root = regularInsertion(root, newNode);
        root = RBFix(newNode, root);
        return root;
    }


    /**
     * Performs a standard BSTree insertion
     * @param root the root node
     * @param newNode the new node that will be inserted
     * @return the root node after the insertion
     */
    static RBNode regularInsertion(RBNode root, RBNode newNode) {

        RBNode curr = root;
        RBNode prev = null;

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

        return root;
    }


    /**
     * Fixes tree in order to maintain RBTree properties with recoloring and rotations
     * @param newNode the new insert Node
     * @param root the root node of the tree
     * @return the root node after the tree has been fixed
     */
    static RBNode RBFix(RBNode newNode, RBNode root){
        Object temp;
        RBNode parentNewNode = null;
        RBNode gpNewNode = null;
        while ((newNode != root) && (newNode.color != 0) && (newNode.parent.color == 1)) {

            parentNewNode = newNode.parent;
            gpNewNode = newNode.parent.parent;

            // A
            if (parentNewNode == gpNewNode.left) {

                RBNode uncle = gpNewNode.right;

                // 1
                if (uncle != null && uncle.color == 1) {
                    gpNewNode.color = 1;
                    parentNewNode.color = 0;
                    uncle.color = 0;
                    newNode = gpNewNode;
                } else {
                    // 2
                    if (newNode == parentNewNode.right) {
                        root = rotateLeft(root, parentNewNode);
                        newNode = parentNewNode;
                        parentNewNode = newNode.parent;
                    }

                    // 3
                    root = rotateRight(root, gpNewNode);
                    temp = parentNewNode.color;
                    parentNewNode.color = gpNewNode.color;
                    gpNewNode.color = (int)temp;
                    newNode = parentNewNode;
                }
            } else {    // B

                RBNode uncle = gpNewNode.left;

                // 1
                if ((uncle != null) && (uncle.color == 1)) {
                    gpNewNode.color = 1;
                    parentNewNode.color = 0;
                    uncle.color = 0;
                    newNode = gpNewNode;
                } else {
                    // 2
                    if (newNode == parentNewNode.left) {
                        root = rotateRight(root, parentNewNode);
                        newNode = parentNewNode;
                        parentNewNode = newNode.parent;
                    }

                    // 3
                    root = rotateLeft(root, gpNewNode);
                    temp = parentNewNode.color;
                    parentNewNode.color = gpNewNode.color;
                    gpNewNode.color = (int)temp;
                    newNode = parentNewNode;
                }
            }
        }

        root.color = 0;
        return root;
    }


    /**
     * Performs left rotation
     * @param root the root node of the tree
     * @param node the node to which the rotation is referred
     * @return the root node after the left rotation is completed
     */
    static RBNode rotateLeft(RBNode root, RBNode node) {
        RBNode nodeRight = node.right;

        node.right = nodeRight.left;

        if (node.right != null) {
            node.right.parent = node;
        }

        nodeRight.parent = node.parent;

        if (node.parent == null) {
            root = nodeRight;
        } else if (node == node.parent.left) {
            node.parent.left = nodeRight;
        } else {
            node.parent.right = nodeRight;
        }

        nodeRight.left = node;
        node.parent = nodeRight;

        return root;
    }


    /**
     * Performs right rotation
     * @param root the root node of the tree
     * @param node the node to which the rotation is referred
     * @return the root node after the right rotation is completed
     */
    static RBNode rotateRight(RBNode root, RBNode node) {
        RBNode nodeLeft = node.left;

        node.left = nodeLeft.right;

        if (node.left != null) {
            node.left.parent = node;
        }

        nodeLeft.parent = node.parent;

        if (node.parent == null) {
            root = nodeLeft;
        } else if (node == node.parent.left) {
            node.parent.left = nodeLeft;
        } else {
            node.parent.right = nodeLeft;
        }

        nodeLeft.right = node;
        node.parent = nodeLeft;
        return root;
    }


    /**
     * Finding the node of the given key in the RBT
     * @param root the root node of the RBT
     * @param key the key of the node to be searched. REQUIRED as a key of an existent node
     * @return the node of the given key
     */
    static RBNode find(RBNode root, int key){
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
    static void show(RBNode root){
        if(root != null){
            System.out.print(root.key + ":" + root.value + ":" + ((root.color == 1) ? "red" : "black") + " ");
            show(root.left);
            show(root.right);
        }else{
            System.out.print("NULL ");
        }
    }
}
