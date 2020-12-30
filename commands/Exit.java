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

import fileSystem.FileTree;
import inputOutput.StandardOutput;

/** This class has all of the methods needed to run Exit */
public class Exit extends Command {

  @Override
  /**
   * Execute the Exit command to quit the shell
   * 
   * @param args arguments for exit command
   * @param shell file tree
   * @return a string containing exit statements
   */
  public String execute(String[] args, FileTree fileTree) {

    // Create an empty string for exit message
    String message = "";

    // Append the exit message
    message += "Quitting the shell...\n";
    message += "Process completed";

    // Return the exit message
    StandardOutput.output(message);

    // Exit the shell
    System.exit(0);

    // Return an empty string
    return "";

  }

  @Override
  /**
   * Returns the manual entry for exit command
   * 
   * @return a string containing manual entry for exit command
   */
  public String toString() {

    String info = "exit\n\n" + "\tQuit the program.";

    return info;
  }

}
