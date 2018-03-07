/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.kevinsawicki.http.HttpRequest;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.guigarage.controls.*;
import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;

import java.util.List;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tn.esprit.infinity_server.persistence.*;
import tn.esprit.infinity_server.interfaces.*;
import tn.esprit.infinity_server.services.*;

/**
 *
 * @author dainer
 */
public class DashboardController implements Initializable {

    static boolean stockFlag = false;
    static boolean forexFlag = true;

    static boolean callBackLock = false;
    static boolean threadQuotes = false;
    static boolean threadNewsSources = false;
    static boolean threadArticles = false;
    static boolean threadArticlesTrending = false;

    static Article articleTrendingContainer = null;
    static Article articleContainer = null;
    static Newsource newssourceContainer = null;

    @FXML
    private ListView<Newsource> newsSources;
    @FXML
    private ListView<Article> articlesTrending;
    @FXML
    private ScrollPane areaContainer;
    @FXML
    private VBox area;

    @FXML
    private HBox titleFinance;

    @FXML
    private HBox terminal;
    @FXML
    private JFXTextField terminalContent;

    @FXML
    private HBox quotesUpdate;
    @FXML
    private VBox quotes;

    @FXML
    private Label libTotalSavedArticles;
    @FXML
    private Label libTotalSubscribedNewsSources;
    @FXML
    private Label libTotalNewsSources;
    @FXML
    private Label libTotalArticlesFetched;
    @FXML
    private Label lbTotalNewsSourceSupported;

    private Label terminalCursor;
    private Label quotesSeprator;
    private Label quotesUpdateSeprator;
    
    private Context context;
    private NewsSourceRemote newsProxy;
    private NewsArticleRemote articleProxy;
    private NewsSource ns;
    private User currentUser;
    
    private List<NewsSource> supportedNewsSources;
    private List<NewsSource> subscribedNewsSources;
    private List<NewsArticle> savedArticles;

    private boolean select = false;

