public class BSTree {


    /**
     * Insertion of a newNode in the BST maintaining BST properties
     * @param root the root node of the BST
     * @param newNode the new node to be inserted
     * @return the root node of the BST after the insertion
     */
    Node insert(Node root, Node newNode){
        if(root == null){
            return newNode;
        }else if(root.key < newNode.key){
            root.right = insert(root.right, newNode);
        }else{
            root.left = insert(root.left, newNode);
        }
        return root;
    }


    /**
     * Visiting the BST in prefix notation printing each node as key:value
     * @param root the root node of the BST
     */
    void show(Node root){
        if(root != null){
            System.out.print(root.key+":"+root.value+" ");
            show(root.left);
            show(root.right);
        }else{
            System.out.print("NULL");
        }
    }


    /**
     * Finding the node of the given key in the BST
     * @param root the root node of the BST
     * @param key the key of the node to be searched. REQUIRED as a key of an existent node
     * @return the node of the given key
     */
    Node find(Node root, int key){
        if(root.key == null || root.key == key){
            return root;
        }else if(root.key < key){
            return find(root.right, key);
        }else{
            return find(root.left, key);
        }
    }
}
