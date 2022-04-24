package com.company;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class udp_client {
    //creating a datagram socket since we are using UDP
    DatagramSocket datagramSocket;

    DatagramPacket receivingPacket;
    DatagramPacket sendingPacket;

    int subMenuChoice;
    int mainMenuChoice;

    ArrayList<Equipment> equipmentArrayList = new ArrayList<Equipment>();
    ArrayList<Equipment> equipmentSearchArrayList = new ArrayList<Equipment>();

    ArrayList<Member> memberArrayList = new ArrayList<Member>();
    ArrayList<Member> memberSearchArrayList = new ArrayList<Member>();

    ArrayList<Membership> membershipArrayList = new ArrayList<Membership>();
    ArrayList<Membership> membershipSearchArrayList = new ArrayList<Membership>();
    ArrayList<Equipment> equipmentArrayListOfMembership = new ArrayList<Equipment>();

//    ArrayList memberMembershipArrayList = new ArrayList();


    //new byte array
    byte[] data = new byte[2048];

    //making equipment class instance
    Equipment equipment;
    int id;
    int amount;
    String equipmentName;
    boolean equipFound = false;


    Member member;
    String memberName;
    int age;
    int memberShipMonths;


    Membership membership;




    public udp_client() {
    }


    public void createSocketAndListenToIt() {
        Scanner input = new Scanner(System.in);
        //Initializing the client to send to and receive data from server's socket

        //initializing datagram socket
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }


        while (true) {

            System.out.println("\nEnter:\n1 to manage Members\n2 to manage Equipment\n3 to manage Memberships \n4 to Exit Program");
            //Main Menu:
            mainMenuChoice = input.nextInt();
//            System.out.println("Sending choice integer to client");
            sendPacket(mainMenuChoice);

            switch (mainMenuChoice) {


                case 1:

                    //Getting equipment data from the server to show if adding a member (to include in membership)
//                    System.out.println("RECEIVING EQUIPMENT FOR MEMBERSHIP");
                    equipmentArrayList = receivePacket(equipment);

                    //Now the Member Sub Menu
                    while (true) {
                        System.out.println("\nEnter: \n1 to Add a member and his/her membership\n2 to View all members\n3 to search a member\n4 to Exit");
                        subMenuChoice = input.nextInt();
                        //clearing Scanner
                        input.nextLine();

                        switch (subMenuChoice) {


                            //Adding a member and a Membership
                            case 1:
                                //Sending data to server
                                //making an object to add to member array list
                                System.out.print("Write member name: ");
                                memberName = input.nextLine();

                                System.out.print("Write Member Age: ");
                                age = input.nextInt();



                                //Viewing equipment to select from
                                System.out.println("Select equipment with id from the following (-1 to exit):");
                                System.out.println(equipmentArrayList);
                                while(true) {
                                    id = input.nextInt();
                                    for(int i = 0; i<equipmentArrayList.size(); ++i) {
                                        if (id == equipmentArrayList.get(i).getId()) {
                                            equipFound = true;
                                            if (equipFound) {
                                                equipmentArrayListOfMembership.add(equipmentArrayList.get(i));
                                                System.out.println("equipment added to membership");
                                            }
                                        }

                                    }
                                    if (!equipFound) {
                                        System.out.println("equipment not found");
                                    }
                                    if (id==-1) {
                                        break;
                                    }
                                }

//                                System.out.println("selected equipment is: ");
//                                System.out.println(equipmentArrayListOfMembership);


                                System.out.print("Membership for months: ");
                                memberShipMonths = input.nextInt();

//                                System.out.println("membership months are: " +memberShipMonths);
                                member = new Member(memberName, age, memberShipMonths, 1);
                                membership = new Membership(1, memberName, memberShipMonths, equipmentArrayListOfMembership );

//                                memberMembershipArrayList.add(member);
//                                memberMembershipArrayList.add(membership);
//
//                                System.out.println("\nTHE MEMBER IS: ");
//                                System.out.println(memberMembershipArrayList.get(0));
//                                System.out.println("\nTHE MEMBERSHIP IS: ");
//                                System.out.println(memberMembershipArrayList.get(1));



                                //Sending Packet
                                sendPacket(member);
                                sendPacket(membership);

                                break;


                            //Viewing all members
                            case 2:
                                member = new Member("", 0, 0, 2);


                                //Sending object to server
                                sendPacket(member);


                                //Receiving object from server
                                //member is dummy to overload function
                                memberArrayList = receivePacket(member);


                                //Viewing all gym members
                                System.out.println("Members:\n" + memberArrayList);

                                break;


                            //Searching for member
                            case 3:
                                System.out.println("Write the name of member to search");
                                memberName = input.next();
                                member = new Member(memberName, 0, 0, 3);


                                //Sending member object to server
                                sendPacket(member);


                                //Receiving searched member object from server
                                memberSearchArrayList = receivePacket(member);

                                if (memberSearchArrayList.get(0).getName().equals("notfound")) {
                                    System.out.println("member not found");
                                } else {
                                    System.out.println("Searched Member is: ");
                                    System.out.println(memberSearchArrayList);
                                }


                                break;


                            //EXITING
                            case 4:
                                member = new Member("", 0, 0, 4);
                                sendPacket(member);
//                                System.out.println("Exiting...");

                                break;
                            default:
                                System.out.println("Invalid Choice");

                        }
                        if (subMenuChoice == 4) {
                            System.out.println("Exiting from member menu...");
                            break;
                        }

                    }
                    break;
















                case 2:
                    //Now the Equipment Sub-Menu
                    while (true) {
                        System.out.println("\nEnter: \n1 to Add an equipment\n2 to View all equipment\n3 to search an equipment\n4 to Exit");
                        subMenuChoice = input.nextInt();
                        switch (subMenuChoice) {


                            //ADDING
                            case 1:
                                //SENDING DATA TO SERVER
                                //making an object to add to equipment array list
                                System.out.print("Write equipment name: ");
                                equipmentName = input.next();
                                System.out.print("Write equipment id: ");
                                id = input.nextInt();
                                input.nextLine();
                                System.out.print("Enter amount of items: ");
                                amount = input.nextInt();
                                equipment = new Equipment(id, equipmentName, amount, 1);

                                //Sending Packet
                                sendPacket(equipment);

                                break;


                            //VIEWING
                            case 2:
                                equipment = new Equipment(0, "dummy", 0, 2);


                                //Sending object to server
                                sendPacket(equipment);


                                //Receiving object from server
                                equipmentArrayList = receivePacket(equipment);


                                //Viewing all gym equipment
                                System.out.println("Gym Equipment:\n" + equipmentArrayList);

                                break;


                            //Searching
                            case 3:
                                System.out.println("Write the name of equipment to search");
                                equipmentName = input.next();
                                equipment = new Equipment(0, equipmentName, 0, 3);


                                //SENDING OBJECT TO SERVER
                                sendPacket(equipment);


                                //RECEIVING OBJECT FROM SERVER
                                equipmentSearchArrayList = receivePacket(equipment);

                                if (equipmentSearchArrayList.get(0).getName().equals("notfound")) {
                                    System.out.println("object not found");
                                } else {
                                    System.out.println("Searched Equipment is: ");
                                    System.out.println(equipmentSearchArrayList);
                                }


                                break;


                            //EXITING
                            case 4:
                                equipment = new Equipment(0, equipmentName, 0, 4);
                                sendPacket(equipment);
//                                System.out.println("Exiting...");

                                break;
                            default:
                                System.out.println("Invalid Choice");

                        }
                        if (subMenuChoice == 4) {
                            System.out.println("Exiting from equipment menu...");
                            break;
                        }

                    }
                    break;



















                case 3:
                    //Now the Membership Sub-Menu

                    while (true) {
                        System.out.println("\nEnter: \n1 to View all memberships\n2 to search a membership\n3 to Exit");
                        subMenuChoice = input.nextInt();
                        switch (subMenuChoice) {

                            //Viewing Memberships
                            case 1:
                                membership = new Membership(1, "", 0, null);

                                //Sending object to server
                                sendPacket(membership);

                                //Receiving object from server
                                membershipArrayList = receivePacket(membership);

                                //Viewing all memberships
                                System.out.println("All memberships are:\n" + membershipArrayList);

                                break;


                            //Searching for a specific membership
                            case 2:
                                System.out.println("Write the name of person to search for membership");
                                memberName = input.next();
                                membership = new Membership(2,memberName,0, null);


                                //Sending membership object to server
                                sendPacket(membership);


                                //RECEIVING OBJECT FROM SERVER
                                membershipSearchArrayList = receivePacket(membership);

                                if (membershipSearchArrayList.get(0).getName().equals("notfound")) {
                                    System.out.println("object not found");
                                } else {
                                    System.out.println("Searched Membership is: ");
                                    System.out.println(membershipSearchArrayList);
                                }


                                break;


                            //EXITING
                            case 3:
                                membership = new Membership(3, "", 0 , null);
                                sendPacket(membership);
//                                System.out.println("Exiting...");

                                break;
                            default:
                                System.out.println("Invalid Choice");

                        }
                        if (subMenuChoice == 3) {
                            System.out.println("Exiting from membership menu...");
                            break;
                        }

                    }
                    break;
















                case 4:
                    break;

                default:
                    System.out.println("Invalid Input");

            }

            if (mainMenuChoice == 4) break;


        }

    }



















    //To send member or equipment choice integer
    public void sendPacket(int mainMenuChoice) {
        try{



            InetAddress IPAddress = InetAddress.getLocalHost();

            data = new byte[2048];

            data = (mainMenuChoice + "").getBytes();

            DatagramPacket sendingPacket = new DatagramPacket(data, data.length, IPAddress, 8240);
            datagramSocket.send(sendingPacket);








        }catch (UnknownHostException e) {
            System.out.println("UnknownHostException in sendPacket int");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException in sendPacket int");
            e.printStackTrace();
        }




    }




















































