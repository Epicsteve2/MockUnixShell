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

import Exceptions.CustomException;
import Exceptions.FileNotFoundException;
import Exceptions.InvalidCommandException;
import Exceptions.InvalidPathException;
import fileSystem.File;
import fileSystem.FileTree;
import fileSystem.MakeFile;
import inputOutput.StandardOutput;
import inputOutput.Error;

import java.util.Arrays;
import java.util.HashMap;

/**
 * This class handles the running of commands, and serves as a template for other commands
 */
public abstract class Command implements Cmd {

  /**
   * The dictionary from syntax to command name
   */
  private static final HashMap<String, String> syntaxToCommand = new HashMap<>();

  /**
   * a boolean representing if the hashmap has been initialized or not
   */
  private static boolean innit = false;

  /**
   * executes the main function of the command
   *
   * @param args
   * @param fileTree the structure of the files
   * @return output of the command
   */
  public abstract String execute(String[] args, FileTree fileTree)
      throws InvalidPathException, InvalidCommandException, FileNotFoundException;

  private static void innit() {// initializing the hashmap
    syntaxToCommand.put("ls", "List");
    syntaxToCommand.put("history", "History");
    syntaxToCommand.put("exit", "Exit");
    syntaxToCommand.put("mkdir", "MakeDirectory");
    syntaxToCommand.put("popd", "PopDirectory");
    syntaxToCommand.put("pushd", "PushDirectory");
    syntaxToCommand.put("pwd", "PrintWorkingDirectory");
    syntaxToCommand.put("cat", "Concatenate");
    syntaxToCommand.put("man", "Manual");
    syntaxToCommand.put("cd", "ChangeDirectory");
    syntaxToCommand.put("echo", "Echo");
    syntaxToCommand.put("tree", "Tree");
    syntaxToCommand.put("mv", "Move");
    syntaxToCommand.put("cp", "Copy");
    syntaxToCommand.put("saveJShell", "SaveJShell");
    syntaxToCommand.put("loadJShell", "LoadJShell");
    syntaxToCommand.put("curl", "Curl");
    syntaxToCommand.put("search", "Search");
    syntaxToCommand.put("rm", "Remove");
    innit = true;
  }

  /**
   * Using the toString to provide documentation of the command
   *
   * @return command documentation
   */
  public abstract String toString();

  /**
   * turns given syntax into a corresponding command class, null if syntax is wrong
   *
   * @param syntax
   * @return command
   */
  public static Command getCommand(String syntax) throws InvalidCommandException {
    Class cmd;

    try {
      // try turning the syntax into a command instance
      cmd = Class.forName("commands." + syntaxToCommand.get(syntax));
      return (Command) cmd.getDeclaredConstructor().newInstance();

    } catch (Exception e) { // if not return null
      throw new InvalidCommandException(syntax);
    }

  }

  /**
   * handles the redirection and file creation for redirection
   *
   * @param msg
   * @param filePath
   * @param append
   */
  private static void redirect(String msg, String filePath, boolean append, FileTree fileTree)
      throws InvalidPathException {

    File file = MakeFile.makeFile(filePath, fileTree);// making or getting the file

    if (file == null) {// if file couldn't be made
      throw new InvalidPathException(filePath);
    }

    StandardOutput.output(msg, file, append);
  }

  private static void checkException(CustomException e, String file, boolean append,
      boolean redirect, FileTree fileTree) {
    if (e.getContent() != null) {
      if (redirect) {// if need to redirect call redirect
        try {
          redirect(e.getContent(), file, append, fileTree);
        } catch (InvalidPathException invalidPathException) {
          StandardOutput.perror(e.toString());
        }
      } else {
        StandardOutput.output(e.getContent());
      }
    }
    StandardOutput.perror(e.toString());
  }

  /**
   * runs command and outputs it where desired
   *
   * @param args
   */
  public static void runCommand(String[] args) {
    FileTree fileTree = FileTree.getFileTree();
    boolean redirect = false, append = false;

    String file = "";

    if (!innit) {
      innit(); // if hash map not initialized, initialize it
    }

    try {// in case of emergency
      Command command = getCommand(args[0]);// get the command itself

      if (Error.checkRedirection(args)) {
        redirect = true;
        file = args[args.length - 1];
        append = args[args.length - 2].equals(">>");
        args = Error.removeRedirection(args);
      }

      String msg = command.execute(args, fileTree);// run command with given arguments

      if (redirect) {// if need to redirect call redirect
        redirect(msg, file, append, fileTree);
      } else if (!msg.equals("")) {// else if non empty output it
        StandardOutput.output(msg);
      }

    } catch (CustomException e) {
      checkException(e, file, append, redirect, fileTree);
    } catch (Exception e) {// catching exceptions
      StandardOutput.perror("Unknown Error has occurred"); // in case something unexpected happens
    }

  }

}
