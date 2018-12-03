
/**
 * Standard Cons-cells
 *
 * @author Stefan Kahrs
 * @version 1
 *
 */

/**
 * type parameter T
 * Below is an explanation for the fancy stuff
 * attached to that type parameter.
 * For the assessment you do not need to know this,
 * but if you are curious: read on!
 *
 * Because we want to be able to compare the elements
 * of the list with one another, we require that
 * class T implements the Comparable interface.
 * That interface has itself a type parameter, which
 * gives you what these values can be compared to.
 * The reason this is (in the most general case) not just T
 * itself is the following scenario:
 * class X implements the interface,
 * so we can compare Xs with Xs, then we define a subclass Y of X,
 * so it inherits the compareTo method from X,
 * but Ys are now compared with Xs.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
public class Node<T extends Comparable<? super T>>
{

    protected T head;
    protected Node<T> tail;

    public Node(T h, Node<T> t) {
        head = h; //data that a Node<T> holds
        tail = t; //holds the next node in the LinkedList
    }

    public int compareTo(Node<T> o) {
        return head.compareTo(o.head);
    }

    /**
     * returns string of the Node<T> list it's given
     */
    public String toString() {
        if (tail == null) return "[" + head + "]";
        return "[" + head + tail.tailString();
    }

    /**
     * modified version of toString but for the end of a list
     */
    private String tailString() {
        String initialPart= "," + head;
        if (tail==null) return initialPart + "]";
        return initialPart + tail.tailString();
    }

    /**
     * returns the length of the Node<T> list it's given
     */
    public int length() {
        int result=1;
        for (Node<T> n=tail; n!=null; n=n.tail) {
            result++;
        }
        return result;
    }

    /**
     * breaks the Node<T> list given into sorted segments
     * {2, 4, 5, 1, 6, 3, 7} --> {{2, 4, 5}, {1, 6}, {3, 7}}
     */
    public Queue<Node<T>> queueSortedSegments() {
        Queue<Node<T>> q = new LinkedList<>();
        Node<T> previousNode = this;
        Node<T> startNode = this;
        Node<T> tempNode = this.tail;

        while (tempNode != null) {
            if (previousNode.compareTo(tempNode) <= 0) { //if the next node is greater than or equal to
                previousNode = tempNode; //update previous node
                tempNode = tempNode.tail;
            } else { //if the next node is less than
                q.add(startNode); //add starting node for this list

                tempNode = previousNode.tail;

                previousNode.tail = null; //cut off this LL
                previousNode = tempNode;

                startNode = tempNode; //update starting node
            }
        }
        q.add(startNode); //add final start node to queue

        System.out.println(q.toString());

        return q;
    }

    public boolean isSorted() {
        Node<T> previous = this;
        for (Node<T> n=tail; n!=null; n=n.tail) { //look through list
            if (previous.compareTo(n) > 0) {
                return false; //if there is an element that is less than the previous element, the list is not sorted
            }
            previous = n;
        }
        return true; //else, the list is sorted
    }

    /**
     * merges two Node<T> lists together
     */
    public Node<T> merge (Node<T> another){
        //this method should merge two sorted linked lists
        //and return their merged resulting list
        assert isSorted();
        assert another==null || another.isSorted();
        //the above are our assumptions about those lists

        Node<T> mergedList; //a pointer for the next node in the merged list
        Node<T> mergedListStart; //the first node in the merged list
        Node<T> thisNode = this; //the first node in 'this' list
        Node<T> anotherNode = another; //the first node in 'another' list

        if (thisNode.compareTo(anotherNode) <= 0) { //if thisNode is less than or equal to anotherNode
            mergedList = thisNode; //thisNode becomes the first node in the merged list
            mergedListStart = thisNode;
            thisNode = thisNode.tail; //move thisNode along its list
        } else {
            mergedList = anotherNode;
            mergedListStart = anotherNode;
            anotherNode = anotherNode.tail;
        }
        while (!(thisNode == null && anotherNode == null)) { //while both lists are not null
            if (thisNode == null) { //if this is null
                mergedList.tail = anotherNode; //add a node from the other list
                anotherNode = anotherNode.tail; //move node along 'another' list
                mergedList = mergedList.tail; //move along mergedList
            } else if (anotherNode == null) { //same for anotherNode
                mergedList.tail = thisNode;
                thisNode = thisNode.tail;
                mergedList = mergedList.tail;
            } else {
                if (thisNode.compareTo(anotherNode) < 0) { //if thisList is less than anotherList
                    mergedList.tail = thisNode; //add thisNode to the mergedList
                    thisNode = thisNode.tail; //move thisNode along its list
                } else if (thisNode.compareTo(anotherNode) > 0){ //do similar if greater than
                    mergedList.tail = anotherNode;
                    anotherNode = anotherNode.tail;
                } else { //do similar if less than
                    mergedList.tail = thisNode;
                    thisNode = thisNode.tail;
                }
                mergedList = mergedList.tail;
            }
        }
        return mergedListStart;
    }

    /**
     * removes two lists from the queue
     * merges them together
     * adds the new list into the queue
     * repeats until there is only one list in the queue
     */
    public Node<T> mergeSort() {
        Queue<Node<T>> list = this.queueSortedSegments(); //split list into sorted segments
        Node<T> listA;
        Node<T> listB;

        while (list.size() > 1) { //while there are two lists in the queue
            listA = list.poll(); //poll pair of lists
            listB = list.poll();

            listA = listA.merge(listB); //merge them

            list.add(listA); //add the merged list back into the queue
        }
        return list.poll(); //return the only list left
    }

    /**
     * Creates a random LinkedList of Integers n long between 0
     * and n
     */
    static public Node<Integer> randomList(int n) {
        //for testing purposes we want some random lists to be sorted
        //the list is n elements long
        //the elements of the random list are numbers between 0 and n-1
        Random r=new Random();
        Node<Integer> result=null;
        int k=n;
        while(k>0) {
            result=new Node<Integer>(r.nextInt(n),result);
            k--;
        }
        return result;
    }

    /**
     * tests the system
     */
    static public void test(int n) {
        assert n >= 1;
        //this method should do the following:
        //1. create a random linked list of length n
        Node<Integer> randList = randomList(n);
        //2. output it
        System.out.println(randList.toString());
        //3. report whether the 'isSorted' method thinks the list is sorted or not
        System.out.println("isSorted() returns: " + randList.isSorted());
        //4. sort the list using mergeSort
        randList = randList.mergeSort();
        //5. output the sorted list
        System.out.println(randList.toString());
        //6. report whether the 'isSorted' method thinks that list is sorted or not
        System.out.println("isSorted() returns: " + randList.isSorted());
    }

    static public void main(String[] args) {
        test(9);
    }

}
