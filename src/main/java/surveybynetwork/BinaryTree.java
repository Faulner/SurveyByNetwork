package surveybynetwork;

//Source:  http://www.newthinktank.com/2013/03/binary-tree-in-java/
// New Think Tank

public class BinaryTree {

    BinaryNode root;

    public void addNode(int key, String name) {

        // Create a new Node and initialize it

        BinaryNode newNode = new BinaryNode(key, name);

        // If there is no root this becomes root

        if (root == null) {

            root = newNode;

        } else {

            // Set root as the Node we will start
            // with as we traverse the tree

            BinaryNode focusNode = root;

            // Future parent for our new Node

            BinaryNode parent;

            while (true) {

                // root is the top parent so we start
                // there

                parent = focusNode;

                // Check if the new node should go on
                // the left side of the parent node

                if (key < focusNode.key) {

                    // Switch focus to the left child

                    focusNode = focusNode.leftChild;

                    // If the left child has no children

                    if (focusNode == null) {

                        // then place the new node on the left of it

                        parent.leftChild = newNode;
                        return; // All Done

                    }

                } else { // If we get here put the node on the right

                    focusNode = focusNode.rightChild;

                    // If the right child has no children

                    if (focusNode == null) {

                        // then place the new node on the right of it

                        parent.rightChild = newNode;
                        return; // All Done

                    }

                }

            }
        }

    }

    // All nodes are visited in ascending order
    // Recursion is used to go to one node and
    // then go to its child nodes and so forth

    public String inOrderTraverseTree(BinaryNode focusNode) {

        String s = "";
        if (focusNode != null) {

            // Traverse the left node
            if(focusNode.leftChild != null) {
                s+= inOrderTraverseTree(focusNode.leftChild) + ",";
            }

            // Visit the currently focused on node
            System.out.println(focusNode);
            s+= focusNode.key + "-" + focusNode.name + ",";

            // Traverse the right node
            if(focusNode.rightChild != null) {
                s += inOrderTraverseTree(focusNode.rightChild) + ",";
            }
        }
        return s;
    }

    public String preOrderTraverseTree(BinaryNode focusNode) {
        String s = "";
        if (focusNode != null) {
            s+= focusNode.key + "-" + focusNode.name + ",";

            if(focusNode.leftChild != null) {
                s += preOrderTraverseTree(focusNode.leftChild) + ",";
            }

            if(focusNode.rightChild != null) {
                s += preOrderTraverseTree(focusNode.rightChild) + ",";
            }
        }
        return s;
    }

    public String postOrderTraverseTree(BinaryNode focusNode) {
        String s = "";
        if (focusNode != null) {

            if(focusNode.leftChild != null) {
                s += postOrderTraverseTree(focusNode.leftChild) + ",";
            }

            if(focusNode.rightChild != null) {
                s += postOrderTraverseTree(focusNode.rightChild) + ",";
            }

            s+= focusNode.key + "-" + focusNode.name + ",";

        }
        return s;
    }

    public BinaryNode findNode(int key) {

        // Start at the top of the tree

        BinaryNode focusNode = root;

        // While we haven't found the Node
        // keep looking

        while (focusNode.key != key) {

            // If we should search to the left

            if (key < focusNode.key) {

                // Shift the focus Node to the left child

                focusNode = focusNode.leftChild;

            } else {

                // Shift the focus Node to the right child

                focusNode = focusNode.rightChild;

            }

            // The node wasn't found

            if (focusNode == null)
                return null;

        }

        return focusNode;

    }

    public String print(String orderType) {                  // print content of list
        String s = "";
        if(orderType == "In") {
            s = "IN-ORDER: " + inOrderTraverseTree(root);
        } else if(orderType == "Pre") {
            s = "PRE-ORDER: " + inOrderTraverseTree(root);
        } else if(orderType == "Post") {
            s = "POST-ORDER: " + inOrderTraverseTree(root);
        }
        s = s.replace(",,", ",");
        if(s.endsWith(",")) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
