package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.MakeDirectory;
import fileSystem.File;
import fileSystem.FileTree;

public class MakeDirectoryTest {

  /*
   * instance of MakeDirectory for unit testing
   */
  private MakeDirectory mkdir;

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

    mkdir = new MakeDirectory();

    standardOuput = new ByteArrayOutputStream();
    pError = System.out;
    System.setOut(new PrintStream(standardOuput));
  }

  @Test
  /*
   * Test making several directories from root
   */
  public void testMakeDirectoriesFromRoot() {
    mkdir.execute(new String[] {"mkdir", "a", "b", "a/c", "d"}, fileT);

    assertEquals("a", fileT.getRoot().findSubDir("a").getName());
    assertEquals("b", fileT.getRoot().findSubDir("b").getName());
    assertEquals("c", fileT.getRoot().findSubDir("a").findSubDir("c").getName());
    assertEquals("d", fileT.getRoot().findSubDir("d").getName());
  }

  @Test
  /*
   * Test making directories in multiple branches of file tree
   */
  public void testMakeDirectoriesDifferentBranches() {

    MockMultipleDirectoryBranch dir = MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(dir);

    mkdir.execute(new String[] {"mkdir", "/a/d", "/a/c/b/c"}, fileT);

    assertEquals("d", fileT.getRoot().findSubDir("a").findSubDir("d").getName());
    assertEquals("c",
        fileT.getRoot().findSubDir("a").findSubDir("c").findSubDir("b").findSubDir("c").getName());
  }

  @Test
  /*
   * Test making directory with duplicate directory name
   */
  public void testDuplicateDirectoryName() {

    MockSingleDirectoryBranch.getMockDir(fileT.getRoot());

    mkdir.execute(new String[] {"mkdir", "a/b"}, fileT);
    assertEquals("a/b Already Exists\n", standardOuput.toString());
  }

  @Test
  /*
   * Test for error making directory with duplicate file name
   */
  public void testDuplicateFileName() {

    File f = new File(fileT.getRoot(), "a");
    fileT.getRoot().addFileChild(f);

    mkdir.execute(new String[] {"mkdir", "a"}, fileT);

    assertEquals("a Already Exists\n", standardOuput.toString());
  }

  @Test
  /*
   * Test for error with invalid directory path creation
   */
  public void testInvalidPath() {

    MockSingleDirectoryBranch.getMockDir(fileT.getRoot());

    mkdir.execute(new String[] {"mkdir", "a/c/b"}, fileT);
    assertEquals("a/c/b Invalid Path\n", standardOuput.toString());
  }

  @Test
  /*
   * Test for error with invalid directory path after succesful arguments
   */
  public void testErrorAfterDirectoryCreations() {

    MockSingleDirectoryBranch.getMockDir(fileT.getRoot());

    mkdir.execute(new String[] {"mkdir", "b", "a/b/c", "d/c", "e"}, fileT);

    assertEquals("d/c Invalid Path\n", standardOuput.toString());

    assertEquals(null, fileT.getRoot().findSubDir("e"));

    assertEquals("b", fileT.getRoot().findSubDir("b").getName());

    assertEquals("c", fileT.getRoot().findSubDir("a").findSubDir("b").findSubDir("c").getName());
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
