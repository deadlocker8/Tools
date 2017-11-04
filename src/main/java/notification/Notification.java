package notification;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Ertellt eine Notification in der unteren rechten Ecke des Bildschirms
 * @author Robert
 *
 */
public class Notification
{	
	public ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	private int notificationWidth = 350;
	private int notificationHeight = 120;
	private int offset = 5; //Abstand vom Rand
	private int iconSize = 50;

	private String title;
	private String description;
	private Image icon;
	private int hideAfterInMillis;
	private int fadeOutTimeInMillis;
	private Timeline timeline;
	
	private Stage stage;
	private Stage owner;

	/**
	 * schließt die Notification, falls diese geöffnet ist
	 */
	public void close()
	{
		if(stage != null)
		{
			stage.close();	
			timeline.stop();
		}
	}
	
	/**
	 * erzeugt die Notification und zeigt sie an
	 */
	public void show()
	{
		try
		{
			//falls keine Zeit angegeben wurde nach welcher Zeit die Notification ausgeblendet werden soll, wird die Standarddauer von  4 Sekunden verwendet
			if(hideAfterInMillis == 0)
			{
				hideAfterInMillis = 4000;
			}

			//falls keine Zeit angegeben wurde wie lange die Notification ausgeblendet werden soll, wird die Standarddauer von  1 Sekunden verwendet			
			if(fadeOutTimeInMillis == 0)
			{
				fadeOutTimeInMillis = 1000;
			}

			//Bildschirmbreite und -höhe
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT);
			stage.setX(primaryScreenBounds.getMaxX() - notificationWidth - offset);
			stage.setY(primaryScreenBounds.getMaxY() - notificationHeight - offset);
			stage.setAlwaysOnTop(true);

			AnchorPane root = new AnchorPane();
			root.setPrefWidth(notificationWidth);
			root.setPrefHeight(notificationHeight);
			root.setBackground(Background.EMPTY);

			//Inhalt der Notification
			AnchorPane content = new AnchorPane();
			content.setPrefWidth(notificationWidth - 10);
			content.setPrefHeight(notificationHeight - 10);
			content.setStyle("-fx-background-color:  linear-gradient(#EFEFEF,#D9D9D9); -fx-border-color: #B5B5B5; -border-radius: 5; "
					+ "-fx-background-radius: 5;  -fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.4), 11, 0.0, 0, 3); ");
			root.getChildren().add(content);
			AnchorPane.setLeftAnchor(content, 5.0);
			AnchorPane.setTopAnchor(content, 5.0);

			//Button zum schließen der Notification
			Button button = new Button();
			button.setText("x");
			button.setStyle("-fx-font-weight: bold; -fx-background-color: transparent; -fx-font-size: 19px");
			button.setFocusTraversable(false);

			button.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event)
				{
					stage.close();
					timeline.stop();
				}
			});

			content.getChildren().add(button);
			AnchorPane.setRightAnchor(button, 5.0);
			AnchorPane.setTopAnchor(button, 0.0);

			//Falls kein Pfad zum Icon angegeben wurde oder der Pfad fehlerhaft ist, wird das Standardicon verwendet
			if(icon == null)
			{
				icon = new Image("/notification/trophyUnlocked.png");
			}

			ImageView view = new ImageView(icon);
			view.setFitWidth(iconSize);
			view.setFitHeight(iconSize);

			content.getChildren().add(view);
			AnchorPane.setLeftAnchor(view, 15.0);
			AnchorPane.setTopAnchor(view, (notificationHeight - offset - iconSize) / 2.0);

			Label labelTitle = new Label(title);
			labelTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

			content.getChildren().add(labelTitle);
			AnchorPane.setLeftAnchor(labelTitle, 15.0 + iconSize + 25.0);
			AnchorPane.setTopAnchor(labelTitle, 15.0);

			Label labelDescription = new Label(description);
			labelDescription.setStyle("-fx-font-size: 17px;");
			labelDescription.setPrefWidth(notificationWidth - offset - iconSize - 35.0);
			labelDescription.setWrapText(true);

			content.getChildren().add(labelDescription);
			AnchorPane.setLeftAnchor(labelDescription, 15.0 + iconSize + 25.0);
			AnchorPane.setTopAnchor(labelDescription, 50.0);

			Scene scene = new Scene(root, notificationWidth, notificationHeight);
			scene.setFill(Color.TRANSPARENT);

			stage.setScene(scene);
			stage.show();
			
			//gibt den Fokus zurück an das Fenster, welches die Notification erstellt hat 
			//(sodass die Notification nicht alle Maus- und Tastaturinteraktion abfängt)
			if(owner != null)
			{
				owner.requestFocus();
			}

			//generiert einen Fadeouteffekt
			timeline = new Timeline();
			KeyFrame key = new KeyFrame(Duration.millis(fadeOutTimeInMillis), new KeyValue(stage.getScene().getRoot().opacityProperty(), 0));
			timeline.getKeyFrames().add(key);
			timeline.setDelay(Duration.millis(hideAfterInMillis));
			timeline.setOnFinished(new EventHandler<ActionEvent>()
			{
				@Override
				public void handle(ActionEvent event)
				{
					if(stage != null)
					{
						stage.close();
						stage = null;
					}
				}
			});
			timeline.play();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * setzt den Titel der Notification
	 * @param title String - Titel
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * setzt den Text der Notification
	 * @param description String - Beschreibung
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * setzt das Icon der Notification
	 * @param icon Image - Icon
	 */
	public void setIcon(Image icon)
	{
		this.icon = icon;
	}

	/**
	 * setzt die Zeit, nach der die Notification ausgeblendet werden soll
	 * @param hideAfterInMillis int - Zeit in Millisekunden
	 */
	public void setHideAfterInMillis(int hideAfterInMillis)
	{
		this.hideAfterInMillis = hideAfterInMillis;
	}

	/**
	 * setzt die Zeit, wie lange die Notification ausgeblendet werden soll
	 * @param fadeOutTimeInMillis int - Zeit in Millisekunden
	 */
	public void setFadeOutTimeInMillis(int fadeOutTimeInMillis)
	{
		this.fadeOutTimeInMillis = fadeOutTimeInMillis;
	}
	
	/**
	 * setzt den Owner
	 * @param owner Stage - Owner
	 */
	public void setOwner(Stage owner)
	{
		this.owner = owner;
	}
	
	/**
	 * gibt zurück, ob die Notification im Moment angezeigt wird
	 * @return
	 */
	public boolean isOpen()
	{		
		if(stage == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}