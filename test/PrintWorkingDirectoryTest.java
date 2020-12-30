package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fileSystem.FileTree;
import commands.PrintWorkingDirectory;
import java.lang.reflect.Field;

public class PrintWorkingDirectoryTest {

  /*
   * instance of PrintWorkingDirectory for unit testing
   */
  private PrintWorkingDirectory pwd;

  /*
   * instance of FileTree to print paths
   */
  private FileTree fileT;

  @Before
  public void setUp() {
    fileT = FileTree.getFileTree();
    fileT.setCurrent(fileT.getRoot());

    pwd = new PrintWorkingDirectory();
  }

  @Test
  /*
   * Test if correct full path is printed for root
   */
  public void testRoot() {
    assertEquals("/", pwd.execute(new String[] {"pwd"}, fileT));
  }

  @Test
  /*
   * Test if implementation can handle duplicate directory names in full path
   */
  public void testDuplicateDirectoryNames() {

    MockSingleDirectoryBranch dir = MockSingleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(dir);

    assertEquals("/a/b/b/c/c", pwd.execute(new String[] {"pwd"}, fileT));
  }

  @Test
  /*
   * Test full path of directory tree with multiple branches
   */
  public void testMultipleDirectoryBranches() {

    MockMultipleDirectoryBranch dir = MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(dir);

    assertEquals("/a/b/c/d2/c", pwd.execute(new String[] {"pwd"}, fileT));
  }

  @Test
  /*
   * Test direct full path printing of root
   */
  public void testPrintEmptyPath() {
    assertEquals("/", PrintWorkingDirectory.printFullPath(fileT.getCurrent()));
  }

  @Test
  /*
   * Test direct full path printing of a directory branch
   */
  public void testPrintFullPath() {

    MockMultipleDirectoryBranch dir = MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    assertEquals("/a/b/c/d2/c", PrintWorkingDirectory.printFullPath(dir));
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileT.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the filetree parameter to null to create new instance next construction
    field.set(null, null);
  }

}
