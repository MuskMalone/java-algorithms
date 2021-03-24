import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next = null;
        private Node previous = null;
    }

    private int size = 0;
    private Node first = null;
    private Node last = null;

    // construct an empty deque
    public Deque() {
    }


    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("ITEM IS NULL");
        if (size == 0) { // if it is the first item of the deque
            first = new Node();
            first.item = item;
            last = first;
        }
        else if (size == 1) { // if 1 item in deque
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
            last = oldFirst;
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("ITEM IS NULL");
        if (size == 0) { // if it is the first item of the deque
            last = new Node();
            last.item = item;
            first = last;
        }
        else if (size == 1) { // if 1 item in deque
            Node oldLast = last;
            last = new Node();
            last.item = item;
            oldLast.next = last;
            last.previous = oldLast;
            first = oldLast;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            oldLast.next = last;
            last.previous = oldLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("DEQUE IS EMPTY");
        Item item = first.item;
        if (size == 2) { // if last 2 items of deque
            first = first.next;
            last = first;
            first.next = null;
            first.previous = null;
        }
        else if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.previous = null;
        }
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("DEQUE IS EMPTY");
        Item item = last.item;
        if (size == 2) { // if last 2 items of deque
            last = last.previous;
            first = last;
            last.next = null;
            last.previous = null;
        }
        else if (size == 1) {
            last = null;
            first = null;
        }
        else {
            last = last.previous;
            last.next = null;
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (current == null)
                    throw new NoSuchElementException("REACHED END OF COLLECTION");
                Item data = current.item;
                current = current.next;
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
        Deque<Integer> dq = new Deque<Integer>();
        Integer i = 1;
        Integer j = 11;
        for (int loops = 1; loops < 5; loops++) {
            dq.addFirst(i);
            dq.addLast(j);
            i++;
            j++;
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
        StdOut.println(dq.isEmpty());
        StdOut.println("--------------");
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.size());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.isEmpty());
    }
}


