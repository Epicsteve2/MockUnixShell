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

import fileSystem.File;

/**
 * This class handles all communication from commands to external sources
 */
public class StandardOutput {

  /**
   * Prints given msg to shell
   * 
   * @param msg
   */
  public static void output(String msg) {
    System.out.println(msg);
  }

  /**
   * if append is true, appends msg to file, else sets file content to msg
   * 
   * @param msg
   * @param file
   * @param append
   */
  public static void output(String msg, File file, boolean append) {

    if (append) {
      file.appendContent(msg);
    } else {
      file.setContent(msg);
    }

  }

  /**
   * logs the following error msg to shell
   * 
   * @param msg
   */
  public static void perror(String msg) {
    System.out.println(msg);
  }

}
