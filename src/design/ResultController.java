package design;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.TextArea;
import core.*;

/**
 * FXML Controller class
 *
 * @author kam
 */
public class ResultController implements Initializable {

    @FXML
    private TextArea tP, tL, tM1, tM2, tM3;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int n = Memory.firestations.size() + 1;

        ArrayList firestations = (ArrayList) Memory.firestations.clone();
        ArrayList lines = (ArrayList) Memory.firestations.clone();

        Dijkstra di = new Dijkstra(firestations, lines);
        Memory.fire.path = di.getPath();
        setNULL();

        firestations = (ArrayList) Memory.firestations.clone();

        Car car = new Car(Memory.fire, Memory.firestations);
        Memory.firestations = car.getFirestations();

        for (int i = 0; i < n - 1; i++) {
            FireStation buf = (FireStation) Memory.firestations.get(i);
            add("" + buf.id, "" + Memory.fire.path[i], "" + buf.r1, "" + buf.r2, "" + buf.r3);
        }

    }
    
    void add(String id, String l, String m1, String m2, String m3) {
        tP.setText(tP.getText() + id + "\n");
        tL.setText(tL.getText() + l + "\n");
        tM1.setText(tM1.getText() + m1 + "\n");
        tM2.setText(tM2.getText() + m2 + "\n");
        tM3.setText(tM3.getText() + m3 + "\n");
    }

    void setNULL() {
        tP.setText("");
        tL.setText("");
        tM1.setText("");
        tM2.setText("");
        tM3.setText("");
    }
    
}
