package Products;

public class Product
{
    public int id;
    public String name;
    public String upc;
    public String manufacturer;
    public double price;
    public int quantity;

    public Product() {}

    public Product(int id, String name, String upc, String manufacturer, double price, int quantity)
    {
        this.id = id;
        this.name = name;
        this.upc = upc;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductInfo()
    {
        return String.format("ID: %d, Название: %s, UPC: %s, Производитель: %s, Цена: %.2f, Количество: %d",
                id, name, upc, manufacturer, price, quantity);
    }
}