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
import java.util.Stack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.PushDirectory;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;


public class PushDirectoryTest {

  private PushDirectory pushd;
  private Directory root, folder1, folder2, folder3, folder4, emptyFolder;
  private File file1, file2, file3, file4, file5, animeFile;
  private FileTree fileTree;

  @Before
  public void setUp() throws Exception {
    fileTree = FileTree.getFileTree();
    fileTree.setCurrent(fileTree.getRoot());
    root = fileTree.getRoot();

    folder1 = new Directory(root, "folder1");
    root.addDirectoryChild(folder1);
    folder2 = new Directory(root, "folder2");
    root.addDirectoryChild(folder2);
    emptyFolder = new Directory(root, "emptyFolder");
    root.addDirectoryChild(emptyFolder);
    folder3 = new Directory(folder1, "folder3");
    folder1.addDirectoryChild(folder3);
    folder4 = new Directory(folder1, "folder4");
    folder1.addDirectoryChild(folder4);

    file1 = new File(root, "file1");
    file1.setContent("apple orange");
    root.addFileChild(file1);

    file2 = new File(root, "file2");
    file2.setContent("iPhone 12 Max");
    root.addFileChild(file2);

    file3 = new File(root, "file3");
    file3.setContent("I love anime!");
    folder1.addFileChild(file3);

    file4 = new File(root, "file4");
    file4.setContent("4th file...");
    folder1.addFileChild(file4);

    file5 = new File(root, "file5");
    file5.setContent("This shell is amazin!");
    folder3.addFileChild(file5);

    animeFile = new File(root, "animeFile");
    animeFile.setContent("ayaya!");
    folder2.addFileChild(animeFile);

    pushd = new PushDirectory();
  }

  @After
  public void tearDown() throws Exception {
    PushDirectory.dirStack = new Stack<Directory>();
  }

  @Test
  public void testValidPath() {
    pushd.execute(new String[] {"pushd", "folder1"}, fileTree);
    assertEquals(PushDirectory.dirStack.peek(), root);
    pushd.execute(new String[] {"pushd", "/folder2"}, fileTree);
    assertEquals(PushDirectory.dirStack.peek().getName(), folder1.getName());
    assertEquals(PushDirectory.dirStack.size(), 2);
  }

  @Test
  public void testInvalidPath() {
    pushd.execute(new String[] {"pushd", "peepeepoopoo man"}, fileTree);
    assertTrue(PushDirectory.dirStack.isEmpty());
    pushd.execute(new String[] {"pushd", "folder1/file3"}, fileTree);
    assertTrue(PushDirectory.dirStack.isEmpty());
  }
}
