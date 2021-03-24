import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] itemArray;
    private int size = 0;
    private int tailIndex = -1;

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemArray = (Item[]) new Object[2]; // default starting capacity of the stack is 2
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("ITEM CANNOT BE NULL");
        if (size == 0) {
            itemArray[0] = item;
            tailIndex = 0;
        }
        else {
            tailIndex++;
            itemArray[tailIndex] = item;
        }
        size++;
        if (size == itemArray.length) resize(itemArray.length * 2);
    }

    // remove and return a random item
    public Item dequeue() {
        int randomIndex = StdRandom.uniform(0, tailIndex + 1);
        Item data = itemArray[randomIndex];
        if (size == 0) {
            throw new NoSuchElementException("QUEUE IS EMPTY");
        }
        else {
            Item oldTail = itemArray[tailIndex];
            itemArray[randomIndex] = oldTail;
            itemArray[tailIndex--] = null;
        }
        size--;
        if (size != 0 && size == (itemArray.length / 4)) resize(itemArray.length / 2);
        return data;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return itemArray[StdRandom.uniform(0, tailIndex + 1)];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            if (i > tailIndex) break;
            copy[i] = itemArray[i];
        }
        itemArray = copy;
    }

    private Item[] copy() {
        Item[] copyArray = (Item[]) new Object[size];
        for (int i = 0; i < size; i++) {
            if (i > tailIndex) break;
            copyArray[i] = itemArray[i];
        }
        return copyArray;
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        Item[] copy = copy();
        StdRandom.shuffle(copy);
        Iterator<Item> it = new Iterator<Item>() {
            private int currentIndex = 0;
            private Item[] array = copy;

            @Override
            public boolean hasNext() {
                return array.length > currentIndex + 1;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException("REACHED END OF COLLECTION");
                Item data = array[currentIndex];
                currentIndex++;
                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> dq = new RandomizedQueue<Integer>();
        Integer i = 1;
        for (int loops = 1; loops < 10; loops++) {
            dq.enqueue(i);
            i++;
        }
        Iterator<Integer> iterator = dq.iterator();
        StdOut.println("--------------");
        while (iterator.hasNext()) {
            try {
                StdOut.println(iterator.next());
            }
            catch (NoSuchElementException e) {
                break;
            }
        }
        StdOut.println(dq.size());
        StdOut.println(dq.isEmpty());
        StdOut.println("--------------");
        StdOut.println(dq.dequeue());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.size());
        StdOut.println(dq.dequeue());
        StdOut.println(dq.isEmpty());
    }
}
