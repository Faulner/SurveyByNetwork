package surveybynetwork;

public class Node {             // class for nodes in a doubly-linked list
    Node prev;                  // previous Node in a doubly-linked list
    Node next;                  // next Node in a doubly-linked list
    SurveyResult surveyResult;// data stored in this Node
    Node() {                    // constructor for head Node
        prev= this;             // of an empty doubly-linked list
        next = this;
        surveyResult = new SurveyResult();
    }

    Node(String topic, String number, String average) {       // constructor for a Node with data
        prev= null;
        next = null;
        surveyResult = new SurveyResult();
        surveyResult.setTopic(topic);
        surveyResult.setNumber(number);
        surveyResult.setAverage(average);
    }

    public void append(Node newNode) {  // attach newNode after this Node
        newNode.prev = this;
        newNode.next = next;
        next.prev = newNode;
        next = newNode;
    }

    public void insert(Node newNode) {  // attach newNode before this Node
        newNode.prev = prev;
        newNode.next = this;
        prev.next = newNode;;
        prev = newNode;
    }

    public void remove() {              // remove this Node
        next.prev = prev;               // bypass this Node
        prev.next = next;
    }

    public String print()
    {
        return surveyResult.print();
    }
}
