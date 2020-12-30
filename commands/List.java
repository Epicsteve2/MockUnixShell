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

import Exceptions.FileNotFoundException;
import Exceptions.InvalidCommandException;
import Exceptions.InvalidPathException;
import java.util.Iterator;
import fileSystem.*;

/**
 * This class has all of the methods needed to run List
 */
public class List extends Command {

  // A string for the path
  private static String path;

  // A string to indicate directory or file
  private static String type;

  // A string to contain all the contents in a directory
  private static String contents;

  // A file object
  private static File file;

  /**
   * Execute the ls command
   *
   * @param argument    arguments for ls command
   * @param fileTree    structure of the file tree
   * @return a string containing contents of the current directory if there are no paths given, or a
   *         string containing the path if the path specifies a file, or a string containing the
   *         path, colon, and contents of the directory if the path specifies a directory, or a
   *         string containing an error message if the path does not exist.
   */
  public String execute(String[] argument, FileTree fileTree)
      throws InvalidPathException, InvalidCommandException, FileNotFoundException {
    // Initialize empty strings
    path = "";
    type = "";
    contents = "";

    // Initialize empty file
    file = null;

    // Get the length of the command and its arguments
    int argLen = argument.length;

    // If the length is at least equal to 2 and the second argument is "-R"
    if (argLen >= 2 && argument[1].equals("-R")) {
      // Then, if there is no path given
      if (argLen == 2) {
        // Get the content of the current directory with recursive mode enabled
        printContents(fileTree.getCurrent(), true);
      }
      // If there are paths given
      else if (argLen > 2) {
        // Then, get the content from the specified directories with recursive mode enabled
        contents = executePath(argLen, argument, true, fileTree);
      }

    }
    // Otherwise, we know the command cannot be recursive
    else {
      // If there is no path given
      if (argLen == 1) {
        // Then, get the content of the current directory with recursive mode disabled
        printContents(fileTree.getCurrent(), false);
      }
      // If there are paths given
      else if (argLen > 1) {
        // Then, get the content from the specified directories with recursive mode disabled
        contents = executePath(argLen, argument, false, fileTree);
      }

    }
    // If there is no content
    if (contents.equals("")) {
      // Then return an empty string
      return contents;
    }
    // Otherwise, return all the contents without a new line afterwards
    return contents.substring(0, contents.length() - 1);
  }

  /**
   * This method recursively, or not recursively, contains all the contents in a given directory
   *
   * @param currentDir the current directory
   * @param recursive a boolean to indicate whether the recursive mode is enabled
   */
  private static void printContents(Directory currentDir, boolean recursive) {

    // Get the number of contents (directories and files) in the current directory
    Directory d;
    File f;

    // Get all the contents (directories and files) in the current directory
    Iterator<Directory> dirChildren = currentDir.getDirectoryChildren();
    Iterator<File> fileChildren = currentDir.getFileChildren();

    // Iterate through all the directory contents
    while (dirChildren.hasNext()) {

      // Append the name of each directory to the string result
      d = dirChildren.next();
      contents += d.getName();

      // Add new lines for formatting
      if (!(contents.equals(""))) {
        contents += "\n";
      }
    }

    // Iterate through all the file contents
    while (fileChildren.hasNext()) {
      f = fileChildren.next();
      // Append the name of each file to the string result
      contents += f.getName();

      // Add new lines for formatting
      if (!(contents.equals(""))) {
        contents += "\n";
      }
    }

    // Additionally, if the recursive mode is enabled
    if (recursive == true) {
      dirChildren = currentDir.getDirectoryChildren();
      // Then, iterate through the all the directory's children
      dirChildren = currentDir.getDirectoryChildren();
      while (dirChildren.hasNext()) {
        // Append the full path together with a directory's child
        d = dirChildren.next();
        // contents += fileTree.getCurrentPath() + d.getName();
        contents += d.getPath();
        // Append a new line
        contents += ":\n";
        // Recursively print the contents of the child
        printContents(d, true);
      }
      // Stop the recursive calls
      recursive = false;
    }
  }

  /**
   * Iterate through all the paths to recursively, or not recursively collect all the contents of
   * the given directories
   *
   * @param argLen the number of arguments for ls command
   * @param argument the arguments for ls command
   * @param recursive a boolean to indicate whether the recursive mode is enabled
   * @return a string containing all the contents of all the given directories, or an empty string
   *         if the given path is invalid
   */
  private static String executePath(int argLen, String[] argument, boolean recursive, 
      FileTree fileTree)
      throws InvalidPathException, InvalidCommandException, FileNotFoundException {

    // Determine the starting point of the iteration
    int i;
    // If the recursive mode is enabled
    if (recursive == true) {
      // Then start at the third argument
      i = 2;
    } else {
      // Otherwise, start at the second argument
      i = 1;
    }

    // Iterate through all the paths
    for (; i < argLen; i++) {

      // Get one path
      path = argument[i];

      // Traverse the path until the end
      file = fileTree.traverseToEnd(path);

      // If the path does not exist
      if (file == null) {

        // Then produce a suitable error message
        throw new InvalidPathException(path, contents);

      } else {
        // Check whether the path leads to a directory or a file
        type = (file instanceof Directory) ? "Directory" : "File";

        // Append the path for formatting
        contents += path;

        // If the path leads to a directory
        if (type.equals("Directory")) {

          // Append colon and contents of the directory, recursively or not recursively
          contents += ":\n";
          printContents((Directory) file, recursive);

        }

        // Append a line-break to separate the current with the next contents
        contents += "\n";
      }
    }
    // Return the contents
    return contents;
  }

  @Override
  /**
   * Returns a manual entry for ls
   *
   * @return a string containing manual entry for ls
   */
  public String toString() {
    String info = "ls [-R] [PATH ...]\n\n" + "\tIf -R is present, recursively "
        + "list all subdirectories.\n"
        + "\tIf no paths are given, print the contents of the current"
        + " directory, with a new line following each of the content.\n" + "\tContents can be file"
        + " or directory.\n\n" + "\tOtherwise, for each path p, the order listed:\n\n"
        + "\tIf p specifies a file, print p.\n" + "\tIf p specifies a directory, print p, a colon"
        + ", then the contents of that directory, then an extra new line.\n"
        + "\tIf p does not exist, print a suitable error message.";
    return info;
  }
}
