/**
 * LinkedList implementation for separate-chaining implementation.
 * @param <T>
 */
public class LinkedList<T> {
    private int size = 0;
    private Node head;
    private Node last;

    public class Node {
        T data;
        Node next;

        Node(T d) {
            data = d;
            next = null;
        }
    }

    /**
     * Adds new node to linked list
     * @param value
     */
    public void add(T value) {
        Node newNode = new Node(value);
        if (head == null && last == null) {
            head = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }

        size++;
    }

    /**
     * Removes node from linked list
     * @param index
     */
    public void remove(int index) {
        Node current = head;
        Node previous = null;
        int counter = 0;

        while(current != null && counter < index) {
            previous = current;
            current = current.next;
            counter += 1;
        }

        if (index == counter && previous != null) {
            previous.next = null;
            if (current != null && current.next == null) last = previous;
            size--;
        } else if (previous == null && head != null) {
            head = null;
            last = null;
        }
    }

    /**
     * Returns size of linked list
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Gets current nodes value from linked list
     * @param index
     * @return
     */
    public T get(int index) {
        Node current = head;
        int counter = 0;

        while(current != null && counter < index) {
            current = current.next;
            counter++;

            if (counter == index && current != null) return current.data;
        }

        if (index == counter && current != null) return current.data;
        return null;
    }
}