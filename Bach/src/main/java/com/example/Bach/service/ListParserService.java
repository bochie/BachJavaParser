package com.example.Bach.service;

import com.example.Bach.model.List;
import com.example.Bach.repository.ListRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class ListParserService {
    private final ListRepository listingRepository;

    public ListParserService(ListRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    private static final String SOURCE_URL = "https://www.olx.ua/uk/elektronika/telefony-i-aksesuary/mobilnye-telefony-smartfony/?currency=UAH";

    @Scheduled(fixedRate = 3600000)
    public void parseListings() {
        try {
            Document doc = Jsoup.connect(SOURCE_URL).get();
            Elements elements = doc.select("div.css-l9drzq");
            java.util.List<List> listings = new ArrayList<>();

            for (Element el : elements) {
                String title = el.select("h4").text();
                String url = el.select("a").attr("href");

                // Змінено пошук ціни для оголошень про телефони
                Element priceElement = el.select("p.css-6j1qjp").first();
                String priceText = priceElement != null ?
                        priceElement.text().replaceAll("[^0-9]", "") : "0";

                BigDecimal price = priceText.isEmpty() ?
                        BigDecimal.ZERO : new BigDecimal(priceText);

                List listing = new List();
                listing.setTitle(title);
                listing.setUrl("https://www.olx.ua/" + url);
                listing.setPrice(price);
                listing.setCurrency("UAH");

                listings.add(listing);
            }

            listingRepository.saveAll(listings);
            System.out.println("Парсинг телефонів завершено! Додано " + listings.size() + " оголошень.");

        } catch (IOException e) {
            System.err.println("Помилка парсингу: " + e.getMessage());
        }
    }
}