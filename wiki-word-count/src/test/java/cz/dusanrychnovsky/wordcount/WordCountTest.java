package cz.dusanrychnovsky.wordcount;

import cz.dusanrychnovsky.util.TopN;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

@Slf4j
public class WordCountTest {

  @Test
  public void countsWordsInFile() throws IOException {

    File file = getFile("pg100.txt");
    Map<String, Integer> counts = measureDuration(() ->
      new WordCount().countWords(new FileInputStream(file))
    );

    List<Map.Entry<String, Integer>> top10 = TopN.getTopN(
      counts.entrySet().stream(),
      (first, second) -> Integer.compare(first.getValue(), second.getValue()),
      10
    );

    assertEquals(10, top10.size());

    assertEquals("the", top10.get(0).getKey());
    assertEquals(27_729, (int) top10.get(0).getValue());

    assertEquals("and", top10.get(1).getKey());
    assertEquals(26_099, (int) top10.get(1).getValue());

    assertEquals("i", top10.get(2).getKey());
    assertEquals(19_540, (int) top10.get(2).getValue());

    assertEquals("to", top10.get(3).getKey());
    assertEquals(18_762, (int) top10.get(3).getValue());

    assertEquals("of", top10.get(4).getKey());
    assertEquals(18_126, (int) top10.get(4).getValue());

    assertEquals("a", top10.get(5).getKey());
    assertEquals(14_436, (int) top10.get(5).getValue());

    assertEquals("my", top10.get(6).getKey());
    assertEquals(12_456, (int) top10.get(6).getValue());

    assertEquals("in", top10.get(7).getKey());
    assertEquals(10_730, (int) top10.get(7).getValue());

    assertEquals("you", top10.get(8).getKey());
    assertEquals(10_696, (int) top10.get(8).getValue());

    assertEquals("that", top10.get(9).getKey());
    assertEquals(10_501, (int) top10.get(9).getValue());
  }

  private static File getFile(String name) {
    return new File(WordCountTest.class.getResource(name).getFile());
  }

  private static <T> T measureDuration(Callable<T> block) {

    log.info("----------------------------------------------------");

    long startTimeMs = System.currentTimeMillis();
    log.info("START TIME: " + startTimeMs);

    T result;
    try {
      result = block.call();
    }
    catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    long finishTimeMs = System.currentTimeMillis();
    log.info("FINISH TIME: " + finishTimeMs);

    long duration = finishTimeMs - startTimeMs;
    log.info("DURATION: " + duration + " ms");

    log.info("----------------------------------------------------");

    return result;
  }

}
