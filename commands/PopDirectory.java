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

/** This class has all of the methods needed to run PopDirectory */
public class PopDirectory extends Command {

  @Override
  /**
   * executes the main function of PopDirectory
   * 
   * @param args arguments for PopDirectory
   * @param shell file tree
   * @return error message if the stack is empty, nothing otherwise
   */
  public String execute(String[] args, FileTree fileTree) {
    // Check if stack is empty, outputs error if it is
    if (PushDirectory.dirStack.empty()) {
      StandardOutput.perror("Error: Stack is empty");
      return "";
    }

    // Pops the top entry in the stack, and changes directory into it
    fileTree.setCurrent(PushDirectory.dirStack.pop());

    return "";
  }

  @Override
  /**
   * returns the manual entry for PopDirectory
   * 
   * @return manual entry for PopDirectory
   */
  public String toString() {
    String info = "popd\n"
        + "\tRemove the top entry from the directory stack, and changes directory into it.\n"
        + "\tThrows an error if the directory stack is empty.";
    return info;
  }

}
