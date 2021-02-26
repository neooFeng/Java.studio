package fengfei.studio.algorithm.queue;

import fengfei.studio.algorithm.queue.ARDeque;

import java.util.HashSet;
import java.util.Set;

public class UniqueARDeque<T> extends ARDeque<T> {

    public UniqueARDeque() {
        super();
    }

    /* DO NOT MODIFY CODE ABOVE */

    // CW3 Part C.2

    /**
     * Creates a deep copy from a Deque object,
     * but throws an exception if the object contains duplicates.
     * @param deque is a Deque object
     * @throws IllegalArgumentException if duplicates in object found
     */
    public UniqueARDeque(ARDeque<T> deque) {
        int size = deque.size();

        Set<T> uniqueSet = new HashSet<>();
        for (int i=0; i< size; i++){
            T item = deque.get(i);

            if(uniqueSet.add(item)){
                addLast(item);
            }else{
                throw  new IllegalArgumentException("Do not input deque with duplicates!");
            }

        }
    }
}