//    //To send member packet
    public void sendPacket(Member member) {
        try {

            InetAddress IPAddress = InetAddress.getLocalHost();

            //converting object into bytes to send over the udp connection because it only accepts data in byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(member);
            data = byteArrayOutputStream.toByteArray();


            //giving the datagramPacket data, data length, IP address of the server and its port to send data to the server
            DatagramPacket sendingPacket = new DatagramPacket(data, data.length, IPAddress, 8240);
            datagramSocket.send(sendingPacket);


        } catch (UnknownHostException e) {
            System.out.println("Host Unknown");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception Occurred in sendPacket");
            e.printStackTrace();
        }


    }




    //To receive member array packet
    public ArrayList<Member> receivePacket(Member dummyMember) {


        try {

            data = new byte[2048];

            //packet to receive data
            receivingPacket = new DatagramPacket(data, data.length);

            //receiving data through packet
            datagramSocket.receive(receivingPacket);

            //storing data in a byte stream because data is of byte stream form
            data = receivingPacket.getData();

            //converting byte data into object data by streams.
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

            //converting byte array input stream into object input stream
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            return (ArrayList<Member>) objectInputStream.readObject();


        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException in receivingPacket");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException in receivingPacket");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound Exception in receivingPacket");
            e.printStackTrace();
        }

        return null;


    }






















