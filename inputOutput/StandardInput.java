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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import commands.Command;
import commands.History;

/**
 * This class has methods that parses an input from the JShell
 */
public class StandardInput {
  /**
   * This method will add spaces between a string and ">>" or ">" parameters if they exist.
   * 
   * @param user input
   * @return same input with spaces inserted between strings and ">>" or ">" if they exist
   */
  private static String addSpaces(String input) {
    String newString = "", inputAfterString = "", stringArgument = "";

    // Checks if there's a string in the input
    int firstOccurrance = input.indexOf('"');
    int lastOccurrance = input.lastIndexOf('"');

    if (!(firstOccurrance == -1 || firstOccurrance == lastOccurrance)) {
      newString += input.substring(0, firstOccurrance);
      stringArgument = input.substring(firstOccurrance, lastOccurrance + 1) + " ";
      newString += stringArgument;
      inputAfterString = input.substring(lastOccurrance + 1);
    } else {
      inputAfterString = input;
    }

    // Checks if ">" or ">>" exists, and adds spaces between them
    for (int i = 0; i < inputAfterString.length() - 1; i++) {
      if (inputAfterString.charAt(i) == '>') {
        if (inputAfterString.charAt(i + 1) == '>') {
          return newString + " >> " + inputAfterString.substring(i + 2);
        }
        return newString + " > " + inputAfterString.substring(i + 1);
      }
      newString += inputAfterString.charAt(i);
    }
    if (inputAfterString.contentEquals("")) {
      return newString;
    }
    return newString + inputAfterString.charAt(inputAfterString.length() - 1);// + inputAfterString;
  }

  /**
   * This method will split up an input by whitespaces, and will run command if the input is
   * validated by Error.java
   *
   * @param input from the JShell
   */
  public static void parse(String input) {

    // Adds input to history
    History.addInputHistory(input);

    // Adds spaces into the input if needed
    input = addSpaces(input);

    // Splits input into commands and arguments
    List<String> argumentList = new ArrayList<String>();
    Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
    while (m.find())
      argumentList.add(m.group(1));

    String[] betterArguments = argumentList.toArray(new String[0]);

    // Executes command if validated
    if (Error.checkSyntax(betterArguments)) {
      History.commandExecuted();
      Command.runCommand(betterArguments);
    }
  }
}
