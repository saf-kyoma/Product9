package Products;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Moloko extends Product implements Perishable, FatContentable, NetGrossWeightable, LiquidVolumeable
{
    private String expirationDate;
    private double fatContent; // процент жирности
    private double netWeight;  // масса нетто
    private double grossWeight; // масса брутто
    private double volume;      // объём жидкости в литрах

    public Moloko(int id, String name, String upc, String manufacturer, double price, int quantity,
                  String expirationDate, double fatContent, double netWeight, double grossWeight, double volume)
    {
        super(id, name, upc, manufacturer, price, quantity);
        this.expirationDate = expirationDate;
        this.fatContent = fatContent;
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.volume = volume;
    }

    @Override
    public boolean isExpired()
    {
        // Определяем текущую дату
        LocalDate currentDate = LocalDate.now();

        try
        {
            // Преобразуем строку с датой в LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Например, формат "ГГГГ-ММ-ДД"
            LocalDate expiration = LocalDate.parse(expirationDate, formatter);

            // Сравниваем текущую дату с датой истечения срока годности
            return currentDate.isAfter(expiration);
        }
        catch (DateTimeParseException e)
        {
            // Обработка ошибок в случае неправильного формата даты
            System.err.println("Неверный формат даты: " + expirationDate);
            return false; // В случае ошибки считаем, что товар не просрочен
        }
    }

    @Override
    public String getExpirationDate()
    {
        return expirationDate;
    }

    @Override
    public double getFatContent()
    {
        return fatContent;
    }

    @Override
    public double getNetWeight()
    {
        return netWeight;
    }

    @Override
    public double getGrossWeight()
    {
        return grossWeight;
    }

    @Override
    public double getVolume()
    {
        return volume;
    }
}