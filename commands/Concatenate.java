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
import fileSystem.*;

/**
 * This class has all of the methods needed to run Concatenate
 */
public class Concatenate extends Command {

  @Override
  /**
   * executes the main function of Concatenate
   *
   * @param arguments for Concatenate
   * @param shell file tree
   * @return error message if any path is invalid. Return the contents of the files listed otherwise
   */
  public String execute(String[] args, FileTree fileTree)
      throws InvalidPathException, InvalidCommandException, FileNotFoundException {
    String output = "";
    File file = null;

    // Iterate through the arguments
    for (int i = 1; i < args.length; i++) {
      file = fileTree.traverseToEnd(args[i]);
      if (file == null) {
        throw new FileNotFoundException(args[i], output);
      }
      // Checks if the file is valid
      if (file instanceof File && !(file instanceof Directory)) {
        output += file.getContent();

        // Outputs the 3 line breaks only if it's not the last file to be concatenated
        if (i + 1 != args.length) {
          output += "\n\n\n";
        }
      } else {
        // Stops concatenating if a file isn't valid
        throw new InvalidPathException(args[i], output);
      }
    }

    return output;
  }

  @Override
  /**
   * returns the manual entry for Concatenate
   *
   * @return manual entry for Concatenate
   */
  public String toString() {
    String info = "cat FILE1 [FILE2 ...]\n"
        + "\tDisplay the contents of FILE1 and other files (i.e. FILE2 ....) concatenated in\n"
        + "\tthe shell. There will be 3 line breaks between each file.\n"
        + "\tThrows an error if FILE doesn't exist. cat will not read the rest of the files.";
    return info;
  }
}
