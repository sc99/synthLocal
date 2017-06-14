/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author BEAR
 */
public class Message {
    public static Alert exceptionAlert(Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(ex.getMessage());
        alert.setHeaderText("Error");
        DialogPane d = alert.getDialogPane();
        d.setMinSize(500.0, 200.0);
        String u = Message.class.getResource("/css").toExternalForm() + "/dialog.css";
        d.getStylesheets().add(u);
        d.getStyleClass().add("myDialog");
        return alert;
    }
    public static Alert explainException(Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        DialogPane d = alert.getDialogPane();
        d.setMinSize(500.0, 200.0);
        String a = Message.class.getResource("/css").toExternalForm() + "/dialog.css";
        System.out.println(a);
        d.getStylesheets().add(a);
        d.getStyleClass().add("myDialog");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String content = sw.toString();
        TextArea area = new TextArea(content);
        area.setEditable(false);
        area.setWrapText(true);
        area.setStyle("-fx-font-size: 15px;");
        area.setMaxWidth(Double.MAX_VALUE);
        area.setMaxHeight(Double.MAX_VALUE);
        Label label = new Label("Comunícate con el desarrollador");
        Button button = new Button("Enviar por correo");
        button.setOnAction(e -> {
            button.setText("Enviando...");
            Mail mail = new Mail();
            button.setText("Enviar por correo");
            Tooltip t = new Tooltip();
            try{
                mail.mandaMail(ex.getMessage(), content);
                 t.setText("Mensaje Enviado");
            }catch(Exception exx){
                t.setText(exx.getMessage());
            }
            Point2D p = button.localToScene(0.0, button.getHeight());
            t.setAutoHide(true);
            t.show(button.getScene().getWindow(), p.getX()
            + button.getScene().getX() + button.getScene().getWindow().getX(), p.getY()
            + button.getScene().getY() + button.getScene().getWindow().getY());
        });
        HBox box = new HBox(label,button);
        box.setSpacing(15.0);
        box.setAlignment(Pos.CENTER);
        GridPane.setVgrow(area, Priority.ALWAYS);
        GridPane.setHgrow(area, Priority.ALWAYS);
        GridPane pane = new GridPane();
        pane.setVgap(15.0);
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.add(area, 0, 0);
        pane.add(box, 0, 1);
        alert.getDialogPane().setContent(pane);
        return alert;
    }
    public static Alert confirmationAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText("Espera!");
        DialogPane d = alert.getDialogPane();
        d.setMinSize(500.0, 200.0);
        d.getStylesheets().add(Message.class.getResource("/css").toExternalForm() + "/dialog.css");
        d.getStyleClass().add("myDialog");
        return alert;
    }
    public static Alert successAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setHeaderText("Exito!");
        DialogPane d = alert.getDialogPane();
        d.setMinSize(500.0, 200.0);
        d.getStylesheets().add(Message.class.getResource("/css").toExternalForm() + "/dialog.css");
        d.getStyleClass().add("myDialog");
        return alert;
    }
}
