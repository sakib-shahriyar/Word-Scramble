/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordscramble;


import com.sun.prism.shader.AlphaTexture_ImagePattern_AlphaTest_Loader;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

/**
 *
 * @author sakib-shahriyar
 */
public class FXMLDocumentController implements Initializable{
    
    @FXML
    private Label attemptsLabel;
    @FXML
    private Button answerButton;
    @FXML
    private Label questionLabel;
    @FXML
    private TextField answerTextField;
    @FXML
    private Label label1;
    @FXML
    private Label label11;
    @FXML
    private Label correctAnswerLabel;
    @FXML
    private Label finalResultLabel;
    @FXML
    private Label toastMsg;
    @FXML
    private Label pointLabel;
   
    
    ArrayList <String> ques = new ArrayList<>();
    ArrayList <String> correctAns = new ArrayList<>();   
    HashMap<String, String> dict = new HashMap<>();
    private String line;
    private int cnt;
    static int points;
    String attempts = " | | | | | | | | | | ";
    
    @FXML
    private Label prevAnswerLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }    
    
    public void init(){
        points = 0;
        cnt = 20;
        toastMsg.setVisible(false);
        correctAnswerLabel.setVisible(false);
        prevAnswerLabel.setVisible(false);
        answerTextField.clear();
        question_genarator();
        answerButton.setDefaultButton(true);
        dict_generator(ques, correctAns);
        result();
        int randomNum = random_generator();
        attemptsLabel.setText(attempts);
        questionLabel.setText(ques.get(randomNum).toUpperCase());
        pointLabel.setText("0");
    }

    @FXML
    private void answerButtonAction(ActionEvent event) throws Exception {
       check();
      // result();
    }
    
    public void check() throws Exception{
        int randomNum = random_generator();
        toastMsg.setVisible(false);
        correctAnswerLabel.setVisible(false);
        String str = answerTextField.getText().toLowerCase().trim();
        String correct = dict.get(questionLabel.getText().toLowerCase());
        
        if(str.isEmpty()){
            prevAnswerLabel.setVisible(false);
            toastMsg.setVisible(true);
          //  toast.makeText(stage, "Please enter your answer", 1500, 300, 300);
        }
        else{
            
            if(str.equals(correct)){
                points ++;
                prevAnswerLabel.setVisible(false);
                String pts = String.valueOf(points);
                pointLabel.setText(pts);
                //int f_pts = final_points(points);
            }else {
                cnt-=4;
                prevAnswerLabel.setVisible(true);
                correctAnswerLabel.setVisible(true);
                correctAnswerLabel.setText(correct);
                attemptsLabel.setText(attempts.substring(0, cnt));
               // final_points(0);
                
//                System.out.println("exact value : "+getPoints());
                
                if (cnt == 0) {
                    retryStage();
                    init();
                }
                
            }
        
            questionLabel.setText(ques.get(randomNum).toUpperCase());
            answerTextField.clear();
        }
    }
    
    public void retryStage() throws Exception{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLWindowDocument.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1)); 
            stage.setTitle("Result");
            stage.show();
        } catch(Exception e) {
           e.printStackTrace();
        }
    }
    
    
    public void question_genarator(){
        String ans;
        try{
            File file = new File("scrumbler.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            
            while ((line = reader.readLine()) != null){
                ans = line.toLowerCase().trim();
                correctAns.add(ans);
                
                line = shuffle(line);
                line = line.toLowerCase().trim();
                ques.add(line);
            }
            reader.close();
            
        }catch (IOException e)  {
            e.printStackTrace();
        }
    }
    
//    public void final_points(int points){
//       this.points = points;
//       // System.out.println("checking : " + this.points);
//    }
//    
//    public int getPoints() {
//       return points;
//    }
    
    public void dict_generator(ArrayList<String> ques, ArrayList<String> correctAns){
        int quesSize = ques.size();
        int ansSize = correctAns.size();
        int cnt = 0;
        
        if(quesSize == ansSize){
            for (int i=0; i<ques.size(); i++) {
                dict.put(ques.get(i), correctAns.get(i));    // is there a clearer way?
            }
        }
    }
    
    
    public int random_generator(){
        int min = 0;
        int max = ques.size();
        int randomNum = ThreadLocalRandom.current().nextInt(min, max);
        return randomNum;
    }
    
    public String shuffle(String input){
        List<Character> characters = new ArrayList<>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(!characters.isEmpty()){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
        //System.out.println(output.toString());
    }

    @FXML
    private void textEntered(KeyEvent event) {
        toastMsg.setVisible(false);
    }
    
    public String result(){
        //this.points = points;
        String res = "Your point is : "+points+". \n";
        int attempt_cnt = 10;
        int avg = points/attempt_cnt;
        
        if(avg >= 10) res += "That's some shit right there, you should write your own dictionary";
        
        if (avg >= 6 && avg < 10) res += "Teach the developer some english, he's so bad";
        
        if (avg >= 3 && avg <6 ) res += "Welcome to school";
        
        if (avg > 0 && avg <3) res += "Nigga have you ever gone to school?";
        
        if (avg == 0) res += "Damn, you're so bad, cow!!!!!";

        return res;
    }

}
