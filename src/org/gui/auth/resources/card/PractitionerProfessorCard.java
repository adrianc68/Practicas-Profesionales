package org.gui.auth.resources.card;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Practitioner;
import org.domain.Project;
import org.util.CSSProperties;

public class PractitionerProfessorCard extends VBox{
    private final int CARD_HEIGHT = 440;
    private final int CARD_WIDTH = 235;
    private final int IMAGE_HEIGHT = 100;
    private final int IMAGE_WIDTH = 100;
    private final int FONT_SIZE = 12;
    private final int FONT_SIZE_MIN = 10;
    private final int SPACING = 15;
    private final int TOP_INSET = 15;
    private final int BOTTOM_INSET = 15;
    private final int LEFT_INSET = 10;
    private final int RIGHT_INSET = 10;
    private final int LIST_VIEW_HEIGHT = 103;
    private Practitioner practitioner;
    private ListView<Project> selectedProjectsListView;
    private Button button;
    private ObservableList<Project> observableList;

    public PractitionerProfessorCard(Practitioner practitioner) {
        this.practitioner = practitioner;
        initCard();
    }

    public Practitioner getPractitioner() {
        return practitioner;
    }

    public void setPractitioner(Practitioner practitioner) {
        this.practitioner = practitioner;
    }

    public ListView<Project> getListView() {
        return selectedProjectsListView;
    }

    public Button getButton() {
        return button;
    }

    public void repaint() {
        getChildren().clear();
        initCard();
    }

    private void initCard() {
        VBox imageBox = new VBox();
        imageBox.setPrefWidth(IMAGE_WIDTH);
        imageBox.setPrefHeight(IMAGE_HEIGHT);
        imageBox.setMaxHeight(IMAGE_HEIGHT);
        imageBox.setMaxWidth(IMAGE_WIDTH);
        imageBox.getStyleClass().add("imageBox");
        Image profilePicture = new Image(getClass().getResourceAsStream("/org/gui/auth/resources/images/profilePictureDefault.png"));
        ImageView imageView = new ImageView(profilePicture);
        imageView.setFitHeight(IMAGE_HEIGHT);
        imageView.setFitWidth(IMAGE_WIDTH);
        imageBox.getChildren().add(imageView);
        Label namePractitionerLabel = new Label( practitioner.getName() );
        namePractitionerLabel.getStyleClass().add("cardLabel");
        namePractitionerLabel.setFont(Font.font(FONT_SIZE));
        namePractitionerLabel.setWrapText(true);
        namePractitionerLabel.setPrefHeight(30);
        namePractitionerLabel.setAlignment(Pos.CENTER);
        namePractitionerLabel.setMinHeight(USE_PREF_SIZE);
        Label enrollmentPractitionerLabel = new Label( practitioner.getEnrollment() );
        enrollmentPractitionerLabel.getStyleClass().add("cardLabel");
        enrollmentPractitionerLabel.setFont(Font.font(FONT_SIZE));
        enrollmentPractitionerLabel.setWrapText(true);
        Label projectAssignedLabel = new Label();
        // T-O Ternary Operator
        String nameProject = (practitioner.getProject() == null) ? "Sin proyecto asignado" : practitioner.getProject().getName();
        projectAssignedLabel.getStyleClass().add("cardLabel");
        projectAssignedLabel.setText(nameProject);
        projectAssignedLabel.setFont(Font.font(FONT_SIZE));
        projectAssignedLabel.setMinHeight(USE_PREF_SIZE);
        projectAssignedLabel.setWrapText(true);
        Label projectedSelectedLabel = new Label("Proyectos seleccionados");
        projectedSelectedLabel.getStyleClass().add("cardSmallLabel");
        projectedSelectedLabel.setFont(Font.font(FONT_SIZE_MIN));
        projectedSelectedLabel.setWrapText(true);
        VBox projectsSelectedLabelBox = new VBox();
        projectsSelectedLabelBox.getStyleClass().add("cardLabel");
        projectsSelectedLabelBox.setFillWidth(true);
        projectsSelectedLabelBox.setAlignment(Pos.CENTER_LEFT);
        projectsSelectedLabelBox.getChildren().add(projectedSelectedLabel);
        selectedProjectsListView = new ListView<>();
        selectedProjectsListView.getStyleClass().add("listView");
        selectedProjectsListView.setPrefHeight(LIST_VIEW_HEIGHT);
        selectedProjectsListView.setMaxHeight(LIST_VIEW_HEIGHT);
        observableList = FXCollections.observableArrayList();
        observableList.setAll( practitioner.getSelectedProjects() );
        selectedProjectsListView.setItems(observableList);
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setCursor(Cursor.HAND);
        setPadding( new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET) );
        setSpacing(SPACING);
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(imageBox);
        getChildren().add(namePractitionerLabel);
        getChildren().add(enrollmentPractitionerLabel);
        getChildren().add(projectAssignedLabel);
        getChildren().add(projectsSelectedLabelBox);
        getChildren().add(selectedProjectsListView);
        setStyleClass();
    }

    private void setStyleClass() {
        getStylesheets().clear();
        getStyleClass().clear();
        getStylesheets().add(getClass().getResource("../" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        getStyleClass().add("card");
    }

}
