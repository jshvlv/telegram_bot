import org.apache.maven.model.Parent;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

public class Bot extends TelegramLongPollingBot {

    int count=0;
    int countAll=0;
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        count++;
        countAll=count;
        if(update.hasMessage()) {
            update.getMessage();
            String message = update.getMessage().getText();
            int data = update.getMessage().getDate();
            sendMsg(update.getMessage().getChatId().toString(), message, data);
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

        Date userDate = new Date((long) data*1000);
        int nowDay = new Date().getDay();
        //Если сегодня новый день - скачать программу заново
        if(userDate.getDay()!=nowDay){
            Parser.updateParsing();
            count=0;
            System.out.println("Parser is updated!");
        }

        SimpleDateFormat simple = new SimpleDateFormat("d MMMM");
        String toDay = simple.format(userDate);

        String sending = "*Сегодня, " + toDay + ", на МатчТВ!*\n\n" +
                Parser.getText();
        SendMessage sendMessage = new SendMessage();
        System.out.println("message " + count + " was sended...");
        if (s.contains("/go")) {
            sending = "*Статистика*\n\n"  +
                    "Запросов сегодня " + count +
                    "\nЗапросов всего: " + countAll;
        }
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.getReplyMarkup();
        sendMessage.setText(sending);
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
        return "1084538682:AAEU5y2jTXoYTK7rGxN56b8U-V2Bfl2ny-M";
    }
    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


}



