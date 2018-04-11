package esprit.tn.Infinity_client;


import org.json.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
public class Futures {

	public static void main(String[] args) {
		currencyConvertion("TND","EUR");
	}
	
	
	public static void currencyConvertion(String from,String to)
	{
		String response = HttpRequest
				.get("https://v3.exchangerate-api.com/bulk/428d417084fe51418dc991a4/"+from)
				.accept("application/json").body();
		JSONObject jsonObject = new JSONObject(response);
		//
		JSONObject status = jsonObject.getJSONObject("rates");
		Double eur = status.getDouble(to);
		System.out.println(eur*200);
	}

}
