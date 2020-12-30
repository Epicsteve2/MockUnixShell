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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import fileSystem.File;
import fileSystem.MakeFile;
import inputOutput.StandardOutput;

/** This class has all of the methods needed to run Curl */
public class Curl extends Command {

  // A string for the address of the URL
  private String urlAddress;

  // A URL object constructed from urlAddress
  private URL url;

  // A File object to save the retrieved file
  private File file;

  // A FileTree object to save the structure of the files
  private FileTree fileTree;

  // An object to open a connection to the URL
  private URLConnection urlConnection;

  // A string to specify information about the browser that made the URL connection request
  private String agentHeader;

  // A boolean to indicate whether the file at the URL has been retrieved successfully
  private boolean success;

  @Override
  /**
   * Executes the curl command
   * 
   * @param args a string array of arguments for the curl command
   * @param fileTree structure of the file tree
   * @return an empty string
   */
  public String execute(String[] args, FileTree fileTree) {
    // Set the URL address as the second argument
    this.urlAddress = args[1];

    // Get the file structure
    this.fileTree = FileTree.getFileTree();

    // Try to open and connect to the URL
    if (createURLObject() && openURLConnection() && connectToURL("")) {
      // Try to retrieve the content from the specified URL address
      String content = retrieveURLFile();
      // If the content is retrieved successfully
      if (this.success == true) {
        // Then make a file with the file name specified by the URL address, write the content into
        // it, and add the file to the current directory. Furthermore, save the retrieved file
        this.file = MakeFile.makeFile(extractFileName(), content, this.fileTree);
      }

    }
    // Return an empty string
    return "";
  }

  /**
   * A getter method to retrieve the file specified by the URL
   * 
   * @return the retrieved file object
   */
  public File getFile() {
    return this.file;
  }

  /**
   * A setter method to set the URL address
   * 
   * @param urlAddress the string of desired of URL address
   */
  public void setURLAddress(String urlAddress) {
    this.urlAddress = urlAddress;
    return;
  }

  /**
   * Create the URL object from the given URL address
   * 
   * @return a boolean of value true if a URL object could be created or false otherwise
   */
  public boolean createURLObject() {
    try {
      // Try to make a URL object from the urlAddress string
      this.url = new URL(this.urlAddress);
      return true;
    } catch (MalformedURLException e) {
      // Produce an error message if the URL is malformed
      StandardOutput.perror("The URL " + this.urlAddress + " is malformed");
      return false;
    }
  }

  /**
   * Opens a connection to the URL
   * 
   * @return a boolean with value of true if a connection with the URL can be opened, or false
   *         otherwise
   */
  private boolean openURLConnection() {
    try {
      // Try to open the connection with the URL
      this.urlConnection = this.url.openConnection();
    } catch (IOException e) {
      // Produce an error message if a new connection with the URL cannot be initiated
      StandardOutput.perror("Unable to create a new connection with the URL " + this.urlAddress);
      // And return false
      return false;
    }
    // If URL object and URL connection creation were both successful, then return true
    return true;
  }

  /**
   * Connect to the URL
   * 
   * @param agentHeader a string containing the agent header
   * @return a boolean with value of true if a connection with the URL can be established, or false
   *         otherwise
   */
  private boolean connectToURL(String agentHeader) {
    // Sets a specified timeout value to connect to the URL in milliseconds
    this.urlConnection.setConnectTimeout(1000);
    // Set the agent header, which specifies information about the browser that makes the request
    setAgentHeader(agentHeader);
    this.urlConnection.setRequestProperty("User-Agent", this.agentHeader);
    // Try to connect to the URL
    try {
      this.urlConnection.connect();
    } catch (IOException e) {
      // If it fails to connect, then produce a suitable error message
      StandardOutput.perror("Fails to connect to " + urlAddress);
      // And return false
      return false;
    }
    // If a connection can be established, then return true
    return true;
  }

  /**
   * Sets the agent header, which specifies information about the browser that makes the request
   * 
   * @param agentHeader a string containing the agent header
   */
  private void setAgentHeader(String agentHeader) {
    // If it's just an empty string
    if (agentHeader.equals("")) {
      // Then use the default agent header
      this.agentHeader = "Mozilla/5.0 (Windows NT 6.1; WOW64) "
          + "AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
    } else {
      // Otherwise, sets to the desired agent header
      this.agentHeader = agentHeader;
    }
    return;
  }

  /**
   * Retrieves the desired file from the specified URL
   * 
   * @return a string containing the contents of the file from the specified URL, or an empty string
   *         if the file cannot be retrieved
   */
  private String retrieveURLFile() {
    // Set a buffered reader
    BufferedReader reader;
    // Set a specified timeout value to read the contents of the file in the specified URL
    this.urlConnection.setReadTimeout(10000);
    try {
      // Try to read the contents and use utf-8 encoding
      reader = new BufferedReader(
          new InputStreamReader(this.urlConnection.getInputStream(), StandardCharsets.UTF_8));
    } catch (IOException e) {
      // Otherwise, indicate that the file is not successfully read
      this.success = false;
      // Produce a suitable error message
      StandardOutput.perror("Fails to create the buffered reader");
      // And return an empty string
      return "";
    }

    // A string for reading the content by line
    String line;
    // A string containing the content
    String content = "";
    try {
      // While there is still content to be read by line
      while ((line = reader.readLine()) != null) {
        // Append that line into the string content
        content += line;
        // Append a new line to move into the next line
        content += "\n";
      }
      // Indicate that the file is successfully read
      this.success = true;
      // Return the content
      return content;
    } catch (IOException e) {
      // Otherwise, indicate that the file is not successfully read
      this.success = false;
      // Produce a suitable error message
      StandardOutput.perror("Fails to read the desired file");
      // And return an empty string
      return "";
    }

    // Attempt to close the reader
    finally {
      // If the reader is not null
      if (reader != null) {
        try {
          // Then try closing the reader
          reader.close();
        } catch (IOException e) {
          // If the reader cannot be closed, then produce a suitable error message
          StandardOutput.perror("Unable to close the buffered reader");
        }
      }
    }
  }

  /**
   * Extract the file name from the specified URL
   * 
   * @return a string containing the file name
   */
  private String extractFileName() {
    // Get the complete path of the URL
    String path = this.url.getFile();
    // Split the path by "/"
    String[] splittedPath = path.split("/");
    // Take the last element since that is the only file name
    String fileName = splittedPath[splittedPath.length - 1];
    // Strip off . from the file name
    return fileName.replace(".", "");
  }

  @Override
  /**
   * Returns a manual entry for curl
   * 
   * @return a string containing manual entry for curl
   */
  public String toString() {
    String info = "curl URL\n" + "\tRetrieve the file at the URL and add it to the current"
        + " working directory.";
    return info;
  }

}
