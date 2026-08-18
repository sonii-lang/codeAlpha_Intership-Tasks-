import java.util.*;
import java.io.*;

public class HotelReservationsSystem {
    // COLORS FOR TERMINAL
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String BRIGHT = "\u001B[1m";
    public static final String CYAN = "\u001B[96m"; // Light Blue
    public static final String GREEN = "\u001B[92m";
    public static final String YELLOW = "\u001B[93m";
    public static final String RED = "\u001B[91m";

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int bookingIdCounter = 1;
    
    public static void main(String[] args) {
        // Sample rooms
        rooms.add(new Room(101, "Standard", 2000, true));
        rooms.add(new Room(102, "Deluxe", 3500, true));
        rooms.add(new Room(103, "Suite", 5000, true));
        rooms.add(new Room(104, "Standard", 2200, true));
        rooms.add(new Room(105, "Deluxe", 3800, true));
        
        int choice;
        do {
            menu();
            System.out.print(BRIGHT + ">> Enter Your Choice: " + RESET);
            choice = sc.nextInt();
            sc.nextLine(); // Buffer clear

            switch(choice){
                case 1: searchRoom(); break;
                case 2: bookRoom(); break;
                case 3: viewRooms(); break;
                case 4: viewBookings(); break;
                case 5: cancelBooking(); break;
                case 6: revenueReport(); break;
                case 7: System.out.println(BRIGHT + GREEN + "\n>>> Data Saved. Thank You! <<<" + RESET); break;
                default: System.out.println(BRIGHT + RED + "\n>>> Invalid Choice! <<<" + RESET);
            }
            System.out.println(CYAN + "\n--------------------------------------" + RESET);
        } while(choice != 7);
        
        sc.close();
    }

    static void menu(){
        System.out.println(BOLD + BRIGHT + CYAN + "\n---------------- MENU ----------------" + RESET);
        System.out.println(BOLD + CYAN + "1. [SEARCH] " + RESET + "Search Room by Type or Price");
        System.out.println(BOLD + CYAN + "2. [BOOK]   " + RESET + "Book a Room with Payment");
        System.out.println(BOLD + CYAN + "3. [VIEW]   " + RESET + "View Available Rooms");
        System.out.println(BOLD + CYAN + "4. [DETAILS]" + RESET + "View All Bookings");
        System.out.println(BOLD + CYAN + "5. [CANCEL] " + RESET + "Cancel Booking + Refund");
        System.out.println(BOLD + CYAN + "6. [REPORT] " + RESET + "Revenue Report");
        System.out.println(BOLD + CYAN + "7. [EXIT]   " + RESET + "Exit and Save Data");
        System.out.println(BOLD + BRIGHT + CYAN + "--------------------------------------" + RESET);
    }

    static void searchRoom() {
        System.out.print(BRIGHT + "Enter Room Type or Max Price: " + RESET);
        String input = sc.nextLine();
        System.out.println(BRIGHT + YELLOW + "\n--- Search Results ---" + RESET);
        for(Room r : rooms){
            if(r.available && (r.type.equalsIgnoreCase(input) || r.price <= Double.parseDouble(input))){
                System.out.println("Room " + r.id + " | " + r.type + " | Rs." + r.price);
            }
        }
    }

    static void bookRoom() {
        viewRooms();
        System.out.print(BRIGHT + "Enter Room ID to Book: " + RESET);
        int id = sc.nextInt(); sc.nextLine();
        System.out.print(BRIGHT + "Enter Name: " + RESET);
        String name = sc.nextLine();
        System.out.print(BRIGHT + "Enter Phone: " + RESET);
        String phone = sc.nextLine();
        System.out.print(BRIGHT + "Enter Days: " + RESET);
        int days = sc.nextInt();
        
        for(Room r : rooms){
            if(r.id == id && r.available){
                r.available = false;
                Booking b = new Booking(bookingIdCounter++, name, phone, r, days);
                bookings.add(b);
                System.out.println(BRIGHT + GREEN + "\n>>> Booking Successful! <<<" + RESET);
                System.out.println("Booking ID: " + b.id + " | Total: Rs." + b.total);
                return;
            }
        }
        System.out.println(BRIGHT + RED + ">>> Room Not Available! <<<" + RESET);
    }

    static void viewRooms() {
        System.out.println(BRIGHT + YELLOW + "\n--- Available Rooms ---" + RESET);
        for(Room r : rooms){
            if(r.available){
                System.out.println("Room " + r.id + " | " + r.type + " | Rs." + r.price);
            }
        }
    }

    static void viewBookings() {
        if(bookings.isEmpty()){
            System.out.println(BRIGHT + YELLOW + "\n>>> No Bookings Yet! <<<" + RESET);
            return;
        }
        System.out.println(BRIGHT + YELLOW + "\n--- All Bookings ---" + RESET);
        for(Booking b : bookings){
            System.out.println("ID: " + b.id + " | Name: " + b.name + " | Room: " + b.room.id + " | Days: " + b.days + " | Total: Rs." + b.total);
        }
    }

    static void cancelBooking() {
        System.out.print(BRIGHT + "Enter Booking ID to Cancel: " + RESET);
        int id = sc.nextInt();
        for(Booking b : bookings){
            if(b.id == id){
                b.room.available = true;
                bookings.remove(b);
                System.out.println(BRIGHT + GREEN + ">>> Booking Cancelled. Refund: Rs." + b.total + " <<<" + RESET);
                return;
            }
        }
        System.out.println(BRIGHT + RED + ">>> Booking ID Not Found! <<<" + RESET);
    }

    static void revenueReport() {
        double total = 0;
        for(Booking b : bookings) total += b.total;
        System.out.println(BRIGHT + GREEN + "\n>>> Total Revenue: Rs." + total + " <<<" + RESET);
        System.out.println(BRIGHT + GREEN + ">>> Total Bookings: " + bookings.size() + " <<<" + RESET);
    }
}

class Room {
    int id; 
    String type; 
    double price; 
    boolean available;
    Room(int id, String type, double price, boolean available){
        this.id=id; this.type=type; this.price=price; this.available=available;
    }
}

class Booking {
    int id; 
    String name; 
    String phone; 
    Room room; 
    int days; 
    double total;
    Booking(int id, String name, String phone, Room room, int days){
        this.id=id; this.name=name; this.phone=phone; this.room=room; 
        this.days=days; this.total=room.price*days;
    }
}