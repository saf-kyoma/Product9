package Management;

import Products.Product;

public class ProductManager
{
    private Stock stock;
    private Store store;

    public ProductManager(Stock stock, Store store)
    {
        this.stock = stock;
        this.store = store;
    }

    // Закупка товара на склад
    public void purchaseToStock(Product product)
    {
        stock.addProduct(product);
    }

    // Удаление товара со склада
    public void removeFromStock(int productId, String upc, int quantityToRemove, boolean removeAll)
    {
        stock.removeProduct(productId, upc, quantityToRemove, removeAll);
    }

    // Перемещение товара из склада в магазин
    public void moveToStoreFromStock(int productId, String upc, int quantity)
    {
        Product product = stock.findProduct(productId, upc);

        if (product != null)
        {
            stock.removeProduct(productId, upc, quantity, false);  // Списываем со склада
            store.addProduct(product);  // Перемещаем в магазин
        }
    }

    // Перемещение товара из магазина на склад
    public void moveToStockFromStore(int productId, String upc, int quantity)
    {
        Product product = store.findProduct(productId, upc);

        if (product != null)
        {
            store.removeProduct(productId, upc, quantity);  // Списываем из магазина
            stock.addProduct(product);  // Перемещаем на склад
        }
        else
        {
            throw new IllegalArgumentException("Товар с id " + productId + " и upc " + upc + " не найден в торговом зале.");
        }
    }

    // Списание товара из магазина
    public void writeOffFromStore(int productId, String upc, int quantityToRemove)
    {
        store.removeProduct(productId, upc, quantityToRemove);
    }
}
