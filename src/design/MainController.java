package design;

import core.*;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import static core.Changer.*;

/**
 *
 * @author kam
 */
public class MainController implements Initializable {
    
    boolean fireB = false, firestationB = false, cancelB = false, pathB = false;
    @FXML
    private Button btnFire;
    
    ArrayList firestations = new ArrayList();
    ArrayList lines = new ArrayList();
    Fire fire;
    char status = '0';
    int id = 0;
    int k = 0;
    
    @FXML
    private Pane field;
    @FXML
    private Text lInfo;
    @FXML
    private TextField textI, textII, textIII, tN;
    @FXML
    private ImageView btnSave;
    @FXML
    private Label lN;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fire = new Fire();
    }    

    @FXML
    private void fire(ActionEvent event) {
        fireB = true;
        firestationB = false;
        cancelB = false;
        pathB = false;
        field.setCursor(Cursor.MOVE);
        visible0Setting();
    }

    @FXML
    private void firestation(ActionEvent event) {
        fireB = false;
        firestationB = true;
        cancelB = false;
        pathB = false;
        field.setCursor(Cursor.MOVE);
        visible0Setting();
    }
    
    @FXML
    private void cancelFunc(ActionEvent event) {
        fireB = false;
        firestationB = false;
        cancelB = false;
        pathB = false;
        field.setCursor(Cursor.CROSSHAIR);
        visible0Setting();
    }

    @FXML
    private void cancel(ActionEvent event) {
        fireB = false;
        firestationB = false;
        cancelB = true;
        pathB = false;
        field.setCursor(Cursor.SW_RESIZE);
        visible0Setting();
    }
    
    int lineStatus = 0;
    
    @FXML
    private void line(ActionEvent event) {
        fireB = false;
        firestationB = false;
        cancelB = false;
        pathB = true;
        field.setCursor(Cursor.CROSSHAIR);
        visible0Setting();
    }
    
    @FXML
    private void save(MouseEvent event) {
        int m1 = Integer.parseInt(textI.getText());
        int m2 = Integer.parseInt(textII.getText());
        int m3 = Integer.parseInt(textIII.getText());
        switch (status) {
            case '0':
                fire.m1 = m1;
                fire.m2 = m2;
                fire.m3 = m3;
                break;
            case '1':
                FireStation frst = new FireStation();
                FireStation buf = (FireStation) firestations.get(id);
                frst.id = Integer.parseInt(tN.getText());
                frst.m1 = m1;
                frst.m2 = m2;
                frst.m3 = m3;
                firestations.set(id, frst);
                break;
            default:
                break;
        }
    }
    
    void visible1SettingV() {
        lInfo.setVisible(true);
        textI.setVisible(true);
        textII.setVisible(true);
        textIII.setVisible(true);
        btnSave.setVisible(true);
    }
    
    void visible0Setting() {
        lN.setVisible(false);
        tN.setVisible(false);
        lInfo.setVisible(false);
        textI.setVisible(false);
        textII.setVisible(false);
        textIII.setVisible(false);
        btnSave.setVisible(false);
    }
    
    private ImageView newObjectV(double x, double y, String img) {
        ImageView iv = new ImageView();
        iv.setImage(new Image(img));
        iv.setFitHeight(68);
        iv.setFitWidth(68);
        iv.setPreserveRatio(true);
        iv.setLayoutX(x);
        iv.setLayoutY(y);
        iv.setCursor(Cursor.HAND);  
        return iv;
    }

    @FXML
    private void click(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
                
        if (fireB == true && fire.status == false) {
            fire.status = true;
            fireB = false;
            field.setCursor(Cursor.CROSSHAIR);
            btnFire.setDisable(true);
                        
            ImageView iv = newObjectV(x, y, Const.pathImageFireV);
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                if (cancelB == true) {
                    field.getChildren().remove(iv);
                    fire = new Fire();
                    fire.status = false;
                    btnFire.setDisable(false);
                } else if (pathB == true) {
                    createLine(iv, 0);
                } else {
                    lInfo.setText(Const.nesCar);
                    textI.setText("" + fire.m1);
                    textII.setText("" + fire.m2);
                    textIII.setText("" + fire.m3);
                    status = '0';
                    visible1SettingV();
                    lN.setVisible(false);
                    tN.setVisible(false);
                }
            });
            field.getChildren().add(iv);
        }
        
        if (firestationB == true) {
            FireStation st = new FireStation();
            int i = k;
            st.id = k + 1;
            firestationB = false;
            firestations.add(st);
            field.setCursor(Cursor.CROSSHAIR);
            ImageView iv = newObjectV(x, y, Const.pathImageFireStationV);
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                if (cancelB == true) {
                    field.getChildren().remove(iv);
                    firestations.set(i, null);
                } else if (pathB == true) {
                    FireStation info = (FireStation) firestations.get(i);
                    createLine(iv, info.id);
                } else {
                    lN.setVisible(true);
                    tN.setVisible(true);
                    lInfo.setText(Const.isCar);
                    FireStation info = (FireStation) firestations.get(i);
                    textI.setText("" + info.m1);
                    textII.setText("" + info.m2);
                    textIII.setText("" + info.m3);
                    tN.setText("" + info.id);
                    status = '1';
                    id = i;
                    visible1SettingV();
                }
            });
            
            k++;
            field.getChildren().add(iv);            
        }
        
    }
    double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    int v1 = 0;
    int kp = 0;
    
    void createLine(ImageView iv, int v) {
        if (lineStatus  == 0) {
            x1 = iv.getLayoutX();
            y1 = iv.getLayoutY();
            lineStatus = 1;
            v1 = v;
        } else if (lineStatus % 2 == 1) {
            lineStatus = 0;
            x2 = iv.getLayoutX();
            y2 = iv.getLayoutY();
            
            Point[] t1 = getVariable(x1, y1, Const.radiusImage);
            Point[] t2 = getVariable(x2, y2, Const.radiusImage);
            
            Rebro ph = new Rebro();
            ph.v1 = Math.min(v, v1);
            ph.v2 = Math.max(v, v1);
            ph.length = getLength();
            ph.id = kp + 1;
            int i = kp;
            
            lines.add(ph);
            Point[] res = getResPoint(t1, t2);
            Line line = new Line(res[0].x, res[0].y, res[1].x, res[1].y);
            line.setCursor(Cursor.HAND);
            line.setStrokeWidth(3);
            line.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                if (cancelB == true) {
                    field.getChildren().remove(line);
                    lines.set(i, null);
                }  else {
                    ph.length = getLength();
                }
            });
            field.getChildren().add(line);  
            kp++;
        }
    }

    @FXML
    private void okey(ActionEvent event) {
        Memory.fire = fire;
        Memory.firestations = newListV(firestations);        
        Memory.lines = newListR(lines, firestations);
        Metod.showWindows("Result.fxml");
    }
    
        
}
