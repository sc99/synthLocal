/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 *
 * @author BEAR
 */
public class Validation {
    public static final String WRONG_STYLE = "-fx-border-color: RGB(210,0,5); -fx-text-fill: RGB(210,0,5)";
    public static final String OK_STYLE = "-fx-border-color: RGB(33,133,35); -fx-text-fill: RGB(33,133,35)";
    public static final String INITIAL_STYLE = "-fx-border-color: RGB(29,0,89); -fx-text-fill: black";
    public static void textWithSpace(TextField content){
        String c = content.getText();
        if(content.getTooltip() == null)content.setTooltip(new Tooltip());
        if(c.matches("^[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ'.]*(?:\\s[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ'.]{2,}?)*$")){
            content.setStyle(OK_STYLE);
            if(content.getUserData() != null)content.getTooltip().setText((String)content.getUserData());
            content.getTooltip().setAutoHide(false);
            content.getTooltip().hide();
        }else{
            content.setStyle(WRONG_STYLE);
            if(!content.getTooltip().isAutoHide()){
                content.setUserData(content.getTooltip().getText());
                Point2D p = content.localToScene(0.0, content.getHeight());
                content.getTooltip().setText("Solo puedes ingresar letras y espacios");
                content.getTooltip().setAutoHide(true);
                content.getTooltip().show(content.getScene().getWindow(), p.getX()
                    + content.getScene().getX() + content.getScene().getWindow().getX(), p.getY()
                    + content.getScene().getY() + content.getScene().getWindow().getY());
            }
        }
    }
    public static void noSpecial(TextField content){
        String c = content.getText();
        if(content.getTooltip() == null)content.setTooltip(new Tooltip());
        if(c.matches("[\\w\\s:\\-_#'áéúíóüÁÉÍÓÚÜñÑ]*") && !c.startsWith(" ") && !c.endsWith(" ")){
            content.setStyle(OK_STYLE);
            if(content.getUserData() != null)content.getTooltip().setText((String)content.getUserData());
            content.getTooltip().setAutoHide(false);
            content.getTooltip().hide();
        }else{
            content.setStyle(WRONG_STYLE);
            if(!content.getTooltip().isAutoHide()){
                content.setUserData(content.getTooltip().getText());
                Point2D p = content.localToScene(0.0, content.getHeight());
                content.getTooltip().setText("No puedes ingresar caracteres especiales (@#$%^&*!()/\\[])");
                content.getTooltip().setAutoHide(true);
                content.getTooltip().show(content.getScene().getWindow(), p.getX()
                    + content.getScene().getX() + content.getScene().getWindow().getX(), p.getY()
                    + content.getScene().getY() + content.getScene().getWindow().getY());
            }
        }
    }
    public static void number(TextField content){
        String c = content.getText();
        if(content.getTooltip() == null)content.setTooltip(new Tooltip());
        if(c.matches("^\\d*$")){
            content.setStyle(OK_STYLE);
            if(content.getUserData() != null)content.getTooltip().setText((String)content.getUserData());
            content.getTooltip().setAutoHide(false);
            content.getTooltip().hide();
        }else{
            content.setStyle(WRONG_STYLE);
            if(!content.getTooltip().isAutoHide()){
                content.setUserData(content.getTooltip().getText());
                Point2D p = content.localToScene(0.0, content.getHeight());
                content.getTooltip().setText("Solo puedes ingresar valores numericos sin punto decimal");
                content.getTooltip().setAutoHide(true);
                content.getTooltip().show(content.getScene().getWindow(), p.getX()
                    + content.getScene().getX() + content.getScene().getWindow().getX(), p.getY()
                    + content.getScene().getY() + content.getScene().getWindow().getY());
            }
        }
    }
    public static void decimalNumber(TextField content){
        String c = content.getText();
        if(content.getTooltip() == null)content.setTooltip(new Tooltip());
        if(c.matches("^\\d*(\\.\\d+)?$")){
            content.setStyle(OK_STYLE);
            if(content.getUserData() != null)content.getTooltip().setText((String)content.getUserData());
            content.getTooltip().setAutoHide(false);
            content.getTooltip().hide();
        }else{
            content.setStyle(WRONG_STYLE);
            if(!content.getTooltip().isAutoHide()){
                content.setUserData(content.getTooltip().getText());
                Point2D p = content.localToScene(0.0, content.getHeight());
                content.getTooltip().setText("Solo puedes ingresar valores numericos enteros o decimales");
                content.getTooltip().setAutoHide(true);
                content.getTooltip().show(content.getScene().getWindow(), p.getX()
                    + content.getScene().getX() + content.getScene().getWindow().getX(), p.getY()
                    + content.getScene().getY() + content.getScene().getWindow().getY());
            }
        }
    }
}
