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
package Exceptions;

/** This class is for an input with an invalid command */
public class InvalidCommandException extends CustomException {

  private String path;
  private String content;

  public InvalidCommandException(String p) {
    this.path = p;
    this.content = null;
  }

  public InvalidCommandException(String p, String content) {
    this.path = p;
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return path + " is an invalid command";
  }

}
