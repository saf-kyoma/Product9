package Products;

// Класс для товаров общего назначения
public class GeneralProduct extends Product
{
    public GeneralProduct(int id, String name, String upc, String manufacturer, double price, int quantity)
    {
        super(id, name, upc, manufacturer, price, quantity);
    }
}

