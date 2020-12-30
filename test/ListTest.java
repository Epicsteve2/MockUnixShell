package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Exceptions.CustomException;
import Exceptions.InvalidPathException;
import commands.List;
import fileSystem.*;

public class ListTest {

  private List list;
  private String expectedOutput;
  private String actualOutput;
  private FileTree fileTree;

  @Before
  public void setUp() {
    list = new List();
    expectedOutput = "";
    actualOutput = "";

    fileTree = FileTree.getFileTree();
    fileTree.setCurrent(fileTree.getRoot());
  }

  @Test
  public void testNonRecursiveWithoutContentNoPath() {
    actualOutput += list.execute(new String[] {"ls"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testNonRecursiveWithContentNoPath() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    File file1 = new File(a, "file1");
    a.addFileChild(file1);

    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);
    File file3 = new File(c, "file3");
    c.addFileChild(file3);

    expectedOutput += "a\nb\nc";
    actualOutput += list.execute(new String[] {"ls"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testNonRecursiveInvalidSingleRelativePath() {
    InvalidPathException expected = new InvalidPathException("a", "");
    try {
      actualOutput += list.execute(new String[] {"ls", "a"}, fileTree);
    } catch (Exception e) {
      assertEquals(expected, e);
    }
  }

  @Test
  public void testNonRecursiveInvalidSingleFullPath() {
    InvalidPathException expected = new InvalidPathException("/b", "");
    try {
      actualOutput += list.execute(new String[] {"ls", "/b"}, fileTree);
    } catch (Exception e) {
      assertEquals(expected, e);
    }
  }

  @Test
  public void testNonRecursiveFirstValidRelativePath() {
    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);
    File file1 = new File(b, "file1");
    b.addFileChild(file1);

    InvalidPathException expected = new InvalidPathException("/a", "b:\nb1\nfile1\n\n");
    try {
      actualOutput += list.execute(new String[] {"ls", "b", "/a"}, fileTree);
    } catch (CustomException e) {
      assertEquals(expected.getContent(), e.getContent());
    }
  }

  @Test
  public void testNonRecursiveFirstValidFullPath() {
    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);
    File file1 = new File(b, "file1");
    b.addFileChild(file1);

    InvalidPathException expected = new InvalidPathException("/a", "/b:\nb1\nfile1\n\n");
    try {
      actualOutput += list.execute(new String[] {"ls", "/b", "/a"}, fileTree);
    } catch (CustomException e) {
      assertEquals(expected.getContent(), e.getContent());
    }
  }

  @Test
  public void testNonRecursiveMultiplePathsToDirectoriesWithoutContentRelativeAndFullPaths() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);

    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);

