package edu.ntnu.stud;

/**
 * This class represents the main part of the program.
 * This is kept as simple as possible, and only contains the main method.
 * Inside the main method, the UserInterface class is instantiated.
 * And the start and init methods are called.
 *
 * @version 1.2 2023-12-11
 * */

public class Main {
  /**
   * Starts the program.
   * */
  public static void main(String[] args) {
    UserInterface ui = new UserInterface();
    ui.init();
    ui.start();
  }
}