    private void selectUnselect(AnchorPane articleArea) {
        if (select == false) {
            articleArea.setStyle("-fx-background-color: #D3D3D3;");
            select = true;
        } else {
            articleArea.setStyle("-fx-background-color: #FFFFFF;");
            select = false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	supportedNewsSources = new ArrayList<>();
		Properties jndiProps = new Properties(); 
		jndiProps.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
		String jndiName="infinity_server-ear/infinity_server-ejb/ServiceNewsSource!tn.esprit.infinity_server.interfaces.NewsSourceRemote", jndiName1="infinity_server-ear/infinity_server-ejb/ServiceNewsArticle!tn.esprit.infinity_server.interfaces.NewsArticleRemote";
		Context context, context1; 
		try {
			context = new InitialContext(jndiProps);
			newsProxy=(NewsSourceRemote)context.lookup(jndiName);
			context1 = new InitialContext(jndiProps);
			articleProxy = (NewsArticleRemote) context.lookup(jndiName1);			
			
			NewsSource source = new NewsSource();
			source.setId(1);
			source.setTitle("Apple");
			source.setDescription("A news source about everything concerning Apple.\n");
			source.setUrl("https://newsapi.org/v2/everything?q=apple&sortBy=popularity&apiKey=a74a9e61aec64107a03439feaac82197");
			source.setImage("https://imageog.flaticon.com/icons/png/512/37/37150.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF");
			NewsSource source1 = new NewsSource();
			source1.setId(2);
			source1.setTitle("Bitcoin");
			source1.setDescription("A news source about everything concerning Bitcoin.\n");
			source1.setUrl("https://newsapi.org/v2/everything?q=bitcoin&sortBy=publishedAt&apiKey=a74a9e61aec64107a03439feaac82197");
			source1.setImage("https://cdn.pixabay.com/photo/2013/12/08/12/12/bitcoin-225079_960_720.png");
			
			NewsSource source2 = new NewsSource();
			source2.setId(3);
			source2.setTitle("Wall Street");
			source2.setDescription("A news source about everything happening in Wall Street.\n");
			source2.setUrl("https://newsapi.org/v2/everything?domains=wsj.com&apiKey=a74a9e61aec64107a03439feaac82197");
			source2.setImage("https://www.poverty-action.org/sites/default/files/styles/default_style/public/thumbnails/image/Wall-Street-Journal-logo.jpg?itok=r0p-ANi3");
			
			
			NewsArticle article = new NewsArticle();
			article.setAuthor("Esprit");
			article.setDescription("Oussama Ben Ghorbel has started developing a GNU like terminal dedicated for traders");
			article.setPublishedAt("2018-03-04");
			article.setTitle("A Tunisian student is willing to change the financial market in an non reversible way");
			article.setUrl("www.facebook.com/Dainerx");
			article.setUrlToImage("https://scontent.ftun5-1.fna.fbcdn.net/v/t1.0-9/17424754_10210583042466219_3653432074743117084_n.jpg?oh=8a8e5716484f05fb5073081fcfd68d10&oe=5B383986");
			articleProxy.addArticle(article);
			
			newsProxy.addNewsSource(source);
			newsProxy.addNewsSource(source1);
			newsProxy.addNewsSource(source2);

			User user = new User();
			user.setId(1);
			currentUser = user;
			newsProxy.testAddUser(user);
			newsProxy.userSubscribeNewsSource(user, source);
			newsProxy.userSubscribeNewsSource(user, source1);
			newsProxy.selectNewsSource(user, source);
			ns = newsProxy.getSelecteddNewsSource(currentUser).getSource();
			supportedNewsSources = newsProxy.getAllNewsSource();
	        subscribedNewsSources = newsProxy.getSubscribedNewsSource(currentUser);
			savedArticles = articleProxy.getAllArticles();
	        buildNewsSources(false);
	    	buildTotalCount();

	        //thread of newssources which is grand parent of the below two threads
	        //thread of articles which is the parent of the below thread
	        //thread of trending articles to be executed last as it is the latest generation of 
            System.out.println("building articles...");
	    	buildArticles(false, ns.getUrl());
	    	/*
	        Timeline articlesCallBacker = new Timeline(new KeyFrame(Duration.seconds(10), (ActionEvent event) -> {
	            if (!threadArticles) {
	                buildArticles(false, ns.getUrl());
	                threadArticles = true;
	            }
	        }));
	        articlesCallBacker.setCycleCount(Timeline.INDEFINITE);
	        articlesCallBacker.play();
	        */
	        
	    	/*
	        Timeline articlesTrendingCallBacker = new Timeline(new KeyFrame(Duration.seconds(5), (ActionEvent event) -> {
	            if (!threadArticlesTrending) {
	                threadArticlesTrending = true;
	            }
	        }));
	        articlesTrendingCallBacker.setCycleCount(Timeline.INDEFINITE);
	        articlesTrendingCallBacker.play();
	        */
            System.out.println("building trending articles...");
            buildTrendingArticles();

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
        setTerminal();
        Timeline terminalCallBack = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
            terminalCursor.setText("> ");
        }));
        terminalCallBack.setCycleCount(Timeline.INDEFINITE);
        terminalCallBack.play();

