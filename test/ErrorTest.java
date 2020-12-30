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
package test;

import static org.junit.Assert.*;
import org.junit.Test;
import inputOutput.Error;

public class ErrorTest {

  @Test
  public void testRemove() {
    assertTrue(Error.checkSyntax(new String[] {"rm", "folder1"}));
    assertTrue(Error.checkSyntax(new String[] {"rm", "folder1/folder3"}));
    assertFalse(Error.checkSyntax(new String[] {"rm"}));
    assertFalse(Error.checkSyntax(new String[] {"rm", "folder1", "extra"}));
  }

  @Test
  public void testMakeDirectory() {
    assertTrue(Error.checkSyntax(new String[] {"mkdir", "a"}));
    assertTrue(Error.checkSyntax(new String[] {"mkdir", "a", "b", "comeOn"}));
    assertTrue(Error.checkSyntax(new String[] {"mkdir", "//"}));
    assertTrue(Error.checkSyntax(new String[] {"mkdir", "oaisjdf9*(*(&^"}));
    assertFalse(Error.checkSyntax(new String[] {"mkdir"}));
    assertTrue(Error.checkSyntax(new String[] {"mkdir", "otherTests", "testing/LoverOfAnime"}));
  }

  @Test
  public void testChangeDirectory() {
    assertTrue(Error.checkSyntax(new String[] {"cd", "amazing"}));
    assertTrue(Error.checkSyntax(new String[] {"cd", "."}));
    assertTrue(Error.checkSyntax(new String[] {"cd", "/"}));
    assertTrue(Error.checkSyntax(new String[] {"cd", "folder1/folder2/folder3/../yo/"}));
    assertFalse(Error.checkSyntax(new String[] {"cd", "//"}));
    assertFalse(Error.checkSyntax(new String[] {"cd"}));
    assertFalse(Error.checkSyntax(new String[] {"cd", "test", "fail"}));
  }

  @Test
  public void testList() {
    assertTrue(Error.checkSyntax(new String[] {"ls", "amazing", "/"}));
    assertTrue(Error.checkSyntax(new String[] {"ls", ".", ".", "a", "a"}));
    assertTrue(Error.checkSyntax(new String[] {"ls", "/"}));
    assertTrue(Error.checkSyntax(new String[] {"ls", "folder1/folder2/folder3/../yo/"}));
    assertTrue(Error.checkSyntax(new String[] {"ls", "//"}));
    assertTrue(Error.checkSyntax(new String[] {"ls"}));
    assertTrue(Error.checkSyntax(new String[] {"ls", "test", "fail"}));
  }

  @Test
  public void testPrintWorkingDirectory() {
    assertTrue(Error.checkSyntax(new String[] {"pwd"}));
    assertFalse(Error.checkSyntax(new String[] {"pwd", "params"}));
    assertFalse(Error.checkSyntax(new String[] {"pwd", "nothing", "works"}));
  }

  @Test
  public void testMove() {
    assertTrue(Error.checkSyntax(new String[] {"mv", "file1", "file2"}));
    assertTrue(Error.checkSyntax(new String[] {"mv", "folder1", "folder2/folder3"}));
    assertTrue(Error.checkSyntax(new String[] {"mv", "folder1", "folder2"}));
    assertFalse(Error.checkSyntax(new String[] {"mv", "folder1"}));
    assertFalse(Error.checkSyntax(new String[] {"mv"}));
    assertFalse(Error.checkSyntax(new String[] {"mv", "folder1", "folder2", "fail"}));
    assertFalse(Error.checkSyntax(new String[] {"mv", "folder1", "folder2(*&(*&(*"}));
    assertFalse(Error.checkSyntax(new String[] {"mv", "fol&!!der1", "folder2"}));
  }

  @Test
  public void testCopy() {
    assertTrue(Error.checkSyntax(new String[] {"cp", "file1", "file2"}));
    assertTrue(Error.checkSyntax(new String[] {"cp", "folder1", "folder2/folder3"}));
    assertTrue(Error.checkSyntax(new String[] {"cp", "folder1", "folder2"}));
    assertFalse(Error.checkSyntax(new String[] {"cp", "folder1"}));
    assertFalse(Error.checkSyntax(new String[] {"cp"}));
    assertFalse(Error.checkSyntax(new String[] {"cp", "folder1", "folder2", "fail"}));
    assertFalse(Error.checkSyntax(new String[] {"cp", "folder1", "folder2(*&(*&(*"}));
    assertFalse(Error.checkSyntax(new String[] {"cp", "fol&!!der1", "folder2"}));
  }

