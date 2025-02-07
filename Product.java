public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;

    Product(int id, String name, double price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Displaying product detail
    public void displayProduct() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: Rs." + price);
        System.out.println("Stock: " + stock);
        System.out.println("-------------------------------------\n");
    }
}
