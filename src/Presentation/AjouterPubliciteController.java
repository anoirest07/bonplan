/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entite.Etablissement;
import Entite.Paiement;
import Entite.Publicite;
import Services.ServiceEtablissement;
import Services.ServicePaiement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterPubliciteController implements Initializable {

    @FXML
    private TextField titre;
    @FXML
    private TextArea desc;
    @FXML
    private Button ajouter;
    @FXML
    private Button cancel;
    @FXML
    private ImageView pic;
    @FXML
    private ImageView pic1;
      private Stage dialogStage;
    private Publicite s;
    private boolean okClicked = false; 
    @FXML
    private ChoiceBox chb;
    @FXML
    private TextField Numcarte;
    @FXML
    private TextField moisexp;
    @FXML
    private TextField anneexp;
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private JFXButton btn1;
    @FXML
    private TextField cvccode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          if(s != null){
            titre.setText(s.getTitre());
            desc.setText(s.getDescription_publicite());
           ServiceEtablissement sp = new ServiceEtablissement();
         List<String> l=new ArrayList<>();
        l=sp.nouri(AuthentificationController.c.getId_user());
        for (int i=0;i<l.size();i++)
        {//System.out.println(l.get(i).toString());
       chb.getItems().add(l.get(i).toString());
                }
           
            
             //System.out.println(photo.getImage());
             
            
           // photo.setImage();
            
        }
        else{
            System.out.println("done");
        }
        // TODO
    }   
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
 public void setPublicite(Publicite s) {
        this.s =s ;
        titre.setText(s.getTitre());
        desc.setText(s.getDescription_publicite());
        desc.setWrapText(true);
          ServiceEtablissement sp = new ServiceEtablissement();
         List<String> l=new ArrayList<>();
        l=sp.nouri(AuthentificationController.c.getId_user());
        for (int i=0;i<l.size();i++)
        {//System.out.println(l.get(i).toString());
       chb.getItems().add(l.get(i).toString());
                }
        
//        photo.setImage(s.getPhoto_publicite().to);
        
       // System.out.println(s.toString());
    }
 public boolean isOkClicked() {
       
        return okClicked;
    }

    @FXML
    private void ajouter(ActionEvent event) {
           if (isInputValid()) {
            s.setTitre(titre.getText());
            s.setDescription_publicite(desc.getText());
            s.getEtablissement().setNom_etablissement((String) chb.getSelectionModel().getSelectedItem());
             
            //s.setPhoto_publicite(pic.getImage().toString());

            //    getImageUrl = selectedFile.getAbsolutePath();
            // System.out.println(getImageUrl);
            // Image value = new Image(getImageUrl);
            //  img.setImage(value);
           
            okClicked = true;
            dialogStage.close();
        
    }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    private void uploadPic(ActionEvent event) throws MalformedURLException, IOException {
        
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
           
            upload(selectedFile);
            String imageFile = selectedFile.toURI().toURL().toString();
            System.err.println(selectedFile.getName());

            Image image = new Image(imageFile);
            pic.setImage(image);
            s.setPhoto_publicite(selectedFile.getName());
            //////a changer static
            /*IuserService is = new UserService();
            User u;
            u = is.findById(15);
            //////////
            u.setPath(imageFile);
            is.update(u);*/
            /////

        } else {
            System.out.println("file doesn't exist");
    }}
    private boolean isInputValid() {
        String errorMessage = "";
        if (titre.getText() == null || titre.getText().length() == 0) {
            errorMessage += "No valid  Titre!\n"; 
        }
        if (desc.getText() == null || desc.getText().length() == 0) {
            errorMessage += "No valid  Description!\n"; 
        }
        if (pic.getImage()== null) {
            errorMessage += "No valid Photo, !\n"; 
        }
        if (chb.getSelectionModel().getSelectedItem()== null ) {
            errorMessage += "No valid  Etablissement!\n"; 
        }
        if (Numcarte.getText()== null || Numcarte.getText().length() == 0 ) {
            errorMessage += "No valid  Numero Carte!\n"; 
        }
        if (moisexp.getText()== null || moisexp.getText().length() == 0 ) {
            errorMessage += "No valid  Mois d'expiration!\n"; 
        }
        if (anneexp.getText()== null || anneexp.getText().length() == 0 ) {
            errorMessage += "No valid Année d'expiration!\n"; 
        }
        if (cvccode.getText()== null || cvccode.getText().length() == 0 ) {
            errorMessage += "No valid CVC Code!\n"; 
        }
        if (txtAmount.getText()== null || txtAmount.getText().length() == 0 ) {
            errorMessage += "No valid Amount!\n"; 
        }
        


         
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please pay for add Publicite ");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }}
        public String upload(File file) throws FileNotFoundException, IOException {
        BufferedOutputStream stream = null;
        String globalPath="C:\\wamp\\www\\image";
        String localPath="localhost:80/";
        String fileName = file.getName();
        fileName=fileName.replace(" ", "_");
        try {
            Path p = file.toPath();
            
            byte[] bytes = Files.readAllBytes(p);
    
            File dir = new File(globalPath);
            if (!dir.exists())
                dir.mkdirs();
            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()+File.separator + fileName);
            stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            return localPath+"/"+fileName;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AjouterPubliciteController.class.getName()).log(Level.SEVERE, null, ex);
            return "error1";
        } catch (IOException ex) {
            Logger.getLogger(AjouterPubliciteController.class.getName()).log(Level.SEVERE, null, ex);
            return "error2";
        }
    }

    @FXML
    private void payer(ActionEvent event) throws AuthenticationException, InvalidRequestException, APIConnectionException, CardException, APIException {
        ServicePaiement p = new ServicePaiement();
        p.payer("4242424242424242", 12, 18, "458", 1000, "testpaimenet");
    }
    
  
}
