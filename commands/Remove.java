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
import inputOutput.StandardOutput;

/** This class has all of the methods needed to run Remove */
public class Remove extends Command {

  /**
   * directory to be removed
   */
  private Directory removeDir;

  /**
   * Deletes a directory from the file system and recursively deletes it's contents
   * 
   * @param the directory to be deleted
   */
  private static void deleteDir(Directory currentDir) {

    // base case
    if (currentDir == null) {
      return;
    }

    // get all the directory children in the current directory
    Iterator<Directory> dirChildren = currentDir.getDirectoryChildren();

    // traverse through directory children and delete them recursively
    while (dirChildren.hasNext()) {
      deleteDir(dirChildren.next());
    }

    // get all the file children in the current directory
    Iterator<File> fileChildren = currentDir.getFileChildren();

    // delete all file children
    while (fileChildren.hasNext()) {
      currentDir.deleteFileChild(fileChildren.next());
      System.gc();
    }

    // delete current dir
    currentDir.getParent().deleteDirectoryChild(currentDir);
    System.gc();

  }

  /**
   * Find directory to be removed and stores it in removeDir global variable
   *
   * @param full path of the directory as a string
   * @param shell file tree
   * @return specific path error or success string
   */
  private String getRemoveDirectory(String arg, FileTree fileTree) {

    // get current directory as parent for dir
    if (arg.lastIndexOf("/") == -1) {
      removeDir = fileTree.getCurrent().findSubDir(arg);

      // get parent of current directory as parent for dir
    } else if (arg.lastIndexOf("/") == 0) {
      removeDir = fileTree.getRoot().findSubDir(arg.substring(1));

      // traverse to start of path and get parent for dir
    } else {
      removeDir = (Directory) fileTree.traverseToEnd(arg.substring(0, arg.lastIndexOf("/")));

      // path cannot be traversed
      if (removeDir == null) {
        return "Invalid Path";
      }

      removeDir = removeDir.findSubDir(arg.substring(arg.lastIndexOf("/") + 1));
    }

    // directory does not exist
    if (removeDir == null) {
      return "Directory does not exist";
    }

    return "good";
  }

  @Override
  /**
   * Execute the rm command
   * 
   * @param a string array of arguments for the rm command
   * @param shell file tree
   * @return a string containing the output of rm command
   */
  public String execute(String[] args, FileTree fileTree) {

    // initialize global remove variable
    removeDir = null;

    // find parent directory in path
    String removeError = getRemoveDirectory(args[1], fileTree);

    // directory in path cannot be removed
    if (removeError != "good") {

      // send invalid remove path error to perror in stdout and terminate execution
      StandardOutput.perror(removeError);
      return "";
    }

    // get path of current directory
    String currentPath = fileTree.getCurrent().getPath();

    // get path of directory to remove
    String removePath = removeDir.getPath();

    // directory to remove is in current directory path
    if (currentPath.contains(removePath)) {
      StandardOutput.perror("Cannot remove directory in current path");
      return "";
    }

    // delete directory and its contents
    deleteDir(removeDir);

    // delete unused memory
    System.gc();

    return "";
  }

  @Override
  /**
   * returns the manual entry for rm
   * 
   * @return manual entry for rm
   */
  public String toString() {

    // create man page for rm command
    String info = "rm DIR\n\n"
        + "\tRemoves the DIR from the file system. This also recursively removes all the\n"
        + "\tchildren of DIR.\n";

    return info;
  }

}
