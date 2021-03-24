import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }

        Iterator<String> iterator = queue.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            if (i > k) break;
            StdOut.println(iterator.next());
            i++;
        }

    }
}
