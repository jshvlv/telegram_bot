import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//Parsing from match.tv into parsing file...
public class Parser {
    public static void main(String[] args) throws IOException {
        System.out.println(getText());
    }


    public static  void updateParsing(){
        Document doc;
        try {
            doc = Jsoup.connect("https://matchtv.ru/channel/matchtv/tvguide#content").get();
            try {
                File file = new File("/home/eugen/IdeaProjects/TeleBot/src/test/parsing.html");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                String str = doc.html();
                fileOutputStream.write(str.getBytes());
                fileOutputStream.close();
                System.out.println("Parsing ok!");
            }
            catch (IOException e){
                System.out.println("File not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getText() {
        String s="";
        try {
            File input = new File("/home/eugen/IdeaProjects/TeleBot/src/test/parsing.html");
            Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

            Elements elements = doc.body().getElementsByClass("teleprogram-schedule__item");

            for (Element el : elements) {
                String str = el.text();
                if (str.contains("Футбол.")) {
                    s = s + str + "\n\n";
                }
            }
        }
        catch (IOException e){
            s=e.toString();
        }

        return s;
    }

}
