package test;

import fileSystem.Directory;

/** This class has all of the methods needed to Mock a single directory branch object */
public class MockSingleDirectoryBranch extends Directory {

  /**
   * Construct a MockSingleDirectoryBranch objecy
   * 
   * @param parent directory for mock directory
   * @param string name of directory
   */
  private MockSingleDirectoryBranch(Directory p, String n) {
    super(p, n);
  }

  /**
   * Get a MockSingleDirectoryBranch object
   * 
   * @param directory root for the file tree
   * @return a MockSingleDirectoryBranch object
   */
  public static MockSingleDirectoryBranch getMockDir(Directory root) {

    Directory p = createDirTree(root);

    MockSingleDirectoryBranch mock = new MockSingleDirectoryBranch(p, "c");

    p.addDirectoryChild(mock);

    // return deepest directory in mock directory
    return mock;
  }

  /**
   * Create a chain of directories that are linked to eachother
   * 
   * @param directory root for the file tree
   * @return deepest Directory created
   */
  private static Directory createDirTree(Directory root) {

    Directory a = new Directory(root, "a");
    root.addDirectoryChild(a);

    Directory b1 = new Directory(a, "b");
    a.addDirectoryChild(b1);

    Directory b2 = new Directory(b1, "b");
    b1.addDirectoryChild(b2);

    Directory c = new Directory(b2, "c");
    b2.addDirectoryChild(c);

    return c;
  }

}
