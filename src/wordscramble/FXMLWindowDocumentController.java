/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordscramble;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author intern
 */

public class FXMLWindowDocumentController extends FXMLDocumentController implements Initializable{

    @FXML
    private Label finalResult;
    @FXML
    private Button retryButton;
    
    FXMLDocumentController docController = new FXMLDocumentController();
    
    String pts = String.valueOf(docController.points);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        finalResult.setText(pts);
        System.out.println(pts);
    }    
    
    @FXML
    private void retryButtonHandler(ActionEvent event) {
        Stage stage = (Stage) retryButton.getScene().getWindow();
        stage.close();
        //docController.init();
    }
    
 
}
