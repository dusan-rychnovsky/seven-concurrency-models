package cz.dusanrychnovsky.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

public class TopN {

  /**
   * Consumes the given stream of entries and returns a list of top count
   * entries on that stream (i.e. entries with maximum value according to
   * the given comparator).
   *
   * Memory complexity: O(count)
   * Time complexity: O(entries.size() * log(count))
   */
  public static <T> List<T> getTopN(Stream<T> entries, Comparator<T> comparator, int count) {

    PriorityQueue<T> queue = new PriorityQueue<T>(count + 1, comparator);

    entries.forEach(entry -> {
      queue.add(entry);
      if (queue.size() > count) {
        queue.poll();
      }
    });

    List<T> result = new ArrayList<>(queue.size());
    while (!queue.isEmpty()) {
      result.add(queue.poll());
    }

    Collections.reverse(result);

    return result;
  }

  /**
   * A convenience interface for handing over an in-memory collection of entries.
   * See {@link #getTopN(Stream, Comparator, int)}.
   */
  public static <T> List<T> getTopN(List<T> entries, Comparator<T> cmp, int n) {
    return getTopN(entries.stream(), cmp, n);
  }
}