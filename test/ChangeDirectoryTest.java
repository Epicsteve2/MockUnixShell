package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.ChangeDirectory;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;

public class ChangeDirectoryTest {

  private ChangeDirectory cd;
  private FileTree fileTree;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Before
  public void setUp() {
    cd = new ChangeDirectory();

    fileTree = FileTree.getFileTree();
    fileTree.setCurrent(fileTree.getRoot());
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void testChangeToNonExistingDirectory() {
    cd.execute(new String[] {"cd", "a"}, fileTree);
    assertEquals("a does not exist", outContent.toString().trim());
  }

  @Test
  public void testChangeToNonDirectory() {
    File file1 = new File(fileTree.getRoot(), "file1");
    fileTree.getRoot().addFileChild(file1);

    cd.execute(new String[] {"cd", "file1"}, fileTree);
    assertEquals("file1 is not a directory", outContent.toString().trim());
  }

  @Test
  public void testChangeToRelativePath() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);

    cd.execute(new String[] {"cd", "a"}, fileTree);
    assertEquals("a", fileTree.getCurrent().getName());
  }

  @Test
  public void testChangeToFullPath() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);

    Directory achild1 = new Directory(a, "achild1");
    a.addDirectoryChild(achild1);

    fileTree.setCurrent(achild1);

    cd.execute(new String[] {"cd", "/a"}, fileTree);
    assertEquals("a", fileTree.getCurrent().getName());
  }

  @Test
  public void testChangeToParentDirectory() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    fileTree.setCurrent(a);

    cd.execute(new String[] {"cd", ".."}, fileTree);
    assertEquals("/", fileTree.getCurrent().getPath());
  }

  @Test
  public void testChangeToCurrentDirectory() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    fileTree.setCurrent(a);

    cd.execute(new String[] {"cd", "."}, fileTree);
    assertEquals("a", fileTree.getCurrent().getName());
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileTree.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the fileTree parameter to null to create new instance next construction
    field.set(null, null);
  }

}
