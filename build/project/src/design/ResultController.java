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
        Memory.fire.path = di.getPath();
        setNULL();
        firestations = (ArrayList) Memory.firestations.clone();
        Car car = new Car(Memory.fire, Memory.firestations);
        Memory.firestations = car.getFirestations();
        dataModels = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            FireStation buf = (FireStation) Memory.firestations.get(i);
            String length = "" + Memory.fire.path[i];
            if (Memory.fire.path[i] == Const.INF) {
                length = "������ �� ����";
            }
            add("" + buf.id, length, "" + buf.r1, "" + buf.r2, "" + buf.r3);
            dataModels.add(new DataModel("" + buf.id, length, "" + buf.r1, "" + buf.r2, "" + buf.r3));
            txt += "������� �������: " + buf.id + "\t³������ �� �����: " + length + "\t������ � ����: " + buf.r1 + "\t������ �I ����: " +  buf.r2 + "\t������ �II ����" +  buf.r3 + "\t\n";
        }

    }

    @FXML
    private void toExcel() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("���������");
        List<DataModel> dataList = dataModels;
        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue("������� �������");
        row.createCell(1).setCellValue("³������ �� �����");
        row.createCell(2).setCellValue("������ � ����");
        row.createCell(3).setCellValue("������ �� ����");
        row.createCell(4).setCellValue("������ ��� ����");
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
