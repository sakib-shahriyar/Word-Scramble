/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordscramble;


import com.sun.prism.shader.AlphaTexture_ImagePattern_AlphaTest_Loader;
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
import javafx.fxml.Initializable;
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
   
    WordScramble stage = new WordScramble();
    ArrayList <String> ques = new ArrayList<>();
    ArrayList <String> correctAns = new ArrayList<>();   
    HashMap<String, String> dict = new HashMap<>();
    private String line;
    private int cnt;
    int points;
    String attempts = " | | | | | | | | | |";
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
        dict_generator(ques, correctAns);
        int randomNum = random_generator();
        attemptsLabel.setText(attempts);
        questionLabel.setText(ques.get(randomNum));
        pointLabel.setText("0");
    }

    @FXML
    private void answerButtonAction(ActionEvent event) throws InterruptedException {
        int randomNum = random_generator();
        toastMsg.setVisible(false);
        correctAnswerLabel.setVisible(false);
        String str = answerTextField.getText().toLowerCase().trim();
        String correct = dict.get(questionLabel.getText());
        
        if(str.isEmpty()){
            prevAnswerLabel.setVisible(false);
            toastMsg.setVisible(true);
          //  toast.makeText(stage, "Please enter your answer", 1500, 300, 300);
        }
        else{
            
            if(str.equals(correct)){
                points ++;
                String pts = String.valueOf(points);
                pointLabel.setText(pts);
            }else {
                cnt-=2;
                prevAnswerLabel.setVisible(true);
                correctAnswerLabel.setVisible(true);
                correctAnswerLabel.setText(correct);
                attemptsLabel.setText(attempts.substring(0, cnt));
            }
        
            questionLabel.setText(ques.get(randomNum));
            answerTextField.clear();
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
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
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

}
