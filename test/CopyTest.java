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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Exceptions.InvalidPathException;
import commands.Copy;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;

public class CopyTest {
  
  private Copy cp;
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
    
    cp = new Copy();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testCopyFile() {
    cp.execute(new String[] {"cp", "file1", "animeFile2"}, fileTree);
    File animeFile2 = fileTree.traverseToEnd("/animeFile2");
    assertEquals(file1.getContent(), animeFile2.getContent());
    assertEquals("apple orange", animeFile2.getContent());
    cp.execute(new String[] {"cp", "animeFile2", "animeFile2"}, fileTree);
    assertEquals("apple orange", animeFile2.getContent());

    cp.execute(new String[] {"cp", "file2", "./folder1/fileInfinity"}, fileTree);
    File fileInfinity = fileTree.traverseToEnd("folder1/fileInfinity");
    assertEquals("iPhone 12 Max", fileInfinity.getContent());
    assertEquals(file2.getContent(), fileInfinity.getContent());
    
    cp.execute(new String[] {"cp", "file1", "folder1"}, fileTree);
    File file1Copy = fileTree.traverseToEnd("folder1/file1");
    assertEquals("apple orange", file1.getContent());
    assertEquals(file1Copy.getContent(), file1.getContent());
  }
  
  @Test
  public void testInvalidFile() {
    InvalidPathException expected = new InvalidPathException("fileNothing");
    try {
      cp.execute(new String[] {"cp", "fileNothing", "animeFile2"}, fileTree);
    } catch (Exception e) {
      assertEquals(expected, e);
    }
    try {
      cp.execute(new String[] {"cp", "fileNothing", "./folder1/animeFile2"}, fileTree);
    } catch (Exception e) {
      assertEquals(expected, e);
    }
    try {
      cp.execute(new String[] {"cp", "fileNothing", "/folder1"}, fileTree);
    } catch (Exception e) {
      assertEquals(expected, e);
    }
  }
  
  @Test
  public void testCopyDirectories() {
    cp.execute(new String[] {"cp", "emptyFolder", "animeFolder"}, fileTree);
    Directory animeFolder = (Directory) fileTree.traverseToEnd("/animeFolder");
    assertEquals(animeFolder.getNumOfFileChildren(), emptyFolder.getNumOfFileChildren());
    
    cp.execute(new String[] {"cp", "folder1", "testFolder"}, fileTree);
    Directory folder1Copy = (Directory) fileTree.traverseToEnd("/testFolder");
    assertEquals(folder1.getNumOfFileChildren(), folder1Copy.getNumOfFileChildren());
  }
  
  @Test
  public void testInvalidDirectory() {
    InvalidPathException expected = new InvalidPathException(".");
    try {
      cp.execute(new String[] {"cp", ".", "."}, fileTree);
    } catch (Exception e) {
      assertEquals(e, expected);
    }
  }
}
