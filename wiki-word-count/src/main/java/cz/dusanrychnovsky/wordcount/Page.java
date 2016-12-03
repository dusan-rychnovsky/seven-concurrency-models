package cz.dusanrychnovsky.wordcount;

import java.util.Iterator;

class Page implements Iterable<String> {

  private final Iterable<String> words;

  public Page(Iterable<String> words) {
    this.words = words;
  }

  @Override
  public Iterator<String> iterator() {
    return words.iterator();
  }
}
