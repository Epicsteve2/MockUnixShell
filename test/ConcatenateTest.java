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
import org.junit.Test;
import Exceptions.FileNotFoundException;
import org.junit.Before;
import commands.Concatenate;
import fileSystem.*;

public class ConcatenateTest {

  private Concatenate cat;
  private FileTree fileTree;
  private Directory root, folder1, folder2, folder3, folder4, emptyFolder;
  private File file1, file2, file3, file4, file5, animeFile;

  @Before
  public void setUp() {
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

    cat = new Concatenate();
  }


  @Test
  public void testValidFiles() {
    String output = cat.execute(new String[] {"cat", "file1"}, fileTree);
    assertEquals(output, "apple orange");

    output = cat.execute(new String[] {"cat", "/file2", "./folder1/file3"}, fileTree);
    assertEquals(output, "iPhone 12 Max\n\n\nI love anime!");

    output = cat.execute(
        new String[] {"cat", "./././././folder1/folder3/file5", "folder2/animeFile"}, fileTree);
    assertEquals(output, "This shell is amazin!\n\n\nayaya!");
  }

  @Test
  public void testInvalidFiles() {
    FileNotFoundException expected = new FileNotFoundException("nothing");
    try {
      String output = cat.execute(new String[] {"cat", "nothing"}, fileTree);
    } catch (Exception e) {
      assertEquals(e, expected);
    }

    expected = new FileNotFoundException("naruto");
    try {
      String output = cat.execute(new String[] {"cat", "file1", "file2", "naruto"}, fileTree);
    } catch (Exception e) {
      assertEquals(e, expected);
    }
  }
}
