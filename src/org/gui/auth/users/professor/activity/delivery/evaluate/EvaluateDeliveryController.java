package org.gui.auth.users.professor.activity.delivery.evaluate;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import org.database.dao.DeliveryDAO;
import org.domain.Delivery;
import org.gui.ValidatorController;
import org.util.CSSProperties;
import org.util.PDFViewer;
import org.util.SwingToFX;
import org.util.Validator;
import org.util.ftp.FTPConnection;
import javax.swing.JPanel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EvaluateDeliveryController extends ValidatorController implements Initializable {
    private boolean updateOperationStatus;
    private Delivery delivery;
    private File deliveryFile;
    @FXML private AnchorPane rootStage;
    @FXML private AnchorPane pdfDisplayerAnchorPane;
    @FXML private MaterialDesignIconView checkIconDescription;
    @FXML private TextArea descriptionTextArea;
    @FXML private MaterialDesignIconView checkIconScore;
    @FXML private Spinner<Double> scoreSpinner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setStyleClass(rootStage, getClass().getResource("../../../../../resources/" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        initSpinner();
        initValidatorToTextInput();
        setActualInformationToTextInputs();
        setStyleClassToCheckIcon();
        downloadDeliveryFileAndDisplayToPDFViewer();
    }

    public void showStage() {
        loadFXMLFile( getClass().getResource("/org/gui/auth/users/professor/activity/delivery/evaluate/EvaluateDeliveryVista.fxml"), this);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public EvaluateDeliveryController(Delivery delivery) {
        this.delivery = delivery;
    }

    public boolean getUpdateOperationStatus() {
        return updateOperationStatus;
    }

    public void initSpinner() {
        scoreSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0,9999,1,1));
    }

    @FXML
    protected void closeButtonPressed(ActionEvent event) {
        deliveryFile.delete();
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
    protected void evaluateButtonPressed(ActionEvent event) {
        if( verifyInputData() ) {
            evaluateDelivery();
            if(updateOperationStatus) {
                deliveryFile.delete();
                stage.close();
            }
        }
    }

    @FXML
    void downloadDeliveryFileToSystem(MouseEvent event) {
        showDirectoryChooserAndSaveFileToNewDirectory();
    }


    private void evaluateDelivery() {
        float score = Float.valueOf( scoreSpinner.getEditor().getText() );
        String observation = descriptionTextArea.getText();
        try {
            updateOperationStatus = new DeliveryDAO().evaluateDeliveryOfActivity(delivery.getId(), score, observation);
        } catch (SQLException e) {
            Logger.getLogger( EvaluateDeliveryController.class.getName() ).log(Level.WARNING, null, e);
        }
    }

    private void showDirectoryChooserAndSaveFileToNewDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecciona una dirección destino para almacenar el archivo");
        File directoryFile = directoryChooser.showDialog(stage);
        if(directoryFile != null ) {
            File newFile = new File( ( directoryFile.getAbsolutePath() + "/" + getFileName( deliveryFile.getName() ) ) );
            saveFileToNewDirectory(deliveryFile, newFile);
        }
    }

    private void saveFileToNewDirectory(File source, File destiny) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(destiny);
            byte[] buffer = new byte[1024];
            int length;
            while( (length = inputStream.read(buffer) ) > 0 ) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            Logger.getLogger( EvaluateDeliveryController.class.getName() ).log(Level.WARNING, null, e);
        } catch (IOException e) {
            Logger.getLogger( EvaluateDeliveryController.class.getName() ).log(Level.WARNING, null, e);
        }
    }

    private String getFileName(String path) {
        int subIndex = 0;
        char delimiter = ':';
        for( int i = path.length() - 1 ; i > 0 ; i-- ) {
            if( path.charAt(i) == delimiter) {
                subIndex = i+1;
                break;
            }
        }
        String fileName = path.substring(subIndex);
        return fileName;
    }

    private void downloadDeliveryFileAndDisplayToPDFViewer() {
        FTPConnection ftpConnection = new FTPConnection();
        deliveryFile = ftpConnection.downloadFile( delivery.getDocumentPath() );
        deliveryFile.deleteOnExit();
        if( isFileAPDF( deliveryFile.getAbsolutePath() ) ) {
            showPDFInComponent();
        }
    }

    private void showPDFInComponent() {
        if( deliveryFile != null ) {
            PDFViewer pdfViewer = new PDFViewer( deliveryFile.getAbsolutePath() );
            JPanel pdfPanel = pdfViewer.getJPanelPDF();
            Node node = SwingToFX.convertJComponentToNode( pdfPanel );
            pdfDisplayerAnchorPane.getChildren().clear();
            pdfDisplayerAnchorPane.getChildren().add( node );
        }
    }

    private boolean isFileAPDF(String path) {
        boolean isPDF = false;
        int extensionLength = 4;
        if( path.length() > extensionLength ) {
            String fileExtension = path.substring( path.length() - extensionLength );
            isPDF = fileExtension.equals(".pdf");
        }
        return isPDF;
    }

    private void setStyleClassToCheckIcon() {
        checkIconScore.setStyleClass("correctlyTextField");
    }

    private void setActualInformationToTextInputs() {
        if( delivery.getObservation() != null ) {
            descriptionTextArea.setText( delivery.getObservation() );
            scoreSpinner.getEditor().setText( String.valueOf( delivery.getScore() ) );
        }
    }

    private void initValidatorToTextInput() {
        interruptorMap = new LinkedHashMap<>();
        interruptorMap.put(descriptionTextArea, false);
        interruptorMap.put(scoreSpinner.getEditor(), false);
        Map<TextInputControl, Object[]> validator = new HashMap<>();
        Object[] scoreConstraints = {Validator.NUMBER_PATTERN, Validator.NUMBER_LENGTH, checkIconScore};
        Object[] descriptionConstraints = {Validator.LARGE_TEXT_PATTERN, Validator.LARGE_TEXT_LENGTH, checkIconDescription};
        validator.put(descriptionTextArea, descriptionConstraints);
        validator.put(scoreSpinner.getEditor(), scoreConstraints);
        initValidatorToTextInputControl(validator);
    }

}
