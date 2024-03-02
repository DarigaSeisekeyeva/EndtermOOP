import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SecondhandShop {

    public static void main(String[] args) {
        SecondhandShop shop = new SecondhandShop();
        shop.run();
    }

    private List<User> users;
    private List<Item> items;

    public SecondhandShop() {
        users = new ArrayList<>();
        items = new ArrayList<>();
        items.add(new Book("Kolobok", "A. N. Tolstoy", 1965));
        items.add(new Clothing("Maxi Dress", "M", "Silk"));
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void listItems() {
        for (Item item : items) {
            System.out.println(item);
        }
    }

    public void sellItem(int id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == id) {
                items.remove(i);
                System.out.println("Item sold successfully");
                return;
            }
        }
        System.out.println("Item not found");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Registration ID: ");
        int registrationId = scanner.nextInt();
        scanner.nextLine();
        User user = getUserById(registrationId);
        if (user == null) {
            System.out.println("ID not found");
            return;
        }
        if (!user.isLoggedIn()) {
            System.out.print("Your name: ");
            String name = scanner.nextLine();
            System.out.print("Your age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Your address: ");
            String address = scanner.nextLine();

            user.setName(name);
            user.setAge(age);
            user.setAddress(address);

            if (age >= 18) {
                System.out.println("Welcome to the Secondhand Shop!");
            } else {
                System.out.println("You must be 18 years or older to access the shop.");
                return;
            }

            user.setLoggedIn(true);
        }

        while (true) {
            System.out.println("1. List items");
            System.out.println("2. Sell item");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    listItems();
                    break;
                case 2:
                    System.out.print("Enter item ID to sell: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); 
                    sellItem(id);
                    break;
                case 3:
                    System.out.println("Exiting program");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private User getUserById(int id) {
        for (User user : users) {
            if (user.getRegistrationId() == id) {
                return user;
            }
        }
        return null;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}

abstract class Item {
    private static int nextId = 1;

    private int id;
    private String name;

    public Item(String name) {
        this.id = nextId++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Type: " + getType();
    }

    public abstract String getType();
}

class Book extends Item {
    private String author;
    private int year;

    public Book(String name, String author, int year) {
        super(name);
        this.author = author;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public String toString() {
        return super.toString() + ", Author: " + author + ", Year: " + year;
    }
}

class Clothing extends Item {
    private String size;
    private String material;

    public Clothing(String name, String size, String material) {
        super(name);
        this.size = size;
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public String getType() {
        return "Clothing";
    }

    @Override
    public String toString() {
        return super.toString() + ", Size: " + size + ", Material: " + material;
    }
}

class User {
    private int registrationId;
    private String name;
    private int age;
    private String address;
    private boolean loggedIn;

    public User(int registrationId, String name, int age, String address, boolean loggedIn) {
        this.registrationId = registrationId;
        this.name = name;
        this.age = age;
        this.address = address;
        this.loggedIn = loggedIn;
    }

    public int getRegistrationId() {
        return registrationId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}