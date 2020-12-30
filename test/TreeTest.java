package test;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fileSystem.*;
import commands.Tree;

public class TreeTest {

  /*
   * instance of Tree to print the file tree
   */
  private Tree tree;

  /*
   * instance of FileTree to print
   */
  private FileTree fileT;

  /*
   * size of indentation to display subdirectories
   */
  private String INDENT;

  @Before
  public void setUp() {
    fileT = FileTree.getFileTree();
    fileT.setCurrent(fileT.getRoot());

    tree = new Tree();

    INDENT = Tree.getIndentationSze();
  }

  @Test
  /*
   * Test if root is printed for empty file tree
   */
  public void testRoot() {
    assertEquals("\\\n", tree.execute(new String[] {"tree"}, fileT));
  }

  @Test
  /*
   * Test if all directories are printed with duplicate namees
   */
  public void testDuplicateDirectoryNames() {
    Directory a = new Directory(fileT.getRoot(), "a");
    fileT.getRoot().addDirectoryChild(a);

    Directory b1 = new Directory(a, "b");
    a.addDirectoryChild(b1);

    Directory b2 = new Directory(b1, "b");
    b1.addDirectoryChild(b2);

    Directory c = new Directory(b2, "c");
    b2.addDirectoryChild(c);

    String expected = "\\\n" + INDENT + "a\n" + INDENT + INDENT + "b\n" + INDENT + INDENT + INDENT
        + "b\n" + INDENT + INDENT + INDENT + INDENT + "c\n";


    assertEquals(expected, tree.execute(new String[] {"tree"}, fileT));
  }

  @Test
  /*
   * Test printing directory tree with multiple branches
   */
  public void testMultipleDirectoryBranches() {
    Directory a = new Directory(fileT.getRoot(), "a");
    fileT.getRoot().addDirectoryChild(a);

    Directory b1 = new Directory(a, "b");
    a.addDirectoryChild(b1);

    Directory c1 = new Directory(a, "c");
    a.addDirectoryChild(c1);

    Directory b2 = new Directory(c1, "b");
    c1.addDirectoryChild(b2);

    Directory c2 = new Directory(b1, "c");
    b1.addDirectoryChild(c2);

    String expected = "\\\n" + INDENT + "a\n" + INDENT + INDENT + "b\n" + INDENT + INDENT + INDENT
        + "c\n" + INDENT + INDENT + "c\n" + INDENT + INDENT + INDENT + "b\n";

    assertEquals(expected, tree.execute(new String[] {"tree"}, fileT));
  }


  @Test
  /*
   * Test printing directory tree with multiple branches and files
   */
  public void testMultipleDirectoryBranchesWithFiles() {
    Directory a = new Directory(fileT.getRoot(), "a");
    fileT.getRoot().addDirectoryChild(a);

    File f1 = new File(a, "f1");
    a.addFileChild(f1);

    File f2 = new File(a, "f2");
    a.addFileChild(f2);

    Directory b1 = new Directory(a, "b");
    a.addDirectoryChild(b1);

    Directory c1 = new Directory(a, "c");
    a.addDirectoryChild(c1);

    Directory b2 = new Directory(c1, "b");
    c1.addDirectoryChild(b2);

    Directory c2 = new Directory(b1, "c");
    b1.addDirectoryChild(c2);

    String expected = "\\\n" + INDENT + "a\n" + INDENT + INDENT + "b\n" + INDENT + INDENT + INDENT
        + "c\n" + INDENT + INDENT + "c\n" + INDENT + INDENT + INDENT + "b\n" + INDENT + INDENT
        + "f1\n" + INDENT + INDENT + "f2\n";

    assertEquals(expected, tree.execute(new String[] {"tree"}, fileT));
  }

  @After
  public void tearDown() throws Exception {
    Field field = (fileT.getClass()).getDeclaredField("filetree");
    field.setAccessible(true);

    // setting the filetree parameter to null to create new instance next construction
    field.set(null, null);
  }

}
