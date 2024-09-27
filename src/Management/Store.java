package Management;

import Products.Product;
import java.util.ArrayList;
import java.util.List;

public class Store
{
    public List<Product> products;

    public Store()
    {
        this.products = new ArrayList<>();
    }

    // Метод для добавления продукта по id и upc
    public void addProduct(Product newProduct)
    {
        Product existingProduct = findProduct(newProduct.id, newProduct.upc);

        if (existingProduct != null)
        {
            // Если продукт уже есть, увеличиваем количество
            existingProduct.quantity += newProduct.quantity;
            System.out.println(newProduct.name + " добавлено " + newProduct.quantity + "шт. в торговый зал" +
                    " Текущее количество: " + existingProduct.quantity + "шт.");
        }
        else
        {
            // Если продукта нет, добавляем новый продукт
            products.add(newProduct);
            System.out.println(newProduct.name + " в количестве " + newProduct.quantity + "шт. добавлен в торговый зал.");
        }
    }

    // Метод для списания продукта по количеству
    public void removeProduct(int id, String upc, int quantityToRemove)
    {
        Product product = findProduct(id, upc);

        if (product == null)
        {
            throw new IllegalArgumentException("Продукт с id " + id + " и upc " + upc + " не найден в торговом зале.");
        }

        // Проверяем, можно ли списать указанное количество
        if (product.quantity >= quantityToRemove)
        {
            product.quantity -= quantityToRemove;
            System.out.println(product.name + " списано " + quantityToRemove + "шт. из торгового зала. Остаток: " + product.quantity + "шт.");
        }
        else
        {
            throw new IllegalArgumentException("Невозможно списать " + quantityToRemove + "шт. продукта." +
                    " Остаток в торговом зале: " + product.quantity + "шт.");
        }
    }

    // Метод для поиска продукта по id и upc
    public Product findProduct(int id, String upc)
    {
        for (Product product : products)
            if (product.id == id && product.upc.equals(upc))
                return product;
        return null;
    }
}
