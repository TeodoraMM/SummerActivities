package ctrl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.*;
import services.ServicesClass;
import services.ServicesException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    //for SummerActivity
    @FXML
    private TextField nameSummerActivity,min_ageSummerActivity,max_ageSummerActivity;
    @FXML
    private Text idSummerActivity;
    @FXML
    private DatePicker dateSummerActivity;

    //for Registration
    @FXML
    private TextField nameRegistration,priceRegistration,ageRegistration;
    @FXML
    private TextField nameSearchedSummerActivity;
    @FXML
    private Text getIdRegistration;
    @FXML
    private DatePicker dateRegistration;
    @FXML
    private TableView<SummerActivity> SummerActivityResults;
    @FXML
    private TableView<SummerActivity> tableSummerActivity;

    //connection to services
    private ServicesClass classServices;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private ObservableList<SummerActivity> SummerActivityList = FXCollections.observableArrayList();

    public Controller(){ }

    public void setService(ServicesClass service){

        this.classServices=service;
        List<SummerActivity> lm=service.getAllSummerActivitys();
        SummerActivityList.addAll(lm);
    }

    @FXML
    public void initialize(){
        tableSummerActivity.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> showSummerActivity(newItem));
        tableSummerActivity.setItems(SummerActivityList);

        StringConverter<LocalDate> converter=new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty())
                        ? LocalDate.parse(string, dateFormatter)
                        : null;
            }
        };
        dateSummerActivity.setConverter(converter);
        dateSummerActivity.setValue(LocalDate.now());
        dateSummerActivity.setEditable(false);

        dateRegistration.setConverter(converter);
        dateRegistration.setValue(LocalDate.now());
        dateRegistration.setEditable(false);
    }
    @FXML
    private void showSummerActivity(SummerActivity newItem) {
        if (newItem == null)
            clearFieldsSummerActivity();
        else {
            idSummerActivity.setText(""+newItem.getID());
            min_ageSummerActivity.setText(newItem.getMin_age().toString());
            max_ageSummerActivity.setText(newItem.getMax_age().toString());
            nameSummerActivity.setText(newItem.getName());
        }
    }
    @FXML
    private void clearFieldsSummerActivity() {
        nameSummerActivity.setText("");
        min_ageSummerActivity.setText("");
        max_ageSummerActivity.setText("");
        dateSummerActivity.setValue(LocalDate.now());
    }

    @FXML
    public void addSummerActivityHandler(ActionEvent actionEvent) {
        String name=nameSummerActivity.getText();
        String dateRe=dateSummerActivity.getValue().format(dateFormatter);
        String min_age=min_ageSummerActivity.getText();
        String max_age=max_ageSummerActivity.getText();
        if (checkString(name)&&checkString(dateRe)){
            try {
                int id = classServices.addSummerActivity(name, dateRe,Integer.parseInt(min_age),Integer.parseInt(max_age));
                SummerActivity m=new SummerActivity(id,name,dateRe,Integer.parseInt(min_age),Integer.parseInt(max_age));
                SummerActivityList.add(m);
                idSummerActivity.setText("Request identification number is " + id);
                showNotification("Summer activity successfully added! ", Alert.AlertType.INFORMATION);
                clearFieldsSummerActivity();
            }catch(ServicesException ex){
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }catch (NumberFormatException ex){
                showNotification("The price must be a number! ", Alert.AlertType.ERROR);
                return;
            }
        }
        else
            showNotification("You have to fill in all the fields! ", Alert.AlertType.ERROR);
    }

    @FXML
    public void deleteSummerActivityHandler(ActionEvent actionEvent) {
        int selectedIndex = tableSummerActivity.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            showErrorMessage("You need to select a record from the table !");
            return;
        } else {
            // SummerActivityList.remove(selectedIndex);


            String name = nameSummerActivity.getText();
            String dateRe = dateSummerActivity.getValue().format(dateFormatter);
            String min_age = min_ageSummerActivity.getText();
            String max_age = max_ageSummerActivity.getText();
            Integer id = Integer.parseInt(idSummerActivity.getText());

            System.out.println(id);

            if (checkString(name) && checkString(dateRe)) {
                try {
                    classServices.deleteSummerActivity(id, name, dateRe, Integer.parseInt(min_age), Integer.parseInt(max_age));
                    SummerActivity m = new SummerActivity(id, name, dateRe, Integer.parseInt(min_age), Integer.parseInt(max_age));
                    SummerActivityList.remove(m);
                    showNotification("Request successfully deleted! ", Alert.AlertType.INFORMATION);
                    clearFieldsSummerActivity();
                } catch (ServicesException ex) {
                    showNotification(ex.getMessage(), Alert.AlertType.ERROR);
                } catch (NumberFormatException ex) {
                    showNotification("The min/max age be a number! ", Alert.AlertType.ERROR);
                    return;
                }
            }
            SummerActivityList.remove(selectedIndex);

        }
    }

    void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error :");
        message.setContentText(text);
        message.showAndWait();
    }

    @FXML
    public void updateSummerActivityHandler(ActionEvent actionEvent) {
        int selectedIndex = tableSummerActivity.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            showErrorMessage("You need to select a record from the table !");
            return;
        } else {

            String name = nameSummerActivity.getText();
            String dateRe = dateSummerActivity.getValue().format(dateFormatter);
            String min_age = min_ageSummerActivity.getText();
            String max_age = max_ageSummerActivity.getText();
            Integer id = Integer.parseInt(idSummerActivity.getText());

            if (checkString(name) && checkString(dateRe)) {
                try {
                    classServices.updateSummerActivity(id, name, dateRe, Integer.parseInt(min_age), Integer.parseInt(max_age));
                    SummerActivity m = new SummerActivity(id, name, dateRe, Integer.parseInt(min_age), Integer.parseInt(max_age));
                    SummerActivityList.set(selectedIndex,m);
                    showNotification("Request successfully updated! ", Alert.AlertType.INFORMATION);
                    clearFieldsSummerActivity();
                } catch (ServicesException ex) {
                    showNotification(ex.getMessage(), Alert.AlertType.ERROR);
                } catch (NumberFormatException ex) {
                    showNotification("The min/max age be a number! ", Alert.AlertType.ERROR);
                    return;
                }
            }
        }
    }

    @FXML
    public void addRegistrationHandler(ActionEvent actionEvent) {
        //System.out.println("Add repaired button pressed ..."+repairDate.getValue().format(dateFormatter));
        int selectedRequest=SummerActivityResults.getSelectionModel().getSelectedIndex();
        if (selectedRequest<0){
            showNotification("You must select a request first! ", Alert.AlertType.ERROR);
            return;
        }
        String priceVal=priceRegistration.getText();
        String age=ageRegistration.getText();
        String name=nameRegistration.getText();
        String repairedDa=dateRegistration.getValue().format(dateFormatter);
        if (checkString(priceVal)&&checkString(repairedDa)){
            try{
                int ageInt=Integer.parseInt(age);
                SummerActivity crr=SummerActivityResults.getItems().get(selectedRequest);
                if(ageInt>=crr.getMin_age() && ageInt<=crr.getMax_age())
                {
                    classServices.addRegistration(crr,name,priceVal,repairedDa,ageInt);
                    SummerActivityResults.getItems().remove(selectedRequest);
                    clearRegistrationFields();
                    showNotification("Registration successfully added! ", Alert.AlertType.INFORMATION);
                }
                else
                {
                    showNotification("Age is inappropriate! ", Alert.AlertType.INFORMATION);

                }

            }catch (NumberFormatException ex){
                showNotification("The price must be a number! ", Alert.AlertType.ERROR);
                return;
            }catch (ServicesException ex){
                showNotification(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }
    @FXML
    public void searchSummerActivityHandler(ActionEvent actionEvent) {
        String searchModel=nameSearchedSummerActivity.getText();
        if (!checkString(searchModel)) {
            showNotification("You must introduce a model! ", Alert.AlertType.ERROR);
            return;
        }
        List<SummerActivity> results=classServices.getSummerActivityByName(searchModel);
        SummerActivityResults.getItems().clear();
        SummerActivityResults.getItems().addAll(results);
    }
    @FXML
    private void clearRegistrationFields() {
        nameRegistration.setText("");
        priceRegistration.setText("");
        ageRegistration.setText("");
        dateRegistration.setValue(LocalDate.now());
    }
    private boolean checkString(String s){
        return s==null || s.isEmpty()? false:true;
    }

    private void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
