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
import inputOutput.Error;
import Exceptions.InvalidPathException;

/** This class has all of the methods needed to run Copy */
public class Copy extends Command {

  /** Variable for the fileTree instance */
  private FileTree fileTree = FileTree.getFileTree();

  /**
   * Finds the parent directory of the full path and stores it in parentDir global variable
   *
   * @param arg which is full path of the directory as a string
   * @param fileTree which is the shell file system
   * @return specific path error or success string
   */
  private String getParentDirectory(String arg, FileTree fileTree) {
    Directory parentDir;

    // check if dir2 path is valid
    if (!Error.isValidPath(arg)) {
      return arg + " Invalid Path";
    }

    // initialize global parent variable
    parentDir = null;

    // get current directory as parent for dir1
    if (arg.lastIndexOf("/") == -1) {
      parentDir = fileTree.getCurrent();

      // get parent of current directory as parent for dir1
    } else if (arg.lastIndexOf("/") == 0) {
      parentDir = fileTree.getRoot();

      // traverse to start of path and get parent for dir2
    } else {
      parentDir = (Directory) fileTree.traverseToEnd(arg.substring(0, arg.lastIndexOf("/")));

      // path cannot be traversed
      if (parentDir == null) {
        return arg + " Invalid Path";
      }
    }

    return "good";
  }

  /**
   * Returns the parent directory of any given path
   *
   * @param argument which is the path as a string
   * @return parent directory of path
   */
  private Directory getParentDirectory(String argument) {
    if (fileTree.traverseToEnd(argument) instanceof Directory) {
      return (Directory) fileTree.traverseToEnd(argument);
    }
    if (argument.indexOf("/") == -1) {
      return fileTree.getCurrent();
    } else {
      if (argument.length() == 1) {
        return fileTree.getCurrent();
      }
    }
    String newPath = argument.substring(0, argument.lastIndexOf("/"));
    return (Directory) fileTree.traverseToEnd(newPath);
  }

  /**
   * Returns the name of the parent directory of any given path
   *
   * @param argument which is any path
   * @return the name of the parent directory of argument
   */
  private String getDirectoryName(String argument) {
    if (argument.indexOf("/") == -1) {
      return argument;
    }
    return argument.substring(argument.indexOf("/") + 1);
  }

  /**
   * Returns the path of the parent directory given any path
   *
   * @param arguments which is any path as a string
   * @return the path of the parent directory of arguments
   */
  private String getParentPath(String arguments) {
    String parentOfInputPath = "";
    Directory parentInput;
    if (!arguments.contains("/")) {
      parentInput = (Directory) fileTree.traverseToEnd(fileTree.getCurrentPath());
      parentOfInputPath = PrintWorkingDirectory.printFullPath(parentInput);
    } else {
      if (arguments.lastIndexOf('/') == 0)
        parentOfInputPath = arguments;
      else
        parentOfInputPath = arguments.substring(0, arguments.lastIndexOf('/'));
      parentInput = getParentDirectory(parentOfInputPath);
      parentOfInputPath = PrintWorkingDirectory.printFullPath(parentInput);
    }
    return parentOfInputPath;
  }

  /**
   * This method takes in 2 paths, and concatenates them
   *
   * @param arg1 is the first path
   * @param arg2 is the second path
   * @return the 2 paths concatenated
   */
  private String concatenatePaths(String arg1, String arg2) {
    if (arg1.charAt(arg1.length() - 1) == '/') {
      return arg1 + arg2;
    }
    return arg1 + "/" + arg2;
  }

  @Override
  /**
   * executes the main function of Copy
   *
   * @param arguments for History
   * @param shell file tree
   * @return empty string
   */
  public String execute(String[] args, FileTree fileTree) {
    this.fileTree = fileTree;
    String inputPath = args[1], outputPath = args[2];
    Directory copiedDirectory = null;

    File inputFile = this.fileTree.traverseToEnd(inputPath);
    File outputFile = this.fileTree.traverseToEnd(outputPath);

    // Checks if output path is valid
    String pathError = getParentDirectory(args[2], this.fileTree);
    // path not found
    if (pathError != "good") {
      throw new InvalidPathException(pathError);
    }
    if (inputFile == null) {
      throw new InvalidPathException(args[1]);
    }

    // If the user wants to copy a file
    if (inputFile instanceof File && !(inputFile instanceof Directory)) {
      // Checks if user wants to copy to a directory
      if (outputFile instanceof Directory) {
        if (outputPath.charAt(outputPath.length() - 1) != '/')
          outputPath += "/";
        MakeFile.makeFile(outputPath + inputFile.getName(), inputFile.getContent(), fileTree);
      } else
        MakeFile.makeFile(outputPath, inputFile.getContent(), fileTree);
      return "";
    }

    // Checks if user is trying to copy a parent to a child directory
    Directory parentDestination = getParentDirectory(args[2]);
    String parentOfInputPath = getParentPath(args[1]);
    if (PrintWorkingDirectory.printFullPath(parentDestination).contains(parentOfInputPath)) {
      if (!PrintWorkingDirectory.printFullPath(parentDestination).equals(parentOfInputPath)
          && !parentOfInputPath.contentEquals("/") && !(parentOfInputPath.lastIndexOf('/') == 0)) {
        throw new InvalidPathException(args[2]);
      }
    }

    // If output file doesn't exist
    if (outputFile == null) {
      Command.runCommand(new String[] {"mkdir", outputPath});
      copiedDirectory = (Directory) this.fileTree.traverseToEnd(outputPath);
      if (copiedDirectory == null)
        return "";
    }
    // Checks if user wants to copy into a directory
    if (outputFile instanceof Directory) {
      if (parentDestination.getPath().contentEquals(parentOfInputPath)) {
        throw new InvalidPathException(args[2]);
      }

      // Creates a new directory
      if (outputPath.charAt(outputPath.length() - 1) != '/')
        outputPath += "/";
      outputPath += getDirectoryName(args[1]);
      Command.runCommand(new String[] {"mkdir", outputPath});
      copiedDirectory = (Directory) this.fileTree.traverseToEnd(outputPath);
    }

    // Gets list of child files and directories
    Iterator<File> fileList = ((Directory) inputFile).getFileChildren();
    Iterator<Directory> directoryList = ((Directory) inputFile).getDirectoryChildren();
    // Recursively copies all directory contents
    while (fileList.hasNext()) {
      File nextFile = fileList.next();
      String newFile = concatenatePaths(PrintWorkingDirectory.printFullPath(copiedDirectory),
          (nextFile).getName());
      String newPath = concatenatePaths(inputPath, (nextFile).getName());
      this.execute(new String[] {"cp", newPath, newFile}, fileTree);
    }
    while (directoryList.hasNext()) {
      Directory nextDirectory = directoryList.next();
      String newFile = concatenatePaths(PrintWorkingDirectory.printFullPath(copiedDirectory),
          (nextDirectory).getName());
      String newPath = concatenatePaths(inputPath, (nextDirectory).getName());
      this.execute(new String[] {"cp", newPath, newFile}, fileTree);
    }

    return "";
  }

  @Override
  /**
   * returns the manual entry for Copy
   *
   * @return manual entry for Copy
   */
  public String toString() {
    String info = "copy OLDPATH NEWPATH" + "\n\t Copies item from OLDPATH to NEWPATH.";
    return info;
  }

}
