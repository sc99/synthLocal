/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jm.JMC;
import jm.audio.RTMixer;
import jm.audio.synth.Filter;
import static jm.constants.Pitches.C3;
import jmusic.DefaultMusic;
import jmusic.MyInstrument;

/**
 * FXML Controller class
 *
 * @author Alumno
 */
public class tecladoCtrl extends synthCtrl implements Initializable, JMC{

    /**
     * Initializes the CONTROLLER class.
     */
    
    private static boolean invitado=false;
    private static boolean mostrar=true;
    public static clases.cEfectos set=new clases.cEfectos();
    
    @FXML AnchorPane aP;
    @FXML Rectangle c1;
    @FXML Rectangle c1sos;
    @FXML Rectangle d1;
    @FXML Rectangle d1sos;
    @FXML Rectangle e1;
    @FXML Rectangle f1;
    @FXML Rectangle f1sos;
    @FXML Rectangle g1;
    @FXML Rectangle g1sos;
    @FXML Rectangle a1;
    @FXML Rectangle a1sos;
    @FXML Rectangle b1; 
    @FXML Rectangle c2;
    @FXML Rectangle c2sos;
    @FXML Rectangle d2;
    @FXML Rectangle d2sos;
    @FXML Rectangle e2;
    @FXML Rectangle f2;
    @FXML Rectangle f2sos;
    @FXML Rectangle g2;
    @FXML Rectangle g2sos;
    @FXML Rectangle a2;
    @FXML Rectangle a2sos;
    @FXML Rectangle b2;
    @FXML Rectangle c3;
    @FXML Rectangle c3sos;
    @FXML Rectangle d3;
    @FXML Rectangle d3sos;
    @FXML Rectangle e3;
    @FXML Rectangle f3;
    @FXML Rectangle f3sos;
    @FXML Rectangle g3;
    @FXML Rectangle g3sos;
    @FXML Rectangle a3;
    @FXML Rectangle a3sos;
    @FXML Rectangle b3;
    
    midi.cMidi MIDI;
    

    private static String [][] arrR=new String[36][2];
    Rectangle [] arrNtas=new Rectangle[36];
    
    @FXML private MenuItem agregarEf;
    @FXML private MenuItem abrirEf;
    @FXML private MenuItem guardar;
    @FXML private MenuBar menu;   
    @FXML private Menu mArchivo;
    @FXML private Menu mEfectos;
    @FXML private Menu mPost;
    @FXML private Menu mExtras;
    
    @FXML private MenuItem guardaSet; 
    @FXML private MenuItem postea;
    @FXML private MenuItem inf;
    @FXML private MenuItem configTecl;
    @FXML private MenuItem mostrarTecl;
    @FXML private ToggleButton record;
    @FXML private ToggleButton stop;
    @FXML private ToggleButton play;
    @FXML private ToggleButton pause;

    public  static ToggleButton Chorus=new ToggleButton();
    public  static ToggleButton Delay=new ToggleButton();
    public  static ToggleButton PitchShift=new ToggleButton();
    public  static ToggleButton Reverb=new ToggleButton();
    public  static ToggleButton Tremolo=new ToggleButton();
    
    public static Map<Integer,RTMixer> statusMixers = new HashMap();
    //TRUE = DISPONIBLE
    //FALSE = NO DISPONIBLE
    private Map<String,Integer> conf = new HashMap();
    //ASIGNACION TECLA - NOTA
    public static Map<Integer,Integer> relNoteMixer = new HashMap();
    
    @FXML private ComboBox<Map<Integer,Map<Integer,Object>>> cmbComponentes;
    @FXML private Label caracteristicas;
    @FXML private CheckBox chkFiltro;
    @FXML private Slider sldFiltro;
    @FXML private Label lblFiltro;
    @FXML private CheckBox chkEnvoltura;
    @FXML private Slider sldEnvoltura1;
    @FXML private Slider sldEnvoltura2;
    @FXML private Slider sldEnvoltura3;
    @FXML private Slider sldEnvoltura4;
    @FXML private Slider sldEnvoltura5;
    @FXML private Label lblEnvoltura1;
    @FXML private Label lblEnvoltura2;
    @FXML private Label lblEnvoltura3;
    @FXML private Label lblEnvoltura4;
    @FXML private Label lblEnvoltura5;
    @FXML private CheckBox chkVolumen;
    @FXML private Slider sldVolumen;
    @FXML private Label lblVolumen;
    @FXML private CheckBox chkPaneo;
    @FXML private Slider sldPaneo;
    @FXML private Label lblPaneo;
    @FXML private VBox contComponentes;
    
