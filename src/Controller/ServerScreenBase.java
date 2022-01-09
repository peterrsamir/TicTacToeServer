package Controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class ServerScreenBase extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final RowConstraints rowConstraints2;
    protected final RowConstraints rowConstraints3;
    protected final Button btnStart;
    protected final Button btnStop;
    protected final Label label;
   
//    protected DataInputStream dis;
//    protected DataOutputStream ps;
//    protected ChatHandler chatHandler;
    
    public ServerScreenBase() {
             
        columnConstraints = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        rowConstraints2 = new RowConstraints();
        rowConstraints3 = new RowConstraints();
        btnStart = new Button();
        btnStop = new Button();
        label = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(30.0);
        rowConstraints2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints3.setMinHeight(10.0);
        rowConstraints3.setPrefHeight(30.0);
        rowConstraints3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        GridPane.setHalignment(btnStart, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(btnStart, 2);
        GridPane.setValignment(btnStart, javafx.geometry.VPos.CENTER);
        btnStart.setMnemonicParsing(false);
        btnStart.setPrefHeight(31.0);
        btnStart.setPrefWidth(111.0);
        btnStart.setText("Start");
//        btnStart.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent event) {
//                try {

//                } catch (IOException ex) {
//                    Logger.getLogger(ServerScreenBase.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            
//        });

        GridPane.setHalignment(btnStop, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(btnStop, 3);
        GridPane.setValignment(btnStop, javafx.geometry.VPos.CENTER);
        btnStop.setMnemonicParsing(false);
        btnStop.setPrefHeight(31.0);
        btnStop.setPrefWidth(111.0);
        btnStop.setText("Stop");

        GridPane.setHalignment(label, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(label, 1);
        GridPane.setValignment(label, javafx.geometry.VPos.CENTER);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setPrefHeight(31.0);
        label.setPrefWidth(211.0);
        label.setText("SERVER");

        getColumnConstraints().add(columnConstraints);
        getRowConstraints().add(rowConstraints);
        getRowConstraints().add(rowConstraints0);
        getRowConstraints().add(rowConstraints1);
        getRowConstraints().add(rowConstraints2);
        getRowConstraints().add(rowConstraints3);
        getChildren().add(btnStart);
        getChildren().add(btnStop);
        getChildren().add(label);
        
    }
}
