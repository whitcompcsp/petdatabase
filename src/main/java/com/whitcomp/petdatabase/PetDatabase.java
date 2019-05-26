/*
 * PetDatabase.java
 * Paul Whitcomb <whitcomp@csp.edu>
 * CSC 422, Spring 2019
 * Concordia University, St. Paul
 * Week 1 Assignment
 */

package com.whitcomp.petdatabase;

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
                    database.consoleViewPets(database.pets);
                    break;
                
                // Add pets
                case 2:
                    database.consoleAddPets();
                    break;
                    
                // Update a pet
                case 3:
                    database.consoleUpdatePet();
                    break;
                    
                // Remove a pet
                case 4:
                    database.consoleRemovePet();
                    break;
                    
                // Search pets by name
                case 5:
                    database.consoleViewPets(database.consoleSearchPetsByName());
                    break;
                    
                // Search pets by age
                case 6:
                    database.consoleViewPets(database.consoleSearchPetsByAge());
                    break;
                    
                // Exit
                case 7:
                    System.out.println("Goodbye!");
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
     * Search for pets by name (case insensitive)
     * @param name name to search
     * @return list of pets that have the given name
     */
    public List<Pet> searchPetsByName(String name) {
        // Initialize list
        ArrayList<Pet> list = new ArrayList<>();
        
        // Add all pets with a matching name
        for(Pet pet : pets) {
            if(pet.getName().equalsIgnoreCase(name)) {
                list.add(pet);
            }
        }
        
        // Return the list
        return list;
    }
    
    /**
     * Search for pets by age
     * @param age age to search
     * @return list of pets that have the given name
     */
    public List<Pet> searchPetsByAge(int age) {
        // Initialize list
        ArrayList<Pet> list = new ArrayList<>();
        
        // Add all pets with a matching name
        for(Pet pet : pets) {
            if(pet.getAge() == age) {
                list.add(pet);
            }
        }
        
        // Return the list
        return list;
    }
    
    /**
     * Get the pet at the given index
     * @param index ID of the pet to get
     * @return pet at the index
     */
    public Pet getPet(int index) {
        return pets.get(index);
    }
    
    /**
     * Remove the pet at the given index
     * @param index ID of the pet to delete
     */
    public void removePet(int index) {
        pets.remove(index);
    }
    
    /**
     * Get the number of pets in the database
     * @return number of pets in the database
     */
    public int size() {
        return pets.size();
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
     * @param petsToShow pets to display
     */
    private void consoleViewPets(List<Pet> petsToShow) {
        // Print the header
        System.out.println("+----------------------+");
        System.out.println("| ID | NAME      | AGE |");
        System.out.println("+----------------------+");
        
        // Print each pet in the indices array
        for(Pet pet : petsToShow) {
            System.out.printf("|%3d | %-10s|%4d |\n", pets.indexOf(pet), pet.getName(), pet.getAge());
        }
        
        // Print the footer
        System.out.println("+----------------------+");
        System.out.printf("%d row%s in set\n", petsToShow.size(), (petsToShow.size() != 1) ? "s" : "");
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
    
    /**
     * Update a pet via the command line
     */
    private void consoleUpdatePet() {
        // Show a list of pets, first
        consoleViewPets(pets);
        System.out.println();
        
        // Ask for an ID to update
        System.out.print("Enter the pet ID you can to update:");
        int index = stdin.nextInt();
        
        // Ask for a name an age to update with
        System.out.print("Enter new name and new age:");
        String name = stdin.next();
        int age = stdin.nextInt();
        
        // Update the pet
        Pet petToUpdate = getPet(index);
        petToUpdate.setName(name);
        petToUpdate.setAge(age);
    }
    
    /**
     * Delete a pet via the command line
     */
    private void consoleRemovePet() {
        // Show a list of pets, first
        consoleViewPets(pets);
        System.out.println();
        
        // Ask for an ID to update
        System.out.print("Enter the pet ID to remove:");
        int index = stdin.nextInt();
        Pet petToRemove = getPet(index);
        
        // Output that we were successful
        removePet(index);
        System.out.printf("%s %d is removed.\n", petToRemove.getName(), petToRemove.getAge());
    }
    
    /**
     * Search for pets by name using a name from standard input
     * @return list of pets to search
     */
    private List<Pet> consoleSearchPetsByName() {
        // Ask for a search query
        System.out.print("Enter a name to search:");
        String searchQuery = stdin.next();
        
        // Return the pets list
        return searchPetsByName(searchQuery);
    }
    
    /**
     * Search for pets by age using an age from standard input
     * @return list of pets to search
     */
    private List<Pet> consoleSearchPetsByAge() {
        // Ask for a search query
        System.out.print("Enter age to search:");
        int searchQuery = stdin.nextInt();
        
        // Return the pets list
        return searchPetsByAge(searchQuery);
    }
}
