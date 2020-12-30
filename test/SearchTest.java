package test;

import static org.junit.Assert.assertEquals;

import Exceptions.InvalidPathException;
import commands.Command;
import commands.List;
import commands.Search;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;
import inputOutput.StandardInput;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchTest {

  private Search search;
  private String expectedOutput;
  private String actualOutput;
  private FileTree fileTree;

  @Before
  public void setUp() {
    search = new Search();
    expectedOutput = "";
    actualOutput = "";

    fileTree = FileTree.getFileTree();
    fileTree.setCurrent(fileTree.getRoot());
  }

  private void reset() {
    fileTree = FileTree.getFileTree();
    fileTree.setCurrent(fileTree.getRoot());
    Command.runCommand(new String[] {"mkdir", "folder1", "folder2", "emptyFolder"});
    Command.runCommand(new String[] {"echo", "\"apple orange\"", ">", "file1"});
    Command.runCommand(new String[] {"echo", "\"iPhone 12 Max\"", ">", "file2"});
    Command.runCommand(new String[] {"cd", "folder1"});
    Command.runCommand(new String[] {"mkdir", "folder3", "folder4"});
    Command.runCommand(new String[] {"echo", "\"I love anime!\"", ">", "file3"});
    Command.runCommand(new String[] {"echo", "\"4th file...\"", ">", "file4"});
    Command.runCommand(new String[] {"cd", "folder3"});
    Command.runCommand(new String[] {"mkdir", "folder3", "folder4"});
    Command.runCommand(new String[] {"echo", "\"This shell is amazin!\"", ">", "file5"});
    Command.runCommand(new String[] {"echo", "\"This shell is not amazin!\"", ">", "file2"});
    Command.runCommand(new String[] {"cd", "/"});
    Command.runCommand(new String[] {"cd", "folder2"});
    Command.runCommand(new String[] {"echo", "\"ayaya!\"", ">", "animeFile"});
    Command.runCommand(new String[] {"cd", "/"});
  }

  @Test
  public void testSearchForValidFile() {
    reset();
    actualOutput = search.execute("search / -type f -name \"file1\"".split(" "), fileTree);
    expectedOutput = "/:\n//file1";
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testSearchForValidFiles() {
    reset();
    actualOutput = search.execute("search / -type f -name \"file2\"".split(" "), fileTree);
    expectedOutput = "/:\n//file2\n\n/folder1/folder3/file2";
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testSearchForInvalidFile() {
    reset();
    actualOutput = search.execute("search / -type f -name \"file\"".split(" "), fileTree);
    expectedOutput = "/:\n";
    assertEquals(expectedOutput, actualOutput);
  }


  @Test
  public void testSearchForValidDir() {
    reset();
    actualOutput = search.execute("search / -type d -name \"folder1\"".split(" "), fileTree);
    expectedOutput = "/:\n/folder1";
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testSearchForValidDirs() {
    reset();
    actualOutput = search.execute("search / -type d -name \"folder3\"".split(" "), fileTree);
    expectedOutput = "/:\n\n/folder1/folder3\n/folder1/folder3/folder3";
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testSearchForInvalidDir() {
    reset();
    actualOutput = search.execute("search / -type d -name \"file\"".split(" "), fileTree);
    expectedOutput = "/:\n";
    assertEquals(expectedOutput, actualOutput);
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileTree.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the fileTree parameter to null to create new instance next construction
    field.set(null, null);
  }
}
