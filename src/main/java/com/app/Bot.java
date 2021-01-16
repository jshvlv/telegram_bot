import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public class Bot extends TelegramLongPollingBot {
    private static String botToken;
    private final SimpleDateFormat fullDate = new SimpleDateFormat("d-MM-yyyy");//12-12-2002
    private final SimpleDateFormat shortFormat = new SimpleDateFormat("d MMMM", new Locale("ru"));//01 January

    public String getShortUserDate(int date) {
        Date userDate = new Date((long) date * 1000);
        int serverDate = new Date().getDate();
        return shortFormat.format(userDate);
    }
    public String getStringDateToParser(int date) {
        Date userDate = new Date((long) date * 1000);
        int serverDate = new Date().getDate();
        return fullDate.format(userDate);

    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            update.getMessage();
            int userDate = update.getMessage().getDate();
            String id = update.getMessage().getChatId().toString();
            String message = "*Сегодня, " + getShortUserDate(userDate) + " на Матч ТВ!*\n" +
                    Parser.updateParsing(getStringDateToParser(userDate));
            sendMsg(id, message, userDate);
            System.out.println("user date is " + getShortUserDate(userDate)
            + " server date is " + new Date());
        }
        else  if(update.hasCallbackQuery()) {
                update.getCallbackQuery();
        }
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения
     */
    public synchronized void sendMsg(String chatId, String s, int data){
        //Parser.updateParsing(getStrinfDateToParser(data));
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.getReplyMarkup();
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    private void log(Level severe, String s, String toString) {
    }

    @Override
    public String getBotUsername() {
        return "ФутБот";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return botToken;
    }
    //Загрузка токена бота из файла с конфигами.
    public static void loadConfig(){
        try {
            File file = new File("src/test/config.properties");
            Properties config = new Properties();
            config.load(new FileReader(file));
            botToken = config.getProperty("botToken");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        loadConfig();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


}



