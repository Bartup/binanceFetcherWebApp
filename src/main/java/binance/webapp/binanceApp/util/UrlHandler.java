package binance.webapp.binanceApp.util;

import binance.webapp.binanceApp.Kline;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UrlHandler {

    public static ArrayList<String> getAllWantedPairs(String unwanted) throws IOException {

        String output;
        ArrayList<String> pairList = new ArrayList();
        JsonParser parser = new JsonParser();

        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Write unwanted pairs like: BTCUSDT LUNAUSDT ... XRPBUSD");

        String[] unwantedPairs = unwanted.split(" ");

        URL url = new URL("https://api.binance.com/api/v3/ticker/price");

        URLConnection urlConnection = url.openConnection();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream())
        );

        while ((output = bufferedReader.readLine()) != null) {
            JsonArray jsonElements = (JsonArray) parser.parse(output);

            for (JsonElement e : jsonElements) {
                String pair = String.valueOf(e.getAsJsonObject().get("symbol"));
                if (isWanted(pair)) {

                    boolean wanted = true;
                    for (String s : unwantedPairs) {
                        if (pair.replaceAll("\"", "").equals(s)) {
                            wanted = false;
                        }
                    }

                    if (wanted)
                        pairList.add(pair.replaceAll("\"", ""));
                }
            }
        }
        bufferedReader.close();
        return pairList;
    }

    public static ArrayList<Kline> getAllKlines(ArrayList<String> pairs) throws IOException {
        int counter = 1;
        String output;
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = new JsonArray();
        ArrayList<Kline> klines = new ArrayList<>();

        for (String s : pairs) {

            //Counter
            System.out.println(counter + "/" + pairs.size());
            counter = counter + 1;

            //Creating connection
            URL url = new URL("https://api.binance.com/api/v3/klines?symbol=" + s + "&interval=1d&limit=1000");
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream())
            );

            //Reading response
            while ((output = bufferedReader.readLine()) != null) {
                jsonArray = parser.parse(output).getAsJsonArray();
            }
            bufferedReader.close();

            //Adding kline
            addKline(jsonArray, klines, s);
        }
        return klines;
    }

    private static void addKline(JsonArray jsonArray, ArrayList<Kline> klines, String pair) {

        float lowestPrice = getLowestPrice(jsonArray);
        klines.add(new Kline(
                pair,
                getOpeningTime(jsonArray),
                lowestPrice,
                getHighestPrice(jsonArray),
                getCurrentPrice(jsonArray),
                getOpeningPrice(jsonArray),
                getLowestPriceDate(lowestPrice, jsonArray)
        ));
    }

    private static Date getOpeningTime(JsonArray jsonArray) {
        return new Date(jsonArray.get(0).getAsJsonArray().get(0).getAsLong());
    }

    private static float getOpeningPrice(JsonArray jsonArray) {
        return jsonArray.get(0).getAsJsonArray().get(1).getAsFloat();
    }

    private static float getCurrentPrice(JsonArray jsonArray) {
        return jsonArray.get(jsonArray.size() - 1).getAsJsonArray().get(4).getAsFloat();
    }

    private static float getLowestPrice(JsonArray jsonArray) {
        float lowest = jsonArray.get(0).getAsJsonArray().get(3).getAsFloat();
        for (JsonElement element : jsonArray) {
            float current = element.getAsJsonArray().get(3).getAsFloat();
            if (current < lowest) {
                lowest = current;
            }
        }
        return lowest;
    }

    private static Date getLowestPriceDate(float price, JsonArray jsonArray) {
        long milis = 0;

        for (JsonElement element : jsonArray) {
            float current = element.getAsJsonArray().get(3).getAsFloat();
            if (current == price) {
                milis = element.getAsJsonArray().get(6).getAsLong();
            }
        }
        return new Date(milis);
    }

    private static float getHighestPrice(JsonArray jsonArray) {
        float highest = jsonArray.get(0).getAsJsonArray().get(2).getAsFloat();
        for (JsonElement element : jsonArray) {
            float current = element.getAsJsonArray().get(2).getAsFloat();
            if (current > highest) {
                highest = current;
            }
        }
        return highest;
    }

    public static boolean isWanted(String pair) {
        return ((pair.endsWith("USDT\"")
                && !pair.contains("DOWNUSDT")
                && !pair.contains("BULLUSDT")
                && !pair.contains("BEARUSDT")
                && !pair.contains("UPUSDT"))
                || (pair.endsWith("BUSD\"")
                && !pair.contains("DOWNBUSD")
                && !pair.contains("UPBUSD")
                && !pair.contains("BULLBUSD")
                && !pair.contains("BEARBUSD")));
    }
}

