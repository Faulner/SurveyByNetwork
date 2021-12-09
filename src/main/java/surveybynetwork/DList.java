package surveybynetwork;

public class DList {
    Node head = new Node();             // head of doubly-linked list

    public Node find(String s) {          // find Node containing x
        for (Node current = head.next; current != head; current = current.next) {
            if (current.print().contains(s)) {        // is x contained in current Node?
                return current;               // return Node containing x
            }
        }
        return null;
    }

    public String print() {                  // print content of list
        if (head.next == head) {             // list is empty, only header Node
            return "list empty";
        }
        String s = "HEAD ";
        for (Node current = head.next; current != head; current = current.next) {
            s += " <-> " + current.print();
        }
        s += " <-> TAIL";
        return s;
    }
}
