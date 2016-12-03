package cz.dusanrychnovsky.wordcount;

import java.io.*;
import java.util.*;

class Pages implements Iterable<Page> {

  private static final int WORDS_PER_PAGE = 1_000;

  private final List<Page> pages = new LinkedList<>();

  public Pages(InputStream in) {
    this(Integer.MAX_VALUE, in);
  }

  public Pages(int count, InputStream in) {

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

      List<String> currWords = new LinkedList<>();
      String line;
      while ((line = reader.readLine()) != null) {

        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }

        for (String word : words(line)) {
          currWords.add(word.toLowerCase());
        }

        if (currWords.size() >= WORDS_PER_PAGE) {
          pages.add(new Page(currWords));
          currWords = new LinkedList<>();

          if (pages.size() == count) {
            break;
          }
        }
      }

      if (!currWords.isEmpty()) {
        pages.add(new Page(currWords));
      }
    }
    catch (IOException ex) {
      throw new IllegalArgumentException("Cannot read the given InputStream.", ex);
    }
  }

  private Iterable<String> words(String line) {
    return Arrays.asList(line.split("\\s+"));
  }

  @Override
  public Iterator<Page> iterator() {
    return pages.iterator();
  }
}
