/*
 * Paul Whitcomb <whitcomp@csp.edu>
 * CSC 422, Spring 2019
 * Concordia University, St. Paul
 * Week 1 Assignment
 */

package petdatabase;

import java.util.ArrayList;
import java.util.List;
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
        stdin = new Scanner(System.in);
        
        // Greet the user
        System.out.println("Pet Database Program.");
        
        // Loop forever until the user wishes to exit the program
        while(true) {
            // Print out an empty line for padding
            System.out.println();
            
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
            
            // Print out another empty line for padding
            System.out.println();
            
            // Choose what to do depending on the input
            switch(selection) {
                // Show pets
                case 1:
                    // Display the pets
                    database.consoleViewPets(database.pets);
                    break;
                
                // Add pets
                case 2:
                    database.consoleAddPets();
                    break;
                    
                // Exit
                case 7:
                    return;
                
                // Anything unimplemented goes here
                default:
                    throw new UnsupportedOperationException();
            }
        }
    }
    
    /**
     * Create a new database of pets
     */
    public PetDatabase() {
        pets = new ArrayList<>();
    }
    
    /**
     * Add a pet to the database
     * @param pet pet to add to the database
     */
    public void addPet(Pet pet) {
        pets.add(pet);
    }
    
    /**
     * Array of pets in the database
     */
    private ArrayList<Pet> pets;
    
    /**
     * Scanner object to get input from the user via standard input
     */
    private static Scanner stdin;
    
    /**
     * View all pets in the command line
     * @param indices indices to display
     */
    private void consoleViewPets(List<Pet> petsToShow) {
        // Print the header
        System.out.println("+----------------------+");
        System.out.println("| ID | NAME      | AGE |");
        System.out.println("+----------------------+");
        
        // Print each pet in the indices array
        petsToShow.forEach((pet) -> {
            System.out.printf("|%3d | %-10s|%4d |\n", pets.indexOf(pet), pet.getName(), pet.getAge());
        });
        
        // Print the footer
        System.out.println("+----------------------+");
        System.out.printf("%d row%s in set\n", pets.size(), (pets.size() != 1) ? "s" : "");
    }
    
    /**
     * Add pets in the command line
     */
    private void consoleAddPets() {
        // Keep asking the user for pet information until "done" is typed
        int petsAdded = 0;
        while(true) {
            System.out.print("add pet (name, age): ");
            
            // If the user types "done" then bail
            String name = stdin.next();
            if(name.equals("done")) {
                break;
            }
            
            // Add it
            int age = stdin.nextInt();
            this.addPet(new Pet(name, age));
            petsAdded++;
        }
        
        // Output how many pets were added.
        System.out.printf("%d pet%s added.\n", petsAdded, (petsAdded != 1) ? "s" : "");
    }
}
