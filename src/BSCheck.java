import java.util.Scanner;

/**
 * Debugging and testing BS trees with iterative cli interface
 */
public class BSCheck {
    public static void main(String[] args) {
        int key;
        Scanner scan = new Scanner(System.in);
        String value;
        BSNode root = null;
        label:
        while(true) {
            System.out.println("Type input");
            String inputLine = scan.nextLine();
            String[] els = inputLine.split("\\s+");

            switch (els[0]) {
                case "insert":
                    key = Integer.parseInt(els[1]);
                    value = els[2];
                    root = BSTree.insert(root, new BSNode(key, value));
                    break;
                case "find":
                    if (root != null) {
                        key = Integer.parseInt(els[1]);
                        System.out.println(BSTree.find(root, key).value);
                    } else {
                        System.out.println("Tree is empty!");
                    }
                    break;
                case "show":
                    BSTree.show(root);
                    System.out.println();
                    break;
                case "clear":
                    root = null;
                    break;
                case "exit":
                    break label;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }
}
