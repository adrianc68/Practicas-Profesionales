package org.gui.auth.users.practitioner.activity.adddelivery;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.database.dao.DeliveryDAO;
import org.domain.Activity;
import org.domain.Delivery;
import org.domain.Practitioner;
import org.gui.ValidatorController;
import org.gui.auth.resources.alerts.OperationAlert;
import org.util.PDFViewer;
import org.util.SwingToFX;
import org.util.Validator;
import org.util.ftp.FTPConnection;
import javax.swing.JPanel;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddDeliveryController extends ValidatorController implements Initializable {
    private boolean addOperationStatus;
    private String fileDirectory;
    private File selectedFile;
    private Activity activity;
    private Practitioner practitioner;
    private Delivery actualDelivery;
    @FXML private AnchorPane pdfDisplayerAnchorPane;
    @FXML private AnchorPane rootStage;
    @FXML private Label systemLabel;
    @FXML private TextField pathTextField;
    @FXML private MaterialDesignIconView checkIconPath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setStyleClass(rootStage);
        initValidatorToTextFields();
        setExistingDeliveryToActivity();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/practitioner/activity/adddelivery/AddDeliveryVista.fxml"), this);
        stage.showAndWait();
    }

    public AddDeliveryController(Activity activity, Practitioner practitioner) {
        this.activity = activity;
        this.practitioner = practitioner;
    }

    @FXML
    protected void closeButtonPressed(ActionEvent event) {
        stage.close();
    }

    @FXML
    protected void stageDragged(MouseEvent event) {
        super.stageDragged(event);
    }

    @FXML
    protected void stagePressed(MouseEvent event) {
        super.stagePressed(event);
    }

    @FXML
    protected void addDeliveryButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
                addDelivery( selectedFile );
                if(addOperationStatus) {
                    stage.close();
                    showSuccessfullAlert();
                }
        }
    }

    @FXML
    protected void selectButtonPressed(ActionEvent event) {
        showFileChooser();
        if( isFileChoosenPDF( pathTextField.getText() ) ) {
            showPDFInComponent();
        } else {
            systemLabel.setText("El archivo no se puede visualizar (Puede que no sea un PDF o este dañado)");
            clearFileSelection();
        }
    }

    private void showSuccessfullAlert() {
        String title = "¡Se ha entregado la actividad correctamente!";
        String contentText = "¡Se ha entregado la actividad correctamente! puedes volver a subirla si no se ha excedido la fecha límite.";
        OperationAlert.showSuccessfullAlert(title, contentText);
    }

    private void clearFileSelection() {
        pdfDisplayerAnchorPane.getChildren().clear();
    }

    private void showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona el archivo a subir");
        selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null ) {
            fileDirectory = practitioner.getName() + ":" + activity.getName() + ":" + activity.getId() + ":" + selectedFile.getName();
            pathTextField.setText( selectedFile.getAbsolutePath() );
        }
    }

    private void showPDFInComponent() {
        PDFViewer pdfViewer = new PDFViewer( pathTextField.getText() );
        JPanel pdfPanel = pdfViewer.getJPanelPDF();
        Node node = SwingToFX.convertJComponentToNode( pdfPanel );
        pdfDisplayerAnchorPane.getChildren().clear();
        pdfDisplayerAnchorPane.getChildren().add( node );
    }

    private boolean isFileChoosenPDF(String path) {
        boolean isPDF = false;
        int extensionLength = 4;
        if( path.length() > extensionLength ) {
            String fileExtension = path.substring( path.length() - extensionLength );
            isPDF = fileExtension.equals(".pdf");
        }
        return isPDF;
    }

    private void addDelivery(File file) {
        Delivery delivery = new Delivery();
        if ( saveFileInServer(file) ) {
            delivery.setPractitioner(practitioner);
            delivery.setActivity(activity);
            delivery.setDocumentPath( fileDirectory );
            delivery.setObservation(null);
            delivery.setScore(0);
            removeOldDeliveryFromFTP();
            try {
                delivery.setId( new DeliveryDAO().addDeliveryToActivity(delivery) );
            } catch (SQLException e) {
                OperationAlert.showSuccessfullAlert( "¡Error!", e.getMessage() );
                Logger.getLogger( AddDeliveryController.class.getName() ).log(Level.WARNING, null, e);
            }
        } else {
            removeInvalidDeliveryFromFTP();
        }
        addOperationStatus = ( delivery.getId() != 0);
    }

    private boolean saveFileInServer(File file) {
        FTPConnection ftpConnection = new FTPConnection();
        boolean isFileSaved = ftpConnection.uploadFile( file, fileDirectory );
        return isFileSaved;
    }

    private void setExistingDeliveryToActivity() {
        if( activity != null && practitioner != null ) {
            try {
                actualDelivery = new DeliveryDAO().getDeliveryByActivityAndPractitioner( activity.getId(), practitioner.getId() );
            } catch (SQLException e) {
                Logger.getLogger( AddDeliveryController.class.getName() ).log(Level.WARNING, null, e);
            }
        }
    }

    private void removeInvalidDeliveryFromFTP() {
        if( fileDirectory != null ) {
            FTPConnection ftpConnection = new FTPConnection();
            ftpConnection.deleteFile( fileDirectory );
        }
    }

    private void removeOldDeliveryFromFTP() {
        if( actualDelivery != null ) {
            FTPConnection ftpConnection = new FTPConnection();
            ftpConnection.deleteFile( actualDelivery.getDocumentPath() );
        }
    }

    private void initValidatorToTextFields() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(pathTextField, false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] nameConstraints = {Validator.PATH_PATTERN, Validator.PATH_LENGTH, checkIconPath};
        validator.put(pathTextField, nameConstraints);
        initValidatorToTextInputControl(validator);
    }

}
