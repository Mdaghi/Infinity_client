/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.kevinsawicki.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Terminal {

	static String doAction(String command, String[] params) {

		if (command.equalsIgnoreCase("HELP")) {
			return help();
		} else if (command.equalsIgnoreCase("STOCKDATA")) {
			return showStock(params[0]);
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
		} else if (command.equalsIgnoreCase("INFO")) {
			return info();
		} else {
			return "unkown command... try the HELP command for guidelines";
		}
	}

	private static JSONArray retriveJSONArray(String url) {
		String response = HttpRequest.get(url, true, "q", "baseball gloves", "size", 100).accept("application/json")
				.body();
		return new JSONArray(response);
	}

	private static JSONObject retriveJSONObject(String url) {
		String response = HttpRequest.get(url, true, "q", "baseball gloves", "size", 100).accept("application/json")
				.body();
		return new JSONObject(response);
	}

	private static String getStockData(String symbol) {

		SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS");
		String response = HttpRequest.get("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol="
				+ symbol + "&interval=1min&apikey=R5Y2Z66NSW3CFTXA").accept("application/json").body();
		JSONObject jsonObject = new JSONObject(response);
		Set<String> keysToFetchFrom = new HashSet<>();
		TreeSet<String> sortedKeysToFetchFrom = new TreeSet<>(new MyComp());
		keysToFetchFrom = ((JSONObject) jsonObject.get("Monthly Time Series")).keySet();
		sortedKeysToFetchFrom.addAll(keysToFetchFrom);
		String[] keys = new String[2];
		int index = 0;
		for (String K : sortedKeysToFetchFrom) {
			if (index == 2)
				break;
			keys[index] = K;
			index++;
		}
		JSONObject result = ((JSONObject) ((JSONObject) jsonObject.get("Monthly Time Series")).get(keys[0]));
		String toStock = "";
		toStock += result.getDouble("1. open");
		toStock += " " + result.getDouble("2. high");
		toStock += " " + result.getDouble("3. low");
		toStock += " " + result.getDouble("4. close");
		toStock += " " + result.getDouble("5. volume");
		return toStock;
	}

	private static String showStock(String symbol) {
		SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS");
		String response = HttpRequest.get("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol="
				+ symbol + "&interval=1min&apikey=R5Y2Z66NSW3CFTXA").accept("application/json").body();
		JSONObject jsonObject = new JSONObject(response);
		Set<String> keysToFetchFrom = new HashSet<>();
		TreeSet<String> sortedKeysToFetchFrom = new TreeSet<>(new MyComp());
		keysToFetchFrom = ((JSONObject) jsonObject.get("Monthly Time Series")).keySet();
		sortedKeysToFetchFrom.addAll(keysToFetchFrom);
		String[] keys = new String[2];
		int index = 0;
		for (String K : sortedKeysToFetchFrom) {
			if (index == 2)
				break;
			keys[index] = K;
			index++;
		}
		JSONObject result = ((JSONObject) ((JSONObject) jsonObject.get("Monthly Time Series")).get(keys[0]));
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
		String[] symbols = { "AMZN", "AAPL", "BAC", "BMW", "DAI", "DIS", "INTC", "GOOG", "NFLX", "MSFT", "WMT" };
		String toStock = "";
		for (int i = 0; i < symbols.length; i++) {
			toStock += symbols[i] + " | ";
		}
		return toStock;
	}

	private static String convert(String currencyOne, String currencyTwo, String quantity) {
		try {
			JSONObject response = retriveJSONObject("https://forex.1forge.com/1.0.3/convert?from=" + currencyOne
					+ "&to=" + currencyTwo + "&quantity=" + quantity + "&api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs");
			return "Rate:" + response.getDouble("value") + " | " + response.getString("text");

		} catch (JSONException e) {
			return "unkown command... try the HELP command for guidelines";
		}
	}

	private static String status() {
		try {
			JSONObject response = retriveJSONObject(
					"https://forex.1forge.com/1.0.3/market_status?api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs");
			return response.getBoolean("market_is_open") ? "The market is open" : "The market is currently closed";
		} catch (JSONException e) {
			return "unkown command... try the HELP command for guidelines";
		}
	}

	private static String symbols() {
		try {
			JSONArray response = retriveJSONArray(
					"https://forex.1forge.com/1.0.3/symbols?api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs");
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
		return "{'CONVERT CURRENCY1 CURRENCY2 QUANTITY', 'FREEZE', 'HELP','SHOWSTOCK','SHOWALLSTOCK','STATUS', 'SYMBOLS'}";
	}

	private static String info() {
		return "Welcome to Infinity Trader Terminal, this terminal was built for a school project by Oussama Ben Ghorbel inspired by his coach Professor Salma";
	}

}
