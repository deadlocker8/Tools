package templates;

import java.io.File;
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
import logger.FileOutputMode;
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
			Logger.error(e);
		}
	}
	
	@Override
	public void init() throws Exception
	{
		ResourceBundle bundle = ResourceBundle.getBundle("application/", Locale.GERMANY);
		
		Parameters params = getParameters();
		String logLevelParam = params.getNamed().get("loglevel");		
		Logger.setLevel(logLevelParam);	
		
		File logFolder = new File(PathUtils.getOSindependentPath() + "/Deadlocker/" + bundle.getString("app.name"));			
		PathUtils.checkFolder(logFolder);
		Logger.enableFileOutput(logFolder, System.out, System.err, FileOutputMode.COMBINED);
		
		Logger.appInfo(bundle.getString("app.name"), bundle.getString("version.name"), bundle.getString("version.code"), bundle.getString("version.date"));		
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}