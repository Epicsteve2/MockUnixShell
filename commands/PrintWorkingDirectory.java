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

import fileSystem.Directory;
import fileSystem.FileTree;

/** This class has all of the methods needed to run PrintWorkingDirectory */
public class PrintWorkingDirectory extends Command {

  /**
   * Creates the absolute path of the given directory as a string
   * 
   * @param the current directory
   * @return path of the given directory as a string
   */
  public static String printFullPath(Directory d) {

    FileTree fileTree = FileTree.getFileTree();

    // directory is empty
    if (d == null) {
      return null;
    }

    // initial path string
    String path = "/" + d.getName();

    // get parent of given directory
    Directory parent = d.getParent();

    // iterate over directory parents until head is reached
    while (parent != null && parent != fileTree.getRoot()) {

      // append directory to front of path
      path = "/" + parent.getName() + path;

      // get next parent
      parent = parent.getParent();
    }

    return path;
  }

  @Override
  /**
   * Execute the pwd command
   * 
   * @param a string array of arguments for the pwd command
   * @param shell file tree
   * @return a string containing the output of pwd command
   */
  public String execute(String[] args, FileTree fileTree) {
    // TODO Auto-generated method stub

    // get current directory from FileTree
    Directory p = fileTree.getCurrent();

    // create absolute path of current directory
    String path = printFullPath(p);

    return path;
  }

  @Override
  /**
   * Returns a manual entry for pwd
   * 
   * @return a string containing manual entry for pwd
   */
  public String toString() {
    // TODO Auto-generated method stub

    // create man page for pwd command
    String info = "pwd\n\n" + "\tPrint the current working directory (including the whole path).\n";

    return info;
  }
}
