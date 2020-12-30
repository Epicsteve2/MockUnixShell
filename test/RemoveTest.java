package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Remove;
import fileSystem.File;
import fileSystem.FileTree;

public class RemoveTest {

  /*
   * instance of Remove for unit testing
   */
  private Remove rm;

  /*
   * instance of FileTree to print paths
   */
  private FileTree fileT;

  /*
   * instances to read print output stream
   */
  private ByteArrayOutputStream standardOuput;
  private PrintStream pError;


  @Before
  public void setUp() {
    fileT = FileTree.getFileTree();
    fileT.setCurrent(fileT.getRoot());

    rm = new Remove();

    standardOuput = new ByteArrayOutputStream();
    pError = System.out;
    System.setOut(new PrintStream(standardOuput));
  }

  @Test
  /*
   * Test removing an entire directory branch not in current directory
   */
  public void testRemoveDirectoryBranch() {

    MockMultipleDirectoryBranch dir = MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(dir);

    rm.execute(new String[] {"rm", "/a/c"}, fileT);

    assertEquals("a", fileT.getRoot().findSubDir("a").getName());

    assertEquals(null, fileT.getRoot().findSubDir("a").findSubDir("c"));
  }

  @Test
  /*
   * Test removing an entire directory branch not in current directory with files
   */
  public void testRemoveDirectoryBranchWithFiles() {

    MockMultipleDirectoryBranch dir = MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(dir);

    File f1 = new File(fileT.getRoot().findSubDir("a").findSubDir("c"), "f1");
    fileT.getRoot().findSubDir("a").findSubDir("c").addFileChild(f1);

    File f2 = new File(fileT.getRoot().findSubDir("a").findSubDir("c"), "f2");
    fileT.getRoot().findSubDir("a").findSubDir("c").addFileChild(f2);

    rm.execute(new String[] {"rm", "/a/c"}, fileT);

    assertEquals("a", fileT.getRoot().findSubDir("a").getName());

    assertEquals(null, fileT.getRoot().findSubDir("a").findSubDir("c"));
  }

  @Test
  /*
   * Test removing a directory in current path
   */
  public void testDeleteCurrentDirectoryPath() {

    MockSingleDirectoryBranch dir = MockSingleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(dir);

    rm.execute(new String[] {"rm", "/a/b/b"}, fileT);

    assertEquals("a", fileT.getRoot().findSubDir("a").getName());

    assertEquals("b", fileT.getRoot().findSubDir("a").findSubDir("b").getName());

    assertEquals("b", fileT.getRoot().findSubDir("a").findSubDir("b").findSubDir("b").getName());

    assertEquals("c",
        fileT.getRoot().findSubDir("a").findSubDir("b").findSubDir("b").findSubDir("c").getName());

    assertEquals("Cannot remove directory in current path\n", standardOuput.toString());
  }

  @Test
  /*
   * Test removing a directory that does not exist
   */
  public void testDeleteMissingDirectory() {
    rm.execute(new String[] {"rm", "/a"}, fileT);
    assertEquals("Directory does not exist\n", standardOuput.toString());
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileT.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the filetree parameter to null to create new instance next construction
    field.set(null, null);

    System.setOut(pError);
  }

}
