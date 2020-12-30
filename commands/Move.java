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

import Exceptions.InvalidPathException;
import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;
import inputOutput.StandardOutput;

/**
 * This class has all of the methods needed to run Move
 */
public class Move extends Command {

  @Override
  /**
   * Execute the mv command
   *
   * @param a string array of arguments for the mv command
   * @param shell file tree
   * @return a string containing the output of mv command
   */
  public String execute(String[] args, FileTree fileTree) throws InvalidPathException {
    // TODO Auto-generated method stub
    
    File rmFile;
    
    // get current directory as parent for dir
    rmFile = fileTree.traverseToEnd(args[1]);
    boolean Dir = rmFile instanceof Directory;
    
    // path cannot be traversedemoveDir
    if (rmFile == null) {
      throw new InvalidPathException(args[1]);
    }

    // get path of current directory
    String currentPath = fileTree.getCurrent().getPath();

    // get path of directory to remove
    String removePath = rmFile.getPath();

    // directory to remove is in current directory path
    if (currentPath.contains(removePath)) {
      StandardOutput.perror("Cannot move file/directory in current path" + currentPath);
      return "";
    }

    try {
      Copy move = new Copy();

      // copy OLDPATH to NEWPATH
      move.execute(args, fileTree);

    } catch (
        Exception e) {
      throw e;
    }
    
    // delete OLDPATH directory and its contents
    if (Dir) {
      return new Remove().execute(new String[]{"rm", args[1]}, fileTree);
    }
    else{
      rmFile.getParent().deleteFileChild(rmFile);
      return "";
    }
  }


  @Override
  /**
   * returns the manual entry for mv
   *
   * @return manual entry for mv
   */
  public String toString() {

    // create man page for mv command
    String info = "mv OLDPATH NEWPATHn\n"
        + "\tMove item OLDPATH to NEWPATH. Both OLD- PATH and NEWPATH may be relative to the\n"
        + "\tcurrent directory or may be full paths. If NEWPATH is a directory, move the item\n"
        + "\tinto the directory.\n";

    return info;
  }

}
