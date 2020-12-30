package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;
import commands.Move;

public class MoveTest {

  private Move move;
  private FileTree fileTree;

  @Before
  public void setUp() throws Exception {
    move = new Move();

    fileTree = FileTree.getFileTree();
    fileTree.setCurrent(fileTree.getRoot());
  }

  @Test
  public void testMoveFileToRelativePathDirectory() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    File file1 = new File(a, "file1");
    fileTree.getRoot().addFileChild(file1);

    move.execute(new String[] {"mv", "file1", "a"}, fileTree);
    assertEquals("file1", fileTree.traverseToEnd("/a/file1").getName());
  }

  @Test
  public void testMoveFileToFullPathDirectory() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    Directory b = new Directory(a, "b");
    a.addDirectoryChild(b);

    File file1 = new File(fileTree.getRoot(), "file1");
    fileTree.getRoot().addFileChild(file1);

    move.execute(new String[] {"mv", "file1", "/a/b"}, fileTree);
    assertEquals("file1", fileTree.traverseToEnd("/a/b/file1").getName());
  }

  @Test
  public void testMoveRelativePathDirectoryToRelativePathDirectory() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);

    move.execute(new String[] {"mv", "b", "a"}, fileTree);
    assertEquals("b", fileTree.traverseToEnd("/a/b").getName());
  }

  @Test
  public void testMoveRelativePathDirectoryToFullPathDirectory() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory c = new Directory(a, "c");
    a.addDirectoryChild(c);

    move.execute(new String[] {"mv", "b", "/a/c"}, fileTree);
    assertEquals("b", fileTree.traverseToEnd("/a/c/b").getName());
  }

  @Test
  public void testMoveFullDirectorytoAnotherFullDirectory() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    Directory b = new Directory(a, "b");
    a.addDirectoryChild(b);
    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);

    move.execute(new String[] {"mv", "c", "/a/b"}, fileTree);
    assertEquals("c", fileTree.traverseToEnd("/a/b/c").getName());
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileTree.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the fileTree parameter to null to create new instance next construction
    field.set(null, null);
  }
}
