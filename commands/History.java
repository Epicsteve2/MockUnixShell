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
import java.util.ArrayList;

/** This class has all of the methods needed to run History */
public class History extends Command {

  /** Initialize a list of user inputs */
  private static ArrayList<String> inputHistory = new ArrayList<String>();

  /** Check if any commands have been run for loadJShell */
  private static boolean commandExecuted = false;

  /**
   * Checks if a command has been executed
   * 
   * @return true if any command has been executed
   */
  public static boolean isCommandExecuted() {
    return commandExecuted;
  }
  
  /**
   * Clears the input history ArrayList
   */
  public static void clearInputHistory() {
    inputHistory = new ArrayList<String>();
  }

  /**
   * Sets commandExecuted to true
   */
  public static void commandExecuted() {
    commandExecuted = true;
  }

  /**
   * Adds a user input to a String array of recent inputs
   * 
   * @param input user input
   */
  public static void addInputHistory(String input) {
    // Checks if input is just whitespace
    if (input.replaceAll("\\s+", "").equals("")) {
      return;
    }

    inputHistory.add(input);
  }

  /**
   * Adds a user input to a String array of recent inputs
   * 
   * @param input user input
   */
  public static ArrayList<String> getHistory() {
    return inputHistory;
  }

  @Override
  /**
   * executes the main function of History
   * 
   * @param arguments for History
   * @param shell file tree
   * @return error message if the second argument is invalid. Return history of commands otherwise
   */
  public String execute(String[] args, FileTree fileTree) {
    String result = "";
    int i = 0;

    // If user only wants to print a few commands
    if (args.length > 1) {
      i = inputHistory.size() - Integer.parseInt(args[1]);

      // Checks if user wants to print more than all recorded commands
      if (i < 0) {
        i = 0;
      }
    }

    // Adds all recent commands to result
    for (; i < inputHistory.size(); i++) {
      result += (i + 1) + ". " + inputHistory.get(i) + "\n";
    }

    if (result.contentEquals("")) {
      return "";
    }

    // Removes last newline character
    return result.substring(0, result.length() - 1);
  }


  @Override
  /**
   * returns the manual entry for History
   * 
   * @return manual entry for History
   */
  public String toString() {
    String info = "history [NUM]\n"
        + "\tThis command will print out recent commands, numbered by one command per line.\n"
        + "\tNUM specifies the number of recent commands to be printed.\n"
        + "\tThrows an error if NUM is not an integer >= 0";
    return info;
  }
}
