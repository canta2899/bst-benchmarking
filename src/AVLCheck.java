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
