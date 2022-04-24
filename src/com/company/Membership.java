package com.company;

import java.io.*;
import java.util.ArrayList;

public class Membership implements  Serializable{

    int statusCode;
    String name;
    int memberShipMonths;
    ArrayList<Equipment> equipmentArrayList;


    public Membership(int statusCode, String name, int memberShipMonths, ArrayList<Equipment> equipmentArrayList) {
        this.statusCode = statusCode;
        this.name = name;
        this.memberShipMonths = memberShipMonths;
        this.equipmentArrayList = equipmentArrayList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberShipMonths() {
        return memberShipMonths;
    }

    public void setMemberShipMonths(int memberShipMonths) {
        this.memberShipMonths = memberShipMonths;
    }

    public ArrayList<Equipment> getEquipmentArrayList() {
        return equipmentArrayList;
    }

    public void setEquipmentArrayList(ArrayList<Equipment> equipmentArrayList) {
        this.equipmentArrayList = equipmentArrayList;
    }




    //memberShip object's data in a String
    public String toString() {
        return "\nName = " + getName() + " Membership Months = " + getMemberShipMonths() + " Equipment Used: " +equipmentArrayList;
    }






    public static ArrayList<Membership> readObjectFromFile() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("membership.txt"));
            return (ArrayList<Membership>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("\nMembership File is not generated yet");
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


    public static void writeObjectToFile(ArrayList<Membership> memberShipArrayList) {

        ObjectOutputStream objectOutputStream = null;

        try {

            objectOutputStream = new ObjectOutputStream(new FileOutputStream("membership.txt"));
            objectOutputStream.writeObject(memberShipArrayList);

        } catch (IOException e) {
            System.out.println("IO Exception while opening membership file");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                    // closing the file even if it contains data or not
                }

            } catch (IOException e) {
                System.out.println("IO Exception while closing membership file");
            }
        }
    }






}
