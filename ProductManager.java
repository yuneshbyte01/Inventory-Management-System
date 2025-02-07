import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;

public class ProductManager {

    private ArrayList<Product> productList;
    private int lastProductId;
    static Scanner scanner =new Scanner(System.in);

    ProductManager() {
        productList = new ArrayList<>();
        loadProducts();
    }

    // Getter
    public ArrayList<Product> getProductList() {
        return productList;
    }

    // Setter
    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    // Generate Unique ID
    public int generateUniqueId() {
        int lastId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    int id = Integer.parseInt(data[0]);
                    if (id > lastId) {
                        lastId = id;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No previous product file found. Starting fresh.");
        }

        for (Product product : productList) {
            if (product.getId() > lastId) {
                lastId = product.getId();
            }
        }

        return lastId + 1;
    }

    // Add product
    public void addProduct(Product product) {
        product.setId(generateUniqueId());
        productList.add(product);
        saveProducts();
        System.out.println("Product Added Successfully.\n");
    }

    // Display all Products
    public void displayAllProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products found!\n");
            return;
        }

        System.out.println("Product List:\n");
        for (Product product : productList) {
            product.displayProduct();
        }
    }

    // Save product in file
    public void saveProducts(){
        try {
            FileWriter fileWriter = new FileWriter("product.txt");
            for (Product product : productList) {
                fileWriter.write(product.getId() + ","
                        + product.getName() + ","
                        + product.getPrice() + ","
                        + product.getStock() + "\n");
                // Debugging: print the product being saved
                System.out.println("Saving Product: ID=" + product.getId() + ", Name=" + product.getName());
            }
            System.out.println("Products saved successfully to file.");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    // Load product from file
    public void loadProducts() {
        File file = new File("product.txt");
        if (!file.exists() || file.length() == 0) {
            System.out.println("No existing products to load.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(",");
                int id = Integer.parseInt(productData[0]);
                String name = productData[1];
                double price = Double.parseDouble(productData[2]);
                int stock = Integer.parseInt(productData[3]);

                boolean duplicateFound = false;
                for (Product existingProduct : productList) {
                    if (existingProduct.getId() == id) {
                        System.out.println("Duplicate product ID found. Skipping product: " + name);
                        duplicateFound = true;
                        break;
                    }
                }

                if (!duplicateFound) {
                    Product product = new Product(id, name, price, stock);
                    productList.add(product);

                    if (id > lastProductId) {
                        lastProductId = id;
                    }
                }
            }
            System.out.println("Products loaded successfully from file.");
        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    // Method Searches by ProductID
    public void searchByID() {
        if (productList.isEmpty()) {
            System.out.println("No products found!\n");
            return;
        }

        System.out.print("Enter the ID to search: ");
        int id = getValidInteger(scanner);

        boolean found = false;
        for (Product product : productList) {
            if (id == product.getId()) {
                product.displayProduct();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Product not found.\n");
        }
    }

    // Search by ProductName
    public void searchByName() {
        if (productList.isEmpty()) {
            System.out.println("No products found!\n");
            return;
        }

        System.out.print("Enter the name to search: ");
        String name = scanner.nextLine();

        boolean found = false;

        for (Product product : productList) {
            if (name.equalsIgnoreCase(product.getName())) {
                product.displayProduct();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Product not found.\n");
        }
    }

    // Update Stock
    public void updateStock() {
        if (productList.isEmpty()) {
            System.out.println("No products found!\n");
            return;
        }

        System.out.print("Enter the ID to search: ");
        int id = getValidInteger(scanner);

        for (Product product : productList) {
            if (id == product.getId()) {
                product.displayProduct();

                System.out.print("Enter the new stock to update: ");
                int newStock = getValidInteger(scanner);

                product.setStock(newStock);
                saveProducts();
                System.out.println("Stock updated successfully.");
            }
        }
    }

    // Delete Products
    public void deleteProduct() {
        if (productList.isEmpty()) {
            System.out.println("No products found!\n");
            return;
        }

        System.out.print("Enter the ID to delete: ");
        int id = getValidInteger(scanner);

        Product productToDelete = null;

        for (Product product : productList) {
            if (product.getId() == id) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete != null) {
            System.out.print("Are you sure you want to delete the product (ID: " + productToDelete.getId() + ")? (y/n): ");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("y")) {
                productList.remove(productToDelete);
                System.out.println("Product deleted successfully.\n");
                saveProducts();
            } else {
                System.out.println("Product deletion canceled.\n");
            }
        } else {
            System.out.println("Product not found.\n");
        }
    }

    // Sort Products
    public void sortProducts() {
        System.out.println("\n*** Sort Products By ***");
        System.out.println("1. Sort by Price");
        System.out.println("2. Sort by Name");
        System.out.println("3. Sort by ID");
        System.out.println("4. Return to Main Menu");
        System.out.print("Choose an option: ");
        int sortChoice = getValidInteger(scanner);

        switch (sortChoice) {
            case 1:
                productList.sort(Comparator.comparingDouble(Product::getPrice));
                break;
            case 2:
                productList.sort(Comparator.comparing(Product::getName));
                break;
            case 3:
                productList.sort(Comparator.comparingInt(Product::getId));
                saveProducts();
                break;
            case 4:
                System.out.println("Returning to Main Menu");
                return;
            default:
                System.out.println("Invalid option.");
        }

        System.out.println("Products sorted successfully.\n");
        displayAllProducts();
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
}
