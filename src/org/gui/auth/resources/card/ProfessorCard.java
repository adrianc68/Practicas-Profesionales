package org.gui.auth.resources.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Professor;

public class ProfessorCard extends Card{
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

    public ProfessorCard(Professor professor) {
        this.professor = professor;
        initCard();
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
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
        Label nameProfessorLabel = new Label( professor.getName() );
        nameProfessorLabel.getStyleClass().add("cardLabel");
        nameProfessorLabel.setFont( Font.font(FONT_SIZE) );
        nameProfessorLabel.setWrapText(true);
        nameProfessorLabel.setAlignment(Pos.CENTER);
        Label staffNumberLabel = new Label( professor.getStaffNumber() );
        staffNumberLabel.setFont( Font.font(FONT_SIZE) );
        staffNumberLabel.setWrapText(true);
        staffNumberLabel.getStyleClass().add("cardLabel");
        Label cubicleLabel = new Label( String.valueOf( professor.getCubicle() ) );
        cubicleLabel.setFont( Font.font(FONT_SIZE) );
        cubicleLabel.setWrapText(true);
        cubicleLabel.getStyleClass().add("cardLabel");
        Label phoneNumber = new Label(String.valueOf( professor.getPhoneNumber() ) );
        phoneNumber.setFont( Font.font(FONT_SIZE) );
        phoneNumber.setWrapText(true);
        phoneNumber.getStyleClass().add("cardLabel");
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setCursor(Cursor.HAND);
        setPadding( new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET) );
        setSpacing(SPACING);
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(imageBox);
        getChildren().add(nameProfessorLabel);
        getChildren().add(staffNumberLabel);
        getChildren().add(cubicleLabel);
        getChildren().add(phoneNumber);
        setStyleClass();
    }

}
