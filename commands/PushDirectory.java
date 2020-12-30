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
import java.util.Stack;
import inputOutput.StandardOutput;

/** This class has all of the methods needed to run PushDirectory */
public class PushDirectory extends Command {

  /** Initialize a stack of directories for the command */
  public static Stack<Directory> dirStack = new Stack<Directory>();

  @Override
  /**
   * executes the main function of PushDirectory
   * 
   * @param arguments for PushDirectory
   * @param shell file tree
   * @return error message if the path is invalid, nothing otherwise
   */
  public String execute(String[] args, FileTree fileTree) {
    // Gets the directory specified by the argument
    File dir = fileTree.traverseToEnd(args[1]);

    // Changes directory if the path is valid
    if (dir instanceof Directory) {
      dirStack.push(fileTree.getCurrent());
      fileTree.setCurrent((Directory) dir);
    } else {
      // Outputs error since the path is invalid
      StandardOutput.perror("Error: The given path is invalid");
    }

    return "";
  }

  @Override
  /**
   * returns the manual entry for PushDirectory
   * 
   * @return manual entry for PushDirectory
   */
  public String toString() {
    String info =
        "pushd DIR\n" + "\tPushes the current working directory onto the directory stack and then\n"
            + "\tchanges direcotry to DIR.\n" + "\tThrows an error if DIR doesn't exist";
    return info;
  }
}
