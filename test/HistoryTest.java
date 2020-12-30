// **********************************************************
// Assignment2:
// Student1: Stephen Guo
// UTORID user_name: guostep2
// UT Student #: 1006313231
// Author: Stephen Guo
//
// Student2: Steven Hans Limantoro
// UTORID user_name: limantor
// UT Student #: 1005780861
// Author: Steven Hans Limantoro
//
// Student3: Vignesh Nanthakumar
// UTORID user_name: nantha33
// UT Student #: 1006278948
// Author: Vignesh Nanthakumar
//
// Student4: Krutik Shah
// UTORID user_name: shahkr10
// UT Student #: 10062135626
// Author: Krutik Shah
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.History;

public class HistoryTest {

  private History history;
  private ArrayList<String> expectedHistoryList;

  @Before
  public void setUp() {
    this.history = new History();
    this.expectedHistoryList = new ArrayList<String>();
  }

  @Test
  public void testEmptyInput() {
    String output = "";
    History.addInputHistory("mkdir a b");
    History.addInputHistory("history");
    output = history.execute(new String[] {"history"}, null);
    assertEquals("1. mkdir a b\n2. history", output);
    History.addInputHistory("echo   \"I love anime!\" > file1");
    History.addInputHistory("");
    History.addInputHistory("     ");
    History.addInputHistory("pog champ");
    History.addInputHistory("ls");
    History.addInputHistory("history");
    output = history.execute(new String[] {"history"}, null);
    assertEquals(
        "1. mkdir a b\n2. history\n3. echo   \"I love anime!\" > file1\n4. pog champ\n5. ls\n6. "
            + "history",
        output);
  }

  @Test
  public void testHistoryValidParameter() {
    String output = "";
    History.addInputHistory("mkdir a b");
    History.addInputHistory("history 2");
    output = history.execute(new String[] {"history", "2"}, null);
    assertEquals("1. mkdir a b\n2. history 2", output);
    History.addInputHistory("echo   \"I love anime!\" > file1");
    History.addInputHistory("");
    History.addInputHistory("     ");
    History.addInputHistory("pog champ");
    History.addInputHistory("ls");
    History.addInputHistory("history 3");
    output = history.execute(new String[] {"history", "3"}, null);
    assertEquals("4. pog champ\n5. ls\n6. history 3", output);
  }

  @Test
  public void testHistoryLargeParameter() {
    String output = "";
    History.addInputHistory("mkdir a b");
    History.addInputHistory("history 2");
    History.addInputHistory("echo   \"I love anime!\" > file1");
    History.addInputHistory("");
    History.addInputHistory("     ");
    History.addInputHistory("pog champ");
    History.addInputHistory("ls");
    History.addInputHistory("history 314158");
    output = history.execute(new String[] {"history", "314158"}, null);
    assertEquals(
        "1. mkdir a b\n2. history 2\n3. echo   \"I love anime!\" > file1\n4. pog champ\n5. ls\n6. "
            + "history 314158",
        output);
  }

  @Test
  public void testGetHistory() {
    expectedHistoryList.add("mkdir a b");
    expectedHistoryList.add("history");
    expectedHistoryList.add("echo   \"I love anime!\" > file1");
    expectedHistoryList.add("pog champ");
    expectedHistoryList.add("ls");
    expectedHistoryList.add("history");

    History.addInputHistory("mkdir a b");
    History.addInputHistory("history");
    History.addInputHistory("echo   \"I love anime!\" > file1");
    History.addInputHistory("");
    History.addInputHistory("     ");
    History.addInputHistory("pog champ");
    History.addInputHistory("ls");
    History.addInputHistory("history");

    ArrayList<String> actualHistoryList = History.getHistory();

    for (int i = 0; i < expectedHistoryList.size(); i++) {
      assertEquals(expectedHistoryList.get(i), actualHistoryList.get(i));
    }
  }

  @After
  public void tearDown() {
    History.clearInputHistory();
  }

}
