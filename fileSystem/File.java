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
package fileSystem;

import commands.PrintWorkingDirectory;

/**
 * This class handles all methods relating to a File
 */
public class File {

  /**
   * parent directory of file
   */
  private Directory parent;

  /**
   * name of file
   */
  private String name;

  /**
   * content of file
   */
  private String content;

  /**
   * Constructor for File class that constructs file object with parent and name
   *
   * @param parent directory of currently constructing file
   * @param name   of currently constructing file
   */
  public File(Directory p, String n) {

    // set parent and name
    this.parent = p;
    this.name = n;
    this.content = "";
  }

  /**
   * Gets the parent directory of the current file
   *
   * @return parent directory of the current file
   */
  public Directory getParent() {
    return this.parent;
  }

  /**
   * Sets the number of directory children for the current directory
   *
   * @param parent directory for the current file
   */
  public void setParent(Directory p) {

    // set parent
    this.parent = p;
  }

  /**
   * Gets the name of the current file
   *
   * @return name of the current file
   */
  public String getName() {
    return this.name;
  }

  /**
   * returns absolute path of the file
   *
   * @return path of current file
   */
  public String getPath() {
    return PrintWorkingDirectory.printFullPath(this.parent) + "/" + this.name;
  }

  /**
   * Sets the name of the current file
   *
   * @param name for the current file
   */
  public void setName(String n) {

    // set name
    this.name = n;
  }

  /**
   * Gets the string content of the current file
   *
   * @return string content of the current file
   */
  public String getContent() {
    return content;
  }

  /**
   * Overwrites the content of the current file with new string content
   *
   * @param string content for the current file
   */
  public void setContent(String content) {

    // set content
    this.content = content;
  }

  /**
   * Appends given string content to the file
   *
   * @param string content
   */
  public void appendContent(String content) {

    // append new string to the end of file contents
    this.content += content;
  }

}
