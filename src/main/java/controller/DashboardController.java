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
import java.util.TreeSet;

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
import javafx.scene.control.ToggleButton;
import com.guigarage.controls.*;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import java.util.Locale;
import javax.speech.Engine;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.synthesis.SynthesizerModeDesc;
import javax.speech.synthesis.Synthesizer;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import com.sun.speech.freetts.*;

import java.util.List;

import java.awt.Desktop;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableCell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
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
import javafx.util.Callback;
import javafx.util.Duration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javafx.scene.control.TableRow;
import javafx.css.PseudoClass;
import javafx.beans.property.SimpleStringProperty;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

	static boolean terminalFlag = false;
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

	static Set<Stock> generalStocks = new HashSet<>();
	static boolean threadGeneralStocks = false;

	@FXML
	JFXToggleButton articlePerspectiveButton;
	@FXML
	JFXToggleButton stockPerspectiveButton;

	@FXML
	ToggleButton activateTerminalButton;
	@FXML
	ToggleButton saveArticleButton;
	@FXML
	ToggleButton myArticlesButton;
	@FXML
	ToggleButton refreshArticlesButton;
	@FXML
	ToggleButton saveStockButton;
	@FXML
	ToggleButton myStocksButton;
	@FXML
	ToggleButton removeStockButton;
	@FXML
	ToggleButton generateCommentButton;

	@FXML
	private ImageView talkingRobot; 
	@FXML
	private JFXTextArea robotDialog;
	@FXML
	Label titleMain;
	@FXML
	private ScrollPane areaContainer;
	@FXML
	private VBox area;
	@FXML
	private TableView<Stock> stockTable;
	@FXML
	private TableView<Stock> mystockTable;
	@FXML
	private TableColumn<Stock, String> _symbol;
	@FXML
	private TableColumn<Stock, Float> _open;
	@FXML
	private TableColumn<Stock, Float> _high;
	@FXML
	private TableColumn<Stock, Float> _low;
	@FXML
	private TableColumn<Stock, Float> _close;
	@FXML
	private TableColumn<Stock, Long> _volume;
	@FXML
	private TableColumn<Stock, Float> _variation;
	@FXML
	private TableColumn<Stock, String> _comment;
	@FXML
	private TableColumn<Stock, String> _symbolGeneral;
	@FXML
	private TableColumn<Stock, Float> _openGeneral;
	@FXML
	private TableColumn<Stock, Float> _highGeneral;
	@FXML
	private TableColumn<Stock, Float> _lowGeneral;
	@FXML
	private TableColumn<Stock, Float> _closeGeneral;
	@FXML
	private TableColumn<Stock, Long> _volumeGeneral;
	@FXML
	private TableColumn<Stock, Float> _variationGeneral;
	@FXML
	private TableColumn<Stock, String> _commentGeneral;
	@FXML
	private ObservableList<Stock> generalStocksObservableList;
	@FXML
	private ObservableList<Stock> myStocksObservableList;

	@FXML
	private ListView<Newsource> newsSources;
	@FXML
	private ListView<Article> articlesTrending;
	private Stock stockHolder;

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
	private ToggleButton forexButton;
	@FXML
	private ToggleButton stockButton;
	private Label terminalCursor;
	private Label quotesSeprator;
	private Label quotesUpdateSeprator;

	private Context contextArticleProxy;
	private Context contextNewsSourceProxy;
	private Context contextStockProxy;
	private NewsSourceRemote newsProxy;
	private NewsArticleRemote articleProxy;
	private StockRemote stockProxy;
	private NewsSource ns;
	private User currentUser;

	private List<NewsSource> supportedNewsSources;
	private List<NewsSource> subscribedNewsSources;
	private List<NewsArticle> cachedArticles;
	private List<NewsArticle> savedArticles;
	private List<Stock> userStocks;

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
		cachedArticles = new ArrayList<>();
		Properties jndiProps = new Properties();
		jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		String jndiName = "infinity_server-ear/infinity_server-ejb/ServiceNewsSource!tn.esprit.infinity_server.interfaces.NewsSourceRemote",
				jndiName1 = "infinity_server-ear/infinity_server-ejb/ServiceNewsArticle!tn.esprit.infinity_server.interfaces.NewsArticleRemote",
				jndiName2 = "infinity_server-ear/infinity_server-ejb/ServiceStock!tn.esprit.infinity_server.interfaces.StockRemote";
		try {
			contextNewsSourceProxy = new InitialContext(jndiProps);
			newsProxy = (NewsSourceRemote) contextNewsSourceProxy.lookup(jndiName);
			contextArticleProxy = new InitialContext(jndiProps);
			articleProxy = (NewsArticleRemote) contextArticleProxy.lookup(jndiName1);
			contextStockProxy = new InitialContext(jndiProps);
			stockProxy = (StockRemote) contextStockProxy.lookup(jndiName2);
			System.out.println("done building the stock proxy");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prepareArticles();
		prepareNewsSources();
		prepareTableViews();
		preparePerspective();
	}

	/* START OF INTERFACE PREP */

	void prepareTableViews() {
		_symbol.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		_open.setCellValueFactory(new PropertyValueFactory<>("open"));
		_high.setCellValueFactory(new PropertyValueFactory<>("high"));
		_low.setCellValueFactory(new PropertyValueFactory<>("low"));
		_close.setCellValueFactory(new PropertyValueFactory<>("close"));
		_volume.setCellValueFactory(new PropertyValueFactory<>("volume"));
		_variation.setCellValueFactory(new PropertyValueFactory<>("variation"));
		_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
		_symbolGeneral.setCellValueFactory(new PropertyValueFactory<>("symbol"));
		_openGeneral.setCellValueFactory(new PropertyValueFactory<>("open"));
		_highGeneral.setCellValueFactory(new PropertyValueFactory<>("high"));
		_lowGeneral.setCellValueFactory(new PropertyValueFactory<>("low"));
		_closeGeneral.setCellValueFactory(new PropertyValueFactory<>("close"));
		_volumeGeneral.setCellValueFactory(new PropertyValueFactory<>("volume"));
		_variationGeneral.setCellValueFactory(new PropertyValueFactory<>("variation"));
		_commentGeneral.setCellValueFactory(new PropertyValueFactory<>("comment"));

		stockTable.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Stock> observable, Stock oldValue, Stock newValue) -> {
					stockHolder = newValue;
				});

		mystockTable.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Stock> observable, Stock oldValue, Stock newValue) -> {
					stockHolder = newValue;
				});

		mystockTable.setRowFactory(tv -> {
			TableRow<Stock> row = new TableRow<>();
			row.itemProperty().addListener((obs, oldItem, newItem) -> {
				if (newItem != null) {
					if (newItem.getVariation() >= 0) {
						row.setStyle("-fx-background-color: #90ee90;" + " -fx-background-insets: 0, 1, 2;"
								+ " -fx-background: -fx-accent;" + " -fx-text-fill: -fx-selection-bar-text;");
					} else {
						row.setStyle("-fx-background-color: #FF5D5D;" + " -fx-background-insets: 0, 1, 2;"
								+ " -fx-background: -fx-accent;" + " -fx-text-fill: -fx-selection-bar-text;");
					}
				}
			});
			return row;
		});

		stockTable.setRowFactory(tv -> {
			TableRow<Stock> row = new TableRow<>();
			row.itemProperty().addListener((obs, oldItem, newItem) -> {
				if (newItem != null) {
					if (newItem.getVariation() >= 0) {
						row.setStyle("-fx-background-color: #90ee90;" + " -fx-background-insets: 0, 1, 2;"
								+ " -fx-background: -fx-accent;" + " -fx-text-fill: -fx-selection-bar-text;");
					} else {
						row.setStyle("-fx-background-color: #FF5D5D;" + " -fx-background-insets: 0, 1, 2;"
								+ " -fx-background: -fx-accent;" + " -fx-text-fill: -fx-selection-bar-text;");
					}
				}
			});
			return row;
		});

		stockTable.setVisible(false);
		mystockTable.setVisible(false);

	}

	void prepareTerminal() {
		setTerminal();

		Timeline terminalCallBack = new Timeline(new KeyFrame(Duration.seconds(2), (ActionEvent event) -> {
			if (terminalFlag)
				terminalCursor.setText("> ");
		}));
		terminalCallBack.setCycleCount(Timeline.INDEFINITE);
		terminalCallBack.play();

		Timeline terminalCallBack1 = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
			if (terminalFlag)
				terminalCursor.setText(" ");
		}));
		terminalCallBack1.setCycleCount(Timeline.INDEFINITE);
		terminalCallBack1.play();

		Timeline quotesCallBacker = new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
			if (terminalFlag) {
				if (!threadQuotes) {
					buildQuotes();
					threadQuotes = true;
				}
				quotesSeprator.setText(quotesSeprator.getText() + " .");
				quotesUpdateSeprator.setText(quotesUpdateSeprator.getText() + " .");
			}
		}));
		quotesCallBacker.setCycleCount(Timeline.INDEFINITE);
		quotesCallBacker.play();

		Timeline quotesCallBackerSymbols = new Timeline(new KeyFrame(Duration.seconds(30), (ActionEvent event) -> {
			if (terminalFlag) {
				if (stockFlag) {
					buildStockQuotes();
				} else {
					buildQuotes();
				}
			}
		}));

		quotesCallBackerSymbols.setCycleCount(Timeline.INDEFINITE);
		quotesCallBackerSymbols.play();
	}

	void preparePerspective() {
		robotDialog.setVisible(false);
		talkingRobot.setVisible(false);
		generateCommentButton.setVisible(false);
		saveArticleButton.setVisible(false);
		myArticlesButton.setVisible(false);
		refreshArticlesButton.setVisible(false);
		saveStockButton.setVisible(false);
		myStocksButton.setVisible(false);
		removeStockButton.setVisible(false);
		titleMain.setText("No perspective selected...");
		articlePerspectiveButton.setOnAction(e -> {
			titleMain.setText("Articles");
			if (stockPerspectiveButton.isSelected())
				stockPerspectiveButton.setSelected(false);
			if (!(articlePerspectiveButton.isSelected())) {
				generateCommentButton.setVisible(false);
				saveArticleButton.setVisible(false);
				myArticlesButton.setVisible(false);
				refreshArticlesButton.setVisible(false);
				saveStockButton.setVisible(false);
				myStocksButton.setVisible(false);
				removeStockButton.setVisible(false);
				titleMain.setText("No perspective selected...");
				area.getChildren().clear();
			} else {
				generateCommentButton.setVisible(false);
				mystockTable.setVisible(false);
				stockTable.setVisible(false);
				areaContainer.setVisible(true);
				saveArticleButton.setVisible(true);
				myArticlesButton.setVisible(true);
				refreshArticlesButton.setVisible(true);
				saveStockButton.setVisible(false);
				myStocksButton.setVisible(false);
				removeStockButton.setVisible(false);
				buildArticles(false, ns.getUrl());
			}
		});
		stockPerspectiveButton.setOnAction(e -> {
			titleMain.setText("Stocks");
			if (articlePerspectiveButton.isSelected())
				articlePerspectiveButton.setSelected(false);
			if (!(stockPerspectiveButton.isSelected())) {
				titleMain.setText("No perspective selected...");
				generateCommentButton.setVisible(false);
				saveArticleButton.setVisible(false);
				myArticlesButton.setVisible(false);
				refreshArticlesButton.setVisible(false);
				saveStockButton.setVisible(false);
				myStocksButton.setVisible(false);
				removeStockButton.setVisible(false);
				area.getChildren().clear();
				mystockTable.setVisible(false);
				stockTable.setVisible(false);
			} else {
				saveArticleButton.setVisible(false);
				myArticlesButton.setVisible(false);
				refreshArticlesButton.setVisible(false);
				generateCommentButton.setVisible(false);
				saveStockButton.setVisible(true);
				myStocksButton.setVisible(true);
				removeStockButton.setVisible(true);
				areaContainer.setVisible(false);
				buildStocks();
			}
		});
	}

	void prepareNewsSources() {
		NewsSource source = new NewsSource();
		source.setId(1);
		source.setTitle("Apple");
		source.setDescription("A news source about everything concerning Apple.\n");
		source.setUrl(
				"https://newsapi.org/v2/everything?q=apple&sortBy=popularity&apiKey=a74a9e61aec64107a03439feaac82197");
		source.setImage(
				"https://imageog.flaticon.com/icons/png/512/37/37150.png?size=1200x630f&pad=10,10,10,10&ext=png&bg=FFFFFFFF");
		NewsSource source1 = new NewsSource();
		source1.setId(2);
		source1.setTitle("Bitcoin");
		source1.setDescription("Everything concerning Bitcoin.\n");
		source1.setUrl(
				"https://newsapi.org/v2/everything?q=bitcoin&sortBy=publishedAt&apiKey=a74a9e61aec64107a03439feaac82197");
		source1.setImage("https://cdn.pixabay.com/photo/2013/12/08/12/12/bitcoin-225079_960_720.png");

		NewsSource source2 = new NewsSource();
		source2.setId(3);
		source2.setTitle("Wall Street");
		source2.setDescription("Everything happening in Wall Street.\n");
		source2.setUrl("https://newsapi.org/v2/everything?domains=wsj.com&apiKey=a74a9e61aec64107a03439feaac82197");
		source2.setImage(
				"https://www.poverty-action.org/sites/default/files/styles/default_style/public/thumbnails/image/Wall-Street-Journal-logo.jpg?itok=r0p-ANi3");

		NewsSource source3 = new NewsSource();
		source3.setId(4);
		source3.setTitle("Netflix");
		source3.setDescription("Everything concerning Netflix.\n");
		source3.setUrl(
				"https://newsapi.org/v2/everything?q=netflix&sortBy=popularity&apiKey=a74a9e61aec64107a03439feaac82197");
		source3.setImage(
				"https://cdn.vox-cdn.com/thumbor/Yq1Vd39jCBGpTUKHUhEx5FfxvmM=/39x0:3111x2048/1200x800/filters:focal(39x0:3111x2048)/cdn.vox-cdn.com/uploads/chorus_image/image/49901753/netflixlogo.0.0.png");
		NewsSource source4 = new NewsSource();
		source4.setId(5);
		source4.setTitle("Facebook");
		source4.setDescription("Everything concerning FB.\n");
		source4.setUrl(
				"https://newsapi.org/v2/everything?q=facebook&sortBy=popularity&apiKey=a74a9e61aec64107a03439feaac82197");
		source4.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/F_icon.svg/2000px-F_icon.svg.png");

		NewsSource source5 = new NewsSource();
		source5.setId(6);
		source5.setTitle("Amazon");
		source5.setDescription("Everything concerning Amazon.\n");
		source5.setUrl(
				"https://newsapi.org/v2/everything?q=amazon&sortBy=popularity&apiKey=a74a9e61aec64107a03439feaac82197");
		source5.setImage("http://media.corporate-ir.net/media_files/IROL/17/176060/img/logos/amazon_logo_RGB.jpg");

		newsProxy.addNewsSource(source);
		newsProxy.addNewsSource(source1);
		newsProxy.addNewsSource(source2);
		newsProxy.addNewsSource(source3);
		newsProxy.addNewsSource(source4);
		newsProxy.addNewsSource(source5);

		User user = new User();
		user.setId(1);
		currentUser = user;
		newsProxy.testAddUser(user);

		/*
		 * newsProxy.userSubscribeNewsSource(user, source);
		 * newsProxy.userSubscribeNewsSource(user, source1);
		 * newsProxy.selectNewsSource(user, source);
		 */

		ns = newsProxy.getSelecteddNewsSource(currentUser).getSource();
		supportedNewsSources = newsProxy.getAllNewsSource();
		subscribedNewsSources = newsProxy.getSubscribedNewsSource(currentUser);
		buildNewsSources(false);
		buildTotalCount();
	}

	void prepareArticles() {
		myStocksObservableList = FXCollections.observableArrayList();
		stockProxy.getAllStock().forEach(s -> {
			myStocksObservableList.add(s);
		});
		savedArticles = articleProxy.getAllArticles();
		System.out.println("building trending articles...");
		buildTrendingArticles();

	}
	/* END OF INTERFACE PREP */

	/* START OF NEWS SOURCES LOGIC */

	void buildNewsSources(boolean updateFlag) {
		if (updateFlag) {
			supportedNewsSources.clear();
			supportedNewsSources = newsProxy.getAllNewsSource();
			subscribedNewsSources = newsProxy.getSubscribedNewsSource(currentUser);
		}
		ObservableList<Newsource> dataNewsSource = FXCollections.observableArrayList();
		for (NewsSource n : supportedNewsSources) {
			Newsource n1 = new Newsource();
			n1 = Adapter.newsSourceAdaptation(n);
			if (newsProxy.getSelecteddNewsSource(currentUser).getSource().equals(n)) {
				n1.setDescription(n1.getDescription() + "✔✔");
				dataNewsSource.add(n1);
				continue;
			}
			if (subscribedNewsSources.contains(n)) {
				n1.setDescription(n1.getDescription() + "✔");
			} else {
				n1.setDescription(n1.getDescription() + "✖");
			}
			dataNewsSource.add(n1);
		}
		newsSources.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends Newsource> observable, Newsource oldValue, Newsource newValue) -> {
					newssourceContainer = newValue;
				});

		newsSources.setCellFactory(v -> new SimpleMediaListCell<>());
		newsSources.setItems(dataNewsSource);
		System.out.println("****** done building news sources *****");
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
			cachedArticles.removeAll(cachedArticles);
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
		}
	}

	/* END OF NEWSSOURCES LOGIC */

	/* START OF ARTICLES LOGIC */

	void buildTrendingArticles() {
		ObservableList<Article> dataArticles = FXCollections.observableArrayList();
		try {
			String response = HttpRequest.get(
					"https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=a74a9e61aec64107a03439feaac82197")
					.accept("application/json").body();
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
				a1.setContent(jsonobject.get("title").toString() + "\n"
						+ jsonobject.get("description").toString().substring(0, 20) + "...\n"
						+ jsonobject.get("publishedAt").toString());
				a1.setUrl(jsonobject.get("url").toString());
				a1.setPublishedAt(jsonobject.getString("publishedAt").toString());
				a1.setTitle(jsonobject.get("title").toString());
				dataArticles.add(a1);
			}

			articlesTrending.getSelectionModel().selectedItemProperty().addListener(
					(ObservableValue<? extends Article> observable, Article oldValue, Article newValue) -> {
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
		System.out.println("***** done fetching trending articles from the web *****");
	}

	void buildArticles(boolean flagMy, String url) {
		area.getChildren().clear();
		if (!flagMy) {
			if (cachedArticles.isEmpty()) {
				String response = HttpRequest.get(url).accept("application/json").body();
				JSONObject jsonObject = new JSONObject(response);
				JSONArray jsonArray = jsonObject.getJSONArray("articles");
				int length = jsonArray.length() > 8 ? 8 : jsonArray.length();
				for (int i = 0; i < length; i++) {
					JSONObject jsonobject = jsonArray.getJSONObject(i);
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
					Article a = new Article();
					a.setAuthor(jsonobject.get("author").toString());
					a.setTitle(jsonobject.get("title").toString());
					a.setCoverUrl(jsonobject.get("urlToImage").toString());
					a.setContent(jsonobject.get("description").toString());
					a.setPublishedAt(jsonobject.get("publishedAt").toString());
					a.setUrl(jsonobject.get("url").toString());
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
					String titleToLabel = (a.getTitle().length() > 50) ? a.getTitle().substring(0, 50) + "..."
							: a.getTitle();
					String authorToLabel = (a.getAuthor().length() > 50) ? a.getAuthor().substring(0, 50) + "..."
							: a.getAuthor();
					Label title = new Label(
							"Title: " + titleToLabel + "                         Author: " + authorToLabel);
					title.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
					title.setLayoutX(120.0);
					title.setLayoutY(0.0);

					TextArea content = new TextArea();
					content.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 14; -fx-text-fill: #000;");
					content.setWrapText(true);
					content.setPrefColumnCount(88);
					content.setPrefRowCount(3);
					String contentToLabel = "";
					contentToLabel += a.getContent();
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

					link.setStyle(
							"-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14; -fx-text-fill: #0645AD;");
					link.setLayoutX(130.0);
					link.setLayoutY(135.0);
					articleArea.getChildren().add(image);
					articleArea.getChildren().add(title);
					articleArea.getChildren().add(content);
					articleArea.getChildren().add(date);
					articleArea.getChildren().add(link);
					area.getChildren().add(articleArea);
					cachedArticles.add(Adapter.articleAdptationReverse(a));
				}
				System.out.println("****** done building articles from web ******");
			} else {
				cachedArticles.forEach(a1 -> {
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
					String titleToLabel = (a.getTitle().length() > 50) ? a.getTitle().substring(0, 50) + "..."
							: a.getTitle();
					String authorToLabel = (a.getAuthor().length() > 50) ? a.getAuthor().substring(0, 50) + "..."
							: a.getAuthor();
					Label title = new Label(
							"Title: " + titleToLabel + "                         Author: " + authorToLabel);
					title.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
					title.setLayoutX(120.0);
					title.setLayoutY(0.0);

					TextArea content = new TextArea();
					content.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 14; -fx-text-fill: #000;");
					content.setWrapText(true);
					content.setPrefColumnCount(88);
					content.setPrefRowCount(3);
					String contentToLabel = "";
					contentToLabel += a.getContent();
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

					link.setStyle(
							"-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14; -fx-text-fill: #0645AD;");
					link.setLayoutX(130.0);
					link.setLayoutY(135.0);
					articleArea.getChildren().add(image);
					articleArea.getChildren().add(title);
					articleArea.getChildren().add(content);
					articleArea.getChildren().add(date);
					articleArea.getChildren().add(link);
					area.getChildren().add(articleArea);
				});
				System.out.println("****** done building articles from cache ******");
			}
		} else {
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
				String titleToLabel = (a.getTitle().length() > 50) ? a.getTitle().substring(0, 50) + "..."
						: a.getTitle();
				String authorToLabel = (a.getAuthor().length() > 50) ? a.getAuthor().substring(0, 50) + "..."
						: a.getAuthor();
				Label title = new Label("Title: " + titleToLabel + "                         Author: " + authorToLabel);
				title.setStyle("-fx-font-family: \"Open Sans Semibold\"; -fx-font-size: 14;");
				title.setLayoutX(120.0);
				title.setLayoutY(0.0);

				TextArea content = new TextArea();
				content.setStyle("-fx-font-family: \"Open Sans\"; -fx-font-size: 14; -fx-text-fill: #000;");
				content.setWrapText(true);
				content.setPrefColumnCount(88);
				content.setPrefRowCount(3);
				String contentToLabel = "";
				contentToLabel += a.getContent();
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
				link.setLayoutX(130.0);
				link.setLayoutY(135.0);
				articleArea.getChildren().add(image);
				articleArea.getChildren().add(title);
				articleArea.getChildren().add(content);
				articleArea.getChildren().add(date);
				articleArea.getChildren().add(link);
				area.getChildren().add(articleArea);
			});
			System.out.println("****** done building my articles ******");
		}
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
			savedArticles.add(Adapter.articleAdptationReverse(articleContainer));
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
			savedArticles.add(Adapter.articleAdptationReverse(articleTrendingContainer));
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

	/* END OF ARTICLES LOGIC */

	/* START OF STOCKS LOGIC */

	void buildStocks() {
		generateCommentButton.setVisible(false);
		if (!threadGeneralStocks) {
			String[] symbolsContainer = { "AMZN", "AAPL", "BAC", "BMW", "DIS", "INTC", "GOOG", "NFLX", "MSFT", "WMT" };
			String[] namesContainer = { "amazon", "apple", "bank of america corp", "bayerische motoren werke ag",
					"walt disney co", "intel corporation", "google", "netflix", "microsoft", "walmart" };
			for (int i = 0; i < symbolsContainer.length; i++) {
				System.out.println("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol="
						+ symbolsContainer[i] + "&interval=1min&apikey=R5Y2Z66NSW3CFTXA");
				String response = HttpRequest
						.get("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol="
								+ symbolsContainer[i] + "&interval=1min&apikey=R5Y2Z66NSW3CFTXA")
						.accept("application/json").body();
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
				JSONObject resultPrevious = ((JSONObject) ((JSONObject) jsonObject.get("Monthly Time Series"))
						.get(keys[1]));

				Stock s = new Stock();
				s.setSymbol(symbolsContainer[i]);
				s.setName(namesContainer[i]);
				s.setOpen((float) (result.getDouble("1. open")));
				s.setHigh((float) (result.getDouble("2. high")));
				s.setLow((float) (result.getDouble("3. low")));
				s.setClose((float) (result.getDouble("4. close")));
				s.setVolume((long) (result.getDouble("5. volume")));
				s.setVariation(s.getOpen() - (float) resultPrevious.getDouble("1. open"));
				s.setComment(
						"In the stock market, there really aren't any guarantees at all! In fact, if you think back several years, you could lose half your portfolio in months if the stock market crashes. However, the prices of stocks are based on the value of the companies. The only time a stock goes to $0 is if the company is worthless");
				generalStocks.add(s);
			}
			generalStocksObservableList = FXCollections.observableArrayList();
			System.out.println("Result of fetching : " + generalStocks);
			generalStocks.forEach(s -> {
				generalStocksObservableList.add(s);
			});
			stockTable.setItems(generalStocksObservableList);
			stockTable.setVisible(true);
			threadGeneralStocks = true;
		} else
			stockTable.setVisible(true);
	}

	void buildMyStocks() {
		generateCommentButton.setVisible(true);
		titleMain.setText("My Watched Stocks");
		mystockTable.setItems(myStocksObservableList);
		stockTable.setVisible(false);
		mystockTable.setVisible(true);
	}

	@FXML
	void saveStock(ActionEvent event) {
		Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
		alertConfirmation.setTitle("Confirmation");
		alertConfirmation.setHeaderText("Addition");
		alertConfirmation.setContentText("Do you really want to add this article to your saved articles?");
		Optional<ButtonType> result = alertConfirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
			Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
			alertInformation.setTitle("Information");
			alertInformation.setHeaderText("Addition");
			alertInformation.setContentText("Stock added to your watched stocks successfully.");
			alertInformation.show();
			stockProxy.addStock(stockHolder);
			myStocksObservableList.add(stockHolder);
		}

	}

	@FXML
	void removeStock(ActionEvent event) {
		Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
		alertConfirmation.setTitle("Confirmation");
		alertConfirmation.setHeaderText("Delete");
		alertConfirmation.setContentText("Do you really want to delete this stock from your stock perspective?");
		Optional<ButtonType> result = alertConfirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
			Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
			alertInformation.setTitle("Information");
			alertInformation.setHeaderText("Delete");
			alertInformation.setContentText("Stock successfully deleted from your stock perspective.");
			alertInformation.show();
			myStocksObservableList.remove(stockHolder);
			stockProxy.deleteStock(stockHolder);
			buildMyStocks();
		}
	}

	@FXML
	void myStocks(ActionEvent event) {
		if (!myStocksButton.isSelected()) {
			titleMain.setText("Stocks");
			buildStocks();
		} else {
			titleMain.setText("My Watched Stocks");
			buildMyStocks();
		}
	}

	/* END OF STOCKS LOGIC */

	/* START OF TERMINAL LOGIC */

	void buildQuotes() {
		quotes.getChildren().clear();
		try {
			String response = HttpRequest.get(
					"https://forex.1forge.com/1.0.3/quotes?pairs=EURUSD,GBPJPY,AUDUSD&api_key=mxQ7qIR5ttl5x1bjQbmoivWLZRR2xiqs",
					true, "q", "baseball gloves", "size", 100).accept("application/json").body();

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
			Label error = new Label(
					"error: could not fetch data for quotes... \n Please check your internet connection.");
			quotes.getChildren().add(error);
		}
	}

	void buildStockQuotes() {
		quotes.getChildren().clear();
		String[] symbols = { "MSFT", "GOOG", "AAPL" };
		SimpleDateFormat sf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss.SSS");
		try {
			final Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			SimpleDateFormat sfYesterday = new SimpleDateFormat("yyyy-MM-dd");

			for (String symbol : symbols) {
				String response = HttpRequest
						.get("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + symbol
								+ "&interval=1min&apikey=R5Y2Z66NSW3CFTXA")
						.accept("application/json").body();
				JSONObject jsonObject = new JSONObject(response);
				JSONObject result = ((JSONObject) ((JSONObject) jsonObject.get("Monthly Time Series"))
						.get(((JSONObject) jsonObject.get("Monthly Time Series")).keySet().iterator().next()));
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
			Label error = new Label(
					"error: could not fetch data for quotes... \n Please check your internet connection.");
			error.setWrapText(true);
			quotes.getChildren().add(error);
		}

	}

	void buildTotalCount() {
		libTotalSavedArticles.setText(String.valueOf(savedArticles.stream().count()));
		libTotalSubscribedNewsSources.setText(String.valueOf(subscribedNewsSources.stream().count()));
		libTotalNewsSources.setText(String.valueOf(supportedNewsSources.stream().count()));
	}

	@FXML
	void stockSelect(ActionEvent event) {
		forexButton.setSelected(false);
		stockFlag = true;
		forexFlag = false;
		buildStockQuotes();
	}

	@FXML
	void forexSelect(ActionEvent event) {
		stockButton.setSelected(false);
		stockFlag = false;
		forexFlag = true;
		buildQuotes();
	}

	@FXML
	void activateTerminal(ActionEvent event) {
		if (!activateTerminalButton.isSelected()) {
			Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
			alertConfirmation.setTitle("Confirmation");
			alertConfirmation.setHeaderText("Activation");
			alertConfirmation.setContentText(
					"Do you really want to activate the Oussama's terminal?\nThis functionality requires a high speed internet.");
			Optional<ButtonType> result = alertConfirmation.showAndWait();
			if (result.get() == ButtonType.OK) {
				Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
				alertInformation.setTitle("Information");
				alertInformation.setHeaderText("Activation");
				alertInformation.setContentText("Terminal successfully activated.");
				alertInformation.show();
				activateTerminalButton.setText("Desactivate");
				prepareTerminal();
			}
		} else {
			Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
			alertConfirmation.setTitle("Confirmation");
			alertConfirmation.setHeaderText("Desactivation");
			alertConfirmation.setContentText(
					"Do you really want to desactivate the terminal?\nThis functionality can be reactivated.");
			Optional<ButtonType> result = alertConfirmation.showAndWait();
			if (result.get() == ButtonType.OK) {
				Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
				alertInformation.setTitle("Information");
				alertInformation.setHeaderText("Desactivation");
				alertInformation.setContentText("Terminal successfully desactivated.");
				alertInformation.show();
				activateTerminalButton.setText("Activate");
				unsetTerminal();
			}
		}
	}

	void unsetTerminal() {
		terminalFlag = false;
		terminal.getChildren().clear();
		quotes.getChildren().clear();
		quotesUpdate.getChildren().clear();
	}

	void setTerminal() {
		terminalFlag = true;
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
		terminalContent.setStyle(
				"-fx-border-width: 0px; -fx-font-weight: bold; -fx-text-fill: #FFFFFF; -fx-border-color: #000; -fx-font-size: 14; -fx-background-color: rgba(51, 51, 51, 1);");
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

	/* END OF TERMINAL LOGIC */

	@FXML
	void generateStockComment() {
		mystockTable.setVisible(false);
		talkingRobot.setVisible(true);
		robotDialog.setVisible(true);
		Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
		alertConfirmation.setTitle("Confirmation");
		alertConfirmation.setHeaderText("Addition");
		alertConfirmation.setContentText(
				"Do you really want to activate the Oussama's AI Stock?\nThis functionality stills on alpha version.");
		titleMain.setText("Please wait for comment generation");
		Optional<ButtonType> result = alertConfirmation.showAndWait();
		
		String comment = StockCommenter.generateComment(stockHolder.getName(), stockHolder.getVariation(),
				stockHolder.getHigh(), new Date().toString());
		robotDialog.setText(comment);
		if (result.get() == ButtonType.OK) {
			stockHolder.setComment(comment);
			stockProxy.updateStock(stockHolder);
			myStocksObservableList.clear();
			stockProxy.getAllStock().forEach(s -> {
				myStocksObservableList.add(s);
			});
		
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			try {
				Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
				Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
				synthesizer.allocate();
				synthesizer.resume();
				synthesizer.speakPlainText(comment, null);
				synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
				synthesizer.deallocate();
			} catch (EngineException | AudioException | EngineStateError | IllegalArgumentException | InterruptedException e1) {
				e1.printStackTrace();
			}
			
			mystockTable.setVisible(true);
			buildMyStocks();
		}
		else
		{
			titleMain.setText("My Watched Stocks");
			mystockTable.setVisible(true);
		}
		
		talkingRobot.setVisible(false);
		robotDialog.setVisible(false);

	}
}
