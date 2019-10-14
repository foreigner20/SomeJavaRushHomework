package com.javarush.task.task28.task2810.model;

import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy{

    private static final String  URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";

    String url = String.format(URL_FORMAT, "Kiev", 3);

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> listVacancy = new ArrayList<>();
        Document doc = null;
        for(int page = 0; ; page++) {
            try {
                doc = getDocument(searchString, page);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
            if (elements.size() == 0) {
                break;
            }
            for(Element element : elements){
                Vacancy vacancy = new Vacancy();
                vacancy.setTitle(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-title").text().trim());
                vacancy.setCity(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-address").text().trim());
                vacancy.setCompanyName(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-employer").text().trim());
                vacancy.setUrl(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-title").attr("href").trim());
                vacancy.setSalary(element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-compensation").text().trim());
                vacancy.setSiteName(URL_FORMAT);
                listVacancy.add(vacancy);
            }
        }
        return listVacancy;
    }

    protected Document getDocument(String searchString, int page)throws IOException{
        String html = String.format(URL_FORMAT, "Uzhorod", page);
        Document doc = Jsoup.connect(html)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                .referrer("no-referrer-when-downgrade")
                .get();
        String shtml = doc.html();
        return doc;
    }
}
