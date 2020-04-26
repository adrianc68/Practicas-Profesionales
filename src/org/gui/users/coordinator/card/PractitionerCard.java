package org.gui.users.coordinator.card;

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
import javafx.scene.input.MouseEvent;
import org.domain.Practitioner;
import org.domain.Project;

public class PractitionerCard extends VBox{
    private final int CARD_HEIGHT = 430;
    private final int CARD_WIDTH = 235;
    private final int IMAGE_HEIGHT = 100;
    private final int IMAGE_WIDTH = 100;
    private final int FONT_SIZE = 12;
    private final int FONT_SIZE_MIN = 10;
    private final int SPACING = 10;
    private final int TOP_INSET = 15;
    private final int BOTTOM_INSET = 15;
    private final int LEFT_INSET = 10;
    private final int RIGHT_INSET = 10;
    private final int BUTTON_HEIGHT = 45;
    private final int BUTTON_WIDTH = 125;
    private final int LIST_VIEW_HEIGHT = 103;
    private Practitioner practitioner;
    private ListView<Project> selectedProjectsListView;
    private Button button;
    private ObservableList<Project> observableList;

    public PractitionerCard(Practitioner practitioner) {
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
        imageBox.setStyle("-fx-border-color:#323232");
        Image profilePicture = new Image(getClass().getResourceAsStream("/org/gui/resources/profilePictureDefault.png"));
        ImageView imageView = new ImageView(profilePicture);
        imageView.setFitHeight(IMAGE_HEIGHT);
        imageView.setFitWidth(IMAGE_WIDTH);
        imageBox.getChildren().add(imageView);

        Label namePractitionerLabel = new Label( practitioner.getName() );
        namePractitionerLabel.setFont(Font.font(FONT_SIZE));
        namePractitionerLabel.setWrapText(true);
        Label enrollmentPractitionerLabel = new Label( practitioner.getEnrollment() );
        enrollmentPractitionerLabel.setFont(Font.font(FONT_SIZE));
        enrollmentPractitionerLabel.setWrapText(true);
        Label professorAssignedLabel = new Label();
        String nameProfessor = (practitioner.getProfessor() == null ) ? "Sin profesor asignado" : practitioner.getProfessor().getName();
        professorAssignedLabel.setText(nameProfessor);
        professorAssignedLabel.setFont(Font.font(FONT_SIZE));
        professorAssignedLabel.setWrapText(true);
        Label projectAssignedLabel = new Label();
        String nameProject = (practitioner.getProject() == null) ? "Sin proyecto asignado" : practitioner.getProject().getName();
        projectAssignedLabel.setText(nameProject);
        projectAssignedLabel.setFont(Font.font(FONT_SIZE));
        projectAssignedLabel.setWrapText(true);

        Label projectedSelectedLabel = new Label("Proyectos seleccionados");
        projectedSelectedLabel.setFont(Font.font(FONT_SIZE_MIN));
        projectedSelectedLabel.setWrapText(true);
        VBox projectsSelectedLabelBox = new VBox();
        projectsSelectedLabelBox.setStyle("fx-border-color:#323232");
        projectsSelectedLabelBox.setFillWidth(true);
        projectsSelectedLabelBox.setAlignment(Pos.CENTER_LEFT);
        projectsSelectedLabelBox.getChildren().add(projectedSelectedLabel);

        selectedProjectsListView = new ListView<>();
        selectedProjectsListView.setPrefHeight(LIST_VIEW_HEIGHT);
        selectedProjectsListView.setMaxHeight(LIST_VIEW_HEIGHT);
        observableList = FXCollections.observableArrayList();
        observableList.setAll(practitioner.getSelectedProjects());
        selectedProjectsListView.setItems(observableList);

        button = new Button("Asignar");
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setPrefWidth(BUTTON_WIDTH);


        HBox buttonBox = new HBox();
        buttonBox.setFillHeight(true);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().add(button);

        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setStyle("-fx-border-color:#323232;");
        setCursor(Cursor.HAND);
        setPadding( new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET) );
        setSpacing(SPACING);
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(imageBox);
        getChildren().add(namePractitionerLabel);
        getChildren().add(enrollmentPractitionerLabel);
        getChildren().add(professorAssignedLabel);
        getChildren().add(projectAssignedLabel);
        getChildren().add(projectsSelectedLabelBox);
        getChildren().add(selectedProjectsListView);
        getChildren().add(buttonBox);
        setOnMouseEntered( (MouseEvent event) -> { setStyle("-fx-border-color:#0043ff;-fx-border-width:2 2 2 2;"); });
        setOnMouseExited( (MouseEvent event) -> { setStyle("-fx-border-color:#323232;-fx-border-width:1 1 1 1;"); });
    }

}