    expectedOutput += "a:\n\n/b:\n\nc:\n";
    actualOutput += list.execute(new String[] {"ls", "a", "/b", "c"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testNonRecursiveMultiplePathsToDirectoriesWithContentRelativeAndFullPaths() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    File file1 = new File(a, "file1");
    a.addFileChild(file1);

    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);
    File file3 = new File(c, "file3");
    c.addFileChild(file3);

    expectedOutput += "/a:\nfile1\n\nb:\nb1\n\n/c:\nfile3\n";
    actualOutput += list.execute(new String[] {"ls", "/a", "b", "/c"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testNonRecursiveMultiplePathsToFilesRelativeAndFullPaths() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    File file1 = new File(a, "file1");
    a.addFileChild(file1);


    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    File file2 = new File(b, "file2");
    b.addFileChild(file2);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);
    File file3 = new File(c, "file3");
    c.addFileChild(file3);


    expectedOutput += "/a/file1\n/b/file2\nc/file3";
    actualOutput += list.execute(new String[] {"ls", "/a/file1", "/b/file2", "c/file3"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRecursiveWithoutContentNoPath() {
    actualOutput += list.execute(new String[] {"ls", "-R"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRecursiveMultiplePathsToDirectoriesWithoutContentRelativeAndFullPaths() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);

    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);

    expectedOutput += "a:\n\n/b:\n\nc:\n";
    actualOutput += list.execute(new String[] {"ls", "-R", "a", "/b", "c"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRecursiveMultiplePathsToDirectoriesWithContentRelativeAndFullPaths() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    File file1 = new File(a, "file1");
    a.addFileChild(file1);

    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);
    File file2 = new File(b1, "file2");
    b1.addFileChild(file2);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);
    File file3 = new File(c, "file3");
    c.addFileChild(file3);

    expectedOutput += "a:\nfile1\n\n/b:\nb1\n/b/b1:\nfile2\n\n/c:\nfile3\n";
    actualOutput += list.execute(new String[] {"ls", "-R", "a", "/b", "/c"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRecursiveMultiplePathsToFilesRelativeAndFullPaths() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    File file1 = new File(a, "file1");
    a.addFileChild(file1);

    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    File file2 = new File(b, "file2");
    b.addFileChild(file2);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);
    File file3 = new File(c, "file3");
    c.addFileChild(file3);

    expectedOutput += "a:\nfile1\n\n/b:\nfile2\n\nc:\nfile3\n";
    actualOutput += list.execute(new String[] {"ls", "-R", "a", "/b", "c"}, fileTree);
    assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testRecursiveWithContentNoPath() {
    Directory a = new Directory(fileTree.getRoot(), "a");
    fileTree.getRoot().addDirectoryChild(a);
    File file1 = new File(a, "file1");
    a.addFileChild(file1);

    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);

    Directory c = new Directory(fileTree.getRoot(), "c");
    fileTree.getRoot().addDirectoryChild(c);
    File file3 = new File(c, "file3");
    c.addFileChild(file3);

    expectedOutput += "a\nb\nc\n/a:\nfile1\n/b:\nb1\n/b/b1:\n/c:\nfile3";
    actualOutput += list.execute(new String[] {"ls", "-R"}, fileTree);
  }

  @Test
  public void testRecursiveInvalidSingleRelativePath() {
    InvalidPathException expected = new InvalidPathException("a", "");
    try {
      actualOutput += list.execute(new String[] {"ls", "-R", "a"}, fileTree);
    } catch (Exception e) {
      assertEquals(expected, e);
    }
  }

  @Test
  public void testRecursiveInvalidSingleFullPath() {
    InvalidPathException expected = new InvalidPathException("/b", "");
    try {
      actualOutput += list.execute(new String[] {"ls", "-R", "/b"}, fileTree);
    } catch (Exception e) {
      assertEquals(expected, e);
    }
  }

  @Test
  public void testRecursiveFirstValidRelativePath() {
    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);
    File file1 = new File(b, "file1");
    b.addFileChild(file1);

    InvalidPathException expected = new InvalidPathException("/a", "b:\nb1\nfile1\n/b/b1:\n\n");
    try {
      actualOutput += list.execute(new String[] {"ls", "-R", "b", "/a"}, fileTree);
    } catch (CustomException e) {
      assertEquals(expected.getContent(), e.getContent());
    }
  }

  @Test
  public void testRecursiveFirstValidFullPath() {
    Directory b = new Directory(fileTree.getRoot(), "b");
    fileTree.getRoot().addDirectoryChild(b);
    Directory b1 = new Directory(b, "b1");
    b.addDirectoryChild(b1);
    File file1 = new File(b, "file1");
    b.addFileChild(file1);

    InvalidPathException expected = new InvalidPathException("/a", "/b:\nb1\nfile1\n/b/b1:\n\n");
    try {
      actualOutput += list.execute(new String[] {"ls", "-R", "/b", "/a"}, fileTree);
    } catch (CustomException e) {
      assertEquals(expected.getContent(), e.getContent());
    }
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileTree.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the fileTree parameter to null to create new instance next construction
    field.set(null, null);
  }
}
