/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.management.Notification;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.kevinsawicki.http.HttpRequest;

import Util.Future;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class FutureController implements Initializable {

	@FXML
	private Label lbTitulo;
	@FXML
	private AnchorPane telaEdicao;
	@FXML
	private TextField txtSearch;
	@FXML
	private GridPane telaCadastro;
	@FXML
	private TextField txtNome;
	@FXML
	private TextArea txtDescricao;
	@FXML
	private TableColumn<Future, String> colName;
	@FXML
	private TableColumn<Future, String> colClose;
	@FXML
	private TableColumn<Future, String> colOpen;
	@FXML
	private TableColumn<Future, String> colPrice;
	@FXML
	private TableColumn<Future, String> colHigh;
	@FXML
	private TableColumn<Future, String> colLow;
	@FXML
	private TableColumn<Future, String> colChange;
	@FXML
	private TableColumn<Future, String> colPercentChange;
	@FXML
	private TableColumn<Future, String> colTime;
	@FXML
	private TableColumn<Future, String> colVolume;
	@FXML
	private TableView<Future> twFuture;
	@FXML
	private TableColumn<Future, String> colSymbole;

	ObservableList<Future> futureObser = FXCollections.observableArrayList();
	
	List<Future> lstNew = new ArrayList();


	Media notification;
	public static Timeline futureTimer;
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		notification = new Media(getClass().getResource("/fxml/img/notification48.mp3").toExternalForm());

		buildData();

		 futureTimer = new Timeline(new KeyFrame(Duration.seconds(10), (ActionEvent event) -> {
			futureObser.clear();
			twFuture.setItems(futureObser);
			updateData();
			MediaPlayer player = new MediaPlayer(notification);
			player.play();
		}));
		 futureTimer.setCycleCount(Timeline.INDEFINITE);
		 futureTimer.play();
		

	}

	private static List<Future> showFutureData() {
		String response = HttpRequest.get("https://marketdata.websol.barchart.com"
				+ "/getQuote.json?apikey=3b317303ebe00e5e3d59400561b0a17d&symbols="
				+ "ZC*1,IBM,GOOGL,%5EEURUSD,AMZN,AAPL,FB,NFLX,INTC,"
				+ "TGT,BAC,PIH,TSLA,BABA,F,MU,INTC,GE,TWTR,WMT,NOK,"
				+ "QQQ,WDC,ECYT,ADMP,GM,TI,SRPT,LRCX,TRXC,TSRO,CSPI," + "CASA,BB,GILD,XOM,PBR,AXP,QCOM,FIT,UAA,DAL,CVS")
				.accept("application/json").body();
		JSONObject jsonObject = new JSONObject(response);
		JSONArray result = jsonObject.getJSONArray("results");
		List<Future> lst = new ArrayList<>();
		for (int i = 0; i < result.length(); i++) {
			JSONObject data = result.getJSONObject(i);
			Future future = new Future();
			future.setRef(i + "");
			future.setClose(data.getDouble("close") + "");
			future.setHigh(data.getDouble("high") + "");
			future.setLastPrice(data.getDouble("lastPrice") + "");
			future.setLow(data.getDouble("low") + "");
			future.setName(data.getString("name"));
			future.setNetChange(data.getDouble("netChange") + "");
			future.setOpen(data.getDouble("open") + "");
			future.setPercentChange(data.getDouble("percentChange") + "");
			future.setSymbol(data.getString("symbol"));
			future.setTradeTimestamp(data.getString("tradeTimestamp"));
			future.setVolume(data.getInt("volume") + "");
			lst.add(future);
		}
		return lst;
	}

	public void updateData() {
		colSymbole.setCellValueFactory(new PropertyValueFactory("symbol"));
		colName.setCellValueFactory(new PropertyValueFactory("name"));
		colClose.setCellValueFactory(new PropertyValueFactory("close"));
		colOpen.setCellValueFactory(new PropertyValueFactory("open"));
		colPrice.setCellValueFactory(new PropertyValueFactory("lastPrice"));
		colHigh.setCellValueFactory(new PropertyValueFactory("high"));
		colLow.setCellValueFactory(new PropertyValueFactory("low"));
		colChange.setCellValueFactory(new PropertyValueFactory("netChange"));
		colPercentChange.setCellValueFactory(new PropertyValueFactory("percentChange"));
		colTime.setCellValueFactory(new PropertyValueFactory("tradeTimestamp"));
		colVolume.setCellValueFactory(new PropertyValueFactory("volume"));
		
		lstNew.clear();
		lstNew = showFutureData();
		
		//
		colPrice.setCellFactory(column -> {
			return new TableCell<Future, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item);

						// Style all dates in March with a different color.
					
						if (!lstNew.get(this.getIndex()).getLastPrice().equals(item)) {
                            
                                setStyle("-fx-font-weight:bold");
                          
                        }
					}
				}
			};
		});
		colPrice.setStyle("");
		//
		colChange.setCellFactory(column -> {
			return new TableCell<Future, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item);

						// Style all dates in March with a different color.
						if (item.contains("-")) {
							setStyle("-fx-text-fill: red");
						} else {
							setStyle("-fx-text-fill: green");
						}
					}
				}
			};
		});
		colPercentChange.setCellFactory(column -> {
			return new TableCell<Future, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item);

						// Style all dates in March with a different color.
						if (item.contains("-")) {
							setStyle("-fx-text-fill: red");
						} else {
							setStyle("-fx-text-fill: green");
						}
					}
				}
			};
		});
		colVolume.setCellFactory(column -> {
			return new TableCell<Future, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item);

						// Style all dates in March with a different color.
						if (!lstNew.get(this.getIndex()).getVolume().equals(item)) {
                            
                                setStyle("-fx-font-weight:bold");
                           
                           
                        }
					}
				}
			};
		});
		colVolume.setStyle("");
		futureObser.addAll(showFutureData());
		twFuture.setItems(futureObser);
	}
	//
	public void buildData() {
		colSymbole.setCellValueFactory(new PropertyValueFactory("symbol"));
		colName.setCellValueFactory(new PropertyValueFactory("name"));
		colClose.setCellValueFactory(new PropertyValueFactory("close"));
		colOpen.setCellValueFactory(new PropertyValueFactory("open"));
		colPrice.setCellValueFactory(new PropertyValueFactory("lastPrice"));
		colHigh.setCellValueFactory(new PropertyValueFactory("high"));
		colLow.setCellValueFactory(new PropertyValueFactory("low"));
		colChange.setCellValueFactory(new PropertyValueFactory("netChange"));
		colPercentChange.setCellValueFactory(new PropertyValueFactory("percentChange"));
		colTime.setCellValueFactory(new PropertyValueFactory("tradeTimestamp"));
		colVolume.setCellValueFactory(new PropertyValueFactory("volume"));
		colChange.setCellFactory(column -> {
			return new TableCell<Future, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item);

						// Style all dates in March with a different color.
						if (item.contains("-")) {
							setStyle("-fx-text-fill: red");
						} else {
							setStyle("-fx-text-fill: green");
						}
					}
				}
			};
		});
		colPercentChange.setCellFactory(column -> {
			return new TableCell<Future, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
						setStyle("");
					} else {
						// Format date.
						setText(item);

						// Style all dates in March with a different color.
						if (item.contains("-")) {
							setStyle("-fx-text-fill: red");
						} else {
							setStyle("-fx-text-fill: green");
						}
					}
				}
			};
		});
		futureObser.addAll(showFutureData());
		twFuture.setItems(futureObser);
	}
	public static void stopListener()
	{
		if(futureTimer != null)
		futureTimer.stop();
	}

}
