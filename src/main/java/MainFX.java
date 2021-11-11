import ctrl.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import repository.*;
import services.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Interface.fxml"));
            Parent root = loader.load();
            Controller ctrl = loader.getController();
            ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Interface");
            primaryStage.show();
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app "+e);
            alert.showAndWait();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    static ServicesClass getService() throws  ServicesException{
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("Interface.properties"));
            String requestFileName=properties.getProperty("SummerActivity");
            if (requestFileName==null){
                requestFileName="SummerActivityFile.txt";
                System.err.println("Requests file not found. Using default "+requestFileName);
            }
            String formsFileName=properties.getProperty("Registration");
            if (formsFileName==null){
                formsFileName="RegistrationFile.txt";
                System.err.println("RepairedForms file not found. Using default "+formsFileName);
            }
            SummerActivityRepository crrRepo = new SummerActivityFileRepository(requestFileName);
            RegistrationRepository crfRepo = new RegistrationFileRepository(formsFileName, crrRepo);
            return new ServicesClass(crrRepo, crfRepo);
        }catch (IOException ex){
            throw new ServicesException("Error starting app "+ex);
        }
    }
}