  @Test
  public void testCat() {
    assertTrue(Error.checkSyntax(new String[] {"cat", "file1", "file2"}));
    assertTrue(Error.checkSyntax(new String[] {"cat", "file2"}));
    assertTrue(
        Error.checkSyntax(new String[] {"cat", "file2", "file3", "animeFile", "brokenFile"}));
    assertFalse(Error.checkSyntax(new String[] {"cat"}));
  }

  @Test
  public void testCurl() {
    assertTrue(Error.checkSyntax(new String[] {"curl", "randomurlHttps//aoisdjfoiajsdiof"}));
    assertFalse(Error.checkSyntax(new String[] {"curl", "file1", "file2"}));
    assertFalse(Error.checkSyntax(new String[] {"curl", "file1", "file2", "file3"}));
    assertFalse(Error.checkSyntax(new String[] {"curl"}));
  }

  @Test
  public void testExit() {
    assertTrue(Error.checkSyntax(new String[] {"exit"}));
    assertFalse(Error.checkSyntax(new String[] {"exit", "parameters"}));
    assertFalse(Error.checkSyntax(new String[] {"exit", "parameters", "thisIsn'tWOrking!"}));
  }

  @Test
  public void testEcho() {
    assertTrue(Error.checkSyntax(new String[] {"echo", "\"anime\""}));
    assertTrue(Error.checkSyntax(new String[] {"echo", "\"test with spaces\""}));
    assertTrue(Error.checkSyntax(new String[] {"echo", "\" singular 'quotes'\""}));
    assertFalse(Error.checkSyntax(new String[] {"echo", "no quotes..."}));
    assertFalse(Error.checkSyntax(new String[] {"echo", "\""}));
    assertFalse(Error.checkSyntax(new String[] {"echo"}));
    assertFalse(Error.checkSyntax(new String[] {"echo", "no quotes...", "params"}));
    assertFalse(Error.checkSyntax(new String[] {"echo", "\"test\"", "\"more quotes!\""}));
  }

  @Test
  public void testPushDirectory() {
    assertTrue(Error.checkSyntax(new String[] {"pushd", "amazing"}));
    assertTrue(Error.checkSyntax(new String[] {"pushd", "."}));
    assertTrue(Error.checkSyntax(new String[] {"pushd", "/"}));
    assertTrue(Error.checkSyntax(new String[] {"pushd", "folder1/folder2/folder3/../yo/"}));
    assertFalse(Error.checkSyntax(new String[] {"pushd", "//"}));
    assertFalse(Error.checkSyntax(new String[] {"pushd"}));
    assertFalse(Error.checkSyntax(new String[] {"pushd", "test", "fail"}));
  }

  @Test
  public void testPopDirectory() {
    assertTrue(Error.checkSyntax(new String[] {"popd"}));
    assertFalse(Error.checkSyntax(new String[] {"popd", "parameters"}));
    assertFalse(Error.checkSyntax(new String[] {"popd", "parameters", "thisIsn'tWOrking!"}));
  }

  @Test
  public void testHistory() {
    assertTrue(Error.checkSyntax(new String[] {"history"}));
    assertTrue(Error.checkSyntax(new String[] {"history", "1"}));
    assertTrue(Error.checkSyntax(new String[] {"history", "283746"}));
    assertTrue(Error.checkSyntax(new String[] {"history", "0"}));
    assertFalse(Error.checkSyntax(new String[] {"history", "-1"}));
    assertFalse(Error.checkSyntax(new String[] {"history", "-298347982"}));
    assertFalse(Error.checkSyntax(new String[] {"history", "5", "6"}));
    assertFalse(Error.checkSyntax(new String[] {"history", "5", "test"}));
  }

  @Test
  public void testSaveJShell() {
    assertTrue(Error.checkSyntax(new String[] {"saveJShell", "randomFileNAME!"}));
    assertFalse(Error.checkSyntax(new String[] {"saveJShell", "file1", "file2"}));
    assertFalse(Error.checkSyntax(new String[] {"saveJShell", "file1", "file2", "file3"}));
    assertFalse(Error.checkSyntax(new String[] {"saveJShell"}));
  }

