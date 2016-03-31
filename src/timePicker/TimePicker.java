package timePicker;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TimePicker extends HBox
{
	@FXML protected Label labelHours;
	@FXML protected Label labelMinutes;
	@FXML protected Label labelSeconds;

	@FXML protected Button buttonHoursUp;
	@FXML protected Button buttonHoursDown;

	@FXML protected Button buttonMinutesUp;
	@FXML protected Button buttonMinutesDown;

	@FXML protected Button buttonSecondsUp;
	@FXML protected Button buttonSecondsDown;

	public TimePicker(int fontSize)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimePicker.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		init();

		try
		{
			fxmlLoader.load();
		}
		catch(IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}

	private void init()
	{
		labelHours.setStyle("-fx-background-color: red;");
	}
}