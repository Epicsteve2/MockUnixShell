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
import inputOutput.Error;
import inputOutput.StandardOutput;

/** This class has all of the methods needed to run MakeDirectory */
public class MakeDirectory extends Command {

  /**
   * parent directory for each directory created
   */
  private Directory parentDir;

  /**
   * Creates a child directory with specified parent and name then returns created directory
   *
   * @param parent directory of new directory
   * @param name of new directory as a string
   * @return newly created directory
   */
  private Directory makeDirectory(Directory parent, String name) {

    Directory dir = null;

    // check if new directory name exists
    if (parent.findSubDir(name) == null && parent.findFile(name) == null) {

      // create new directory
      dir = new Directory(parent, name);

      // add directory as child to parent directory
      parent.addDirectoryChild(dir);

      // return duplicate directory
    } else {
      return null;
    }

    return dir;
  }

  /**
   * Finds the parent directory of the full path and stores it in parentDir global variable
   *
   * @param full path of the directory as a string
   * @param shell file tree
   * @return specific path error or success string
   */
  private String getParentDirectory(String arg, FileTree fileTree) {

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

      // traverse to start of path and get parent for dir1
    } else {
      parentDir = (Directory) fileTree.traverseToEnd(arg.substring(0, arg.lastIndexOf("/")));

      // path cannot be traversed
      if (parentDir == null) {
        return arg + " Invalid Path";
      }
    }

    return "good";
  }

  @Override
  /**
   * Execute the mkdir command
   * 
   * @param a string array of arguments for the mkdir command
   * @param shell file tree
   * @return a string containing the output of mkdir command
   */
  public String execute(String[] args, FileTree fileTree) {


    // initialize global parent variable
    parentDir = null;

    // attempt to make each directory in args
    for (int i = 1; i < args.length; i++) {

      // check if argument is invalid slash
      if (args[i].equals("/")) {

        // send invalid path error to perror in stdout and terminate execution
        StandardOutput.perror(args[i] + " Invalid Path");
        return "";
      }

      // find parent directory in path
      String pathError = getParentDirectory(args[i], fileTree);

      // path not found
      if (pathError != "good") {

        // send invalid path error to perror in stdout and terminate execution
        StandardOutput.perror(pathError);
        return "";
      }

      // get name of directory to be created
      String dir = args[i].substring(args[i].lastIndexOf("/") + 1);

      // create first directory
      Directory newDir = makeDirectory(parentDir, dir);

      // dir1 creation unsuccessful
      if (newDir == null) {

        // send duplicate directory error to perror in stdout and terminate execution
        StandardOutput.perror(args[i] + " Already Exists");
        return "";
      }

    }

    return "";

  }

  @Override
  /**
   * Returns the manual entry for mkdir command
   * 
   * @return a string containing manual entry for mkdir command
   */
  public String toString() {
    // TODO Auto-generated method stub

    // create man page for mkdir command
    String info = "mkdir DIR ...\n\n"
        + "\tThis command takes in any amount of arguments arguments only. Create directories,\n "
        + "\teach of which may be relative to the current directory or may be a full path.\n"
        + "\tIf creating a DIR results in any kind of error, do not proceed with creating the\n"
        + "\tremaining DIRs. If a DIR creation results in an error, then give back an error\n"
        + "\tspecific to that DIR.\n";

    return info;
  }

}
