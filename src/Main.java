public class Main {

    public static void main(String[] args) {
        SecondhandShop shop = new SecondhandShop();
        shop.addUser(new User(1, "Dariga", 25, "street", false));
        shop.run();
    }
}