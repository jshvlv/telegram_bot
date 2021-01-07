import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

//Parsing from match.tv into parsing file...
public class Parser {
    //main только для тестирования парсера
    public static void main(String[] args) throws IOException {
        updateParsing("08-01-2021");
    }

    /*
     * Функция updateParsing принимает дату в текстовой строке
     * для того, чтобы можно было запросить программу на разные дни
     * Разбор программы необходимо сделать с обновлением записей в БД
     * Эта функция на стадии реализации
     */
    public static String updateParsing(String date) {
        List<String> printList = new ArrayList<>();
        String progText="";
        String chName = "";
        String progTime = "";
        String progName = "";
        try {
            //Подключаемся к странице через суп
            Document doc = Jsoup.connect("https://matchtv.ru/tvguide?date=" + date).get();
            //Парсим страницу на отдельные каналы, в лист элементов (объекты супа)
            Elements channels = doc.body().getElementsByClass(
                    "tv-programm__chanels-item col-lg-4 col-md-6 col-sm-6 col-xs-12"
            );

            for (Element channel : channels
            ) { // разбираем каждый канал по названию канала
                chName = channel.getElementsByClass("heading-3").text() +
                        "\n---------";
                //Разбираем программу передач по каждому каналу на время и передачу
                Elements items = channel.getElementsByClass("tv-programm__tvshows-item");
                progText = "";
                for (Element item : items
                ) {
                    progTime = item.getElementsByClass("tv-programm__tvshow-time").text() + "\n";
                    progName = item.getElementsByClass("tv-programm__tvshow-title").text() +
                            "\n ---------------------------";
                    if(progName.contains("Футбол")) {
                        String output = chName + " " + progTime + " " + progName;
                        printList.add(output);
                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        for (String s: printList
             ) {
            progText = progText + s;
        }
        System.out.println(progText);
        return progText;

    }


}