  @Test
  public void testLoadJShell() {
    assertTrue(Error.checkSyntax(new String[] {"loadJShell", "randomFileNAME!"}));
    assertFalse(Error.checkSyntax(new String[] {"loadJShell", "file1", "file2"}));
    assertFalse(Error.checkSyntax(new String[] {"loadJShell", "file1", "file2", "file3"}));
    assertFalse(Error.checkSyntax(new String[] {"loadJShell"}));
  }

  @Test
  public void testSearch() {
    assertTrue(Error.checkSyntax(
        new String[] {"search", "random/path/here/./././", "-type", "f", "-name", "\"folder\""}));
    assertTrue(Error
        .checkSyntax(new String[] {"search", "thisPath", "-type", "d", "-name", "\"yahello!\""}));
    assertFalse(Error.checkSyntax(
        new String[] {"search", "nicePath/name", "-type", "d", "-name", "notaString"}));
    assertFalse(Error.checkSyntax(new String[] {"search", "only", "5", "parameters", "heere"}));
    assertFalse(Error.checkSyntax(
        new String[] {"search", "too", "many", "parameters", "for", "this", "command!"}));
    assertFalse(Error.checkSyntax(new String[] {"search"}));
    assertFalse(Error.checkSyntax(new String[] {"search", "one"}));
    assertFalse(Error
        .checkSyntax(new String[] {"search", "thisPath", "-type", "h", "-name", "\"yahello!\""}));
    assertFalse(Error
        .checkSyntax(new String[] {"search", "thisPath", "-name", "d", "-name", "\"yahello!\""}));
    assertFalse(Error
        .checkSyntax(new String[] {"search", "thisPath", "-type", "d", "-file", "\"yahello!\""}));
    assertFalse(Error.checkSyntax(new String[] {"search", "asdf", "\"yahello!\""}));
  }

  @Test
  public void testTree() {
    assertTrue(Error.checkSyntax(new String[] {"tree"}));
    assertFalse(Error.checkSyntax(new String[] {"tree", "parameters"}));
    assertFalse(Error.checkSyntax(new String[] {"tree", "parameters", "thisIsn'tWOrking!"}));
  }

  @Test
  public void testInvalidCommand() {
    assertFalse(Error.checkSyntax(new String[] {"what", "parameters"}));
    assertFalse(Error.checkSyntax(new String[] {}));
    assertFalse(Error.checkSyntax(new String[] {"is"}));
    assertFalse(Error.checkSyntax(new String[] {"this", "command", "here!?"}));
  }

  @Test
  public void testIsValidPath() {
    assertTrue(Error.isValidPath("love"));
    assertTrue(Error.isValidPath("hate/anime"));
    assertTrue(Error.isValidPath("././././././."));
    assertTrue(Error.isValidPath("./Relative"));
    assertTrue(Error.isValidPath("/"));
    assertTrue(Error.isValidPath("."));
    assertFalse(Error.isValidPath("//"));
    assertFalse(Error.isValidPath("oasidjf*&^@*&#^$*"));
  }

  @Test
  public void testCheckRedirection() {
    assertTrue(Error.checkRedirection(new String[] {"what", "parameters", ">", "file"}));
    assertTrue(Error.checkRedirection(new String[] {"append", ">>", "file2/folder1/file3234"}));
    assertFalse(Error.checkRedirection(new String[] {">>", "file2"}));
    assertFalse(Error.checkRedirection(new String[] {"ls", ">>>", "file2"}));
    assertFalse(Error.checkRedirection(new String[] {"ls", ">"}));
  }

  @Test
  public void testRemoveRedirection() {
    String[] inputArguments = {"what", "parameters", ">", "file"};
    assertEquals(new String[] {"what", "parameters"}, Error.removeRedirection(inputArguments));
    assertEquals(new String[] {"no", "redirection"}, new String[] {"no", "redirection"});
  }

  @Test
  public void testIsValidRedirection() {
    assertTrue(Error.checkRedirection(new String[] {"what", "parameters", ">", "file"}));
    assertTrue(Error.checkRedirection(new String[] {"append", ">>", "file2/folder1/file3234"}));
    assertFalse(Error.checkRedirection(new String[] {">>", "file2"}));
    assertFalse(Error.checkRedirection(new String[] {"ls", ">>>", "file2"}));
    assertFalse(Error.checkRedirection(new String[] {"ls", ">"}));
  }
}
