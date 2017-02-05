package templates;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tools.Worker;

public class Controller
{
	private Stage stage;
	private Image icon = new Image("application/icon.png");
	private final ResourceBundle bundle = ResourceBundle.getBundle("application/", Locale.GERMANY);
	
	public void init(Stage stage)
	{
		this.stage = stage;
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent event)
			{
				Worker.shutdown();
				System.exit(0);
			};
		});
	}
	
	public void about()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Über " + bundle.getString("app.name"));
		alert.setHeaderText(bundle.getString("app.name"));
		alert.setContentText("Version:     " + bundle.getString("version.name") + "\r\nDatum:      " + bundle.getString("version.date") + "\r\nAutor:        Robert Goldmann\r\n");
		Stage dialogStage = (Stage)alert.getDialogPane().getScene().getWindow();
		dialogStage.getIcons().add(icon);
		dialogStage.centerOnScreen();
		alert.showAndWait();
	}
}