        Timeline terminalCallBack1 = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
            terminalCursor.setText(" ");

        }));
        terminalCallBack1.setCycleCount(Timeline.INDEFINITE);
        terminalCallBack1.play();

        Timeline quotesCallBacker = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            if (!threadQuotes) {
                buildQuotes();
                threadQuotes = true;
            }
            quotesSeprator.setText(quotesSeprator.getText() + " .");
            quotesUpdateSeprator.setText(quotesUpdateSeprator.getText() + " .");

        }));
        quotesCallBacker.setCycleCount(Timeline.INDEFINITE);
        quotesCallBacker.play();
        Timeline quotesCallBackerSymbols = new Timeline(new KeyFrame(Duration.seconds(30), (ActionEvent event) -> {
            if (stockFlag) {
                buildStockQuotes();
            } else {
                buildQuotes();
            }
        }));
        quotesCallBackerSymbols.setCycleCount(Timeline.INDEFINITE);
        quotesCallBackerSymbols.play();
        

    }

    void buildNewsSources(boolean updateFlag) {
    	if (updateFlag)
    	{
    		supportedNewsSources.clear();
    		supportedNewsSources = newsProxy.getAllNewsSource();
    		subscribedNewsSources = newsProxy.getSubscribedNewsSource(currentUser);
    	}
        ObservableList<Newsource> dataNewsSource = FXCollections.observableArrayList();
        for (NewsSource n : supportedNewsSources) {
        	Newsource n1 = new Newsource();
        	n1 = Adapter.newsSourceAdaptation(n);
        	System.out.println(n1);
        	if(newsProxy.getSelecteddNewsSource(currentUser).getSource().equals(n))
        	{
        		n1.setDescription(n1.getDescription() + "✔✔");
            	dataNewsSource.add(n1);
        		continue;
        	}
        	if (subscribedNewsSources.contains(n))
        	{
        		n1.setDescription(n1.getDescription() + "✔");
        	}
        	else
        	{
        		n1.setDescription(n1.getDescription() + "✖");
        	}
        	dataNewsSource.add(n1);
        }
        newsSources.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Newsource> observable, Newsource oldValue, Newsource newValue) -> {
        	newssourceContainer = newValue;
        });

        newsSources.setCellFactory(v -> new SimpleMediaListCell<>());
        newsSources.setItems(dataNewsSource);
        System.out.println("done");
    }

    void buildTrendingArticles() {
        ObservableList<Article> dataArticles = FXCollections.observableArrayList();
        try {
            String response = HttpRequest.get("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a74a9e61aec64107a03439feaac82197")
                    .accept("application/json")
                    .body();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                if ("null".equals(jsonobject.get("author").toString())) {
                    continue;
                }
                if (jsonobject.get("description").toString().length() < 21) {
                    continue;
                }
                Article a1 = new Article();
                a1.setAuthor(jsonobject.get("author").toString());
                a1.setCoverUrl(jsonobject.get("urlToImage").toString());
                a1.setContent(jsonobject.get("title").toString() + "\n" + jsonobject.get("description").toString().substring(0, 20) + "...\n" + jsonobject.get("publishedAt").toString());
                a1.setUrl(jsonobject.get("url").toString());
                a1.setPublishedAt(jsonobject.getString("publishedAt").toString());
                a1.setTitle(jsonobject.get("title").toString());
                dataArticles.add(a1);
            }

            articlesTrending.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Article> observable, Article oldValue, Article newValue) -> {
                articleTrendingContainer = newValue;
                try {
                    new ProcessBuilder("x-www-browser", newValue.getUrl()).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            articlesTrending.setCellFactory(v -> new SimpleMediaListCell<>());
            articlesTrending.setItems(dataArticles);
        } catch (Exception e) {
            System.out.println("could not fetch articles from selected news source.");
        }
    }

    void buildArticles(boolean flagMy, String url) {
    	area.getChildren().clear();
        if (!flagMy) {
        	System.out.println(ns);
            String response = HttpRequest.get(url)
                    .accept("application/json")
                    .body();
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            int length = jsonArray.length() > 5 ? 5 : jsonArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                if (!(jsonobject.get("author").toString().toUpperCase().charAt(0) >= 65 && jsonobject.get("author").toString().toUpperCase().charAt(0) <= 90)) {
                    continue;
                }
                if (!(jsonobject.get("title").toString().toUpperCase().charAt(0) >= 65 && jsonobject.get("title").toString().toUpperCase().charAt(0) <= 90)) {
                    continue;
                }
                if (!(jsonobject.get("description").toString().toUpperCase().charAt(0) >= 65 && jsonobject.get("description").toString().toUpperCase().charAt(0) <= 90)) {
                    continue;
                }
                if ("null".equals(jsonobject.get("author").toString())) {
                    continue;
                }
                Article a = new Article();
                a.setAuthor(jsonobject.get("author").toString());
                a.setTitle(jsonobject.get("title").toString());
                a.setCoverUrl(jsonobject.get("urlToImage").toString());
                a.setContent(jsonobject.get("description").toString());
                a.setPublishedAt(jsonobject.get("publishedAt").toString());
                a.setUrl(jsonobject.get("url").toString());
                AnchorPane articleArea = new AnchorPane();
                articleArea.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> selectUnselect(articleArea)
                );
                articleArea.setPrefHeight(180.0);
                articleArea.setPrefWidth(1000.0);

                ImageView image = new ImageView("/fxml/img/404Image.jpeg");
                image.setFitHeight(100);
                image.setFitWidth(100);
                image.setLayoutX(0);
                image.setLayoutY(0);
                try {
                    image = new ImageView(a.getCoverUrl());
                    image.setFitHeight(100);
                    image.setFitWidth(100);
                    image.setLayoutX(0);
                    image.setLayoutY(0);
                } catch (Exception e) {
                }
                String titleToLabel = (a.getTitle().length() > 50) ? a.getTitle().substring(0, 50) + "..." : a.getTitle();
                String authorToLabel = (a.getAuthor().length() > 50) ? a.getAuthor().substring(0, 50) + "..." : a.getAuthor();
                Label title = new Label("Title: " + titleToLabel + " Author: " + authorToLabel);
                title.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
                title.setLayoutX(120.0);
                title.setLayoutY(0.0);

                Label content = new Label();
                content.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 14;");
                content.setWrapText(true);
                String contentToLabel = "";
                for (int k = 0; k < Math.ceil(((double) a.getContent().length()) / 150.0d); k++) {
                    if ((150 * (k + 1)) > a.getContent().length() - 1) {
                        contentToLabel += a.getContent().substring((150 * k), a.getContent().length() - 1) + "\n";
                        continue;
                    }
                    contentToLabel += a.getContent().substring((150 * k), (150 * (k + 1))) + "\n";
                }
                contentToLabel += "...";
                content.setText(contentToLabel);
                content.setLayoutX(120.0);
                content.setLayoutY(30.0);

                Label date = new Label("Published at: " + a.getPublishedAt());
                date.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
                date.setLayoutX(1000.0);
                date.setLayoutY(0);

                Label link = new Label("Read more...");
                link.setOnMouseClicked((MouseEvent event) -> {
                    try {
                        articleContainer = a;
                        new ProcessBuilder("x-www-browser", a.getUrl()).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                
                link.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14; -fx-text-fill: #0645AD;");
                link.setLayoutX(120.0);
                link.setLayoutY(105.0);
                articleArea.getChildren().add(image);
                articleArea.getChildren().add(title);
                articleArea.getChildren().add(content);
                articleArea.getChildren().add(date);
                articleArea.getChildren().add(link);
                area.getChildren().add(articleArea);
            }
        } else {
        	savedArticles = articleProxy.getAllArticles();
        	savedArticles.forEach(a1 -> {
        		Article a = Adapter.articleAdaptation(a1);
                AnchorPane articleArea = new AnchorPane();
                articleArea.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> selectUnselect(articleArea));
                articleArea.setPrefHeight(180.0);
                articleArea.setPrefWidth(1000.0);

                ImageView image = new ImageView("/fxml/img/404Image.jpeg");
                image.setFitHeight(100);
                image.setFitWidth(100);
                image.setLayoutX(0);
                image.setLayoutY(0);
                try {
                    image = new ImageView(a.getCoverUrl());
                    image.setFitHeight(100);
                    image.setFitWidth(100);
                    image.setLayoutX(0);
                    image.setLayoutY(0);
                } catch (Exception e) {
                }
                String titleToLabel = (a.getTitle().length() > 50) ? a.getTitle().substring(0, 50) + "..." : a.getTitle();
                String authorToLabel = (a.getAuthor().length() > 50) ? a.getAuthor().substring(0, 50) + "..." : a.getAuthor();
                Label title = new Label("Title: " + titleToLabel + " Author: " + authorToLabel);
                title.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
                title.setLayoutX(120.0);
                title.setLayoutY(0.0);

                Label content = new Label();
                content.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 14;");
                content.setWrapText(true);
                String contentToLabel = "";
                for (int k = 0; k < Math.ceil(((double) a.getContent().length()) / 150.0d); k++) {
                    if ((150 * (k + 1)) > a.getContent().length() - 1) {
                        contentToLabel += a.getContent().substring((150 * k), a.getContent().length() - 1) + "\n";
                        continue;
                    }
                    contentToLabel += a.getContent().substring((150 * k), (150 * (k + 1))) + "\n";
                }
                contentToLabel += "...";
                content.setText(contentToLabel);
                content.setLayoutX(120.0);
                content.setLayoutY(30.0);

                Label date = new Label("Published at: " + a.getPublishedAt());
                date.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
                date.setLayoutX(1000.0);
                date.setLayoutY(0);

                Label link = new Label("Read more...");
                link.setOnMouseClicked((MouseEvent event) -> {
                    try {
                        articleContainer = a;
                        new ProcessBuilder("x-www-browser", a.getUrl()).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                
                link.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14; -fx-text-fill: #0645AD;");
                link.setLayoutX(120.0);
                link.setLayoutY(105.0);
                articleArea.getChildren().add(image);
                articleArea.getChildren().add(title);
                articleArea.getChildren().add(content);
                articleArea.getChildren().add(date);
                articleArea.getChildren().add(link);
                area.getChildren().add(articleArea);
        	});
        }

    }

    void buildQuotes() {
        quotes.getChildren().clear();
        try {
            String response = HttpRequest.get("https://forex.1forge.com/1.0.3/quotes?pairs=EURUSD,GBPJPY,AUDUSD&api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs", true, "q", "baseball gloves", "size", 100)
                    .accept("application/json")
                    .body();

            SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS");
            JSONArray jsonarray = new JSONArray(response);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                Label quote = new Label();
                quote.setWrapText(true);
                String toQuote = jsonobject.getString("symbol") + "\n" + "--------------------------------\n";
                toQuote += "price: " + jsonobject.getDouble("price") + "\n";
                toQuote += "ask: " + jsonobject.getDouble("ask") + "\n";
                toQuote += "bid: " + jsonobject.getDouble("bid") + "\n";
                Date date = new Date(1000 * jsonobject.getLong("timestamp"));
                toQuote += "time: " + sf.format(date) + "\n";
                toQuote += "--------------------------------";
                quote.setText(toQuote);
                quote.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
                quotes.getChildren().add(quote);
            }

            Date current = new Date();
            Label quotesLatestUpdate = new Label("LATEST UPDATE: " + sf.format(current));
            quotesLatestUpdate.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");
            quotesUpdateSeprator = new Label(".");
            quotesUpdateSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");

            quotesUpdate.getChildren().clear();
            quotesUpdate.getChildren().add(quotesLatestUpdate);
            quotesUpdate.getChildren().add(quotesUpdateSeprator);

            quotesSeprator = new Label(".");
            quotesSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
            quotes.getChildren().add(quotesSeprator);
        } catch (HttpRequest.HttpRequestException | JSONException e) {
            Label error = new Label("error: could not fetch data for quotes... \n Please check your internet connection.");
            quotes.getChildren().add(error);
        }
    }

    void buildStockQuotes() {
        quotes.getChildren().clear();
        String[] symbols = {"MSFT", "GOOG", "AAPL"};
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS");
        try {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            SimpleDateFormat sfYesterday = new SimpleDateFormat("yyyy-MM-dd");

            for (String symbol : symbols) {
                String response = HttpRequest.get("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol + "&interval=1min&apikey=R5Y2Z66NSW3CFTXA").accept("application/json").body();
                JSONObject jsonObject = new JSONObject(response);
                JSONObject result = ((JSONObject) ((JSONObject) jsonObject.get("Monthly Time Series")).get(sfYesterday.format(cal.getTime())));
                Label quote = new Label();
                quote.setWrapText(true);
                String toQuote = symbol + "\n" + "--------------------------------\n";
                toQuote += "open: " + result.getDouble("1. open") + "\n";
                toQuote += "high: " + result.getDouble("2. high") + "\n";
                toQuote += "low: " + result.getDouble("3. low") + "\n";
                toQuote += "close: " + result.getDouble("4. close") + "\n";
                toQuote += "volume: " + result.getDouble("5. volume") + "\n";
                Date date = new Date();
                toQuote += "time: " + sf.format(date) + "\n";
                toQuote += "--------------------------------";
                quote.setText(toQuote);
                quote.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
                quotes.getChildren().add(quote);
            }

            Date current = new Date();
            Label quotesLatestUpdate = new Label("LATEST UPDATE: " + sf.format(current));
            quotesLatestUpdate.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");
            quotesUpdateSeprator = new Label(".");
            quotesUpdateSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");

            quotesUpdate.getChildren().clear();
            quotesUpdate.getChildren().add(quotesLatestUpdate);
            quotesUpdate.getChildren().add(quotesUpdateSeprator);

            quotesSeprator = new Label(".");
            quotesSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
            quotes.getChildren().add(quotesSeprator);
        } catch (HttpRequest.HttpRequestException | JSONException e) {
            Label error = new Label("error: could not fetch data for quotes... \n Please check your internet connection.");
            error.setWrapText(true);
            quotes.getChildren().add(error);
        }

    }

    void buildTotalCount() {
        libTotalArticlesFetched.setText("122");
        lbTotalNewsSourceSupported.setText(String.valueOf(supportedNewsSources.stream().count()+1));
        libTotalSavedArticles.setText(String.valueOf(savedArticles.stream().count()));
        libTotalSubscribedNewsSources.setText(String.valueOf(subscribedNewsSources.stream().count()));
        libTotalNewsSources.setText(String.valueOf(supportedNewsSources.stream().count()));
    }

    @FXML
    void stockSelect(ActionEvent event) {
        stockFlag = true;
        forexFlag = false;
        buildStockQuotes();
    }

    @FXML
    void forexSelect(ActionEvent event) {
        stockFlag = false;
        forexFlag = true;
        buildQuotes();
    }

    @FXML
    void saveArticle(ActionEvent event) {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setTitle("Confirmation");
        alertConfirmation.setHeaderText("Addition");
        alertConfirmation.setContentText("Do you really want to add this article to your saved articles?");
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setTitle("Information");
            alertInformation.setHeaderText("Addition");
            alertInformation.setContentText("Article successfully added to your saved articles.");
            alertInformation.show();
            articleProxy.addArticle(Adapter.articleAdptationReverse(articleContainer));
        }
    }

    @FXML
    void saveArticleTrending(ActionEvent event) {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setTitle("Confirmation");
        alertConfirmation.setHeaderText("Addition");
        alertConfirmation.setContentText("Do you really want to add this article to your saved articles?");
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setTitle("Information");
            alertInformation.setHeaderText("Addition");
            alertInformation.setContentText("Article successfully added to your saved articles.");
            alertInformation.show();
            articleProxy.addArticle(Adapter.articleAdptationReverse(articleTrendingContainer));
            System.out.println("done");
        }
    }

    @FXML
    void myArticles(ActionEvent event) {
        buildArticles(true, ns.getUrl());
    }

    @FXML
    void refreshArticles(ActionEvent event) {
        buildArticles(false, ns.getUrl());
    }

    @FXML
    void refreshArticlesTrending(ActionEvent event) {
        buildTrendingArticles();
    }

    @FXML

    void selectNewsSource(ActionEvent event) {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setTitle("Confirmation");
        alertConfirmation.setHeaderText("Selection");
        alertConfirmation.setContentText("Do you really want to select this news source?");
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setTitle("Information");
            alertInformation.setHeaderText("Selection");
            alertInformation.setContentText("News source successfully selected.");
            alertInformation.show();
            newsProxy.selectNewsSource(currentUser, Adapter.newsSourceAdapationReverse(newssourceContainer));
            ns = Adapter.newsSourceAdapationReverse(newssourceContainer);
            buildNewsSources(true);
            buildArticles(false, ns.getUrl());
        }

    }

    @FXML
    void unsubscribeToNewsSource(ActionEvent event) {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setTitle("Confirmation");
        alertConfirmation.setHeaderText("Subscribtion");
        alertConfirmation.setContentText("Do you really want to unsubscribe from this news source?");
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setTitle("Information");
            alertInformation.setHeaderText("Subscribtion");
            alertInformation.setContentText("Unsubscribtion successfully done.");
            alertInformation.show();
            newsProxy.userUnSubscribeNewsSource(currentUser, Adapter.newsSourceAdapationReverse(newssourceContainer));
            buildNewsSources(true);
            System.out.println("done");
        }

    }

    @FXML
    void subscribeToNewsSource(ActionEvent event) {
        Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmation.setTitle("Confirmation");
        alertConfirmation.setHeaderText("Subscribtion");
        alertConfirmation.setContentText("Do you really want to subscribe to this news source?");
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.get() == ButtonType.OK) {
            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
            alertInformation.setTitle("Information");
            alertInformation.setHeaderText("Subscribtion");
            alertInformation.setContentText("Subscribtion successfully done.");
            alertInformation.show();
            newsProxy.userSubscribeNewsSource(currentUser, Adapter.newsSourceAdapationReverse(newssourceContainer));
            buildNewsSources(true);
            System.out.println("done");
        }
    }

    void setTerminal() {
        terminal.getChildren().clear();
        terminalCursor = new Label("> ");
        terminalCursor.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 17;");
        terminal.getChildren().add(terminalCursor);
        terminalContent = new JFXTextField();
        terminalContent.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                executeTerminal();
            }
        });
        terminalContent.setPrefWidth(1800);
        terminalContent.setStyle("-fx-border-width: 0px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-border-color: #000; -fx-font-size: 14; -fx-background-color: rgba(51, 51, 51, 1);");
        terminal.getChildren().add(terminalContent);
    }

    void executeTerminal() {
        String[] input = terminalContent.getText().split(" ");
        String[] params = new String[10];
        switch (input.length) {
            case 4:
                params[0] = input[1];
                params[1] = input[2];
                params[2] = input[3];
                break;

            case 3:
                params[0] = input[1];
                params[1] = input[2];
                break;
            case 2:
                params[0] = input[1];
                break;
            default:
                params[0] = "";
                break;
        }

        terminalContent.setText(Terminal.doAction(input[0], params));
    }

}
