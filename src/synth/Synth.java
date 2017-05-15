/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package synth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Saul
 */
public class Synth extends Application {
    
    private Stage stag;
    
    public Stage getStage(){
        return stag;
    }
    public void setStage(Stage st){
        this.stag=st;
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/synth.fxml"));       
        Scene scene = new Scene(root);       
        scene.getStylesheets().add(getClass().getResource("/css/pruebas.css").toExternalForm());
        stage.getIcons().add(new Image("/imgs/Synth.png"));
        stage.setScene(scene);
        stage.setTitle("Synth! MD");
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
