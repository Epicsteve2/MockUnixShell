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
import fileSystem.FileTree;
import fileSystem.MakeFile;
import inputOutput.StandardOutput;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/** This class has all of the methods needed to run LoadJShell */
public class LoadJShell extends Command {

  /**
   * This method will load the directories from the file
   * 
   * @param br bufferedreader to read from the file
   * @param f file system for the shell
   * @throws IOException
   */
  private void loadDir(BufferedReader br, FileTree f) throws IOException {
    MakeDirectory mkdir = new MakeDirectory();
    String line, path = "";
    while (!(line = br.readLine()).equals("</Directory>")) {// while the name is to be read
      path += line;
    }
    mkdir.execute(new String[] {"mkdir", path}, f);// make directory from name
  }

  /**
   * This method load the files from the file
   * 
   * @param br bufferedreader to read from the file
   * @param f file system for the shell
   * @throws IOException
   */
  private void loadFile(BufferedReader br, FileTree f) throws IOException {
    String line, path = "", content = "";
    path = br.readLine();// read file name
    br.readLine();// read <Contents>
    while (!(line = br.readLine()).equals("</Content>")) {
      content += line + "\n";// reading file contents
    }
    if (!content.equals("")) {
      content = content.substring(0, content.length() - 1);// removing trailing newline
    }
    br.readLine();// reading </File>
    MakeFile.makeFile(path, content, f);// making file with name and content
  }

  /**
   * This method will load the file system from the file
   * 
   * @param br bufferedreader to read from the file
   * @param f file system for the shell
   * @throws IOException
   */
  private void loadFileSys(BufferedReader br, FileTree f) throws IOException {
    String line;
    while (!(line = br.readLine()).equals("</FileSystem>")) {// checking if there is still
                                                             // filesystem data to be read
      switch (line) {// checking if reading directory or a file
        case ("<Directory>"):
          loadDir(br, f);
          break;
        case ("<File>"):
          loadFile(br, f);
          break;
        default:
          break;
      }
    }

  }

  /**
   * This method will load the directory stack for PushD from the file
   * 
   * @param br bufferedreader to read from the file
   * @param f file system for the shell
   * @throws IOException
   */
  private void loadDirStack(BufferedReader br, FileTree f) throws IOException {
    String line;// loading the directory stack
    while (!(line = br.readLine()).equals("</DirectoryStack>")) {
      PushDirectory.dirStack.push((Directory) f.traverseToEnd(line));
    }
  }

  private void loadHist(BufferedReader br, FileTree f) throws IOException {
    String line;// loading history
    while (!(line = br.readLine()).equals("</History>")) {
      History.addInputHistory(line);
    }
  }

  @Override
  /**
   * Execute the loadJShell command
   * 
   * @param a string array of arguments for the loadJShell command
   * @param shell file tree
   * @return a string containing the output of loadJShell command
   */
  public String execute(String[] args, FileTree f) {
    String line;
    try {// try reading from file
      BufferedReader br = new BufferedReader(new FileReader(args[1]));
      try {
        while ((line = br.readLine()) != null) {
          switch (line) {
            case ("<FileSystem>"):
              loadFileSys(br, f);// if loading filesystem
              break;
            case ("<History>"):
              loadHist(br, f);// if loading history
              break;
            case ("<DirectoryStack>"):
              loadDirStack(br, f); // if loading directory stack
              break;
            default:
              StandardOutput.perror("Invalid file selected");// if it doesn't fit the syntax
              return "";
          }
        }
      } finally {
        br.close();
      }
    } catch (Exception e) {
      StandardOutput.perror("trouble reading file");
    }

    return "";
  }

  @Override
  /**
   * returns the manual entry for loadJShell
   * 
   * @return manual entry for loadJShell
   */
  public String toString() {
    return "Loads Shell from file";
  }
}
