package fengfei.studio.algorithm.queue;

import java.util.*;
import java.util.function.Consumer;

public class ARDeque<T> implements Iterable{
    private List<T> items;

    /**
     * @return the number of items in the deque.
     */
    public int size() {
        return items.size();
    }


    /*
     ***************************
     * DO NOT MODIFY CODE ABOVE
     ***************************
     */


    /*
     ******************* HELPER METHODS START *******************
     ***** Include your helper method(s) in EACH Submission *****
     *********************** that uses it ***********************
     */


    /* Resizes the underlying array to the target capacity. */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {



    }

    /*
     ******************** HELPER METHODS END ********************
     */


    // EXERCISE 10.1 EMPTY CONSTRUCTOR

    /**
     * Creates an empty deque.
     */
    @SuppressWarnings("unchecked")
    public ARDeque() {

        this.items = new ArrayList<>();

    }


    // EXERCISE 10.2 ADD TO BACK

    /**
     * Adds an item of type T to the back of the deque.
     * @param item is a type T object to be added.
     */
    public void addLast(T item) {

        this.items.add(item);

    }


    // EXERCISE 10.3 PRINT ITEMS

    /**
     * Prints the items in the deque from first to last,
     * separated by a space, ended with a new line.
     */
    public void printDeque() {
        for (T item : items){
            System.out.println(item);
        }


    }


    // EXERCISE 10.4 GET ITEM

    /**
     * Gets the item at the given index.
     * Does not mutate the deque.
     * @param index is an index where 0 is the front.
     * @return the index-th item of the deque.
     * @throws IndexOutOfBoundsException if no item exists at the given index.
     */
    public T get(int index) {
        return this.items.get(index);
    }


    // ASSIGNMENT 10.1 ADD TO FRONT

    /**
     * Adds an item of type T to the front of the deque.
     * @param item is a type T object to be added.
     */
    public void addFirst(T item) {



    }


    // ASSIGNMENT 10.2 DELETE FRONT

    /**
     * Deletes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * @return the first item of the deque, null if it does not exist.
     */
    public T delFirst() {

        return null;

    }


    // ASSIGNMENT 10.3 DELETE BACK

    /**
     * Deletes and returns the item at the back  of the deque.
     * If no such item exists, returns null.
     * @return the last item of the deque, null if it does not exist.
     */
    public T delLast() {


        return null;

    }


    // ASSIGNMENT 10.4 COPY CONSTRUCTOR

    /**
     * Creates a (deep) copy of another Deque object.
     * @param other is another ARDeque<T> object.
     */
    public ARDeque(ARDeque<T> other) {



    }


    @Override
    public Iterator iterator() {
        return new ARDequeOddIterator();
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }

    private class ARDequeOddIterator <T> implements Iterator<T>{
        int cursor;
        @Override
        public boolean hasNext() {
            if (cursor > size() - 1) {
                return false;
            }
            return items.get(cursor) != null;
        }

        @Override
        public T next() {
            cursor++;
            if (cursor % 2 == 0) {
                return (T) "";
            }

            return cursor > size() - 1 ? (T) "":(T) items.get(cursor);
        }
    }

    public static void main(String[] args) {
        ARDeque<String> deque = new ARDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        deque.addLast("d");
        deque.addLast("e");
        deque.addLast("f");
        for (Object o : deque) {
            System.out.print(String.valueOf(o) + "");
        }
    }
}
