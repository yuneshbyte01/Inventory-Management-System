import java.util.Scanner;

public class InventoryManagementSystem {

    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display the menu
            System.out.println("====== Inventory Management System ======");
            System.out.println("1. Add Product");
            System.out.println("2. Display All Products");
            System.out.println("3. Search Product");
            System.out.println("4. Update Stock");
            System.out.println("5. Delete Product");
            System.out.println("6. Sort Products");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            // Get user input
            int choice = getValidInteger(scanner);

            switch (choice) {
                case 1:
                    // Add product
                    addProductMenu(productManager, scanner);
                    break;
                case 2:
                    // Display all products
                    productManager.displayAllProducts();
                    break;
                case 3:
                    // Search Product
                    searchProductMenu(productManager, scanner);
                    break;
                case 4:
                    // Update Stock
                    productManager.updateStock();
                    break;
                case 5:
                    // Delete Product
                    productManager.deleteProduct();
                    break;
                case 6:
                    // Sort Products
                    productManager.sortProducts();
                    break;
                case 7:
                    // Exit the program
                    System.out.println("Exiting... Thank you for using the Inventory Management System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option! Please choose a valid number between 1 and 5.");
            }
        }
    }

    private static void addProductMenu(ProductManager productManager, Scanner scanner) {

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Product Price: ");
        double price = getValidDouble(scanner);

        System.out.print("Enter Product Stock: ");
        int stock = getValidInteger(scanner);

        int id = productManager.generateUniqueId();

        Product product = new Product(id, name, price, stock);

        productManager.addProduct(product);
    }

    private static void searchProductMenu(ProductManager productManager, Scanner scanner) {
        while (true) {
            System.out.println("\n*** Search Product By ***");
            System.out.println("1. Search by ID");
            System.out.println("2. Search by Name");
            System.out.println("3. Return to Main Menu");
            System.out.print("Choose an option: ");

            int choiceBy = getValidInteger(scanner);

            switch (choiceBy) {
                case 1:
                    productManager.searchByID();
                    break;
                case 2:
                    productManager.searchByName();
                    break;
                case 3:
                    System.out.println("Returning to Main Menu.");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static int getValidInteger(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }

    private static double getValidDouble(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number for price.");
            }
        }
    }
}
