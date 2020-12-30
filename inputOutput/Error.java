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
package inputOutput;

import java.util.Arrays;
import commands.History;

/**
 * This class has all of the methods that checks if an input from JShell is valid
 */
public class Error {

  /**
   * Checks if a string has any of the illegal characters for file or directory names
   * 
   * @param an argument for a command
   * @return true if the argument has no illegal characters. Return false otherwise
   */
  private static boolean checkIllegalCharacters(String argument) {
    String illegalCharacters = "!@#$%^&*(){}~|<>? ";

    for (int i = 0; i < illegalCharacters.length(); i++) {
      if (argument.indexOf(illegalCharacters.charAt(i)) != -1) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if a string could be a potential path in a filesystem
   * 
   * @param an argument for a command
   * @return true if the string could be a potential path in a filesystem. Return false otherwise
   */
  public static boolean isValidPath(String path) {
    String splitPath[] = path.split("/");

    // Checks if '//' is in the string
    if (path.indexOf("//") != -1) {
      return false;
    }

    for (int i = 0; i < splitPath.length; i++) {
      // Checks if path has either '..' or '.'
      if (splitPath[i].indexOf(".") != -1 && !splitPath[i].contentEquals(".")
          && !splitPath[i].contentEquals("..")) {
        return false;
      }

      // Checks if there's any illegal characters
      if (!checkIllegalCharacters(splitPath[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if an argument is a non-negative integer
   * 
   * @param an argument for a command
   * @return true if the argument is a non-negative integer. Return false otherwise
   */
  private static boolean isValidInteger(String argument) {
    try {
      Integer.parseInt(argument);
    } catch (Exception e) {
      return false;
    }
    // Check if integer is non-negative
    return Integer.parseInt(argument) >= 0;
  }

  /**
   * Checks if an argument is a valid string for the JShell
   * 
   * @param an argument for a command
   * @return true if the argument is surrounded by double quotes, but contains no double quotes
   *         inside. Return false otherwise
   */
  private static boolean isValidString(String argument) {
    // Checks if string starts and ends with double quotes
    if (!(argument.charAt(0) == '"' && argument.charAt(argument.length() - 1) == '"')) {
      return false;
    }

    // Checks if string is just '"'
    if (argument.length() == 1) {
      return false;
    }

    // Checks if string has double quotes in it
    argument = argument.substring(1, argument.length() - 1);
    if (argument.indexOf('"') != -1) {
      return false;
    }
    return true;
  }

  /**
   * This method will check if an input for the echo command is valid
   * 
   * @param arguments for echo
   * @return true if it's a valid input for echo. Return false otherwise.
   */
  private static boolean checkEcho(String[] arguments) {
    String fullString = "";
    int argumentLength = arguments.length + 1;

    if (arguments.length == 1) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }

    // Gets full string from arguments
    for (int i = 1; i < arguments.length; i++) {
      fullString += arguments[i] + " ";
      argumentLength--;
      if (fullString.length() > 2 && fullString.charAt(fullString.length() - 2) == '"') {
        break;
      }
    }
    fullString = fullString.substring(0, fullString.length() - 1);

    // Checks if the string is valid
    if (!isValidString(fullString)) {
      StandardOutput.perror("Error: 1st argument is not a valid string");
      return false;
    }
    // If user doesn't want to write to a file
    if (argumentLength == 2) {
      return true;
    }
    StandardOutput.perror("Error: Too many arguments");
    return false;
  }

  /**
   * This method checks if an input for a command that takes in no parameters is valid
   * 
   * @param arguments for the command
   * @return true if it's a valid input for a no-parameter command. Return false otherwise.
   */
  private static boolean checkNoArgumentCommand(String[] arguments) {
    if (arguments.length == 1) {
      return true;
    }
    StandardOutput.perror("Error: Too many arguments");
    return false;
  }

  /**
   * This method checks if an input for a command that takes in one path is valid
   * 
   * @param arguments for the command
   * @return true if it's a valid input for a one path command. Return false otherwise.
   */
  private static boolean checkOnePathCommand(String[] arguments) {
    if (arguments.length == 1) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }
    if (arguments.length == 2 && isValidPath(arguments[1])) {
      return true;
    }
    if (arguments.length == 2 && !isValidPath(arguments[1])) {
      StandardOutput.perror("Error: Illegal characters in path");
      return false;
    }
    StandardOutput.perror("Error: Too many arguments");
    return false;
  }

  /**
   * This method checks if an input for a command that takes in more than one argument is valid
   * 
   * @param arguments for the command
   * @return true if it's a valid input for a more-than-one-argument command. Return false
   *         otherwise.
   */
  private static boolean checkAtLeastOneParameter(String[] arguments) {
    if (arguments.length == 1) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }
    return true;
  }

  /**
   * This method will check if an input for the history command is valid
   * 
   * @param arguments for history
   * @return true if it's a valid input for history. Return false otherwise.
   */
  private static boolean checkHistory(String[] arguments) {
    if (arguments.length == 1) {
      return true;
    }
    if (arguments.length == 2 && isValidInteger(arguments[1])) {
      return true;
    }
    if (arguments.length == 2 && !isValidInteger(arguments[1])) {
      StandardOutput.perror("Error: Not a valid integer provided");
      return false;
    }
    StandardOutput.perror("Error: Too many arguments");
    return false;
  }

  /**
   * Checks if an argument is a name of a command
   * 
   * @param command
   * @return true if the argument is a valid command. Return false otherwise
   */
  private static boolean isCommand(String command) {
    String commands[] =
        // list of all the valid commands
        {"exit", "mkdir", "cd", "ls", "pwd", "pushd", "popd", "history", "cat", "echo", "man",
            "tree", "mv", "cp", "rm", "saveJShell", "loadJShell", "curl", "search"};

    // go through the list and see if the command is valid
    for (int i = 0; i < commands.length; i++) {
      if (command.contentEquals(commands[i])) {
        return true;
      }
    }

    // if it is not in the list of valid commands, it is invalid, so return false
    return false;
  }

  /**
   * This method will check if an input for the man command is valid
   * 
   * @param arguments for man
   * @return true if it's a valid input for man. Return false otherwise.
   */
  private static boolean checkManual(String[] arguments) {
    if (arguments.length == 1) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }
    if (arguments.length == 2) {
      if (isCommand(arguments[1])) {
        return true;
      }
      StandardOutput.perror("Error: Not a valid command");
      return false;
    }
    StandardOutput.perror("Error: Too many arguments");
    return false;
  }

  /**
   * This method will check if an input for any command that takes in 2 paths is valid
   * 
   * @param arguments for a 2 path command
   * @return true if it's a valid input for a 2 path command. Return false otherwise.
   */
  private static boolean checkTwoPaths(String[] arguments) {
    if (arguments.length < 3) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }
    if (arguments.length == 3) {
      if (isValidPath(arguments[1]) && isValidPath(arguments[2]))
        return true;
      StandardOutput.perror("Error: Not a valid path");
      return false;
    }

    StandardOutput.perror("Error: Too many arguments");
    return false;
  }

  /**
   * This method will check if an input for the search command is valid
   * 
   * @param arguments for search
   * @return true if it's a valid input for search. Return false otherwise.
   */
  private static boolean checkSearch(String[] arguments) {
    if (arguments.length < 6) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }

    String thirdLastArgument = arguments[arguments.length - 3];

    if (!arguments[arguments.length - 4].contentEquals("-type")) {
      StandardOutput.perror("Error: Didn't specify a type");
      return false;
    }
    if (!thirdLastArgument.contentEquals("f") && !thirdLastArgument.contentEquals("d")) {
      StandardOutput.perror("Error: Type must be file ('f') or directory ('d')");
      return false;
    }
    if (!arguments[arguments.length - 2].contentEquals("-name")) {
      StandardOutput.perror("Error: Didn't specify what you wanted to search");
      return false;
    }
    if (!isValidString(arguments[arguments.length - 1])) {
      StandardOutput.perror("Error: Not a valid string in last argument");
      return false;
    }
    return true;
  }

  /**
   * This method checks if a set of arguments is asking for redirection to a file
   * 
   * @param arguments for any command
   * @return true if the user wants to redirect to a file. Return false otherwise
   */
  public static boolean checkRedirection(String[] arguments) {
    if (arguments.length < 3) {
      return false;
    }
    if (arguments[arguments.length - 2].contentEquals(">")
        || arguments[arguments.length - 2].contentEquals(">>")) {
      return true;
    }
    return false;
  }

  /**
   * This method checks if a set of arguments for redirection is valid
   * 
   * @param arguments for any command
   * @return true if redirection is possible. Return false otherwise
   */
  public static boolean isValidRedirection(String[] arguments) {
    if (arguments.length == 0) {
      return true;
    }
    if (arguments[arguments.length - 1].contentEquals(">")
        || arguments[arguments.length - 1].contentEquals(">>")) {
      StandardOutput.perror("Error: Not enough arguments for redirection");
      return false;
    }
    if (checkRedirection(arguments)) {
      if (!isValidPath(arguments[arguments.length - 1])) {
        StandardOutput.perror("Error: Illegal characters in filename/path");
        return false;
      }
    }
    return true;
  }

  /**
   * This method will strip any command of their redirection arguments if they exist.
   * 
   * @param arguments for a command
   * @return the same strings if there is no redirection return the strings without the redirection
   *         arguments otherwise
   */
  public static String[] removeRedirection(String[] arguments) {
    if (checkRedirection(arguments))
      return Arrays.copyOf(arguments, arguments.length - 2);
    return arguments;
  }

  /**
   * This method will check if an input for any command that takes in 1 parameter is valid
   * 
   * @param arguments for a 1 parameter command
   * @return true if it's a valid input for a 1 parameter command. Return false otherwise.
   */
  private static boolean checkOneParameterCommand(String[] arguments) {
    if (arguments.length == 1) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }
    if (arguments.length >= 3) {
      StandardOutput.perror("Error: Too many arguments");
      return false;
    }
    return true;
  }