//    membership sending and receiving

    //To send membership packet
    public void sendPacket(Membership membership) {
        try {

            InetAddress IPAddress = InetAddress.getLocalHost();

            //converting object into bytes to send over the udp connection because it only accepts data in byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(membership);
            data = byteArrayOutputStream.toByteArray();


            //giving the datagramPacket data, data length, IP address of the server and its port to send data to the server
            sendingPacket = new DatagramPacket(data, data.length, IPAddress, 8240);
            datagramSocket.send(sendingPacket);


        } catch (UnknownHostException e) {
            System.out.println("Host Unknown");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception Occurred in sendPacket");
            e.printStackTrace();
        }


    }


    //To receive membership array packet
    public ArrayList<Membership> receivePacket(Membership dummyMembership) {


        try {

            data = new byte[2048];

            //packet to receive data
            receivingPacket = new DatagramPacket(data, data.length);

            //receiving data through packet
            datagramSocket.receive(receivingPacket);

            //storing data in a byte stream because data is of byte stream form
            data = receivingPacket.getData();

            //converting byte data into object data by streams.
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

            //converting byte array input stream into object input stream
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            return (ArrayList<Membership>) objectInputStream.readObject();


        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException in membership receivingPacket");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException in membership receivingPacket");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound Exception in membership receivingPacket");
            e.printStackTrace();
        }

        return null;


    }

























//      equipment sending and receiving
    //To send equipment packet
    public void sendPacket(Equipment equipment) {
        try {

            InetAddress IPAddress = InetAddress.getLocalHost();

            //converting object into bytes to send over the udp connection because it only accepts data in byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(equipment);
            data = byteArrayOutputStream.toByteArray();


            //giving the datagramPacket data, data length, IP address of the server and its port to send data to the server
            sendingPacket = new DatagramPacket(data, data.length, IPAddress, 8240);
            datagramSocket.send(sendingPacket);


        } catch (UnknownHostException e) {
            System.out.println("Host Unknown");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception Occurred in sendPacket");
            e.printStackTrace();
        }


    }


    //To receive equipment array packet
    public ArrayList<Equipment> receivePacket(Equipment dummyEquipment) {


        try {

            data = new byte[2048];

            //packet to receive data
            receivingPacket = new DatagramPacket(data, data.length);

            //receiving data through packet
            datagramSocket.receive(receivingPacket);

            //storing data in a byte stream because data is of byte stream form
            data = receivingPacket.getData();

            //converting byte data into object data by streams.
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

            //converting byte array input stream into object input stream
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            return (ArrayList<Equipment>) objectInputStream.readObject();


        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException in receivingPacket");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException in receivingPacket");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound Exception in receivingPacket");
            e.printStackTrace();
        }

        return null;


    }














    //Main function call
    public static void main(String[] args) {
        udp_client client = new udp_client();
        client.createSocketAndListenToIt();
    }
}
