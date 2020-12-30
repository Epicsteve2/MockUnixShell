package test;

import fileSystem.Directory;

/** This class has all of the methods needed to Mock a multiple directory branch object */
public class MockMultipleDirectoryBranch extends Directory {

  /**
   * Construct a MockMultipleDirectoryBranch objecy
   * 
   * @param parent directory for mock directory
   * @param string name of directory
   */
  private MockMultipleDirectoryBranch(Directory p, String n) {
    super(p, n);
  }

  /**
   * Get a MockMultipleDirectoryBranch object
   * 
   * @param directory root for the file tree
   * @return a MockMultipleDirectoryBranch object
   */
  public static MockMultipleDirectoryBranch getMockDir(Directory root) {

    Directory p = createDirTree(root);

    MockMultipleDirectoryBranch mock = new MockMultipleDirectoryBranch(p, "c");

    p.addDirectoryChild(mock);

    // return deepest directory in mock directory
    return mock;
  }

  /**
   * Create different branches of directories that are linked to eachother
   * 
   * @param directory root for the file tree
   * @return deepest Directory created
   */
  private static Directory createDirTree(Directory root) {

    Directory a = new Directory(root, "a");
    root.addDirectoryChild(a);

    Directory b1 = new Directory(a, "b");
    a.addDirectoryChild(b1);

    Directory c1 = new Directory(a, "c");
    a.addDirectoryChild(c1);

    Directory b2 = new Directory(c1, "b");
    c1.addDirectoryChild(b2);

    Directory c2 = new Directory(b1, "c");
    b1.addDirectoryChild(c2);

    Directory d1 = new Directory(c2, "d1");
    c2.addDirectoryChild(d1);

    Directory d2 = new Directory(c2, "d2");
    c2.addDirectoryChild(d2);

    return d2;
  }

}
