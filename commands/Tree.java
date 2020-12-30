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
package commands;

import java.util.Iterator;
import fileSystem.*;

/** This class has all of the methods needed to run Tree */
public class Tree extends Command {

  /**
   * spacing for each depth
   */
  private static String INDENT = "  ";


  /**
   * Gets the indentation size used to display tree subdirectories
   * 
   * @return the indentation used to display tree
   */
  public static String getIndentationSze() {
    return INDENT;
  }

  /**
   * Traverse the current directory using DFS and add its contents to the tree.
   * 
   * @param the current directory
   * @param an integer for the depth of the current directory
   * @return a string containing the contents of the directory in levels
   */
  public String treeTraversal(Directory currentDir, int depth) {

    // base case
    if (currentDir == null) {
      return "";
    }

    // add current root to tree
    String branch = INDENT.repeat(depth) + currentDir.getName() + "\n";

    // get all the directory children in the current directory
    Iterator<Directory> dirChildren = currentDir.getDirectoryChildren();

    // traverse through directory children of root using DFS and add them to tree
    while (dirChildren.hasNext()) {
      branch = branch.concat(treeTraversal(dirChildren.next(), depth + 1));
    }

    // get all the file children in the current directory
    Iterator<File> fileChildren = currentDir.getFileChildren();

    // add all file children in root to tree
    while (fileChildren.hasNext()) {
      branch = branch.concat(INDENT.repeat(depth + 1) + fileChildren.next().getName() + "\n");
    }

    return branch;
  }

  @Override
  /**
   * Execute the tree command
   * 
   * @param a string array of arguments for the tree command
   * @param shell file tree
   * @return a string containing the output of tree command
   */
  public String execute(String[] args, FileTree fileTree) {

    // get root of file tree
    Directory treeRoot = fileTree.getRoot();

    // root directory to string
    String tree = "\\";

    // call DFS to traverse tree
    tree = tree.concat(treeTraversal(treeRoot, 0));

    return tree;
  }

  @Override
  /**
   * returns the manual entry for tree
   * 
   * @return manual entry for tree
   */
  public String toString() {

    // create man page for tree command
    String info = "tree\n\n"
        + "\tStarting from the root directory (‘\\’) display the entire file system as a tree.\n"
        + "\tEvery level of the tree is indented by a tab character.\n";

    return info;
  }

}
