package design;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import core.Const;
/**
 *
 * @author kam
 */
public class Metod {
            
    public static void showWindows(String str){	
	Stage primaryStage = new Stage();
    	Pane mainPane;
        try {
                mainPane = (Pane)FXMLLoader.load(SolverOfFire.class.getResource(str));
                primaryStage.setScene(new Scene(mainPane));
                primaryStage.setResizable(false);
                primaryStage.getIcons().add(new Image(Const.iconApplication));
                primaryStage.setTitle(Const.nameApplication);
                primaryStage.show();
        } catch (IOException e) {
        }
        }
    
    
    
    
}
