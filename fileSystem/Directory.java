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
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class handles all methods relating to a Directory
 */
public class Directory extends File {

  /**
   * list of directory children
   */
  private LinkedList<Directory> dirChildren;

  /**
   * list of file children
   */
  private LinkedList<File> fileChildren;

  /**
   * Constructor for Directory class that constructs directory object with parent and name
   * 
   * @param parent directory of currently constructing directory
   * @param name of currently constructing directory
   */
  public Directory(Directory p, String n) {

    // inherit parent and name from File class
    super(p, n);

    // initialize lists of children
    dirChildren = new LinkedList<Directory>();
    fileChildren = new LinkedList<File>();
  }


  /**
   * Gets the number of directory children for the current directory
   * 
   * @return number of directory children for current directory
   */
  public int getNumOfDirectoryChildren() {
    return this.dirChildren.size();
  }

  @Override
  public String getPath() {
    return PrintWorkingDirectory.printFullPath(this);
  }

  /**
   * Gets the number of file children for the current directory
   * 
   * @return number of file children for current directory
   */
  public int getNumOfFileChildren() {
    return this.fileChildren.size();
  }

  /**
   * Gets all the directory children of the current directory
   * 
   * @return linked list of directory children for current directory
   */
  public Iterator<Directory> getDirectoryChildren() {
    return this.dirChildren.listIterator();
  }

  /**
   * Gets all the file children of the current directory
   * 
   * @return linked list of file children for current directory
   */
  public Iterator<File> getFileChildren() {
    return this.fileChildren.listIterator();
  }


  public void addDirectoryChild(Directory c) {

    // add directory to directory child list
    dirChildren.add(c);
  }

  /**
   * Adds a file as a child to the current directory
   * 
   * @param child file for current directory
   */
  public void addFileChild(File c) {

    // add file to file child list
    fileChildren.add(c);
  }

  /**
   * Deletes a directory child from the current directory
   * 
   * @param child directory of current directory
   */
  public void deleteDirectoryChild(Directory c) {

    // remove directory from directory child list
    dirChildren.remove(c);
  }

  /**
   * Deletes a file child from the current directory
   * 
   * @param child file of current directory
   */
  public void deleteFileChild(File c) {

    // remove file from file child list
    fileChildren.remove(c);
  }

  /**
   * Searches the directory`s directory children to see if a specific directory exists as it`s
   * direct child
   * 
   * @param name of child directory as a string
   * @return child directory corresponding to name
   */
  public Directory findSubDir(String name) {

    // iterate over linked list
    for (int i = 0; i < dirChildren.size(); i++) {

      // compare if directory is found
      if (name.equals(dirChildren.get(i).getName())) {
        return dirChildren.get(i);
      }

    }

    // directory not found
    return null;
  }

  /**
   * Searches the directory`s file children to see if a specific file exists as it`s direct child
   * 
   * @param name of child file as a string
   * @return child file corresponding to name
   */
  public File findFile(String name) {

    // iterate over linked list
    for (int i = 0; i < fileChildren.size(); i++) {

      // compare if file is found
      if (name.equals(fileChildren.get(i).getName())) {
        return fileChildren.get(i);
      }

    }

    // file not found
    return null;
  }
}
