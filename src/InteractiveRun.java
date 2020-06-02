import java.util.Scanner;

/**
 * Class that allows the execution interactive programs for trees
 */
public class InteractiveRun {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type bst, avl or rbt according to which tree you want to use");
        String choice = scan.nextLine();
        if(choice.equals("bst")){
            bstMenu(scan);
        }else if(choice.equals("avl")){
            avlMenu(scan);
        }else if(choice.equals("bst")){
            System.out.println("Not yet implemented");
        }else{
            System.out.println("Invalid input");
        }
    }

    /**
     * Menu for the BINARY SEARCH TREE
     * Implemented functions are:
     *      • insert [key] [value]
     *      • find [key]
     *      • show
     *      • clear
     *      • exit
     *
     * @param scan the scanner to take input from cli
     */
    private static void bstMenu(Scanner scan){
        int key;
        String value;
        Node root = null;
        while(true) {
            System.out.println("Type input");
            String inputLine = scan.nextLine();
            String els[] = inputLine.split("\\s+");

            if(els[0].equals("insert")){
                key = Integer.parseInt(els[1]);
                value = els[2];
                root = BSTree.insert(root, new Node(key, value));
            }else if(els[0].equals("find")){
                if (root != null) {
                    key = Integer.parseInt(els[1]);
                    System.out.println(BSTree.find(root, key).value);
                } else {
                    System.out.println("Tree is empty!");
                }
            }else if(els[0].equals("show")) {
                BSTree.show(root);
                System.out.println();
            }else if(els[0].equals("clear")) {
                root = null;
            }else if(els[0].equals("exit")) {
                break;
            }else {
                System.out.println("Unknown command");
            }
        }
    }


    /**
     * Menu for the AVL TREE
     * Implemented functions are:
     *      • insert [key] [value]
     *      • find [key]
     *      • show
     *      • height
     *      • clear
     *      • exit
     *
     * @param scan the scanner to take input from cli
     */
    private static void avlMenu(Scanner scan){
        int key;
        String value;
        Node root = null;
        while(true) {
            System.out.println("Type input");
            String inputLine = scan.nextLine();
            String els[] = inputLine.split("\\s+");

            if(els[0].equals("insert")){
                key = Integer.parseInt(els[1]);
                value = els[2];
                root = AVLTree.insert(root, new Node(key, value, 1));
            }else if(els[0].equals("find")){
                if (root != null) {
                    key = Integer.parseInt(els[1]);
                    System.out.println(AVLTree.find(root, key).value);
                } else {
                    System.out.println("Tree is empty!");
                }
            }else if(els[0].equals("show")) {
                AVLTree.show(root);
                System.out.println();
            }else if(els[0].equals("clear")) {
                root = null;
            }else if(els[0].equals("exit")) {
                break;
            }else if(els[0].equals("height")){
                System.out.println(AVLTree.computeHeight(root));
            }else {
                System.out.println("Unknown command");
            }
        }

    }
}
