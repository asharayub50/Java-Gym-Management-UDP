package com.company;

import java.io.*;
import java.util.ArrayList;

public class Equipment implements Serializable {

    private int statusCode;
    private int id;
    private int amount;
    private String name;

    //constructor
    public Equipment(int id, String name, int amount, int statusCode) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.statusCode = statusCode;
    }


    //setters and getters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getStatusCode() {
        return statusCode;
    }

    //equipment object's data in a String
    public String toString() {
        return "\nId = " + getId() + " Name = " + getName() + " Amount =" + getAmount();
    }


    public static ArrayList<Equipment> readObjectFromFile() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("equipment.txt"));
            return (ArrayList<Equipment>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("\nEquipment File is not generated yet");
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;

    }


    public static void writeObjectToFile(ArrayList<Equipment> equipmentArrayList) {

        ObjectOutputStream objectOutputStream = null;

        try {

            objectOutputStream = new ObjectOutputStream(new FileOutputStream("equipment.txt"));
            objectOutputStream.writeObject(equipmentArrayList);

        } catch (IOException e) {
            System.out.println("IO Exception while opening file");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                    // closing the file even if it contains data or not
                }

            } catch (IOException e) {
                System.out.println("IO Exception while closing file");
            }
        }
    }
}









