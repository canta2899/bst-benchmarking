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
                System.out.println(getHeight(root));
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

        while(curr != null) {
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

        int key;
        while (prev != null) {
            prev.height = 1 + Math.max(getHeight(prev.left), getHeight(prev.right));
            int bal = getBalanceFactor(prev);
            key = prev.key;
            if(bal > 1){
                if (newNode.key > prev.left.key){
                    // Reassigning left child with left rotation and rotating right root
                    prev.left = leftRotate(prev.left);
                    prev = rightRotate(prev);
                }else{   // Left left case
                    prev = rightRotate(prev);
                }
            }else if(bal < -1){
                // Right left case
                if (newNode.key < prev.right.key){
                    // Reassigning right child with right rotation and rotating left root
                    prev.right = rightRotate(prev.right);
                    prev = leftRotate(prev);
                }else{
                    // Left rotation
                    prev = leftRotate(prev);
                }
            }

            // Updating parent of rotated subtree in case of rotations
            if(Math.abs(bal) > 1){
                if(prev.parent == null)
                    root = prev;
                else if(key > prev.parent.key)
                    prev.parent.right = prev;
                else
                    prev.parent.left = prev;
            }

            prev = prev.parent;
        }
        return root;
    }

/*
insert 0 zero
insert 17 seventeen
insert 14 fourteen
insert 8 eight
insert 15 fifteen
insert 13 thirteen
insert 10 ten
insert 6 six
insert 2 two
insert 5 five
insert 12 twelve
insert 7 seven
show
exit
 */

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
        AVLNode temp;
        r.left = node;
        node.right = l;
        if(r.left.parent != null) {
            // temp = r.parent;
            r.parent = r.left.parent;
            r.left.parent = r; // was temp before
        }else{
            r.parent = null;
            r.left.parent = r;
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
        AVLNode l = node.left;
        AVLNode r = l.right;
        AVLNode temp;
        l.right = node;
        node.left = r;
        if(l.right.parent != null) {
            // temp = l.parent;
            l.parent = l.right.parent;
            l.right.parent = l;  // was temp before
        }else{
            l.parent = null;
            l.right.parent = l;
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
