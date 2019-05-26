/*
 * Pet.java
 * Paul Whitcomb <whitcomp@csp.edu>
 * CSC 422, Spring 2019
 * Concordia University, St. Paul
 * Week 1 Assignment
 */
package com.whitcomp.petdatabase;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Pet class for holding pet data
 * @author Paul
 */
public class Pet {
    /**
     * Minimum age of pets
     */
    static final int MINIMUM_AGE = 1;
    
    /**
     * Maximum age of pets
     */
    static final int MAXIMUM_AGE = 20;
    
    /**
     * Get the current age of the pet
     * @return Age of the pet in years
     */
    public final int getAge() {
        return age;
    }
    
    /**
     * Set the age of the pet
     * @param age New age of the pet in years. Must be between MINIMUM_AGE and MAXIMUM_AGE
     */
    public final void setAge(int age) {
        // Make sure the age is valid
        if(age < MINIMUM_AGE || age > MAXIMUM_AGE) {
            throw new IllegalArgumentException(String.format("%d is not a valid age.", age));
        }
        
        this.age = age;
    }
    
    /**
     * Get the current name of the pet
     * @return name of the pet
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Set the name of the pet
     * @param name New name of the pet
     */
    public final void setName(String name) {
        this.name = name;
    }
    
    /**
     * Constructor for a pet
     * @param name Name of the pet
     * @param age Age of the pet in years. Must be between MINIMUM_AGE and MAXIMUM_AGE
     */
    public Pet(String name, int age) {
        this.setAge(age);
        this.setName(name);
    }
    
    /**
     * Constructor for pet
     */
    private Pet() {}
    
    /**
     * Constructor for a pet. This is internally used with input from standard input in the main function.
     * @param nameAge Name and age of the pet separated with a space. Age must be between MINIMUM_AGE and MAXIMUM_AGE.
     * @return pet
     */
    static Pet petFromNameAndAge(String nameAge) {
        Pet pet = new Pet();
        pet.setNameAndAge(nameAge);
        return pet;
    }
    
    /**
     * Set pet data. This is internally used with input from standard input in the main function.
     * @param nameAge Name and age of the pet separated with a space. Age must be between MINIMUM_AGE and MAXIMUM_AGE.
     */
    void setNameAndAge(String nameAge) {
        // Interrogate it with a Scanner
        Scanner scanner = new Scanner(nameAge);
        
        // Get the name and age
        String newName;
        int newAge;
        try {
            newName = scanner.next();
            newAge = scanner.nextInt();
        }
        
        // If not, throw an IllegalArgumentException
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException(String.format("%s is not valid input.", nameAge));
        }
        
        // Make sure there's nothing else to add
        if(scanner.hasNext()) {
            throw new IllegalArgumentException(String.format("%s is not valid input.", nameAge));
        }
        
        // Set the new values
        this.setAge(newAge);
        this.setName(newName);
    }
    
    /**
     * Name of the pet
     */
    private String name;
    
    /**
     * Age of the pet in years
     */
    private int age;
}
