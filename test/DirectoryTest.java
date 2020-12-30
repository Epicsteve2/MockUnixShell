package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fileSystem.*;

public class DirectoryTest {

  /*
   * instance of FileTree to print paths
   */
  private FileTree fileT;

  @Before
  public void setUp() {
    fileT = FileTree.getFileTree();
    fileT.setCurrent(fileT.getRoot());
  }

  @Test
  /*
   * Test finding a sub directory in a given directory
   */
  public void testFindSubDir() {
    MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());
    assertEquals("a", fileT.getRoot().findSubDir("a").getName());
  }

  @Test
  /*
   * Test adding a sub directory in a given directory
   */
  public void testAddSubDir() {
    MockSingleDirectoryBranch dir = MockSingleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(dir);

    fileT.getCurrent().addDirectoryChild(new Directory(fileT.getCurrent(), "h"));

    assertEquals("h", fileT.getCurrent().findSubDir("h").getName());
  }

  @Test
  /*
   * Test deleting a sub directory in a given directory
   */
  public void testDeleteSubDir() {
    MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(fileT.getRoot().findSubDir("a").findSubDir("c"));

    fileT.getCurrent().deleteDirectoryChild(fileT.getCurrent().findSubDir("b"));

    assertEquals(null, fileT.getRoot().findSubDir("a").findSubDir("c").findSubDir("b"));
  }

  @Test
  /*
   * Test adding a file to a sub directory and finding the file
   */
  public void testAddFindFile() {
    MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    File f1 = new File(fileT.getRoot(), "f1");
    fileT.getRoot().addFileChild(f1);

    File f2 = new File(fileT.getRoot(), "f2");
    fileT.getRoot().addFileChild(f2);

    assertEquals("f1", fileT.getRoot().findFile("f1").getName());
    assertEquals("f2", fileT.getRoot().findFile("f2").getName());
  }

  @Test
  /*
   * Test getting the directory children of a directory and iterating over them
   */
  public void testGetDirectoryChildren() {
    MockMultipleDirectoryBranch.getMockDir(fileT.getRoot());

    fileT.setCurrent(fileT.getRoot().findSubDir("a"));

    Iterator<Directory> dirChildren = fileT.getCurrent().getDirectoryChildren();

    String[] dirs = {"b", "c"};

    int count = 0;

    while (dirChildren.hasNext()) {
      assertEquals(dirs[count], dirChildren.next().getName());
      count++;
    }
  }

  @Test
  /*
   * Test getting the file children of a directory and iterating over them
   */
  public void testGetFileChildren() {

    File f1 = new File(fileT.getRoot(), "f1");
    fileT.getRoot().addFileChild(f1);

    File f2 = new File(fileT.getRoot(), "f2");
    fileT.getRoot().addFileChild(f2);

    Iterator<File> fileChildren = fileT.getCurrent().getFileChildren();

    String[] dirs = {"f1", "f2"};

    int count = 0;

    while (fileChildren.hasNext()) {
      assertEquals(dirs[count], fileChildren.next().getName());
      count++;
    }
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileT.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the filetree parameter to null to create new instance next construction
    field.set(null, null);
  }

}
