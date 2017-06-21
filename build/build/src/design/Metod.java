package design;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
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
                mainPane = FXMLLoader.load(SolverOfFire.class.getResource(str));
                primaryStage.setScene(new Scene(mainPane));
                primaryStage.setResizable(false);
                primaryStage.getIcons().add(new Image(Const.iconApplication));
                primaryStage.setTitle(Const.nameApplication);
                primaryStage.setWidth(632);
                primaryStage.setHeight(412);
                primaryStage.show();
        } catch (IOException e) {
        }
    }

    public static String getPath() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(null);
        return selectedFile.getAbsolutePath();
    }

    
}
