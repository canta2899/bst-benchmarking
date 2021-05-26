import java.util.Scanner;

/**
 * Debugging and testing AVL trees with iterative cli interface
 */
public class AVLCheck {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int key;
        String value;
        AVLNode root = null;
        while(true) {
            System.out.println("Type input");
            String inputLine = scan.nextLine();
            String els[] = inputLine.split("\\s+");

            if(els[0].equals("insert")){
                key = Integer.parseInt(els[1]);
                value = els[2];
                root = insert(root, new AVLNode(key, value, 1));
            }else if(els[0].equals("find")){
                if (root != null) {
                    key = Integer.parseInt(els[1]);
                    System.out.println(find(root, key).value);
                } else {
                    System.out.println("Tree is empty!");
                }
            }else if(els[0].equals("show")) {
                show(root);
                System.out.println();
            }else if(els[0].equals("clear")) {
                root = null;
            }else if(els[0].equals("exit")) {
                break;
            }else if(els[0].equals("height")){
                System.out.println(computeHeight(root));
            }else {
                System.out.println("Unknown command");
            }
        }
    }


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

        AVLFix(root, newNode.parent, newNode.key);

        return root;
    }

    static void AVLFix(AVLNode root, AVLNode start, int key){
        if(start != null){
            start.height = 1 + Math.max(computeHeight(start.left), computeHeight(start.right));
            int bal = getBalanceFactor(start);
            AVLNode temp = start;
            // Left right case
            if (bal > 1 && start.left != null && key > start.left.key){
                // Reassigning left child with left rotation and rotating right root
                temp = leftRotate(root, start.left);
                heightUpdate(temp);
                temp = rightRotate(root, start);
                heightUpdate(temp);
            }

            // Right left case
            else if (bal < -1 && start.right != null && key < start.right.key){
                // Reassigning right child with right rotation and rotating left root
                temp = rightRotate(root, start.right);
                heightUpdate(temp);

                temp = leftRotate(root, start);
                heightUpdate(temp);
            }

            // Left left case
            else if (bal > 1 && start.left != null && key < start.left.key){
                // Right rotation
                temp = rightRotate(root, start);
                heightUpdate(temp);
            }

            else if (bal < -1 && start.right != null && key > start.right.key) {
                // Left rotation

                temp = leftRotate(root, start);
                heightUpdate(temp);
            }
            AVLFix(root, start.parent, key);
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
    private static AVLNode leftRotate(AVLNode root, AVLNode node) {
        if(node.parent == null){ // x is the root of the avl
            root = node.right;
        } else {
            if(node.parent.left == node){
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
        }

        node.right.parent = node.parent;
        node.parent = node.right;
        node.right = node.right.left;

        if(node.right != null){
            node.right.parent = node;
        }

        node.parent.left = node;

        return node.parent;
    }


    /**
     * Performs right rotation algorithm to maintain AVL tree balance property
     * @param node the node that will be rotated
     * @return node after rotation
     */
    private static AVLNode rightRotate(AVLNode root, AVLNode node) {
        if(node.parent == null){ // x is the root of the avl
            root = node.left;
            node.left.parent = null;
        } else {
            if(node.parent.left == node){
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }
        }

        node.left.parent = node.parent;
        node.parent = node.left;
        node.left = node.left.right;

        if(node.left != null){
            node.left.parent = node;
        }

        node.parent.right = node;
        return node.parent;
    }


    private static AVLNode heightUpdate(AVLNode node){
        if(node.right != null){
            node.right.height = 1 + Math.max(computeHeight(node.right.right), computeHeight(node.right.left));
        }
        if(node.left != null){
            node.left.height = 1 + Math.max(computeHeight(node.left.right), computeHeight(node.left.left));
        }
        node.height = 1 + Math.max(computeHeight(node.right), computeHeight(node.left));
        return node;
    }


    /**
     * Computes balance factor for a given node according to leaf's heights
     * @param node the node
     * @return 0 if the node is null, balance factor value otherwise
     */
    private static int getBalanceFactor(AVLNode node) {
        if(node == null)
            return 0;
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
        return node.height;
    }
}
