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
import fileSystem.File;
import fileSystem.FileTree;
import inputOutput.Error;
import inputOutput.StandardOutput;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This class has all of the methods needed to run Search
 */
public class Search extends Command {

  /**
   * Search for a file in a directory.
   *
   * @param a directory to search
   * @param the name of a file to search for
   * @return the path of the file if found
   */
  private String searchFile(Directory dir, String name) {
    File f;
    // getting iterator for File children
    Iterator<File> fileChildren = dir.getFileChildren();
    while (fileChildren.hasNext()) {
      // checking all all files and if fond, return the name
      f = fileChildren.next();
      if (f.getName().equals(name)) {
        return f.getPath();
      }
    }
    return "";
  }

  /**
   * Search for a sub directory in a directory.
   *
   * @param a directory to search
   * @param the name of a directory to search for
   * @return the path of the directory if found
   */
  private String searchDir(Directory dir, String name) {
    Directory f;
    // getting dir children iterator
    Iterator<Directory> dirChildren = dir.getDirectoryChildren();
    while (dirChildren.hasNext()) {
      // looping through all directories and if found return name
      f = dirChildren.next();
      if (f.getName().equals(name)) {
        return f.getPath();
      }
    }
    return "";
  }

  /**
   * Search a directory for files or sub directories.
   *
   * @param a directory to search
   * @param the name of a directory or file to search for
   * @param boolean to search for file or directory
   * @return a string of all found file or directory paths
   */
  private String search(Directory dir, String name, boolean file) {
    String output;
    // searching for file or directory depending on type
    if (file) {
      output = searchFile(dir, name);
    } else {
      output = searchDir(dir, name);
    }
    Directory f;
    String temp;
    // recursively searching through all sub directories
    Iterator<Directory> dirChildren = dir.getDirectoryChildren();
    while (dirChildren.hasNext()) {
      f = dirChildren.next();
      temp = search(f, name, file);
      if (!temp.equals("")) {
        output += "\n" + temp;
      }
    }
    return output;
  }

  @Override
  /**
   * Execute the search command
   *
   * @param a string array of arguments for the search command
   * @param shell file trees
   * @return a string containing the output of search command
   */
  public String execute(String[] args, FileTree fileTree) {

    boolean file = args[args.length - 3].equals("f");

    String name = args[args.length - 1];
    name = name.substring(1, name.length() - 1);
    String output = "";
    Directory dir;
    args = Arrays.copyOfRange(args, 1, args.length - 4);
    for (String path : args) {
      if (!Error.isValidPath(path)) {
        StandardOutput.perror(path + " is not a valid path");
        return output;
      }
      dir = (Directory) fileTree.traverseToEnd(path);
      if (dir == null) {
        StandardOutput.perror(path + " is not a directory");
        return output;
      }
      output += path + ":\n" + search(dir, name, file) + "\n\n";
    }

    if (output.equals("")) {
      return output;
    }
    return output.substring(0, output.length() - 2);
  }

  @Override
  /**
   * returns the manual entry for search
   *
   * @return manual entry for search
   */
  public String toString() {

    // create man page for tree command
    String info =
        "search path ... -type [f|d] -name expression\n\n" + "\tSearches given directories\n";

    return info;
  }
}
