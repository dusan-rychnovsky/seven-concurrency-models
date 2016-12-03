package cz.dusanrychnovsky.wordcount;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WordCount {

  public Map<String, Integer> countWords(InputStream in) {

    Map<String, Integer> counts = new HashMap<>();

    Pages pages = new Pages(in);
    for (Page page : pages) {
      for (String word : page) {
        countWord(counts, word);
      }
    }

    return counts;
  }

  private static void countWord(Map<String, Integer> counts, String word) {
    Integer currCount = counts.get(word);
    if (currCount == null) {
      counts.put(word, 1);
    }
    else {
      counts.put(word, currCount + 1);
    }
  }
}
