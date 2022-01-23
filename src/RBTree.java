public class RBTree {
    // 1 --> RED
    // 0 --> BLACK

    /**
     * Performs a standard BSTree insertion
     * @param root the root node
     * @param newNode the new node that will be inserted
     * @return the root node after the insertion
     */
    static RBNode insert(RBNode root, RBNode newNode) {

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

        return RBFix(newNode, root);
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

            // A (parent is left child of gp)
            if (parentNewNode == gpNewNode.left) {

                RBNode uncle = gpNewNode.right;

                // 1  (uncle is red)
                if (uncle != null && uncle.color == 1) {
                    gpNewNode.color = 1;
                    parentNewNode.color = 0;
                    uncle.color = 0;
                    newNode = gpNewNode;
                } else {
                    // 2  (p right child of parent, so rotate left)
                    if (newNode == parentNewNode.right) {
                        root = rotateLeft(root, parentNewNode);
                        newNode = parentNewNode;
                        parentNewNode = newNode.parent;
                    }

                    // 3 (p is now left child of its parent and i can right rotate to gp)
                    root = rotateRight(root, gpNewNode);
                    temp = parentNewNode.color;
                    parentNewNode.color = gpNewNode.color;
                    gpNewNode.color = (int)temp;
                    newNode = parentNewNode;
                }
            } else {    // B parent of pt is right child of gp

                RBNode uncle = gpNewNode.left;

                // 1  (uncle is red)
                if ((uncle != null) && (uncle.color == 1)) {
                    gpNewNode.color = 1;
                    parentNewNode.color = 0;
                    uncle.color = 0;
                    newNode = gpNewNode;
                } else {
                    // 2  (p is left child of its parent, apply right rotation)
                    if (newNode == parentNewNode.left) {
                        root = rotateRight(root, parentNewNode);
                        newNode = parentNewNode;
                        parentNewNode = newNode.parent;
                    }

                    // 3  (p is now right child of its parent so i can apply left rotation on gp)
                    root = rotateLeft(root, gpNewNode);
                    temp = parentNewNode.color;
                    parentNewNode.color = gpNewNode.color;
                    gpNewNode.color = (int)temp;
                    newNode = parentNewNode;
                }
            }
        }

        root.color = 0; // Root is always black
        return root;
    }


    /**
     * Performs left rotation
     * @param root the root node of the tree
     * @param node the node to which the rotation is referred
     * @return the root node after the left rotation is completed
     */
	static RBNode rotateLeft(RBNode root, RBNode node) {
		RBNode p = node.parent;
		RBNode r = node.right;

		node.right = r.left;
		if(node.right != null){
			node.right.parent = node;
		}
		r.parent = p;
		if (node.parent == null){
			root = r;
		}else if(node == node.parent.right){
			node.parent.right = r;
		}else{
			node.parent.left = r;
		}
		r.left = node;
		node.parent = r;
		return root;
	}

    /**
     * Performs right rotation
     * @param root the root node of the tree
     * @param node the node to which the rotation is referred
     * @return the root node after the right rotation is completed
     */
	static RBNode rotateRight(RBNode root, RBNode node) {
		RBNode p = node.parent;
		RBNode l = node.left;
		node.left = l.right;
		if(node.left != null){
			node.left.parent = node;
		}
		l.parent = p;
		if(node.parent == null){
			root = l;
		}else if(node == node.parent.right){
			node.parent.right = l;
		}else{
			node.parent.left = l;
		}
		l.right = node;
		node.parent = l;
		return root;
	}

    /**
     * Finding the node of the given key in the RBT
     * @param root the root node of the RBT
     * @param key the key of the node to be searched. REQUIRED as a key of an existent node
     * @return the node of the given key
     */
    static RBNode find(RBNode root, int key){
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
