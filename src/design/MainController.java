package design;

import core.*;
import java.net.URL;
import java.util.*;
import core.struct.*;
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
import static core.Const.*;

/**
 *
 * @author kam
 */
public class MainController implements Initializable {
    
    @FXML
    private Button btnFire;
    
    ArrayList firestations = new ArrayList();
    ArrayList lines = new ArrayList();
    Fire fire;
    char status = '0';
    int id = 0, k = 0, lineStatus = 0, v1 = 0, kp = 0;
    double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
    boolean fireB = false, firestationB = false, cancelB = false, pathB = false;
    
    @FXML
    private Pane field;
    @FXML
    private Text lInfo, l1, l2, l3;
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
        firestationB = cancelB = pathB = false;
        field.setCursor(Cursor.MOVE);
        visible0Setting();
    }

    @FXML
    private void firestation(ActionEvent event) {
        firestationB = true;
        fireB = cancelB = pathB = false;
        field.setCursor(Cursor.MOVE);
        visible0Setting();
    }
    
    @FXML
    private void cancelFunc(ActionEvent event) {
        fireB = firestationB = cancelB = pathB = false;
        field.setCursor(Cursor.CROSSHAIR);
        visible0Setting();
    }

    @FXML
    private void cancel(ActionEvent event) {
        fireB = firestationB = pathB = false;
        cancelB = true;
        field.setCursor(Cursor.SW_RESIZE);
        visible0Setting();
    }

    @FXML
    private void line(ActionEvent event) {
        fireB = firestationB = cancelB = false;
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
                fire.setM1(m1);
                fire.setM2(m2);
                fire.setM3(m3);
                break;
            case '1':
                FireStation frst = new FireStation();
                FireStation buf = (FireStation) firestations.get(id);
                frst.setId(Integer.parseInt(tN.getText()));
                frst.setM1(m1);
                frst.setM2(m2);
                frst.setM3(m3);
                firestations.set(id, frst);
                break;
        }
    }

    @FXML
    private void click(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
                
        if (fireB == true && fire.getStatus() == false) {
            fire.setStatus(true);
            fireB = false;
            field.setCursor(Cursor.CROSSHAIR);
            btnFire.setDisable(true);
                        
            ImageView iv = newObjectV(x, y, PATH_IMAGE_FIRE_V);
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                if (cancelB == true) {
                    field.getChildren().remove(iv);
                    fire = new Fire();
                    fire.setStatus(false);
                    btnFire.setDisable(false);
                } else if (pathB == true) {
                    createLine(iv, 0);
                } else {
                    lInfo.setText("Необхідні машини типу:");
                    textI.setText("" + fire.getM1());
                    textII.setText("" + fire.getM2());
                    textIII.setText("" + fire.getM3());
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
            st.setId(k + 1);
            firestationB = false;
            firestations.add(st);
            field.setCursor(Cursor.CROSSHAIR);
            ImageView iv = newObjectV(x, y, PATH_IMAGE_FIRESTATION_V);
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                if (cancelB == true) {
                    field.getChildren().remove(iv);
                    firestations.set(i, null);
                } else if (pathB == true) {
                    FireStation info = (FireStation) firestations.get(i);
                    createLine(iv, info.getId());
                } else {
                    lN.setVisible(true);
                    tN.setVisible(true);
                    lInfo.setText("Наявні машини типу:");
                    FireStation info = (FireStation) firestations.get(i);
                    textI.setText("" + info.getM1());
                    textII.setText("" + info.getM2());
                    textIII.setText("" + info.getM3());
                    tN.setText("" + info.getId());
                    status = '1';
                    id = i;
                    visible1SettingV();
                }
            });
            
            k++;
            field.getChildren().add(iv);            
        }
        
    }

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
            
            Point[] t1 = getVariable(x1, y1, RADIUS_IMAGE);
            Point[] t2 = getVariable(x2, y2, RADIUS_IMAGE);
            
            Rebro ph = new Rebro();
            ph.setV1(Math.min(v, v1));
            ph.setV2(Math.max(v, v1));
            ph.setLength(getLength());
            ph.setId(kp + 1);
            int i = kp;
            
            lines.add(ph);
            Point[] res = getResPoint(t1, t2);
            Line line = new Line(res[0].getX(), res[0].getY(), res[1].getX(), res[1].getY());
            line.setCursor(Cursor.HAND);
            line.setStrokeWidth(3);
            line.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                if (cancelB == true) {
                    field.getChildren().remove(line);
                    lines.set(i, null);
                }  else {
                    ph.setLength(getLength());
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

    void visible1SettingV() {
        lInfo.setVisible(true);
        textI.setVisible(true);
        textII.setVisible(true);
        textIII.setVisible(true);
        l1.setVisible(true);
        l2.setVisible(true);
        l3.setVisible(true);
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
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
    }

    private ImageView newObjectV(double x, double y, String img) {
        ImageView iv = new ImageView();
        iv.setImage(new Image(img));
        iv.setFitHeight(RADIUS_IMAGE * 2);
        iv.setFitWidth(RADIUS_IMAGE* 2);
        iv.setPreserveRatio(true);
        iv.setLayoutX(x);
        iv.setLayoutY(y);
        iv.setCursor(Cursor.HAND);
        return iv;
    }

}