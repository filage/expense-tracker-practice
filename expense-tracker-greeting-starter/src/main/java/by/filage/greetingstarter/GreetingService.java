package by.filage.greetingstarter;

public class GreetingService {

    public String createStartupMessage(String message, String formattedDate, String currency) {
        return String.format(
                "%s Сейчас %s, текущая валюта — %s.",
                message,
                formattedDate,
                currency
        );
    }
}
