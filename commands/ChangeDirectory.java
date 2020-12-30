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

import fileSystem.*;
import inputOutput.StandardOutput;

/** This class has all of the methods needed to run ChangeDirectory */
public class ChangeDirectory extends Command {

  @Override
  /**
   * Execute the cd command
   * 
   * @param args arguments for cd command
   * @param fileTree the structure of the files
   * @return an empty string
   */
  public String execute(String[] args, FileTree fileTree) {

    // Get the path
    String path = args[1];

    // Traverse to the end of the path and get the object
    File object = fileTree.traverseToEnd(path);

    // If the path specifies a directory
    if (object instanceof Directory) {

      // Then change to the current directory
      fileTree.setCurrent((Directory) object);

      // If the path does not exist
    } else if (object == null) {

      // Then produce a suitable error message
      StandardOutput.perror(args[1] + " does not exist");

      // If the path specifies a file, which is not a directory
    } else {

      // Then produce a suitable error message
      StandardOutput.perror(args[1] + " is not a directory");
    }

    // Return an empty string
    return "";
  }

  @Override
  /**
   * Returns a manual entry for cd command
   * 
   * @return a string containing manual entry for cd command
   */
  public String toString() {

    // create man page for cd command
    String info = "cd DIR\n\n" + "\tChange directory to DIR, which may be relative to the current"
        + " directory or may be a full path.";

    return info;
  }

}
