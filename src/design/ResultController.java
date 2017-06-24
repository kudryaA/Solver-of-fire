package design;

import java.io.*;
import java.net.URL;
import java.util.*;
import core.struct.*;
import javafx.fxml.*;
import javafx.scene.control.TextArea;
import core.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;

import static core.Const.*;
import static design.Metod.getPath;

/**
 * FXML Controller class
 *
 * @author kam
 */
public class ResultController implements Initializable {

    @FXML
    private TextArea tP, tL, tM1, tM2, tM3;
    private List<DataModel> dataModels;
    private String txt = "";

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int n = Memory.firestations.size() + 1;

        ArrayList firestations = (ArrayList) Memory.firestations.clone();
        ArrayList lines = (ArrayList) Memory.lines.clone();

        Dijkstra di = new Dijkstra(firestations, lines);
        Memory.fire.setPath(di.getPath());
        setNULL();
        firestations = (ArrayList) Memory.firestations.clone();
        Car car = new Car(Memory.fire, Memory.firestations);
        Memory.firestations = car.getFirestations();
        dataModels = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            FireStation buf = (FireStation) Memory.firestations.get(i);
            String length = "" + Memory.fire.getPath()[i];
            if (Memory.fire.getPath()[i] == INF) {
                length = "Дороги не існує";
            }
            add("" + buf.getId(), length, "" + buf.getR1(), "" + buf.getR2(), "" + buf.getR3());
            dataModels.add(new DataModel("" + buf.getId(), length, "" + buf.getR1(), "" + buf.getR2(), "" + buf.getR3()));
            txt += "Пожежна частина: " + buf.getId() + "\tВідстань до пожежі: " + length + "\tМашина І типу: " + buf.getR1() + "\tМашина ІI типу: " +  buf.getR2() + "\tМашина ІII типу" +  buf.getR3() + "\t\n";
        }

    }

    @FXML
    private void toExcel() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Результат");
        List<DataModel> dataList = dataModels;
        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("Пожежна частина");
        row.createCell(1).setCellValue("Відстань до пожежі");
        row.createCell(2).setCellValue("Машина І типу");
        row.createCell(3).setCellValue("Машина ІІ типу");
        row.createCell(4).setCellValue("Машина ІIІ типу");
        for (DataModel dataModel : dataList) {
            createSheetHeader(sheet, ++rowNum, dataModel);
        }


        try (FileOutputStream out = new FileOutputStream(new File(getPath() + ".xls"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toTXT() {
        try(FileWriter writer = new FileWriter(getPath() + ".txt", true)) {
            String[] mas = txt.split("\n");
            writer.write(txt);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void createSheetHeader(HSSFSheet sheet, int rowNum, DataModel dataModel) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(dataModel.getFireStation());
        row.createCell(1).setCellValue(dataModel.getLength());
        row.createCell(2).setCellValue(dataModel.getM1());
        row.createCell(3).setCellValue(dataModel.getM2());
        row.createCell(4).setCellValue(dataModel.getM3());
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
