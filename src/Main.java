import Products.*;
import Management.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main
{
    private static Stock stock = new Stock();
    private static Store store = new Store();
    private static ProductManager manager = new ProductManager(stock, store);
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args)
    {
        System.out.println("Выберите режим:");
        System.out.println("1. Тестовый режим");
        System.out.println("2. Интерактивное управление");

        int mode = validateIntInput(1, 2);

        if (mode == 1)
            runTestMode();
        else if (mode == 2)
            runInteractiveMode();
    }

    // Метод для запуска тестового режима
    private static void runTestMode() {
        System.out.println("=== ЗАПУСК ТЕСТОВОГО РЕЖИМА ===\n");

        // Создание тестовых продуктов
        Tvorog tvorog = new Tvorog(1, "Творог Домашний", "111222333", "Молочная ферма", 150.50, 10, "12-11-2024", 9.0, 500, 550);
        Maslo maslo = new Maslo(2, "Масло Крестьянское", "444555666", "Маслозавод", 250.75, 5, "15-02-2025", 82.5, 200, 210);
        Moloko moloko = new Moloko(3, "Молоко Пастеризованное", "777888999", "Молочная ферма", 60.30, 20, "01-11-2024", 3.2, 1000, 1050, 1.0);
        GeneralProduct chocolate = new GeneralProduct(4, "Шоколад", "1122334455", "Шоколадная фабрика", 85.90, 50);

        // Добавление товаров на склад
        System.out.println(">>> ДОБАВЛЕНИЕ ТОВАРОВ НА СКЛАД:");
        manager.purchaseToStock(tvorog);
        manager.purchaseToStock(maslo);
        manager.purchaseToStock(moloko);
        manager.purchaseToStock(chocolate);

        System.out.println("\n>>> ТЕКУЩЕЕ СОСТОЯНИЕ СКЛАДА:");
        viewAllProductsOnStock();  // Вывод всех товаров на складе

        // Перемещение товаров со склада в магазин
        System.out.println("\n>>> ПЕРЕМЕЩЕНИЕ ТОВАРОВ В МАГАЗИН:");
        manager.moveToStoreFromStock(1, "111222333", 5);  // Перемещаем 5 единиц Творога
        manager.moveToStoreFromStock(2, "444555666", 2);  // Перемещаем 2 единицы Масла

        System.out.println("\n>>> ТЕКУЩЕЕ СОСТОЯНИЕ МАГАЗИНА:");
        viewAllProductsInStore();  // Вывод всех товаров в магазине

        System.out.println("\n>>> ТЕКУЩЕЕ СОСТОЯНИЕ СКЛАДА ПОСЛЕ ПЕРЕМЕЩЕНИЯ:");
        viewAllProductsOnStock();  // Обновленный список товаров на складе

        // Возвращение товаров из магазина на склад
        System.out.println("\n>>> ВОЗВРАТ ТОВАРОВ ИЗ МАГАЗИНА НА СКЛАД:");
        manager.moveToStockFromStore(1, "111222333", 3);  // Возвращаем 3 единицы Творога на склад

        System.out.println("\n>>> ТЕКУЩЕЕ СОСТОЯНИЕ СКЛАДА ПОСЛЕ ВОЗВРАТА:");
        viewAllProductsOnStock();  // Обновленный список товаров на складе

        // Списание товаров
        System.out.println("\n>>> СПИСАНИЕ ТОВАРОВ ИЗ МАГАЗИНА:");
        manager.writeOffFromStore(1, "111222333", 2);  // Списываем 2 единицы Творога из магазина

        System.out.println("\n>>> ТЕКУЩЕЕ СОСТОЯНИЕ МАГАЗИНА ПОСЛЕ СПИСАНИЯ:");
        viewAllProductsInStore();  // Обновленный список товаров в магазине

        // Проверка срока годности молока
        System.out.println("\n>>> ПРОВЕРКА СРОКА ГОДНОСТИ МОЛОКА:");
        if (moloko.isExpired()) {
            System.out.println(moloko.name + " просрочено!");
        } else {
            System.out.println(moloko.name + " еще не просрочено.");
        }

        // Полное удаление продукта со склада
        System.out.println("\n>>> ПОЛНОЕ УДАЛЕНИЕ МАСЛА СО СКЛАДА:");
        manager.removeFromStock(2, "444555666", 0, true);  // Полностью удаляем масло со склада

        System.out.println("\n>>> ТЕКУЩЕЕ СОСТОЯНИЕ СКЛАДА ПОСЛЕ УДАЛЕНИЯ МАСЛА:");
        viewAllProductsOnStock();  // Обновленный список товаров на складе

        System.out.println("\n=== ТЕСТОВЫЙ РЕЖИМ ЗАВЕРШЕН ===");
    }

    private static void runInteractiveMode()
    {
        boolean running = true;
        while (running)
        {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить товар на склад");
            System.out.println("2. Найти товар на складе");
            System.out.println("3. Переместить товар со склада в магазин");
            System.out.println("4. Переместить товар из магазина на склад");
            System.out.println("5. Списать товар из магазина");
            System.out.println("6. Списать товар со склада");
            System.out.println("7. Проверить товар на просроченность");
            System.out.println("8. Просмотреть все товары на складе");
            System.out.println("9. Просмотреть все товары в магазине");
            System.out.println("10. Выйти");

            int choice = validateIntInput(1, 10);

            switch (choice)
            {
                case 1:
                    addProductToStock();
                    break;
                case 2:
                    findProductInStock();
                    break;
                case 3:
                    moveProductToStore();
                    break;
                case 4:
                    moveProductToStock();
                    break;
                case 5:
                    writeOffFromStore();
                    break;
                case 6:
                    removeFromStock();
                    break;
                case 7:
                    checkExpiration();
                    break;
                case 8:
                    viewAllProductsOnStock(); // Просмотр товаров на складе
                    break;
                case 9:
                    viewAllProductsInStore(); // Просмотр товаров в магазине
                    break;
                case 10:
                    running = false;
                    System.out.println("Программа завершена.");
                    break;
            }
        }
    }

    // Функция для валидации целочисленного ввода в заданных пределах
    private static int validateIntInput(int min, int max)
    {
        int input = -1;
        while (input < min || input > max)
        {
            System.out.print("Введите число (" + min + "-" + max + "): ");
            if (scanner.hasNextInt())
            {
                input = scanner.nextInt();
            }
            else
            {
                System.out.println("Ошибка ввода. Введите корректное число.");
            }
            scanner.nextLine(); // Очистка буфера
        }
        return input;
    }

    // Добавление товара на склад (с поддержкой GeneralProduct)
    private static void addProductToStock()
    {
        System.out.println("Выберите тип продукта:");
        System.out.println("1. Творог");
        System.out.println("2. Масло");
        System.out.println("3. Молоко");
        System.out.println("4. Другой продукт");

        int productType = validateIntInput(1, 4);

        System.out.print("Введите название товара: ");
        String name = scanner.nextLine();

        System.out.print("Введите UPC: ");
        String upc = scanner.nextLine();

        System.out.print("Введите производителя: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Введите цену: ");
        double price = validateDoubleInput();

        System.out.print("Введите количество: ");
        int quantity = validateIntInput(1, Integer.MAX_VALUE);

        System.out.print("Введите срок годности (формат: дд-ММ-гггг): ");
        String expirationDate = validateDateInput();

        Product product = null;

        switch (productType)
        {
            case 1: // Творог
                System.out.print("Введите процент жирности: ");
                double fatContentTvorog = validateDoubleInput();
                System.out.print("Введите массу нетто: ");
                double netWeightTvorog = validateDoubleInput();
                System.out.print("Введите массу брутто: ");
                double grossWeightTvorog = validateDoubleInput();
                product = new Tvorog(stock.products.size() + 1, name, upc, manufacturer, price, quantity, expirationDate, fatContentTvorog, netWeightTvorog, grossWeightTvorog);
                break;
            case 2: // Масло
                System.out.print("Введите процент жирности: ");
                double fatContentMaslo = validateDoubleInput();
                System.out.print("Введите массу нетто: ");
                double netWeightMaslo = validateDoubleInput();
                System.out.print("Введите массу брутто: ");
                double grossWeightMaslo = validateDoubleInput();
                product = new Maslo(stock.products.size() + 1, name, upc, manufacturer, price, quantity, expirationDate, fatContentMaslo, netWeightMaslo, grossWeightMaslo);
                break;
            case 3: // Молоко
                System.out.print("Введите процент жирности: ");
                double fatContentMoloko = validateDoubleInput();
                System.out.print("Введите массу нетто: ");
                double netWeightMoloko = validateDoubleInput();
                System.out.print("Введите массу брутто: ");
                double grossWeightMoloko = validateDoubleInput();
                System.out.print("Введите объем (литры): ");
                double volume = validateDoubleInput();
                product = new Moloko(stock.products.size() + 1, name, upc, manufacturer, price, quantity, expirationDate, fatContentMoloko, netWeightMoloko, grossWeightMoloko, volume);
                break;
            case 4: // Общий продукт
                product = new GeneralProduct(stock.products.size() + 1, name, upc, manufacturer, price, quantity);
                break;
        }

        manager.purchaseToStock(product);
    }

    // Валидация числовых входных данных (double)
    private static double validateDoubleInput()
    {
        while (!scanner.hasNextDouble())
        {
            System.out.println("Ошибка ввода. Введите корректное число.");
            scanner.nextLine(); // Очистка буфера
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Очистка буфера
        return value;
    }

    // Валидация и форматирование ввода даты
    private static String validateDateInput()
    {
        String dateInput;
        while (true)
        {
            dateInput = scanner.nextLine();
            try
            {
                LocalDate.parse(dateInput, dateFormatter);
                break;
            }
            catch (DateTimeParseException e)
            {
                System.out.println("Неверный формат даты. Попробуйте снова (дд-ММ-гггг).");
            }
        }
        return dateInput;
    }

    // Функция для проверки срока годности
    private static void checkProductExpiration(Product product)
    {
        if (product instanceof Perishable)
        {
            Perishable perishable = (Perishable) product;
            if (perishable.isExpired())
                System.out.println(product.name + " просрочено.");
            else
                System.out.println(product.name + " еще не просрочено.");
        }
    }

    // Валидация корректности даты продукта
    private static void checkExpiration()
    {
        System.out.print("Введите id товара: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Поглощение новой строки

        System.out.print("Введите UPC товара: ");
        String upc = scanner.nextLine();

        Product product = stock.findProduct(id, upc);
        if (product == null)
        {
            System.out.println("Товар с id " + id + " и UPC " + upc + " не найден.");
            return;
        }

        if (product instanceof Perishable)
        {
            Perishable perishable = (Perishable) product;
            if (perishable.isExpired())
                System.out.println(product.name + " просрочено.");
            else
                System.out.println(product.name + " еще не просрочено.");
        }
        else
        {
            System.out.println("Товар не является скоропортящимся.");
        }
    }

    // Метод для поиска продукта на складе
    private static void findProductInStock()
    {
        System.out.print("Введите id товара: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Поглощение новой строки

        System.out.print("Введите UPC товара: ");
        String upc = scanner.nextLine();

        Product product = stock.findProduct(id, upc);
        if (product != null)
            System.out.println("Товар найден на складе: " + product.getProductInfo());
        else
            System.out.println("Товар с id " + id + " и UPC " + upc + " не найден.");
    }

    // Метод для перемещения продукта со склада в магазин
    private static void moveProductToStore()
    {
        System.out.print("Введите id товара: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Поглощение новой строки

        System.out.print("Введите UPC товара: ");
        String upc = scanner.nextLine();

        System.out.print("Введите количество для перемещения: ");
        int quantity = scanner.nextInt();

        manager.moveToStoreFromStock(id, upc, quantity);
    }

    // Метод для перемещения продукта из магазина на склад
    private static void moveProductToStock()
    {
        System.out.print("Введите id товара: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Поглощение новой строки

        System.out.print("Введите UPC товара: ");
        String upc = scanner.nextLine();

        System.out.print("Введите количество для перемещения: ");
        int quantity = scanner.nextInt();

        manager.moveToStockFromStore(id, upc, quantity);
    }

    // Метод для списания товара из магазина
    private static void writeOffFromStore()
    {
        System.out.print("Введите id товара: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Поглощение новой строки

        System.out.print("Введите UPC товара: ");
        String upc = scanner.nextLine();

        System.out.print("Введите количество для списания: ");
        int quantity = scanner.nextInt();

        manager.writeOffFromStore(id, upc, quantity);
    }

    // Метод для удаления товара со склада
    private static void removeFromStock()
    {
        System.out.print("Введите id товара: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Поглощение новой строки

        System.out.print("Введите UPC товара: ");
        String upc = scanner.nextLine();

        System.out.print("Введите количество для списания: ");
        int quantity = scanner.nextInt();

        System.out.print("Удалить весь товар? (true/false): ");
        boolean removeAll = scanner.nextBoolean();

        manager.removeFromStock(id, upc, quantity, removeAll);
    }

    // Вывод всех товаров на складе и в магазине
    private static void viewAllProducts()
    {
        System.out.println("Товары на складе:");
        for (Product product : stock.products)
        {
            System.out.println(product.getProductInfo());
        }

        System.out.println("\nТовары в магазине:");
        for (Product product : store.products)
        {
            System.out.println(product.getProductInfo());
        }
    }

    // Метод для вывода всех товаров на складе
    private static void viewAllProductsOnStock() {
        System.out.println("Товары на складе:");
        if (stock.products.isEmpty()) {
            System.out.println("Склад пуст.");
        } else {
            for (Product product : stock.products) {
                System.out.println(product.getProductInfo());
            }
        }
    }

    // Метод для вывода всех товаров в магазине
    private static void viewAllProductsInStore() {
        System.out.println("Товары в магазине:");
        if (store.products.isEmpty()) {
            System.out.println("Магазин пуст.");
        } else {
            for (Product product : store.products) {
                System.out.println(product.getProductInfo());
            }
        }
    }
}
