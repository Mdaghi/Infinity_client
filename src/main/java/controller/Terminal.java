/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.kevinsawicki.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Terminal {

    static String doAction(String command, String[] params) {
        if (command.equalsIgnoreCase("HELP")) {
            return help();
        } else if (command.equalsIgnoreCase("CONVERT")) {
            return convert(params[0], params[1], params[2]);
        } else if (command.equalsIgnoreCase("STATUS")) {
            return status();
        } else if (command.equalsIgnoreCase("SYMBOLS")) {
            return symbols();
        } else if (command.equalsIgnoreCase("SHOWSTOCK")) {
            return showStock(params[0]);
        } else if (command.equalsIgnoreCase("SHOWALLSTOCK")) {
            return showAllStocks();
        } else {
            return "unkown command... try the HELP command for guidelines";
        }
    }

    private static JSONArray retriveJSONArray(String url) {
        String response = HttpRequest.get(url, true, "q", "baseball gloves", "size", 100)
                .accept("application/json")
                .body();
        return new JSONArray(response);
    }

    private static JSONObject retriveJSONObject(String url) {
        String response = HttpRequest.get(url, true, "q", "baseball gloves", "size", 100)
                .accept("application/json")
                .body();
        return new JSONObject(response);
    }

    private static String showStock(String symbol) {
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS");
        String response = HttpRequest.get("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&interval=1min&apikey=R5Y2Z66NSW3CFTXA")
                .accept("application/json")
                .body();
        JSONObject jsonObject = new JSONObject(response);
        JSONObject result = ((JSONObject) ((JSONObject) jsonObject.get("Monthly Time Series")).get("2018-03-02"));
        Label quote = new Label();
        quote.setWrapText(true);
        String toStock = "symbol: " + symbol + " | ";
        toStock += "open: " + result.getDouble("1. open") + " | ";
        toStock += "high: " + result.getDouble("2. high") + " | ";
        toStock += "low: " + result.getDouble("3. low") + " | ";
        toStock += "close: " + result.getDouble("4. close") + " | ";
        toStock += "volume: " + result.getDouble("5. volume") + " | ";
        Date date = new Date();
        toStock += "time: " + sf.format(date) + "\n";
        toStock += " | --end of line";
        return toStock;
    }

    private static String showAllStocks() {
        String[] symbols = {"AMZN", "AAPL", "BAC", "INTC", "GOOG", "sNFLX", "MSFT", "WMT"};
        String toStock = "";
        for (int i = 0; i < symbols.length; i++) {
            Label quote = new Label();
            quote.setWrapText(true);
            toStock += symbols[i] + " | ";
        }
        return toStock;
    }

    private static String convert(String currencyOne, String currencyTwo, String quantity) {
        try {
            JSONObject response = retriveJSONObject("https://forex.1forge.com/1.0.3/convert?from=" + currencyOne + "&to=" + currencyTwo + "&quantity=" + quantity + "&api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs");
            return "Rate:" + response.getDouble("value") + " | " + response.getString("text");

        } catch (JSONException e) {
            return "unkown command... try the HELP command for guidelines";
        }
    }

    private static String status() {
        try {
            JSONObject response = retriveJSONObject("https://forex.1forge.com/1.0.3/market_status?api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs");
            return response.getBoolean("market_is_open") ? "The market is open" : "The market is currently closed";
        } catch (JSONException e) {
            return "unkown command... try the HELP command for guidelines";
        }
    }

    private static String symbols() {
        try {
            JSONArray response = retriveJSONArray("https://forex.1forge.com/1.0.3/symbols?api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs");
            String symbols = "";
            for (int i = 0; i < response.length(); i++) {
                symbols += response.getString(i) + "|";
            }
            return symbols;
        } catch (JSONException e) {
            return "unkown command... try the HELP command for guidelines";
        }
    }

    private static String help() {
        return "Welcome to Oussama's terminal, this terminal supports the following commands: {'CONVERT CURRENCY1 CURRENCY2', 'FREEZE', 'HELP','SHOWSTOCK','STATUS', 'SYMBOLS'}";
    }
}
