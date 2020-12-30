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

import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;
import java.io.StringBufferInputStream;

/**
 * This class handles all methods relating to file creation
 */
public class MakeFile {

  /**
   * creates a new file with given name and adds it to given parent
   *
   * @param parent
   * @param name
   * @return the freshly made file
   */
  private static File addFile(Directory parent, String name) {
    File file = new File(parent, name);
    parent.addFileChild(file);
    return file;
  }

  /**
   * traverses the path and sees if it leads to a file, if it does then returns that file. If not,
   * it will make the file and return that instead.
   *
   * @param path
   * @return the file at the end of the path
   */
  public static File makeFile(String path, FileTree fileTree) {

    // initializing variables
    String parentPath;
    Directory parent;
    File file;

    if (path.equals("/")) {// special invalid path
      return null;
    }

    // finding the parent to the file
    int p = path.lastIndexOf("/");

    // if no "/" is found then it is a direct child
    if (p == -1) {

      // set parent to current dir
      parentPath = fileTree.getCurrentPath();

      // if there is only one "/"
    } else if (p == 0) {

      // set path to root
      parentPath = "/";

      // otherwise extract substring
    } else {
      parentPath = path.substring(0, p);
    }

    // traverse substring and get parent
    parent = (Directory) fileTree.traverseToEnd(parentPath);

    // if parent not found, throw an error
    if (parent == null) {
      return null;
    }

    // if the path leads to a file throw an error
    if (parent.findSubDir(path.substring(p + 1)) != null) {
      return null;
    }

    // check if the file exists
    file = parent.findFile(path.substring(p + 1));

    // if not, make one
    if (file == null) {
      file = addFile(parent, path.substring(p + 1));
    }

    return file;
  }

  /**
   * over loaded make file with content
   *
   * @param path
   * @param content
   * @return File
   */
  public static File makeFile(String path, String content, FileTree fileTree) {
    File file = makeFile(path, fileTree);
    if (file != null) {
      file.setContent(content);
    }
    return file;
  }

}
