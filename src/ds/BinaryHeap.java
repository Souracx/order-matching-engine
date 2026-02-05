package ds;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * Custom Binary Heap (Priority Queue).
 */
public class BinaryHeap<T> {

    private Object[] data;	//array representing a binary tree 
    private int size;		//current element in the heap 
    private final Comparator<T> comparator;	

    public BinaryHeap(Comparator<T> comparator) {
        if (comparator == null) throw new IllegalArgumentException("Comparator cannot be null.");
        this.comparator = comparator;
        this.data = new Object[16];	//initial capacity 
        this.size = 0;
    }

    public int size() { 
    	return size; 
    	}
    
    //Return true if empty heap 
    public boolean isEmpty() { 
    	return size == 0; 
    	}

    public void add(T value) {
        if (value == null) throw new IllegalArgumentException("Cannot insert null.");
        ensureCapacity(size + 1);
        data[size] = value;
        bubbleUp(size);
        size++;
    }

    /** Safe peek: returns null if empty */
    public T peek() {
        return size == 0 ? null : elementAt(0);
    }

    /** Removes root of the heap */
    public T poll() {
        if (size == 0) throw new NoSuchElementException("Heap is empty.");

        T top = elementAt(0); //save the best element 
        size--; //reduce heap size

        if (size > 0) {
        	//Move last element to the root 
            data[0] = data[size];
            data[size] = null;
            bubbleDown(0);	//restore heap property 
        } else {
            data[0] = null;
        }

        return top;
    }

   
    @SuppressWarnings("unchecked")
    private T elementAt(int index) {
        return (T) data[index];
    }

    //Expand array capacity 2x when needed 
    private void ensureCapacity(int needed) {
        if (needed <= data.length) return;
        Object[] next = new Object[data.length * 2];
        System.arraycopy(data, 0, next, 0, size);
        data = next;
    }

    
    //Restores heap property by moving UP an element 
    //used after insertion 
    private void bubbleUp(int idx) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;

            T cur = elementAt(idx);
            T par = elementAt(parent);
            // If current element has higher priority than parent → swap
            if (comparator.compare(cur, par) < 0) {
                data[idx] = par;
                data[parent] = cur;
                idx = parent;
            } else {
            	// Heap property restored
            	break;}
        }
    }

    //Restores heap property after moving an element down 
    //Used after removing root 
    private void bubbleDown(int idx) {
        while (true) {
            int left = idx * 2 + 1;
            int right = idx * 2 + 2;
            //Stop if no children 
            if (left >= size) break;
            
            //Choose child with high priority 
            int best = left;
            if (right < size &&
                comparator.compare(elementAt(right), elementAt(left)) < 0) {
                best = right;
            }
            
            //Swap if child is better than current 
            if (comparator.compare(elementAt(best), elementAt(idx)) < 0) {
                Object tmp = data[idx];
                data[idx] = data[best];
                data[best] = tmp;
                idx = best;
            } else {
            	
            	break;}
        }
    }
}