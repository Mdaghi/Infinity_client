package controller;

import com.github.kevinsawicki.http.HttpRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.print.attribute.standard.PresentationDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

class StockCommenter {

	public static Map<String, String> buildDataFeedOnStock(String name) {
		Map<String, String> dataFeed = new HashMap<>();
		String response = HttpRequest
				.get("https://newsapi.org/v2/everything?q=" + name
						+ "&sortBy=popularity&apiKey=a74a9e61aec64107a03439feaac82197")
				.accept("application/json").body();
		JSONObject jsonObject = new JSONObject(response);
		JSONArray jsonArray = jsonObject.getJSONArray("articles");
		int i = 0;
		while (dataFeed.size()<3)
		{
			System.out.println(i);
			JSONObject jsonobject = jsonArray.getJSONObject(i);
			if (dataFeed.containsKey(jsonobject.get("author").toString()))
			{
				i++;
				continue;
			}
			if (!(jsonobject.get("author").toString().toUpperCase().charAt(0) >= 65
					&& jsonobject.get("author").toString().toUpperCase().charAt(0) <= 90)) {
				continue;
			}
			if (!(jsonobject.get("title").toString().toUpperCase().charAt(0) >= 65
					&& jsonobject.get("title").toString().toUpperCase().charAt(0) <= 90)) {
				continue;
			}
			if (!(jsonobject.get("description").toString().toUpperCase().charAt(0) >= 65
					&& jsonobject.get("description").toString().toUpperCase().charAt(0) <= 90)) {
				continue;
			}
			if ("null".equals(jsonobject.get("author").toString())) {
				continue;
			}

			dataFeed.put(jsonobject.get("author").toString(), jsonobject.get("title").toString());
		}
		return dataFeed;
	}

	public static String generateComment(String name, Float change, Float high, String date) {
		String comment = "";
		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);

		SPhraseSpec phrase;
		SPhraseSpec phrase1;
		SPhraseSpec phrase2;

		if (change < 0) {
			if (change < 10.0f) {
				phrase = nlgFactory.createClause(name + "'s Stock", "decrease");
				phrase.addComplement("exponentially");
			} else {
				phrase = nlgFactory.createClause(name + "'s Stock", "decrease");
				phrase.addComplement("slightly");
			}
		} else {
			if (change > 5.0f) {
				phrase = nlgFactory.createClause(name + "'s Stock", "increase");
				phrase.addComplement("exponentially");
			} else {
				phrase = nlgFactory.createClause(name + "'s Stock", "increase");
				phrase.addComplement("slightly");
			}
		}
		phrase.setFeature(Feature.TENSE, Tense.PRESENT);
		phrase.setFeature(Feature.PERFECT, true);

		phrase1 = nlgFactory.createClause("It", "reach", "a value of " + high + " as for " + date);
		phrase1.setFeature(Feature.TENSE, Tense.PAST);
		phrase1.setFeature(Feature.PERFECT, true);

		phrase2 = (change > 0) ? nlgFactory.createClause("A lot of reasons", "explain", "this increase")
				: nlgFactory.createClause("A lot of reasons", "explain", "this decrease");
		phrase2.addComplement("such as the articles fetched by our System");
		phrase2.setFeature(Feature.TENSE, Tense.PRESENT);
		phrase2.setFeature(Feature.PERFECT, true);

		List<SPhraseSpec> articlePhrases = new ArrayList<>();
		int incrementor = 0;
		Iterator it = buildDataFeedOnStock(name).entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String linker = "";
			switch (incrementor) {
			case 0:
				linker = "First";
				break;
			case 1:
				linker = "Also";
				break;
			case 2:
				linker = "And finally";
				break;
			default:
				break;
			}
			SPhraseSpec pArticle = nlgFactory.createClause(linker + " " + pair.getKey(), "say",
					"that " + pair.getValue());
			pArticle.setFeature(Feature.TENSE, Tense.PRESENT);
			articlePhrases.add(pArticle);
			incrementor++;
			it.remove();
		}

		// Creating sentences
		DocumentElement firstSentence = nlgFactory.createSentence(phrase);
		DocumentElement secondSentence = nlgFactory.createSentence(phrase1);
		DocumentElement thirdSentence = nlgFactory.createSentence(phrase2);

		DocumentElement articleSentences[] = new DocumentElement[3];
		int index = 0;
		for (SPhraseSpec p : articlePhrases) {
			articleSentences[index] = nlgFactory.createSentence(p);
			index++;
		}

		// Building a paragraph using different document elements
		DocumentElement par = nlgFactory.createParagraph(Arrays.asList(firstSentence, secondSentence, thirdSentence,
				articleSentences[0], articleSentences[1], articleSentences[2]));
		comment = realiser.realise(par).getRealisation();
		return comment;
	}

}
