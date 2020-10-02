package fengfei.studio.test;

public class LLDeque<T> {

    private class Node {
        Node prev;
        T item;
        Node next;

        Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
        Node() {

        }
    }

    private Node sentinel;
    private int size;

    /**
     * @return the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * @return true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     ***************************
     * DO NOT MODIFY CODE ABOVE
     ***************************
     */


    // EXERCISE 8.1 EMPTY CONSTRUCTOR

    /**
     * Creates an empty deque.
     */
    public LLDeque() {
//        sentinel = new Node(null,null, null);
        sentinel = new Node();
        sentinel.prev = sentinel;

    }


    // EXERCISE 8.2 ADD TO FRONT

    /**
     * Adds an item of type T to the front of the deque.
     * @param item is a type T object added to the deque.
     */
    public void addFirst(T item) {



    }


    // EXERCISE 8.3 PRINT ITEMS

    /**
     * Prints the items in the deque from first to last,
     * separated by a space, ended with a new line.
     */
    public void printDeque() {



    }


    // EXERCISE 8.4 ITERATIVE GET ITEM

    /**
     * Gets the item at the given index.
     * If no such item exists, returns null.
     * Does not mutate the deque.
     * @param index is an index where 0 is the front.
     * @return the ith item of the deque, null if it does not exist.
     */

    // ASSIGNMENT 8.1 ADD TO BACK

    /**
     * Adds an item of type T to the back of the deque.
     * @param item is a type T object added to the deque.
     */
    public void addLast(T item) {
        Node node = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.next = node;
        sentinel.prev = node;
        size += 1;

    }


    // ASSIGNMENT 8.2 DELETE FRONT

    /**
     * Deletes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * @return the first item of the deque, null if it does not exist.
     */
    public T delFirst() {
        if (isEmpty()) {
            return null;
        }
        Node p = sentinel.next;
        p.next.prev = sentinel;
        sentinel.next = p.next;
        size -= 1;
        return p.item;



    }


    // ASSIGNMENT 8.3 DELETE BACK

    /**
     * Deletes and returns the item at the back  of the deque.
     * If no such item exists, returns null.
     * @return the last item of the deque, null if it does not exist.
     */
    public T delLast() {
        if (isEmpty()) {
            return null;
        }
        Node p = sentinel.prev;
        p.prev.next = sentinel;
        sentinel.prev = p.prev;
        size -= 1;
        return p.item;


    }


    // ASSIGNMENT 8.4 RECURSIVE GET ITEM

    /**
     * Gets the item at the given index.
     * If no such item exists, returns null.
     * Does not mutate the deque.
     * @param index is an index where 0 is the front.
     * @return the ith item of the deque, null if it does not exist.
     */
    public T recGet(int index) {
        return recGetHelper(index, sentinel.next);
    }

    public T recGetHelper(int index, Node node) {
        if (isEmpty() || index < 0 || index >= size) {
            return null;
        }
        if (index == 0) {
            return node.item;
        }
        return recGetHelper(index - 1, node.next);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LLDeque) {
            if (((LLDeque) o).size() != size()) {
                return false;
            }
            int count = 0;
            for (int i = 0; i < size(); i++) {
                T item = recGet(i);
                if (!item.equals(((LLDeque) o).recGet(i))) {
                    count++;
                }
                if (count > 1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        LLDeque<String> deque = new LLDeque<>();
        deque.addLast("b");
        deque.addLast("a");

        LLDeque<String> deque2 = new LLDeque<>();
        deque.addLast("b");
        deque.addLast("a");

        System.out.println(deque.equals(deque2));
    }

}

