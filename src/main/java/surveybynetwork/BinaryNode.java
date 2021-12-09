package surveybynetwork;
//Source:  http://www.newthinktank.com/2013/03/binary-tree-in-java/
// New Think Tank

public class BinaryNode {

    int key;
    String name;

    BinaryNode leftChild;
    BinaryNode rightChild;

    BinaryNode(int key, String name) {

        this.key = key;
        this.name = name;

    }

    public String toString() {

        return name + " has the key " + key;

        /*
         * return name + " has the key " + key + "\nLeft Child: " + leftChild +
         * "\nRight Child: " + rightChild + "\n";
         */

    }
}
