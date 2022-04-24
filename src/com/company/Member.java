package com.company;

import java.io.*;
import java.util.ArrayList;

public class Member implements Serializable{
    int statusCode;
    String name;
    int age;
    int memberShipMonths;


    public Member(String name, int age, int memberShipMonths, int statusCode) {

        this.name = name;
        this.age = age;
        this.memberShipMonths = memberShipMonths;
        this.statusCode = statusCode;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMemberShipMonths() {
        return memberShipMonths;
    }

    public void setMemberShipMonths(int memberShipMonths) {
        this.memberShipMonths = memberShipMonths;
    }

    //member object's data in a String
    public String toString() {
        return "\nName = " + getName() + " Age = " + getAge() + " Membership Months = " + getMemberShipMonths();
    }


    public static ArrayList<Member> readObjectFromFile() {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("member.txt"));
            return (ArrayList<Member>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            System.out.println("\nMember File is not generated yet");
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


    public static void writeObjectToFile(ArrayList<Member> memberArrayList) {

        ObjectOutputStream objectOutputStream = null;

        try {

            objectOutputStream = new ObjectOutputStream(new FileOutputStream("member.txt"));
            objectOutputStream.writeObject(memberArrayList);

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
