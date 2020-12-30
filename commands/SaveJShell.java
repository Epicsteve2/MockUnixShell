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

import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileTree;
import inputOutput.StandardOutput;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * this class handles all the code for the command to save the JShell state
 */
public class SaveJShell extends Command {

  /**
   * This method will take a file and return a string of information in an XML style
   * 
   * @param f file to be saved
   * @return file info in an XML style
   */
  private String saveFile(File f) {
    String output = "";
    output = // writing file name and content in a xml style
        "<File>\n" + f.getPath() + "\n<Content>\n" + f.getContent() + "\n</Content>\n</File>\n";
    return output;
  }

  /**
   * This method will take a directory and return a string of information in an XML style
   * 
   * @param dir to be saved
   * @return directory info in an XML style
   */
  private String saveDir(Directory dir) {
    String output = "";
    output = "<Directory>\n" + PrintWorkingDirectory.printFullPath(dir) + "\n</Directory>\n";// saving
                                                                                             // dir
    Iterator<Directory> dirChildren = dir.getDirectoryChildren();// getting iterators
    Iterator<File> fileChildren = dir.getFileChildren();
    while (dirChildren.hasNext()) { // load through all sub directories and files and save them
      output += this.saveDir(dirChildren.next());
    }
    while (fileChildren.hasNext()) {
      output += this.saveFile(fileChildren.next());
    }
    return output;
  }

  /**
   * This method will take an ArrayList of history inputs and return a string of information in an
   * XML style
   * 
   * @param h ArrayList of history inputs
   * @return history info in an XML style
   */
  private String saveHist(ArrayList<String> h) {
    String output = "";
    output += "<History>\n";// loop through history and save each command
    for (int i = 0; i < h.size(); i++) {
      output += h.get(i) + "\n";
    }
    output += "</History>\n";
    return output;
  }

  /**
   * This method will take an Stack of Directories for PushDirectory and return a string of
   * information in an XML style
   * 
   * @param ds stack of directories
   * @return Stack info in an XML style
   */
  private String saveDirStack(Stack<Directory> ds) {
    String output = "";
    Directory[] h = new Directory[ds.size()];
    h = ds.toArray(h);
    output += "<DirectoryStack>\n";// loop directory in revers order for ease of loading
    for (int i = h.length - 1; i >= 0; i--) {
      output += PrintWorkingDirectory.printFullPath(h[i]) + "\n";
    }
    output += "</DirectoryStack>\n";
    return output;
  }

  @Override
  /**
   * Execute the saveJShell command
   * 
   * @param a string array of arguments for the saveJShell command
   * @param shell file tree
   * @return a string containing the output of saveJShell command
   */
  public String execute(String[] args, FileTree fileTree) {
    String output = "";
    output += "<FileSystem>\n";// signalling start of filesystem save data
    // loading in the iterators
    Iterator<Directory> dirChildren = fileTree.getRoot().getDirectoryChildren();
    Iterator<File> fileChildren = fileTree.getRoot().getFileChildren();
    // looping through every directory and saving them
    while (dirChildren.hasNext()) {
      output += this.saveDir(dirChildren.next());
    }
    // looping through the files and saving them
    while (fileChildren.hasNext()) {
      output += this.saveFile(fileChildren.next());
    }
    output += "</FileSystem>\n";// signalling end of file system data
    output += this.saveHist(History.getHistory());// saving command history
    output += this.saveDirStack(PushDirectory.dirStack);// saving directory stack
    try {// try to write to file
      java.io.File file = new java.io.File(args[1]);
      if (!file.exists()) {
        file.createNewFile();
      }
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(output);
      bw.close();
    } catch (Exception e) {
      // e.printStackTrace();
      StandardOutput.perror("trouble writing to file");
    }
    return "";
  }

  @Override
  /**
   * returns the manual entry for saveJShell
   * 
   * @return manual entry for saveJShell
   */
  public String toString() {
    return "Saves current shell to specified File";
  }
}
