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
package fileSystem;

import commands.PrintWorkingDirectory;

/**
 * This class handles all methods relating to the file system and its traversal
 */
public class FileTree {

  /**
   * The Root directory
   */
  private Directory root;

  /**
   * The current directory
   */
  private Directory current;

  /**
   * The current path
   */
  private String currentPath;

  /**
   * The only instance of FileTree
   */
  private static FileTree filetree = null;

  /**
   * getter for root directory
   * 
   * @return root
   */
  public Directory getRoot() {
    return this.root;
  }

  /**
   * singleton getter: if file-tree exists, return it. Otherwise, initialize one and return it.
   * 
   * @return file-tree
   */
  public static FileTree getFileTree() {
    if (filetree == null) {
      filetree = new FileTree();
    } ;
    return filetree;

  }

  private FileTree() {
    this.root = new Directory(null, "");
    this.current = this.root;
    this.currentPath = "/";
  }

  /**
   * setter for root
   * 
   * @param root
   */
  public void setRoot(Directory root) {
    this.root = root;
  }

  /**
   * getter for current directory
   * 
   * @return current directory
   */
  public Directory getCurrent() {
    return this.current;
  }

  /**
   * setter for current directory
   * 
   * @param current
   */
  public void setCurrent(Directory current) {
    this.current = current;
    this.setCurrentPath(PrintWorkingDirectory.printFullPath(current));
  }

  /**
   * here fore testing all the options on the final branch of a path
   * 
   * @param dir
   * @param file
   * @return File
   */
  private File getLastFile(Directory dir, String file) {

    // check if last element is a File:
    if (dir.findFile(file) != null) {
      return dir.findFile(file);

      // if not check all three cases for directories
    } else {

      if (file.equals("..")) {
        return dir.getParent();

      } else if (file.equals(".")) {
        return dir;

      } else {
        return dir.findSubDir(file);
      }
    }

  }

  /**
   * Iteratively traverse the given path and return the directory or file
   *
   * @param path
   * @return the file or directory object
   */
  public File traverseToEnd(String path) {
    // Split the entire path into individual directories or files
    String[] p = path.split("/");

    if (p.length == 0) {
      return this.root;
    }

    // Decide whether the path is absolute or relative
    Directory dir = (p[0].equals("")) ? this.root : this.current;

    int i = (p[0].equals("")) ? 1 : 0;

    // Iterate through all the individual directories except the last one
    for (; i < p.length - 1; i++) {

      // Find the corresponding sub-directory
      if (p[i].equals("..")) {

        // if sub directory is .. get parent
        dir = dir.getParent();

        // only modify if sub directory is not .
      } else if (!p[i].equals(".")) {
        dir = dir.findSubDir(p[i]);
      }

      // If the sub-directory is not found
      if (dir == null) {
        return null;
      }

    }

    // get the last file
    return this.getLastFile(dir, p[p.length - 1]);
  }

  /**
   * getter for current path
   * 
   * @return current path
   */
  public String getCurrentPath() {
    return this.currentPath;
  }

  /**
   * setter for current path
   * 
   * @param new current path
   */
  public void setCurrentPath(String currentPath) {
    this.currentPath = currentPath;
  }
}
