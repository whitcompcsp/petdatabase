/*
 * Paul Whitcomb <whitcomp@csp.edu>
 * CSC 422, Spring 2019
 * Concordia University, St. Paul
 * Week 1 Assignment
 */

package petdatabase;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main pet database class.
 * @author Paul
 */
public class PetDatabase {
    /**
     * Entry point for the application
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Initialize a pet database
        PetDatabase database = new PetDatabase();
        
        // Initialize a scanner we'll need for getting input from the user
        Scanner stdin = new Scanner(System.in);
        
        // Loop forever until the user wishes to exit the program
        while(true) {
            // Ask the user what to do next
            System.out.println("What would you like to do?");
            System.out.println(" 1) View all pets");
            System.out.println(" 2) Add more pets");
            System.out.println(" 3) Update an existing pet");
            System.out.println(" 4) Remove an existing pet");
            System.out.println(" 5) Search pets by name");
            System.out.println(" 6) Search pets by age");
            System.out.println(" 7) Exit program");
            
            // Get the input
            System.out.print("Your choice: ");
            int selection = stdin.nextInt();
            
            // Choose what to do depending on the input
            switch(selection) {
                // Anything unimplemented goes here
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
    
    /**
     * Array of pets in the database
     */
    private ArrayList<Pet> pets;
}
