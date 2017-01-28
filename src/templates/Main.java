package templates;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logger.LogLevel;
import logger.Logger;
import tools.PathUtils;
import tools.Worker;

public class Main extends Application
{
	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("controller/GUI.fxml"));
			Parent root = (Parent)loader.load();

			Scene scene = new Scene(root, 800, 600);		

			((Controller)loader.getController()).init(stage);

			stage.setResizable(false);
			stage.getIcons().add(new Image("/application/icon.png"));
			stage.setTitle("<AppName Here>");
			stage.setScene(scene);

			stage.setOnCloseRequest(new EventHandler<WindowEvent>()
			{
				public void handle(WindowEvent we)
				{
					Worker.shutdown();
					System.exit(0);
				}
			});

			stage.show();
		}
		catch(Exception e)
		{
			Logger.log(LogLevel.ERROR, Logger.exceptionToString(e));
		}
	}

	public static void main(String[] args)
	{
		if(Arrays.asList(args).contains("debug"))
		{
			Logger.setLevel(LogLevel.ALL);
			Logger.log(LogLevel.INFO, "Running in Debug Mode");
		}
		else
		{
			Logger.setLevel(LogLevel.ERROR);
		}

		try
		{
			ResourceBundle bundle = ResourceBundle.getBundle("application/", Locale.GERMANY);
			PathUtils.checkFolder(new File(PathUtils.getOSindependentPath() + "/Deadlocker/" + bundle.getString("app.name") + "/"));
			Logger.enableFileOutput(new File(PathUtils.getOSindependentPath() + "/Deadlocker/" + bundle.getString("app.name") + "/error.log"));
			Logger.log(LogLevel.INFO, "File output enabled (" + PathUtils.getOSindependentPath().toString().replace("\\", "/") + "/Deadlocker/" + bundle.getString("app.name") + "/error.log)");
			Logger.log(LogLevel.INFO, bundle.getString("app.name") + " - v" + bundle.getString("version.name") + " - (versioncode: " + bundle.getString("version.code") + ") from " + bundle.getString("version.date"));
		}
		catch(Exception e)
		{
			Logger.disableFileOutput();
		}

		launch(args);
	}
}