    public static final Map<Integer,Map<Integer,Object>> CONTROLLER = new HashMap();
    private final Map<Integer,Object> FILTER_VALUES = new HashMap();
    private final Map<Integer,Object> ENVELOPE_VALUES = new HashMap();
    private final Map<Integer,Object> VOLUME_VALUES = new HashMap();
    private final Map<Integer,Object> PAN_VALUES = new HashMap();
    
   public static boolean getInvitado(){
       return invitado;
   }
   public static boolean getMostrar(){
       return mostrar;
   }
   public void ventanaConfig(Stage princStage) throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("/fxml/configEfecto.fxml"));       
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/pruebas.css").toExternalForm());
        Stage secStage=new Stage();
        secStage.getIcons().add(new Image("/imgs/Synth.png"));
        secStage.initOwner(princStage);
        secStage.initModality(Modality.WINDOW_MODAL);
        secStage.setScene(scene);
        secStage.setResizable(false);
        secStage.setTitle("EFECTOS");
        secStage.setOnCloseRequest(e->alerta(secStage,e));
        secStage.show();
        //scene.getRoot().requestFocus();
   }
   public void ventanaCfgTecl(Stage princStage,int modificable) throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("/fxml/configTec.fxml"));       
        Scene scene = new Scene(root);
        Stage secStage=new Stage();
        secStage.getIcons().add(new Image("/imgs/Synth.jpg"));
        secStage.initOwner(princStage);
        secStage.initModality(Modality.WINDOW_MODAL);
        secStage.setScene(scene);
        secStage.setResizable(false);
        secStage.setTitle("EFECTOS");
        //            secStage.setOnCloseRequest(e->alerta(secStage,e));
        if(modificable==1)
            secStage.setOnCloseRequest(e->cerrarNoModificable(secStage));
        secStage.show();
   }
   public void cerrarNoModificable(Stage st){
        Alert alerta=new Alert(Alert.AlertType.INFORMATION);
        st.getIcons().add(new Image("/imgs/Synth.png"));
        alerta.setTitle("¡Hey!");
        alerta.setHeaderText("¡Recuerda!");
        alerta.setContentText("Si esta configuracion no te acomoda puedes cambiarla.");
        Optional<ButtonType> result=alerta.showAndWait();
        if(result.get() == ButtonType.OK){
            mostrar=true;
            st.close();
            System.out.println(mostrar);
        }
       
   }
   public void ventanaInfo(Stage princStage) throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("/fxml/webInf.fxml"));       
        Scene scene = new Scene(root);
        Stage secStage=new Stage();
        secStage.getIcons().add(new Image("/imgs/Synth.png"));
        secStage.initOwner(princStage);
        secStage.initModality(Modality.WINDOW_MODAL);
        secStage.setScene(scene);
        secStage.setResizable(false);
        secStage.setTitle("EFECTOS");
       // secStage.setOnCloseRequest(e->alerta(secStage,e));
        secStage.show();
   }
   public void ventanaAbreEf(Stage st){
    FileChooser choser= new FileChooser();
    choser.setTitle("Abrir archivo de configuración");
    choser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Config Sonido","*.synth"));
    choser.setInitialDirectory(new File("C:\\Synth"));
    File config = choser.showOpenDialog(st);
    if(config!= null){
        System.out.println("Capturado");
        System.out.println(config.getAbsolutePath());
        System.out.println(config.getName());
        //clases.cEfectos nSet=new clases.cEfectos(config.getAbsolutePath());
        set.setSetEfectos(config.getAbsolutePath());
        if(set.cargaEfectos())
        {
            System.out.println("Efectos cargados!");
            aviso((Stage)menu.getScene().getWindow(),"Se han cargado nuevos efectos.");
        }
        else
        {
           aviso((Stage)menu.getScene().getWindow(),"¡Error al cargar los efectos!");
        }
        
    }else{
        archivoVacio(st,"Si no introduces un archivo de sonido no se cargarán nuevos efectos");  
    }
       
   }
   public void archivoVacio(Stage st,String msj){
        Alert alerta=new Alert(Alert.AlertType.INFORMATION);
        st.getIcons().add(new Image("/imgs/Synth.png"));
        alerta.setTitle("¡Hey!");
        alerta.setHeaderText("\tRecuerda");
        alerta.setContentText(msj);
        Optional<ButtonType> result=alerta.showAndWait();
   }
   public void aviso(Stage st,String msj){
       Alert aviso=new Alert(Alert.AlertType.INFORMATION);
       st.getIcons().add(new Image("/imgs/Synth.png"));
       aviso.setTitle("Aviso");
       aviso.setHeaderText("Mensaje de estatus");
       aviso.setContentText(msj);
      Optional<ButtonType> result=aviso.showAndWait();
   }
   public void guardaArchivo(Stage st){
       boolean fallo=true;
       String extension="";
       FileChooser saver=new FileChooser();  
       if((new File("C:\\Synth")).exists())
           saver.setInitialDirectory(new File("C:\\Synth"));
       
           saver.setTitle("Guardar archivo");
           FileChooser.ExtensionFilter ext=
           new FileChooser.ExtensionFilter("Archivo de set de "
                                            + "efectos (*.synth)","*.synth");    
           saver.getExtensionFilters().add(ext);
           File file= saver.showSaveDialog(st);
           if(file !=null){

               extension=file.getName();
               if(extension.endsWith(".synth"))
               {
                  System.out.println(file.getAbsolutePath());
                  fallo= set.guardaSet(file.getAbsolutePath());
                  if(fallo)
                      archivoVacio(st,"Fallo en la creación del archivo");
                  else
                      aviso(st,"Archivo creado con éxito");
               }else
                   archivoVacio(st,"Extension de archivo inválida");
           }
   }

   public String [] getNomBtns(){
       String nombres []={"Delay","Chorus","Reverb","Pitch Shift","Tremolo"};
       return nombres;
   }
   public  void desbloqueaEfecto(String idEfecto,boolean value){
       switch(idEfecto){
           case "Delay":
               Delay.setDisable(value);
               break;
           case "Chorus":
               Chorus.setDisable(value);
               break;
           case "Reverb":
               Reverb.setDisable(value);
               break;
           case "Pitch Shift":
               PitchShift.setDisable(value);
               break;
           case "Tremolo":
               Tremolo.setDisable(value);
               break;
       }
   }
    @FXML
    public void manejaMenu(ActionEvent e) throws IOException{
        if(e.getSource()==agregarEf)
        {
            ventanaConfig((Stage) menu.getScene().getWindow());
        }
        else
        {
            if(e.getSource()==abrirEf)
            {
                ventanaAbreEf((Stage)menu.getScene().getWindow());
            }
            else
            {
                if(e.getSource()==guardaSet){
                    guardaArchivo((Stage)menu.getScene().getWindow());
                }else
                {
                    if(e.getSource()==inf){
                        ventanaInfo((Stage) menu.getScene().getWindow());
                    }else
                        if(e.getSource()==configTecl)
                        {
                            ventanaCfgTecl((Stage) menu.getScene().getWindow(),0);
                        }else
                            if(e.getSource()==mostrarTecl)
                            {
                                mostrar=false;
                                ventanaCfgTecl((Stage) menu.getScene().getWindow(),1);
                            }
                }
            }
        }
    }

    public String getNotaTocada(String tecla){
        String nota="";
        for(int i=0;i<36;i++){
            if(arrR[i][1].equalsIgnoreCase(tecla))
                nota=arrR[i][0];
        }
        return nota;
    }
    @FXML
    public void tocarTecla(KeyEvent e){
        try{
            String idTcla=getNotaTocada(e.getText());
            int xId=0;
            for(int i=0;i<arrNtas.length;i++){
                if(arrNtas[i].getId().equals(idTcla)){           
                   Paint efecto=idTcla.endsWith("s")?Color.PURPLE:Color.LIGHTCYAN;
                   if(!arrNtas[i].getFill().equals(efecto)){
                        int nota = i + C3;
                        MIDI.addToQueue(nota, 1);
                   }
                   arrNtas[i].setFill(efecto);
                }
            }
        }catch(Exception ex){
            utilities.Message.explainException(ex).showAndWait();
        }
    }
    @FXML
   public void sueltaTecla(KeyEvent e){
        try{
            String idTcla=getNotaTocada(e.getText());
            int xId=0;
            if(e.getText().equals(" ")){
                cController.soft=false;
            }
            for(int i=0;i<arrNtas.length;i++){
                if(arrNtas[i].getId().equals(idTcla)){
                    Paint efecto=idTcla.endsWith("s")?Color.BLACK:Color.WHITE;
                    arrNtas[i].setFill(efecto);
                    int nota = i + C3;
                    MIDI.addToQueue(nota, 0);
                }
            }
        }catch(Exception ex){
            utilities.Message.explainException(ex).showAndWait();
        }
   }
    
    //Asigna configuracion de teclas guardada a
    //cada rectangulo del teclado virtual
    public void asignarTeclas(Rectangle [] tecl){
        String [] teclas= synthCtrl.sesion.getTeclado();
        //Aquí llamamos a la clase de serialización y abrimos el archivo.
            for(int i=0;i<tecl.length;i++){
                arrR[i][0]=tecl[i].getId();
                arrR[i][1]=teclas[i];
            }
        
    }
    public static void asignarNvsTcls(){
        String [] teclas=synthCtrl.sesion.getTeclado();
        for(int i=0;i<teclas.length;i++)
        {
            arrR[i][1]=teclas[i];
        }
    }
    
    public void mostrarTeclado(Rectangle [] aR){
        aR[0].setWidth(lPant/24);
        aR[0].setHeight(aPant/3);
        aR[0].setLayoutX(lPant/16);
        aR[0].setY(aPant/2);
        aR[0].setFill(Color.WHITE);
        aR[0].setStroke(Color.BLACK);
        //para teclas blancas
        for(int i=1;i<aR.length;i++){
            aR[i].setY(aPant/2);
            //negra 
            if(!aR[i].getId().endsWith("s")){
                aR[i].setWidth(lPant/24);
                aR[i].setHeight(aPant/3);
                if(aR[i].getId().startsWith("f")||aR[i].getId().startsWith("c"))
                    aR[i].setLayoutX(aR[i-1].getLayoutX()+aR[i-1].getWidth());
                else
                    aR[i].setLayoutX(aR[i-2].getLayoutX()+aR[i-2].getWidth());                 
                aR[i].setFill(Color.WHITE);
                aR[i].setStroke(Color.BLACK); 
            }
        }
        for(int i=1;i<aR.length;i++){
            aR[i].setY(aPant/2);
            //negra 
            if(aR[i].getId().endsWith("s")){
                aR[i].setWidth(lPant/48);
                aR[i].setHeight(aPant/6);
                aR[i].setLayoutX(aR[i-1].getLayoutX()+aR[i-1].getWidth()*3/4);
                aR[i].setFill(Color.BLACK);
                aR[i].setStroke(Color.WHITE); 
            }
        }
    }
    
    @FXML
    public void manejaEfecto(ActionEvent e){
        System.out.println("Delay");
    }
    public void btnsNoVisibles(boolean estado){
        Chorus.setDisable(estado);
        Delay.setDisable(estado);
        Reverb.setDisable(estado);
        Tremolo.setDisable(estado);
        PitchShift.setDisable(estado);
    }
    public void setBorderR(String borderR){
        Chorus.setStyle(borderR);
        Delay.setStyle(borderR);
        Reverb.setStyle(borderR);
        Tremolo.setStyle(borderR);
        PitchShift.setStyle(borderR);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            MIDI = new midi.cMidi();
            Image cho=new Image(getClass().getResourceAsStream("/imgs/Chorus.png"));   
            ImageView s=new ImageView(cho);
            s.setFitWidth(lPant/12);
            s.setFitHeight(lPant/12);
            Chorus.setGraphic(s);

            Image de=new Image(getClass().getResourceAsStream("/imgs/Delay.png"));
            ImageView d=new ImageView(de);
            d.setFitWidth(lPant/12);
            d.setFitHeight(lPant/12);
            Delay.setGraphic(d);


            Image ps=new Image(getClass().getResourceAsStream("/imgs/PitchShift.png"));
            ImageView p=new ImageView(ps);
            p.setFitWidth(lPant/12);
            p.setFitHeight(lPant/12);
            PitchShift.setGraphic(p);

            Image rev=new Image(getClass().getResourceAsStream("/imgs/Reverb.png"));
            ImageView re=new ImageView(rev);
            re.setFitWidth(lPant/12);
            re.setFitHeight(lPant/12);
            Reverb.setGraphic(re);

            Image trem=new Image(getClass().getResourceAsStream("/imgs/Tremolo.png"));
            ImageView t=new ImageView(trem);
            t.setFitWidth(lPant/12);
            t.setFitHeight(lPant/12);
            Tremolo.setGraphic(t);

            Delay.setMaxSize(lPant/12,lPant/12);
            Delay.setMinSize(lPant/12,lPant/12);
            Delay.setLayoutX(lPant/64*7);
            Delay.setLayoutY(aPant/8);

            Chorus.setMaxSize(lPant/12,lPant/12);
            Chorus.setMinSize(lPant/12,lPant/12);
            Chorus.setLayoutX(Delay.getLayoutX()+Delay.getWidth()+lPant/16*14/5);
            Chorus.setLayoutY(aPant/8);

            PitchShift.setMaxSize(lPant/12,lPant/12);
            PitchShift.setMinSize(lPant/12,lPant/12);
            PitchShift.setLayoutX(Chorus.getLayoutX()+Chorus.getWidth()+lPant/16*14/5);
            PitchShift.setLayoutY(aPant/8);

            Reverb.setMaxSize(lPant/12,lPant/12);
            Reverb.setMinSize(lPant/12,lPant/12);
            Reverb.setLayoutX(PitchShift.getLayoutX()+PitchShift.getWidth()+lPant/16*14/5);
            Reverb.setLayoutY(aPant/8);

            Tremolo.setMaxSize(lPant/12,lPant/12);
            Tremolo.setMinSize(lPant/12,lPant/12);
            Tremolo.setLayoutX(Reverb.getLayoutX()+Reverb.getWidth()+lPant/16*14/5);
            Tremolo.setLayoutY(aPant/8);

            Chorus.setManaged(false);
            PitchShift.setManaged(false);
            Delay.setManaged(false);
            Reverb.setManaged(false);
            Tremolo.setManaged(false);

            aP.getChildren().addAll(Chorus,Delay,Reverb,PitchShift,Tremolo);
            Rectangle [] aR={c1,c1sos,d1,d1sos,e1,f1,f1sos,g1,g1sos,a1,a1sos,b1,
                c2,c2sos,d2,d2sos,e2,f2,f2sos,g2,g2sos,a2,a2sos,b2,
                c3,c3sos,d3,d3sos,e3,f3,f3sos,g3,g3sos,a3,a3sos,b3};

            arrNtas=aR;

            menu.setPrefSize(lPant,aPant/16);

            asignarTeclas(aR);
            //arrR=aR;       
            mostrarTeclado(aR);
            Image rec = new Image(getClass().getResourceAsStream("/imgs/micro.png"));
            ImageView reco = new ImageView(rec);
            reco.setFitWidth(lPant / 31);
            reco.setFitHeight(lPant / 31);
            record.setGraphic(reco);
            record.setMaxSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            record.setMinSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            record.setLayoutX(lPant / 5);
            record.setLayoutY(aPant / 16 * 6);
            record.setDisable(false);

            Image pau = new Image(getClass().getResourceAsStream("/imgs/pausa.png"));
            ImageView paus = new ImageView(pau);
            paus.setFitWidth(lPant / 31 * 0.7);
            paus.setFitHeight(lPant / 31 * 0.7);
            pause.setGraphic(paus);
            pause.setMaxSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            pause.setMinSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            pause.setLayoutX(lPant / 5 * 2);
            pause.setLayoutY(aPant / 16 * 6);
            pause.setDisable(true);

            Image st = new Image(getClass().getResourceAsStream("/imgs/stop.png"));
            ImageView sto = new ImageView(st);
            sto.setFitWidth(lPant / 31 * 0.7);
            sto.setFitHeight(lPant / 31 * 0.7);
            stop.setGraphic(sto);
            stop.setMaxSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            stop.setMinSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            stop.setLayoutX(lPant / 5 * 3);
            stop.setLayoutY(aPant / 16 * 6);
            stop.setDisable(true);

            Image pl = new Image(getClass().getResourceAsStream("/imgs/play.png"));
            ImageView pla = new ImageView(pl);
            pla.setFitWidth(lPant / 31 * 0.7);
            pla.setFitHeight(lPant / 31 * 0.7);
            play.setGraphic(pla);
            play.setMaxSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            play.setMinSize(lPant / 31 * 1.5, aPant / 31 * 1.5);
            play.setLayoutX(lPant / 5 * 4);
            play.setLayoutY(aPant / 16 * 6);
            play.setDisable(true);

            MIDI.start();
            btnsNoVisibles(true);
            setBorderR("-fx-border-radius: 19; -fx-border-color: transparent");
            
            cmbComponentes.setItems(DefaultMusic.defaultComponentes());
            cmbComponentes.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Map<Integer, Map<Integer, Object>>> observable, Map<Integer, Map<Integer, Object>> oldValue, Map<Integer, Map<Integer, Object>> newValue) -> {
                if(newValue.get(MyInstrument.OSCILLATOR) != null){
                    CONTROLLER.remove(MyInstrument.NOISE);
                    CONTROLLER.remove(MyInstrument.PLUCK);
                    CONTROLLER.put(MyInstrument.OSCILLATOR,cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.OSCILLATOR));
                    ComboBox<Map<String,Integer>> oscType = new ComboBox();
                    oscType.setItems(DefaultMusic.defaultOscillatorTypes());
                    oscType.setCellFactory((ListView<Map<String, Integer>> param) -> new ListCell<Map<String,Integer>>(){
                        @Override
                        public void updateItem(Map<String,Integer> item, boolean empty){
                            super.updateItem(item, empty);
                            if(item!=null&&!empty){
                                setText((String)item.keySet().toArray()[0]);
                            }else{
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    });
                    oscType.setButtonCell(oscType.getCellFactory().call(null));
                    oscType.valueProperty().addListener((ObservableValue<? extends Map<String, Integer>> observable1, Map<String, Integer> oldValue1, Map<String, Integer> newValue1) -> {
                        Integer value = newValue1.get((String) newValue1.keySet().toArray()[0]);
                        cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.OSCILLATOR).put(MyInstrument.OSCILLATOR_WAVE, value);
                        CONTROLLER.get(MyInstrument.OSCILLATOR).put(MyInstrument.OSCILLATOR_WAVE, value);
                    });
                    Integer selected = (Integer)cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.OSCILLATOR).get(MyInstrument.OSCILLATOR_WAVE);
                    for(Map<String,Integer> o:oscType.getItems()){
                        if(o.containsValue(selected))oscType.getSelectionModel().select(o);
                    }
                    contComponentes.getChildren().setAll(oscType);
                }else if(newValue.get(MyInstrument.NOISE) != null){
                    CONTROLLER.remove(MyInstrument.OSCILLATOR);
                    CONTROLLER.remove(MyInstrument.PLUCK);
                    CONTROLLER.put(MyInstrument.NOISE,new HashMap<>(cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.NOISE)));
                    ComboBox<Map<String,Integer>> noiseType = new ComboBox();
                    noiseType.setItems(DefaultMusic.defaultNoiseTypes());
                    noiseType.setCellFactory((ListView<Map<String, Integer>> param) -> new ListCell<Map<String,Integer>>(){
                        @Override
                        public void updateItem(Map<String,Integer> item, boolean empty){
                            super.updateItem(item, empty);
                            if(item!=null&&!empty){
                                setText((String)item.keySet().toArray()[0]);
                            }else{
                                setText(null);
                                setGraphic(null);
                            }
                        }
                    });
                    noiseType.setButtonCell(noiseType.getCellFactory().call(null));
                    noiseType.valueProperty().addListener((ObservableValue<? extends Map<String, Integer>> observable1, Map<String, Integer> oldValue1, Map<String, Integer> newValue1) -> {
                        Integer value = newValue1.get((String) newValue1.keySet().toArray()[0]);
                        cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.NOISE).put(MyInstrument.NOISE_TYPE, value);
                        CONTROLLER.get(MyInstrument.NOISE).put(MyInstrument.NOISE_TYPE, value);
                    });
                    Integer selected = (Integer)cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.NOISE).get(MyInstrument.NOISE_TYPE);
                    for(Map<String,Integer> o:noiseType.getItems()){
                        if(o.containsValue(selected))noiseType.getSelectionModel().select(o);
                    }
                    contComponentes.getChildren().setAll(noiseType);
                }else if(newValue.get(MyInstrument.PLUCK) != null){
                    CONTROLLER.remove(MyInstrument.OSCILLATOR);
                    CONTROLLER.remove(MyInstrument.NOISE);
                    CONTROLLER.put(MyInstrument.PLUCK,new HashMap<>(cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.PLUCK)));
                    Slider feed = new Slider(0,1,0.5);
                    feed.setBlockIncrement(0.1);
                    Label value = new Label();
                    feed.valueProperty().addListener((ObservableValue<? extends Number> observable1, Number oldValue1, Number newValue1) -> {
                        value.setText(Double.toString(Math.floor(newValue1.doubleValue() * 100)));
                        cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.PLUCK).put(MyInstrument.PLUCK_FEEDBACK, newValue1.doubleValue());
                        CONTROLLER.get(MyInstrument.PLUCK).put(MyInstrument.PLUCK_FEEDBACK, newValue1.doubleValue());
                    });
                    Double selected = (Double)cmbComponentes.getSelectionModel().getSelectedItem().get(MyInstrument.PLUCK).get(MyInstrument.PLUCK_FEEDBACK);
                    feed.setValue(selected);
                    contComponentes.getChildren().setAll(feed,value);
                }
            });
            cmbComponentes.setCellFactory((ListView<Map<Integer, Map<Integer, Object>>> param) -> new ListCell<Map<Integer,Map<Integer,Object>>>(){
                @Override
                public void updateItem(Map<Integer,Map<Integer,Object>> item, boolean empty){
                    super.updateItem(item, empty);
                    if(item!=null&&!empty){
                        if(item.get(MyInstrument.OSCILLATOR) != null){
                            setText("OSCILLATOR");
                        }else if(item.get(MyInstrument.NOISE) != null){
                            setText("NOISE");
                        }else if(item.get(MyInstrument.PLUCK) != null){
                            setText("PLUCK");
                        }
                    }else{
                        setText(null);
                        setGraphic(null);
                    }
                }
            });
            cmbComponentes.setButtonCell(cmbComponentes.getCellFactory().call(null));
            
            //----------------------------------FILTRO--------------------------------------
            
            FILTER_VALUES.put(MyInstrument.FILTER_TYPE, Filter.LOW_PASS);
            SimpleDoubleProperty cutoff = new SimpleDoubleProperty(100.0);
            cutoff.bind(sldFiltro.valueProperty());
            sldFiltro.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                lblFiltro.setText(Double.toString(Math.floor(newValue.doubleValue())));
            });
            FILTER_VALUES.put(MyInstrument.FILTER_CUTOFF, cutoff);
            chkFiltro.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(newValue){
                    CONTROLLER.put(MyInstrument.FILTER, FILTER_VALUES);
                }else{
                    CONTROLLER.remove(MyInstrument.FILTER);
                }
            });
            
            //---------------------------------ENVOLTURA-------------------------------------
            
            ENVELOPE_VALUES.put(MyInstrument.ENVELOPE_VALUE, new double[]{0.5,0.5,0.5,0.5,0.5});
            Slider[] slds = {sldEnvoltura1,sldEnvoltura2,sldEnvoltura3,sldEnvoltura4,sldEnvoltura5};
            Label[] lbls = {lblEnvoltura1,lblEnvoltura2,lblEnvoltura3,lblEnvoltura4,lblEnvoltura5};
            for(int i = 0; i < slds.length; i++){
                Label lbl = lbls[i];
                Slider sld = slds[i];
                sld.setUserData(i);
                sld.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    NumberFormat formater = new DecimalFormat("#0.000");
                    lbl.setText(formater.format(newValue.doubleValue()).replace(",","."));
                    ((double[])ENVELOPE_VALUES.get(MyInstrument.ENVELOPE_VALUE))[(int)sld.getUserData()] = Double.parseDouble(formater.format(newValue.doubleValue()).replace(",","."));
                });
            }
            chkEnvoltura.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(newValue){
                    CONTROLLER.put(MyInstrument.ENVELOPE, ENVELOPE_VALUES);
                }else{
                    CONTROLLER.remove(MyInstrument.ENVELOPE);
                }
            });
            
            //--------------------------------VOLUMEN-----------------------------
            
            SimpleDoubleProperty val = new SimpleDoubleProperty(0.5);
            val.bind(sldVolumen.valueProperty());
            VOLUME_VALUES.put(MyInstrument.VOLUME_VALUE,val);
            sldVolumen.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                lblVolumen.setText(Double.toString(newValue.doubleValue() * 10));
            });
            chkVolumen.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(newValue){
                    CONTROLLER.put(MyInstrument.VOLUME,VOLUME_VALUES);
                }else{
                    CONTROLLER.remove(MyInstrument.VOLUME);
                }
            });
            
            //-------------------------------PANEO---------------------------------
            
            chkVolumen.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(newValue){
                    midi.Message.pan.bind(sldPaneo.valueProperty());
                }else{
                    midi.Message.pan.unbind();
                }
            });
            sldPaneo.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                lblPaneo.setText(Double.toString(newValue.doubleValue() * 10));
            });
            
        }catch(Exception e){
            utilities.Message.explainException(e).showAndWait();
        }
    }  
    @FXML
    public void record(ActionEvent e){
//        cSequence.turnRecording();
//        record.setDisable(true);
//        pause.setDisable(false);
//        stop.setDisable(false);
//        play.setDisable(true);
    }
    @FXML
    public void play(ActionEvent e){
//        try{
//            cSequence.playRecord();
//        }catch(Exception ex){
//            System.out.println("Error al reproducir!!");
//            System.out.println(ex.getMessage());
//        }
//        record.setDisable(true);
//        pause.setDisable(true);
//        stop.setDisable(false);
//        play.setDisable(true);
    }
    @FXML
    public void stop(ActionEvent e){
//        cSequence.finish();
//        record.setDisable(false);
//        pause.setDisable(false);
//        stop.setDisable(false);
//        play.setDisable(false);
    }
    @FXML
    public void pause(ActionEvent e){
//        cSequence.turnRecording();
//        record.setDisable(false);
//        pause.setDisable(true);
//        stop.setDisable(false);
//        play.setDisable(false);
    }
    @FXML
    public void save(ActionEvent e){
//        try{
//            if(cSequence.getSequence() != null){
//                FileChooser choser= new FileChooser();
//                choser.setTitle("Guardar audio");
//                choser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("MID","*.mid")
//                );
//                choser.setInitialDirectory(new File("C:\\Synth"));
//                File config = choser.showSaveDialog((Stage)menu.getScene().getWindow());
//                if(config != null){
//                    int[] types = MidiSystem.getMidiFileTypes(cSequence.getSequence());
//                    MidiSystem.write(cSequence.getSequence(), types[0], config);
//                    aviso((Stage)menu.getScene().getWindow(),"Archivo guardado con exito");
//                }
//            }else{
//                doANotification(AlertType.ERROR, "ERROR", "Tu grabacion se encuentra vacía", "Tienes que grabar una pista antes de poder guardarla.");
//            }
//        }catch(Exception es){
//            es.printStackTrace();
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Mensaje del programa");
//            alert.setHeaderText("Error guardando el archivo!");
//            alert.setContentText("Comprueba que el nombre del archivo sea valido (.mid) \n"
//                    + "o asegúrese de que ya hay un audio grabado listo para guardarse.");
//            alert.showAndWait();
//        }
    }
    public void doANotification(AlertType type, String title, String header, String text){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
}