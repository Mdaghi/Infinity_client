
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.github.kevinsawicki.http.HttpRequest;
import java.net.URL;
import java.util.ResourceBundle;
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
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dainer
 */
public class DashboardController implements Initializable {

    static boolean stockFlag = false;
    static boolean forexFlag = true;

    static Article articleTrendingContainer = null;
    static Newsource newssourceContainer = null;

    @FXML
    private ListView newsSources;
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
       /* buildArticles();
        buildNewsSources();
        buildTrendingArticles();
        buildTotalCount();

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

        buildQuotes();
        Timeline quotesCallBacker = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
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
*/
    }

    void buildNewsSources() {
        ObservableList<Newsource> dataNewsSource = FXCollections.observableArrayList();

        Newsource n1 = new Newsource();
        n1.setDescription("A news source about everything concerning Apple.\n ✖");
        n1.setCoverUrl("https://imageog.flaticon.com/icons/png/512/37/37150.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF");
        n1.setName("Apple");
        dataNewsSource.add(n1);

        Newsource n2 = new Newsource();
        n2.setDescription("A news source about everything concerning Bitcoin.\n ✔✔");
        n2.setCoverUrl("https://cdn.pixabay.com/photo/2013/12/08/12/12/bitcoin-225079_960_720.png");
        n2.setName("Bitcoin");
        dataNewsSource.add(n2);

      //  newsSources.setCellFactory(v -> new SimpleMediaListCell<>());
        newsSources.setItems(dataNewsSource);

    }

    void buildTrendingArticles() {
       /* ObservableList<Article> dataArticles = FXCollections.observableArrayList();
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
        
    }

    void buildArticles() {
        for (int i = 0; i < 5; i++) {
            AnchorPane articleArea = new AnchorPane();
            articleArea.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent e) -> selectUnselect(articleArea)
            );
            articleArea.setPrefHeight(180.0);
            articleArea.setPrefWidth(1000.0);

            ImageView image = new ImageView("https://cdn.pixabay.com/photo/2013/12/08/12/12/bitcoin-225079_960_720.png");
            image.setFitHeight(100);
            image.setFitWidth(100);
            image.setLayoutX(0);
            image.setLayoutY(0);

            Label title = new Label("Title: Houssem Nouira TCPC   Author: Oussama Ben Ghorbel");
            title.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
            title.setLayoutX(120.0);
            title.setLayoutY(0.0);

            Label content = new Label();
            content.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 14;");
            content.setWrapText(true);
            content.setText("Content Content Content Content Content Content Content Content Content Content Content Content\nContent Content Content ContentContent Content Content Content\nnContent Content Content ContentContent Content Content Content\nnContent Content Content ContentContent Content Content ContentnContent Content Content ContentContent Content Content Content");
            content.setLayoutX(120.0);
            content.setLayoutY(30.0);

            Label date = new Label("Published at: 2017-12-11");
            date.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
            date.setLayoutX(880.0);
            date.setLayoutY(0);

            Label link = new Label("Read more...");
            link.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14; -fx-text-fill: #0645AD;");
            link.setLayoutX(120.0);
            link.setLayoutY(105.0);

            Separator sep = new Separator();
            sep.setLayoutX(0);
            sep.setLayoutY(120);
            sep.setPrefHeight(4.0);
            sep.setPrefWidth(1250.0);
            sep.setStyle("-fx-font-weight: bold");

            articleArea.getChildren().add(image);
            articleArea.getChildren().add(title);
            articleArea.getChildren().add(content);
            articleArea.getChildren().add(date);
            //articleArea.getChildren().add(sep);
            articleArea.getChildren().add(link);

            area.getChildren().add(articleArea);
        }
        */
    }

    void buildQuotes() {
        quotes.getChildren().clear();
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
        Label quotesLatestUpdate = new Label("LATEST UPDATE: " + sf.format(current).toString());
        quotesLatestUpdate.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");
        quotesUpdateSeprator = new Label(".");
        quotesUpdateSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");

        quotesUpdate.getChildren().clear();
        quotesUpdate.getChildren().add(quotesLatestUpdate);
        quotesUpdate.getChildren().add(quotesUpdateSeprator);

        quotesSeprator = new Label(".");
        quotesSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        quotes.getChildren().add(quotesSeprator);
    }

    void buildStockQuotes() {
        quotes.getChildren().clear();
        String[] symbols = {"MSFT", "GOOG", "AAPL"};
        SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS");
        for (int i = 0; i < symbols.length; i++) {
            String response = HttpRequest.get("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + symbols[i] + "&interval=1min&apikey=R5Y2Z66NSW3CFTXA")
                    .accept("application/json")
                    .body();
            JSONObject jsonObject = new JSONObject(response);
            JSONObject result = ((JSONObject) ((JSONObject) jsonObject.get("Monthly Time Series")).get("2018-03-02"));
            Label quote = new Label();
            quote.setWrapText(true);
            String toQuote = symbols[i] + "\n" + "--------------------------------\n";
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
        Label quotesLatestUpdate = new Label("LATEST UPDATE: " + sf.format(current).toString());
        quotesLatestUpdate.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");
        quotesUpdateSeprator = new Label(".");
        quotesUpdateSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 14;");

        quotesUpdate.getChildren().clear();
        quotesUpdate.getChildren().add(quotesLatestUpdate);
        quotesUpdate.getChildren().add(quotesUpdateSeprator);

        quotesSeprator = new Label(".");
        quotesSeprator.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF;");
        quotes.getChildren().add(quotesSeprator);

    }

    void buildTotalCount() {
        libTotalArticlesFetched.setText("80");
        lbTotalNewsSourceSupported.setText("4");
        libTotalSavedArticles.setText("2");
        libTotalSubscribedNewsSources.setText("1");
        libTotalNewsSources.setText("2");
        

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

    }

    @FXML
    void saveArticleTrending(ActionEvent event) {
    }

    @FXML
    void myArticles(ActionEvent event) {

    }

    @FXML
    void refreshArticles(ActionEvent event) {

    }

    @FXML
    void refreshArticlesTrending(ActionEvent event) {
        buildTrendingArticles();
    }

    @FXML

    void selectNewsSource(ActionEvent event) {

    }

    @FXML
    void unsubscribeToNewsSource(ActionEvent event) {

    }

    @FXML
    void subscribeToNewsSource(ActionEvent event) {

    }

    void setTerminal() {
        terminal.getChildren().clear();
        terminalCursor = new Label("> ");
        terminalCursor.setStyle("-fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-font-size: 17;");
        terminal.getChildren().add(terminalCursor);
        terminalContent = new JFXTextField();
      /*  terminalContent.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                executeTerminal();
            }
        });*/
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
