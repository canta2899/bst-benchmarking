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
        label:
        while(true) {
            System.out.println("Type input");
            String inputLine = scan.nextLine();
            String[] els = inputLine.split("\\s+");

            switch (els[0]) {
                case "insert":
                    key = Integer.parseInt(els[1]);
                    value = els[2];
                    root = AVLTree.insert(root, new AVLNode(key, value, 1));
                    break;
                case "find":
                    if (root != null) {
                        key = Integer.parseInt(els[1]);
                        System.out.println(AVLTree.find(root, key).value);
                    } else {
                        System.out.println("Tree is empty!");
                    }
                    break;
                case "show":
                    AVLTree.show(root);
                    System.out.println();
                    break;
                case "clear":
                    root = null;
                    break;
                case "exit":
                    break label;
                case "height":
                    System.out.println(AVLTree.getHeight(root));
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}
