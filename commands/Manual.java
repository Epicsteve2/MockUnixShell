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
import fileSystem.FileTree;

/** This class handles displaying documentation for commands */
public class Manual extends Command {

  @Override
  /**
   * executes the main function of Manual
   * 
   * @param args arguments for Manual
   * @param shell file tree
   * @return manual entry for the specified command
   */
  public String execute(String[] args, FileTree fileTree)
      throws InvalidPathException, InvalidCommandException, FileNotFoundException {
    return Command.getCommand(args[1]).toString();
  }

  /**
   * documentation of man
   * 
   * @return documentation for manual
   */
  @Override
  public String toString() {
    return "man CMD \n\n\tPrint documentation for CMD";
  }
}
