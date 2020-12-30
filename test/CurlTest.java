package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Curl;


public class CurlTest {

  Curl curl;

  private String invalidURL1;
  private String invalidURL2;
  private String validURLTXT;
  private String validURLHTML;
  private String expectedStringTXT;
  private String expectedStringHTML;
  private String fileNameTXT;
  private String fileNameHTML;

  @Before
  public void setUp() {
    curl = new Curl();
    invalidURL1 = "abcdef";
    invalidURL2 = "htt://www.w3.org/TR/PNG/iso_8859-1.txt";
    validURLTXT = "https://www.w3.org/TR/PNG/iso_8859-1.txt";
    validURLHTML = "http://www.htmlandcssbook.com/code-samples/chapter-01/example.html";
    fileNameTXT = "iso_8859-1txt";
    fileNameHTML = "examplehtml";
    expectedStringTXT = "The following are the graphical (non-control) characters defined by\n"
        + "ISO 8859-1 (1987).  Descriptions in words aren't all that helpful,\n"
        + "but they're the best we can do in text.  A graphics file illustrating\n"
        + "the character set should be available from the same archive as this\n" + "file.";
    expectedStringHTML = "<html>\n";
  }


  @Test
  public void testInvalidURL1() {
    curl.setURLAddress(invalidURL1);
    assertFalse(curl.createURLObject());
  }

  @Test
  public void testInvalidURL2() {

    curl.setURLAddress(invalidURL2);
    assertFalse(curl.createURLObject());
  }

  @Test
  public void testValidURLTXT() {
    curl.setURLAddress(validURLTXT);
    assertTrue(curl.createURLObject());
  }

  @Test
  public void testValidURLHTML() {
    curl.setURLAddress(validURLHTML);
    assertTrue(curl.createURLObject());
  }

  @Test
  public void testCreateHTMLFile() {
    curl.execute(new String[] {"curl", validURLHTML}, null);
    assertEquals(fileNameHTML, curl.getFile().getName());
  }

  @Test
  public void testCreateTXTFile() {
    curl.execute(new String[] {"curl", validURLTXT}, null);
    assertEquals(fileNameTXT, curl.getFile().getName());
  }

  @Test
  public void testContentHTMLFile() {
    curl.execute(new String[] {"curl", validURLHTML}, null);
    assertEquals(expectedStringHTML,
        curl.getFile().getContent().substring(0, expectedStringHTML.length()));
  }

  @Test
  public void testContentTXTFile() {
    curl.execute(new String[] {"curl", validURLTXT}, null);
    assertEquals(expectedStringTXT,
        curl.getFile().getContent().substring(0, expectedStringTXT.length()));
  }
}
