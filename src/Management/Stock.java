package Management;

import Products.Product;
import java.util.ArrayList;
import java.util.List;

public class Stock
{
    public List<Product> products;

    public Stock()
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
            System.out.println(newProduct.name + " добавлено " + newProduct.quantity + "шт. на склад." +
                    " Текущее количество: " + existingProduct.quantity + "шт.");
        }
        else
        {
            // Если продукта нет, добавляем новый продукт
            products.add(newProduct);
            System.out.println(newProduct.name + " в количестве " + newProduct.quantity + "шт. добавлен на склад.");
        }
    }

    // Метод для удаления продукта по количеству или полностью
    public void removeProduct(int id, String upc, int quantityToRemove, boolean removeAll)
    {
        Product product = findProduct(id, upc);

        if (product == null)
        {
            throw new IllegalArgumentException("Продукт с id " + id + " и upc " + upc + " не найден на складе.");
        }

        // Если флаг removeAll установлен, удаляем весь продукт
        if (removeAll)
        {
            products.remove(product);
            System.out.println(product.name + " полностью удален со склада.");
        }
        else
        {
            // Проверяем, можно ли удалить указанное количество
            if (product.quantity >= quantityToRemove)
            {
                product.quantity -= quantityToRemove;
                System.out.println(product.name + " удалено " + quantityToRemove + "шт. со склада. Остаток: " + product.quantity + "шт.");

                // Если количество стало 0, удаляем продукт
                if (product.quantity == 0)
                {
                    products.remove(product);
                    System.out.println(product.name + " полностью удален со склада.");
                }
            }
            else
            {
                throw new IllegalArgumentException("Невозможно удалить " + quantityToRemove + "шт. продукта." +
                        " Остаток на складе: " + product.quantity + "шт.");
            }
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