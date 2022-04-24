package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;


public class udp_server {
    //creating a datagram socket since we are using UDP
    DatagramSocket datagramSocket = null;
    DatagramPacket sendingPacket;
    DatagramPacket receivingPacket;
    ArrayList<Equipment> equipmentArrayList = new ArrayList<Equipment>();
    ArrayList<Equipment> equipmentSearchArrayList = new ArrayList<Equipment>();
    ArrayList<Member> memberArrayList = new ArrayList<Member>();
    ArrayList<Member> memberSearchArrayList = new ArrayList<Member>();
    ArrayList<Membership> membershipArrayList = new ArrayList<Membership>();
    ArrayList<Membership> membershipSearchArrayList = new ArrayList<Membership>();
    byte[] data = new byte[2048];
    Equipment equipment;
    Member member;
    Membership membership;

    int memberEquipmentSwitch;


    public void createSocketAndListenToIt() {

        //initializing datagram socket
        try {
            datagramSocket = new DatagramSocket(8240);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //reading data from EQUIPMENT FILE and storing it in equipment arraylist
        equipmentArrayList = Equipment.readObjectFromFile();
        System.out.print("\nEquipment ArrayList is read: ");
        System.out.println(equipmentArrayList);

        //if equipment arraylist is null, re initializing it to avoid null pointer exception
        if (equipmentArrayList == null) {
            equipmentArrayList = new ArrayList<Equipment>();
        }




        //reading data from MEMBER FILE and storing it in member arraylist
        memberArrayList = Member.readObjectFromFile();
        System.out.print("\nMember ArrayList is read: ");
        System.out.println(memberArrayList);

        //if member arraylist is null, re initializing it to avoid null pointer exception
        if (memberArrayList == null) {
            memberArrayList = new ArrayList<Member>();
        }



        //reading data from MEMBERSHIP FILE and storing it in member arraylist
        membershipArrayList = Membership.readObjectFromFile();
        System.out.print("\nMembership ArrayList is read: ");
        System.out.println(membershipArrayList);

        //if member arraylist is null, re initializing it to avoid null pointer exception
        if (membershipArrayList == null) {
            membershipArrayList = new ArrayList<Membership>();
        }










        //if arraylist is null, re initializing it to avoid null pointer exception
        if (equipmentArrayList == null) {
            equipmentArrayList = new ArrayList<Equipment>();
        }


        while (true) {
            System.out.println("\nReceiving Choice Integer from client");
            memberEquipmentSwitch = receivePacket(memberEquipmentSwitch);
//            System.out.println("INTEGER RECEIVED IS: " +memberEquipmentSwitch);

            switch (memberEquipmentSwitch) {

                //Member Sub-Menu
                case 1:
                    System.out.println("Now in member sub menu");

                    //sending equipment arraylist to client to help the member add a membership
                    System.out.println("SENDING EQUIPMENT FOR MEMBERSHIP");
                    sendPacket(equipmentArrayList, equipment);

                    while (true) {

                        //RECEIVING DATA FROM CLIENT
                        member = receivePacket(member);


                        //Checking what function the member status code is intended to do
                        switch (member.getStatusCode()) {

                            //ADDING a member
                            case 1:
                                boolean memberExists = false;


                                System.out.println("Adding Member and Membership");
                                membership = receivePacket(membership);



                                //Checking if the name already exists
                                for (int i = 0; i<memberArrayList.size(); ++i) {
                                    if (member.getName().equals(memberArrayList.get(i).getName())) {
                                        memberExists = true;
                                        break;
                                    }
                                }
//                                System.out.println( "member exists is: " +memberExists);

                                //send a packet to server to acknowledge that the member exists already

                                if (memberExists) {
                                    System.out.println("member already exists");
                                }
                                else {

                                    //adding member and membership to their respective arraylists
                                    memberArrayList.add(member);
                                    membershipArrayList.add(membership);

                                    //writing member and membership to their respective files
                                    Member.writeObjectToFile(memberArrayList);
                                    Membership.writeObjectToFile(membershipArrayList);

                                }





                                break;


                            //Viewing all members
                            case 2:
                                System.out.println("\nViewing All Members\n\n");
                                sendPacket(memberArrayList, member);
                                break;


                            //Searching for a member
                            case 3:
                                System.out.println("\nSearching for the member");
                                boolean exists = false;
                                Member searchedMember = new Member("notfound", 0,0,0);
                                for (Member value : memberArrayList) {
                                    if (value.getName().equals(member.getName())) {
                                        exists = true;
                                        searchedMember = value;
                                    }
                                }
                                if (exists) {
                                    System.out.println("Item exists\n\n");
                                } else {
                                    System.out.println("Item does not exist");
                                }
                                //Adding searched member
                                memberSearchArrayList.add(searchedMember);
                                //Sending searched member object to client
                                sendPacket(memberSearchArrayList, member);
                                //Emptying member search arraylist
                                memberSearchArrayList.remove(searchedMember);
                                break;


                            case 4:
                                System.out.println("Exiting...");
                                break;


                            default:
                                System.out.println("Invalid Choice");


                        }

                        if (member.getStatusCode() == 4) {
                            System.out.println("exiting from member menu");
                            break;
                        }


                    }
                    break;














                    //Equipment Sub-Menu
                case 2:
                    System.out.println("Now in equipment sub menu");
                    while (true) {

                        //RECEIVING DATA FROM CLIENT
                        equipment = receivePacket(equipment);


                        //Checking what function the status code is intended to do
                        switch (equipment.getStatusCode()) {

                            //ADDING
                            case 1:

                                boolean equipmentExists= false;

                                for(int i = 0; i<equipmentArrayList.size(); ++i) {
                                    if (equipment.getName().equals(equipmentArrayList.get(i).getName())) {
//                                        System.out.println("Equipment already exists");
                                        equipmentExists = true;
                                    }
                                }

                                for(int i = 0; i<equipmentArrayList.size(); ++i) {
                                    if (equipment.getId() == (equipmentArrayList.get(i).getId())) {
                                        equipmentExists = true;
                                    }
                                }

                                //send a packet to server to acknowledge that the item exists already

                                if (equipmentExists) {
                                    System.out.println("Equipment or equipment id already exists");
                                }else {
                                    //adding to arraylist
                                    equipmentArrayList.add(equipment);
                                    //writing to file
                                    Equipment.writeObjectToFile(equipmentArrayList);
                                }


                                break;


                            //VIEWING
                            case 2:
                                System.out.println("\nViewing All Equipments\n\n");
                                sendPacket(equipmentArrayList, equipment);
                                break;


                            //SEARCHING
                            case 3:
                                System.out.println("\nSearching for the equipment");
                                boolean exists = false;
                                Equipment searchedEquipment = new Equipment(0, "notfound", 0, 0);
                                for (int i = 0; i < equipmentArrayList.size(); ++i) {
                                    if (equipmentArrayList.get(i).getName().equals(equipment.getName())) {
                                        exists = true;
                                        searchedEquipment = equipmentArrayList.get(i);
                                    }
                                }
                                if (exists) {
                                    System.out.println("Item exists\n\n");
                                } else {
                                    System.out.println("Item does not exist");
                                }
                                //Adding searched equipment
                                equipmentSearchArrayList.add(searchedEquipment);
                                //Sending searched object to client
                                sendPacket(equipmentSearchArrayList, equipment);
                                //Emptying search arraylist
                                equipmentSearchArrayList.remove(searchedEquipment);
                                break;


                            case 4:
//                                System.out.println("Exiting...");
                                break;


                            default:
                                System.out.println("Invalid Choice");


                        }

                        if (equipment.getStatusCode() == 4) {
                            System.out.println("Exiting from equipment menu");
                        break;
                        }


                    }
                    break;















                    //Membership Sub-Menu
                case 3:
                    System.out.println("Now in membership sub menu");
                    while (true) {

                        //Receiving membership data from client
                        membership = receivePacket(membership);


                        //Checking what function the status code is intended to do
                        switch (membership.getStatusCode()) {

                            //Viewing membership data
                            case 1:
                                System.out.println("\nViewing All Memberships\n\n");
                                sendPacket(membershipArrayList, membership);
                                break;


                            //Searching for a membership
                            case 2:
                                System.out.println("\nSearching for a membership");
                                boolean exists = false;
                                Membership searchedMembership = new Membership(0, "notfound", 0, null);
                                for (int i = 0; i < membershipArrayList.size(); ++i) {
                                    if (membershipArrayList.get(i).getName().equals(membership.getName())) {
                                        exists = true;
                                        searchedMembership = membershipArrayList.get(i);
                                    }
                                }
                                if (exists) {
                                    System.out.println("membesrhip exists\n\n");
                                } else {
                                    System.out.println("membership does not exist");
                                }
                                //Adding searched equipment
                                membershipSearchArrayList.add(searchedMembership);
                                //Sending searched object to client
                                sendPacket(membershipSearchArrayList, membership);
                                //Emptying search arraylist
                                membershipSearchArrayList.remove(searchedMembership);
                                break;


                            case 3:
//                                System.out.println("Exiting...");
                                break;


                            default:
                                System.out.println("Invalid Choice");


                        }

                        if (membership.getStatusCode() == 3) {
                            System.out.println("Exiting from membership menu");
                            break;
                        }


                    }
                    break;

















                //Exiting
                case 4:


                    break;


                default:
                    System.out.println("Invalid Choice");


            }



        }



    }










    //To receive member or equipment choice integer
    public int receivePacket(int dummyX) {

        try {

            data = new byte[2048];

            receivingPacket = new DatagramPacket(data, data.length);
            datagramSocket.receive(receivingPacket);


            String receivedString = new String(receivingPacket.getData());
            receivedString = receivedString.trim();


//            System.out.println("RECEIVED STRING IS: "+receivedString);

            int a = Integer.parseInt(receivedString);

//            System.out.println("RECEIVED INTEGER IS: " +a);

            return Integer.parseInt(receivedString);




        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;

    }














    //sending and receiving members
    //to send a member array packet
    public void sendPacket(ArrayList<Member> memberArrayList, Member dummyMember) {
        try {

            data = new byte[2048];

            InetAddress IPAddress = InetAddress.getLocalHost();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(memberArrayList);

            data = byteArrayOutputStream.toByteArray();
            sendingPacket = new DatagramPacket(data, data.length, IPAddress, receivingPacket.getPort());
            datagramSocket.send(sendingPacket);

        } catch (IOException e) {
            System.out.println("IOException in sendPacket member");
            e.printStackTrace();
        }
    }





    //To receive a member packet
    public Member receivePacket(Member dummyMember) {

        try {


            data = new byte[2048];

            //Receiving packet
            receivingPacket = new DatagramPacket(data, data.length);
            datagramSocket.receive(receivingPacket);

            //Converting packet from byte to object
            data = receivingPacket.getData();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Member receivedMember = (Member) objectInputStream.readObject();

            System.out.println("\nInitializing...\nMember object received = " + member);

            return receivedMember;

        } catch (IOException e) {
            System.out.println("IOException in receivingPacket member");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in receivingPacket member");
            e.printStackTrace();
        }
        return null;
    }
























    //sending and receiving membership packet
    //to send a membership array packet
    public void sendPacket(ArrayList<Membership> membershipArrayList, Membership dummyMembership) {
        try {

            data = new byte[2048];

            InetAddress IPAddress = InetAddress.getLocalHost();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(membershipArrayList);

            data = byteArrayOutputStream.toByteArray();
            sendingPacket = new DatagramPacket(data, data.length, IPAddress, receivingPacket.getPort());
            datagramSocket.send(sendingPacket);

        } catch (IOException e) {
            System.out.println("IOException in sendPacket membership");
            e.printStackTrace();
        }
    }





    //To receive a membership packet
    public Membership receivePacket(Membership dummyMembership) {

        try {

            data = new byte[2048];

            //Receiving packet
            receivingPacket = new DatagramPacket(data, data.length);
            datagramSocket.receive(receivingPacket);

            //Converting packet from byte to object
            data = receivingPacket.getData();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Membership receivedMembership = (Membership) objectInputStream.readObject();

            System.out.println("\nInitializing...\nMembership object received = " + membership);

            return receivedMembership;

        } catch (IOException e) {
            System.out.println("IOException in receivingPacket membership");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in receivingPacket membership");
            e.printStackTrace();
        }
        return null;
    }



















    //sending and receiving equipment
    //To send an equipment array packet
    public void sendPacket(ArrayList<Equipment> equipmentArrayList, Equipment dummyEquipment) {
        try {

            data = new byte[2048];


            InetAddress IPAddress = InetAddress.getLocalHost();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(equipmentArrayList);

            data = byteArrayOutputStream.toByteArray();
            sendingPacket = new DatagramPacket(data, data.length, IPAddress, receivingPacket.getPort());
            datagramSocket.send(sendingPacket);

        } catch (IOException e) {
            System.out.println("IOException in sendPacket equipment");
            e.printStackTrace();
        }
    }




    //To receive an equipment packet
    public Equipment receivePacket(Equipment dummyEquipment) {

        try {

            data = new byte[2048];


            //Receiving packet
            receivingPacket = new DatagramPacket(data, data.length);
            datagramSocket.receive(receivingPacket);

            //Converting packet from byte to object
            data = receivingPacket.getData();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Equipment receivedEquipment = (Equipment) objectInputStream.readObject();

            System.out.println("\nInitializing...\nEquipment object received = " + equipment);

            return receivedEquipment;

        } catch (IOException e) {
            System.out.println("IOException in receivingPacket equipment");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException in receivingPacket equipment");
            e.printStackTrace();
        }
        return null;
    }








    //Main function call
    public static void main(String[] args) {
        udp_server server = new udp_server();
        server.createSocketAndListenToIt();
    }
}








