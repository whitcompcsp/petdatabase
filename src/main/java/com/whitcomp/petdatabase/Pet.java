/*
 * Pet.java
 * Paul Whitcomb <whitcomp@csp.edu>
 * CSC 422, Spring 2019
 * Concordia University, St. Paul
 * Week 1 Assignment
 */
package com.whitcomp.petdatabase;

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
    public int getAge() {
        return age;
    }
    
    /**
     * Set the age of the pet
     * @param age New age of the pet in years
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * Get the current name of the pet
     * @return name of the pet
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of the pet
     * @param name New name of the pet
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Constructor for a pet
     * @param name Name of the pet
     * @param age Age of the pet in years. Must be between MINIMUM_AGE and MAXIMUM_AGE
     */
    public Pet(String name, int age) {
        // Make sure the age is valid
        if(age < MINIMUM_AGE || age > MAXIMUM_AGE) {
            throw new IllegalArgumentException(String.format("%d is not a valid age.", age));
        }
        
        this.age = age;
        this.name = name;
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
