package colorPick.test;

import colorPick.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logger.LogLevel;
import logger.Logger;

public class Main extends Application
{
	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("colorPick/GUI.fxml"));
			Parent root = (Parent)loader.load();

			Scene scene = new Scene(root, 500, 225);		

			//this line is important
			((Controller)loader.getController()).init(stage, (finishColor)->{
				System.out.println(finishColor);
			});

			stage.setResizable(false);			
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			Logger.error(e);
		}
	}

	public static void main(String[] args)
	{		
		Logger.setLevel(LogLevel.ALL);		

		launch(args);
	}
}