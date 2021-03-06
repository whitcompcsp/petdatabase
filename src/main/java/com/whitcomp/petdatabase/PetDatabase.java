/*
 * PetDatabase.java
 * Paul Whitcomb <whitcomp@csp.edu>
 * CSC 422, Spring 2019
 * Concordia University, St. Paul
 * Week 1 Assignment
 */

package com.whitcomp.petdatabase;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.naming.LimitExceededException;

/**
 * The main pet database class.
 * @author Paul
 */
public class PetDatabase {
    /**
     * Default file name of the database.
     */
    static final String DEFAULT_FILE_NAME = "pet_database.txt";
    
    /**
     * Maximum pets in the database
     */
    static final int MAXIMUM_PETS = 5;
    
    /**
     * Entry point for the application
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Initialize a pet database
        PetDatabase database = new PetDatabase(DEFAULT_FILE_NAME);
        
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
            int selection;
            
            try {
                selection = Integer.parseInt(stdin.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number between 1 and 7.");
                continue;
            }
            
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
                    try {
                        database.consoleViewPets(database.consoleSearchPetsByAge());
                    }
                    catch (NumberFormatException e) {}
                    break;
                    
                // Exit and save
                case 7:
                    // Save
                    try {
                        database.save();
                    }
                    catch (IOException e) {
                        // TODO: Error handling
                    }
                    
                    System.out.println("Goodbye!");
                    return;
                
                // Anything unimplemented goes here
                default:
                    System.out.println("Error: Please enter a number between 1 and 7.");
            }
        }
    }
    
    /**
     * Create a new database of pets
     * @param fileName File name of the database
     */
    public PetDatabase(String fileName) {
        this.pets = new ArrayList<>();
        this.fileName = fileName;
        
        // Load the pet database from a file
        File file = new File(fileName);
        
        // Open the file if possible
        FileReader reader = null;
        Scanner readerScanner = null;
        boolean errorOccurred = false;
        
        try {
            reader = new FileReader(file);
            readerScanner = new Scanner(reader);
            
            // Keep adding pets while there are no more pets to add
            while(readerScanner.hasNextLine()) {
                // Add a pet
                try {
                    // Get the next line
                    String line = readerScanner.nextLine();
                    
                    // Only add lines that have non-whitespace characters in them
                    if(!line.isBlank()) {
                        this.addPet(line);
                    }
                }
                
                // Handle exceptions (can occur if a pet's age is invalid or we reached the limit of pets in the database)
                catch (IllegalArgumentException | LimitExceededException e) { //Ignore empty lines in the database file
                    System.out.printf("Error: %s\n", e.getMessage());
                    errorOccurred = true;
                    break;
                }
            }
        }
        
        // If we get an IO Exception of any kind, clear the database as it's probably corrupt
        catch (IOException e) {
            errorOccurred = true;
        }
        
        // Close the file if it was opened
        finally {
            if(readerScanner != null) {
                readerScanner.close();
            }
            if(reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) { }
            }
            
            if(errorOccurred) {
                this.pets.clear();
                System.out.println("Because an error occurred, the pet database will be cleared.");
            }
        }
    }
    
    /**
     * Get the file name of the database
     * @return file name of the database
     */
    public String getFileName() {
        return fileName;
    };
    
    /**
     * Add a pet to the database
     * @param pet pet to add to the database
     * @throws LimitExceededException thrown if the database is full
     */
    public void addPet(Pet pet) throws LimitExceededException {
        if(pets.size() >= MAXIMUM_PETS) {
            throw new LimitExceededException("Database is full.");
        }
        pets.add(pet);
    }
    
    /**
     * Add a pet to the database
     * @param petNameAge name and age of the pet to add to the database separated by spaces
     * @throws LimitExceededException thrown if the database is full
     */
    private void addPet(String petNameAge) throws LimitExceededException {
        // Add the pet
        addPet(Pet.petFromNameAndAge(petNameAge));
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
     * Save the pet database
     * @throws IOException thrown if an IOException occurs on writing
     */
    public void save() throws IOException {
        // Attempt to open for writing
        FileWriter writer = null;
        try {
            File file = new File(fileName);
            writer = new FileWriter(file);
        }
        catch (IOException e) {
            throw e;
        }
        
        // Write each pet
        try {
            for(Pet pet : pets) {
                writer.write(String.format("%s %d\n", pet.getName(), pet.getAge()));
            }
        }
        
        // If an exception occurs, make sure that the file is closed
        finally {
            writer.close();
        }
    }
    
    /**
     * File name of the database
     */
    private String fileName;
    
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
            String entry = stdin.nextLine();
            if(entry.equals("done")) {
                break;
            }
            
            // Add it
            try {
                this.addPet(entry);
                petsAdded++;
            }
            
            // If we got an exception we can handle, log it
            catch (IllegalArgumentException | LimitExceededException e) {
                System.out.printf("Error: %s\n", e.getMessage());
                
                // If we reached the limit, there is no point in continuing
                if(e instanceof LimitExceededException) {
                    break;
                }
            }
        }
        
        // Output how many pets were added if any were added.
        if(petsAdded > 0) {
            System.out.printf("%d pet%s added.\n", petsAdded, (petsAdded != 1) ? "s" : "");
        }
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
        int index = Integer.parseInt(stdin.nextLine());
        
        // Make sure the index is valid
        if(index < 0 || index >= pets.size()) {
            System.out.printf("Error: ID %d does not exist.\n", index);
            return;
        }
        
        // Ask for a name an age to update with
        System.out.print("Enter new name and new age:");
        String nameAge = stdin.nextLine();
        
        // Update the pet
        Pet petToUpdate = getPet(index);
        petToUpdate.setNameAndAge(nameAge);
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
        int index;
        String indexLine = stdin.nextLine();
        
        // Make sure it's valid
        try {
            index = Integer.parseInt(indexLine);
        }
        catch (NumberFormatException e) {
            System.out.printf("Error: %s is not valid input.\n", indexLine);
            return;
        }
        
        // Make sure the index is valid
        if(index < 0 || index >= pets.size()) {
            System.out.printf("Error: ID %d does not exist.\n", index);
            return;
        }
        
        // Carry on
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
        String searchQuery = stdin.nextLine();
        
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
        int searchQuery;
        String indexLine = stdin.nextLine();
        
        // Make sure it's valid
        try {
            searchQuery = Integer.parseInt(indexLine);
        }
        catch (NumberFormatException e) {
            System.out.printf("Error: %s is not valid input.\n", indexLine);
            throw e;
        }
        
        // Return the pets list
        return searchPetsByAge(searchQuery);
    }
}
