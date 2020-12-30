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

/** This class has all of the methods needed to run Echo */
public class Echo extends Command {

  @Override
  /**
   * Execute the echo command
   * 
   * @param a string array of arguments for the echo command
   * @param shell file tree
   * @return a string containing the output of echo command
   */
  public String execute(String[] args, FileTree fileTree) {
    // TODO Auto-generated method stub

    String fullString = "";

    // gets full string by concatenating arguments followed by a space
    for (int i = 1; i < args.length; i++) {

      // add argument to string
      fullString += args[i] + " ";

      // end of arguments
      if (fullString.length() > 2 && fullString.charAt(fullString.length() - 2) == '"') {
        break;
      }
    }

    // removes the last space and the double quotes
    fullString = fullString.substring(1, fullString.length() - 2);

    return fullString;
  }

  @Override
  /**
   * Returns the manual entry for echo command
   * 
   * @return a string containing manual entry for echo command
   */
  public String toString() {
    // TODO Auto-generated method stub

    // create man page for echo command
    String info = "echo STRING [> OUTFILE]\n\n"
        + "\tIf OUTFILE is not provided, print STRING on the shell. Otherwise, put STRING into\n"
        + "\tfile OUTFILE. STRING is a string of characters surrounded by double quotation marks.\n"
        + "\tThis creates a new :ile if OUTFILE does not exists and erases the old contents if\n"
        + "\tOUTFILE already exists. In either case, the only thing in OUTFILE should be STRING.\n"
        + "\necho STRING >> OUTFILE\n\n"
        + "\tLike the previous command, but appends instead of overwrites.\n";

    return info;
  }

}