  /**
   * This method will check if an input for the loadJShell command is valid
   * 
   * @param arguments for loadJShell
   * @return true if it's a valid input for loadJShell. Return false otherwise.
   */
  private static boolean checkLoadJShell(String[] arguments) {
    if (arguments.length == 1) {
      StandardOutput.perror("Error: Not enough arguments");
      return false;
    }
    if (arguments.length >= 3) {
      StandardOutput.perror("Error: Too many arguments");
      return false;
    }

    if (History.isCommandExecuted()) {
      StandardOutput.perror("Error: JShell is already in use");
    }
    return true;
  }

  /**
   * Checks if an array of strings is a valid input for JShell
   * 
   * @param array of commands and arguments
   * @return true if the input is a valid input for JShell. Return false otherwise
   */
  public static boolean checkSyntax(String[] arguments) {
    if (arguments.length == 0)
      return false;

    if (!isValidRedirection(arguments))
      return false;

    String[] newArguments = removeRedirection(arguments);

    switch (newArguments[0]) {
      case ("exit"):
        return checkNoArgumentCommand(newArguments);
      case ("mkdir"):
        return checkAtLeastOneParameter(newArguments);
      case ("cd"):
        return checkOnePathCommand(newArguments);
      case ("ls"):
        return true;
      case ("pwd"):
        return checkNoArgumentCommand(newArguments);
      case ("pushd"):
        return checkOnePathCommand(newArguments);
      case ("popd"):
        return checkNoArgumentCommand(newArguments);
      case ("history"):
        return checkHistory(newArguments);
      case ("cat"):
        return checkAtLeastOneParameter(newArguments);
      case ("echo"):
        return checkEcho(newArguments);
      case ("man"):
        return checkManual(newArguments);
      case ("tree"):
        return checkNoArgumentCommand(newArguments);
      case ("mv"):
        return checkTwoPaths(newArguments);
      case ("cp"):
        return checkTwoPaths(newArguments);
      case ("curl"):
        return checkOneParameterCommand(newArguments);
      case ("rm"):
        return checkOnePathCommand(newArguments);
      case ("saveJShell"):
        return checkOneParameterCommand(newArguments);
      case ("loadJShell"):
        return checkLoadJShell(newArguments);
      case ("search"):
        return checkSearch(newArguments);
      case (""):
        return false;
    }

    StandardOutput.perror("Error: Not a valid command");
    return false;
  }
}
