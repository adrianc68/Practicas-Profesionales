package org.gui.users.coordinator.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Professor;

public class ProfessorCard {
    private final int CARD_HEIGHT = 300;
    private final int CARD_WIDTH = 235;
    private final int IMAGE_HEIGHT = 100;
    private final int IMAGE_WIDTH = 100;
    private final int FONT_SIZE = 12;
    private final int SPACING = 10;
    private final int TOP_INSET = 15;
    private final int BOTTOM_INSET = 15;
    private final int LEFT_INSET = 10;
    private final int RIGHT_INSET = 10;
    private Professor professor;
    private VBox vbox;

    public ProfessorCard(Professor professor) {
        this.professor = professor;
        this.vbox = initCard();
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public VBox getVBox() {
        return vbox;
    }

    public void repaint() {
        this.vbox = initCard();
    }

    private VBox initCard() {
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
        Label nameProfessorLabel = new Label( professor.getName() );
        nameProfessorLabel.setFont(Font.font(FONT_SIZE));
        nameProfessorLabel.setWrapText(true);
        Label staffNumberLabel = new Label( professor.getStaffNumber() );
        staffNumberLabel.setFont(Font.font(FONT_SIZE));
        staffNumberLabel.setWrapText(true);
        Label cubicleLabel = new Label( String.valueOf( professor.getCubicle() ) );
        cubicleLabel.setFont(Font.font(FONT_SIZE));
        cubicleLabel.setWrapText(true);
        Label phoneNumber = new Label(String.valueOf( professor.getPhoneNumber() ) );
        phoneNumber.setFont(Font.font(FONT_SIZE));
        phoneNumber.setWrapText(true);
        VBox card = new VBox();
        card.setPrefWidth(CARD_WIDTH);
        card.setPrefHeight(CARD_HEIGHT);
        card.setMaxWidth(CARD_WIDTH);
        card.setMaxHeight(CARD_HEIGHT);
        card.setStyle("-fx-border-color:#323232;");
        card.setCursor(Cursor.HAND);
        card.setPadding( new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET) );
        card.setSpacing(SPACING);
        card.setAlignment(Pos.TOP_CENTER);
        card.getChildren().add(imageBox);
        card.getChildren().add(nameProfessorLabel);
        card.getChildren().add(staffNumberLabel);
        card.getChildren().add(cubicleLabel);
        card.getChildren().add(phoneNumber);
        card.setOnMouseEntered( (MouseEvent event) -> { card.setStyle("-fx-border-color:#0043ff;-fx-border-width:2 2 2 2;"); });
        card.setOnMouseExited( (MouseEvent event) -> { card.setStyle("-fx-border-color:#323232;-fx-border-width:1 1 1 1;"); });
        return card;
    }

}
