package commands;

/*
 * do not change the name of the package
 */
//package b07.fall2020.quizzes;
import java.util.Iterator;

//**********************************************************
//Quiz
//UTOR user_name: guostep2
//UT Student #: 1006313231
//Author: Stephen Guo
//
//
//Honor Code: I pledge that this program represents my own
//program code and that I have coded on my own. I received
//help from no one in designing and debugging my program.
//I have also read the plagarism section in the course info
//sheet of CSC B07 and understand the consequences. I understand that 
//the instructor and the course team staff will be running 
//my quizzes and assignments for plagarism 
//check and will hold a Zoom interview with me if any further clarification 
// is required on my submission. My grades will be witheld until after the Zoom interview
// has been completed. 
//Note from instructor: If you do not complete this honor
//code, we will give you no credit and award you 0%.
//**********************************************************


/*
 * Step 1: TODO: Have your LinkedList to be an Iterable
 * Step 2: A dummy node is a node that contains no data. The head Node always points at this 
 * dummy node and should never ever change. The contents of the LinkedList begins after the dummy
 * node. This is an implementation detail and the user who uses your LinkedList is not aware of it. 
 * Step 3: TODO: Override the toString method inside the LinkedList
 * Step 4: TODO: Complete the addData method inside the LinkedList
 * Step 5: TODO: Write an Iterator class that traverses the contents of the LinkedList. This Iterator class
 * must be inner nested class (non static), similar to what we did in lecture.
 * Step 6: TODO: Make sure to complete your honor code.  
 * Step 7: Submit your LinkedList.java on Gradescope. 
 * Do not use stack or queue when answering this question. 
 * 
 * You are free to edit the main function in any way you like, however, we have provided some 
 * test cases and you are encouraged to write your own test cases as well. 
 */
public class LinkedListQuiz1<E> implements Iterable<E>{

    //head always points to the dummy node
    private Node head;
    //tail always points to the last node of your linked list
    private Node tail;
    
    /*
     * DO NOT modify this constructor. 
     */
    public LinkedListQuiz1()
    {
        /*
         * in an empty linked list, the head points to the dummy node
         * and the tail also points to the dummy node. 
         */
        //points to a dummy node
        head=new Node();
        tail=head;
    }
    
    /*
     * TODO: Override the toString method, such that for a linked list
     * that is shown in the main function, you must return back a textual representation
     * of the LinkedList. For the example shown in the test1() function, you will return back a 
     * String that is "1->2->3->4->Null", I have just put double quotes around the actual string, to 
     * denote what the actual string looks like. For an empty linked list you must return back the string
     * "Null". 
     */
    public String toString () {
      String output = "";
      Node currentNode = tail.next;
      while (currentNode != null && currentNode.next != null) {
        output += currentNode.data;
        output += "->";
        currentNode = currentNode.next;
      }
      output += "Null";
      return output;
    }
    
    @Override
    public Iterator<E> iterator() {
      return new LinkedListIterator(tail);
    }
    
    private class LinkedListIterator implements Iterator<E> {
      Node currentNode;
      
      public LinkedListIterator(Node tail) {
        this.currentNode = tail;
      }
      
      @Override
      public boolean hasNext() {
        return currentNode.next != null && currentNode.next != head;
      }

      @Override
      public E next() {
        currentNode = currentNode.next;
        return currentNode.data;
      }
    }
    
    /*
     * DO NOT MODIFY THE NODE CLASS
     * ANY MODIFICATION TO THE NODE CLASS
     * WILL RESULT IN ZERO
     */
    private class Node {
        
        private Node next;
        private E data;
        
        public Node()
        {
            next=null;
        }
        public Node(E data)
        {
            this.data=data;
            next=null;
        }
    }
    
    
    /*
     * TODO: You must complete this method. 
     * This method can never move the head reference of the 
     * LinkedList. The head reference of the linked list 
     * must always refer to the dummy node. Instead you must use 
     * the tail for the LinkedList to grow. 
     * This is about 2-4 lines of code. 
     */
    public void addData(E data)
    {
      Node newNode = new Node(data);
      newNode.next = head;
      if (tail == head) {
        tail = new Node();
        tail.next = newNode;
      } else {
        Node currentNode = tail.next;
        while (currentNode.next != head) {
          currentNode = currentNode.next;
        }
        currentNode.next = newNode;
      }
    }
    
    public static void main(String[] args)
    {
        test1();
        test2();
        //test3();
    }

    private static void test1() {
        LinkedListQuiz1<Integer> l =new LinkedListQuiz1<>();
        l.addData(1);
        l.addData(2);
        l.addData(3);
        l.addData(4);
        
        /*
         * The following should print
         * 1->2->3->4->Null
         */
        System.out.println(l); 
        
        /*
         * The following should print
         * 1
         * 2
         * 3
         * 4
         */
        /* 
         */
        for (Integer i:l)
        {
            System.out.println(i);
        }
    }
    
    private static void test2() {
        LinkedListQuiz1<Integer> l =new LinkedListQuiz1<>();
        /*
         * The following should print
         * Null
         */
        System.out.println(l); 
        
        /*
         * The linked list is empty and hence the loop will 
         * not run i.e., nothing gets printed. 
         */
        for (Integer i:l)
        {
            System.out.println(i);
        }
    }
    
    private static void test3() {
      LinkedListQuiz1<Boolean> l =new LinkedListQuiz1<>();
      l.addData(true);
      l.addData(true);
      l.addData(false);
      
      System.out.println(l + "\n\n"); 
      for (Boolean i:l)
      {
          System.out.println(i);
      }
    }
    }

