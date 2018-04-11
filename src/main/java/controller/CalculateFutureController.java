/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Mdaghi
 */
public class CalculateFutureController implements Initializable {

	@FXML
	private Label lbTitulo;
	@FXML
	private GridPane telaCadastro;
	@FXML
	private AnchorPane telaEdicao;
	@FXML
	private MediaView media;
	@FXML
	private Label fieldset;
	@FXML
	private Button btnPlay;
	@FXML
	private Label timeLabel;
	@FXML
	private Slider timeSlider;
	@FXML
	private Label titleLabel;
	@FXML
	private Slider volSlider;
	@FXML
	private ImageView volImage;
	@FXML
	private Button btnStop;
	@FXML
	private Label txtFirstname;
	@FXML
	private TextField txtInterest;
	@FXML
	private TextField txtPresentValue;
	@FXML
	private TextField txtPeriod;
	@FXML
	private Button btnCalculate;
	@FXML
	private LineChart<String, Integer> chart;
	@FXML
	private JFXTextArea report;

	private final Image volLow = new Image(getClass().getResourceAsStream("/fxml/img/audio_volume_low_newschool.png"));
	private final Image volHigh = new Image(
			getClass().getResourceAsStream("/fxml/img/audio_volume_high_newschool.png"));
	private final Image volMute = new Image(
			getClass().getResourceAsStream("/fxml/img/audio_volume_muted_newschool.png"));

	Media video;
	public static MediaPlayer player;
	private Duration duration;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		video = new Media(getClass().getResource("/fxml/video/FuturesMarketExplained.mp4").toExternalForm());
		player = new MediaPlayer(video);
		media.setMediaPlayer(player);
		player.play();
		volSlider.setValue(0.5);
		btnPlay.setOnAction((ActionEvent e)->{
			player.play();
		});
		btnStop.setOnAction((ActionEvent e) -> {
			player.stop();
		});
		//
		volSlider.valueProperty().addListener((javafx.beans.Observable observable) -> {
				if (volSlider.isValueChanging()) {
					player.setVolume(volSlider.getValue() / 1.0);
				}
				if (volSlider.getValue() == 0) {
					volImage.setImage(volMute);
				} else if (volSlider.getValue() > 0 && volSlider.getValue() < 0.70) {
					volImage.setImage(volLow);
				} else if (volSlider.getValue() > 0.70) {
					volImage.setImage(volHigh);
				}
		});
		//
		timeSlider.valueProperty().addListener((javafx.beans.Observable observable) -> {
				if (timeSlider.isValueChanging()) {
					// multiply duration by percentage calculated by slider
					// position
					player.seek(duration.multiply(timeSlider.getValue() / 100.0));
				}
		});
		//
		player.setOnReady(() -> {
			duration = player.getMedia().getDuration();
			updateValues();

		});

		player.currentTimeProperty().addListener((javafx.beans.Observable observable) -> {
				updateValues();
		});

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void valide(ActionEvent event) {
		Double interest = Double.parseDouble(txtInterest.getText());
		Double presentValue = Double.parseDouble(txtPresentValue.getText());
		int period = Integer.parseInt(txtPeriod.getText());
		Double futureValue = (double) Math.round(presentValue * Math.pow((1 + interest * 0.01), period));
		report.setText(" If today you were to invest $" + presentValue + " at a rate of " + interest + " %, "
				+ "you would have $" + futureValue + " at the end of 50 time periods (e.g. weeks, months, or years)."
				+ " In other words, " + "a future value of $" + futureValue + " is equal to a present value of only $"
				+ presentValue + "."
				+ "What does this mean to you? Well, if you had a choice between taking an amount higher than the $"
				+ presentValue + " today " + "and taking the $" + futureValue
				+ " at the end of 50 time periods, you should take the money today. "
				+ "By doing so, you would be able to invest the higher amount at " + interest + " % for " + period
				+ " equal time periods, " + "which would end up giving you more than $" + futureValue + "");

		/**********************************************************/
		chart.getXAxis().setLabel("Period");
		chart.getYAxis().setLabel("Value");
		chart.setTitle("Future Value ");
		chart.getData().clear();
		ObservableList<XYChart.Series<String, Integer>> answer = FXCollections.observableArrayList();
		Series<String, Integer> series = new Series<>();
		Double value;
		series.getData().add(new XYChart.Data<String,Integer>(0+"", 0));
		for(int i=1 ;i<period;i+=10)
		{
		value = (double) Math.round(presentValue * Math.pow((1 + interest * 0.01), i));
		series.getData().add(new XYChart.Data(i+"",value));
		}
		series.getData().add(new XYChart.Data(period+"",futureValue ));
		series.setName("Evolution in time");
		answer.addAll(series);
		chart.getData().addAll(answer);

	}

	@SuppressWarnings("deprecation")
	private void updateValues() {
		Platform.runLater(() -> {
			Duration currentTime = player.getCurrentTime();
			timeLabel.setText(formatTime(currentTime, duration));
			timeSlider.setDisable(duration.isUnknown());
			if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
				timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
			}
		});
	}

	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) Math.floor(duration.toSeconds());
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds,
						durationHours, durationMinutes, durationSeconds);
			} else {
				return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes,
						durationSeconds);
			}
		} else {
			if (elapsedHours > 0) {
				return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
			} else {
				return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
			}
		}
	}
	
	

	public static void stopListener() {
		if (player != null)
			player.stop();
	}

}
