package org.gui.auth.resources.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Delivery;
import org.util.CSSProperties;

public class DeliveryCard extends VBox {
    private final int CARD_HEIGHT = 100;
    private final int CARD_WIDTH = 1000;
    private final int FONT_SIZE = 22;
    private final int FONT_SMALL_SIZE = 13;
    private final int TITLE_BOX_HEIGHT = 43;
    private final int DESCRIPTION_BOX_HEIGHT = 73;
    private final int DESCRIPTION_CONTAINER_WIDTH = 856;
    private final int SCORE_BOX_WIDTH = 144;
    private final int SPACING = 15;
    private final int SPACING_CONTAINER = 1;
    private final int SPACING_CONTENT = 5;
    private final int TOP_INSET = 5;
    private final int BOTTOM_INSET = 5;
    private final int LEFT_INSET = 5;
    private final int RIGHT_INSET = 5;
    private final int PADDING = 15;
    private final int NO_PADDING = 0;
    private final int DESCRIPTION_PADDING = 10;
    private Delivery delivery;

    public DeliveryCard(Delivery delivery) {
        this.delivery = delivery;
        initCard();
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void repaint() {
        getChildren().clear();
        initCard();
    }

    private void initCard() {
        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("cardTopDescription");
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setFillHeight(true);
        titleBox.setMaxSize(CARD_WIDTH, TITLE_BOX_HEIGHT);
        titleBox.setMinSize(CARD_WIDTH, TITLE_BOX_HEIGHT);
        titleBox.setPadding( new Insets(DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING) );
        Label namePractitionerLabel = new Label( delivery.getPractitioner().getName() );
        namePractitionerLabel.setFont( Font.font(FONT_SIZE) );
        namePractitionerLabel.getStyleClass().add("cardFirstLevelLabel");
        titleBox.getChildren().add(namePractitionerLabel);
        HBox descriptionBox = new HBox();
        descriptionBox.setMaxSize(CARD_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionBox.setMinSize(CARD_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionBox.setAlignment(Pos.CENTER);
        VBox observationBox = new VBox();
        observationBox.setAlignment(Pos.TOP_LEFT);
        observationBox.setMaxSize(DESCRIPTION_CONTAINER_WIDTH, DESCRIPTION_BOX_HEIGHT);
        observationBox.setMinSize(DESCRIPTION_CONTAINER_WIDTH, DESCRIPTION_BOX_HEIGHT);
        observationBox.setSpacing(SPACING);
        observationBox.setPadding( new Insets(DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING));
        Label observationLabel = new Label( delivery.getObservation() );
        observationLabel.setFont( Font.font(FONT_SMALL_SIZE) );
        observationLabel.getStyleClass().add("cardThirdLevelLabel");
        observationBox.getChildren().add(observationLabel);
        VBox scoreContainerBox = new VBox();
        scoreContainerBox.setMaxSize(SCORE_BOX_WIDTH, DESCRIPTION_BOX_HEIGHT);
        scoreContainerBox.setMinSize(SCORE_BOX_WIDTH, DESCRIPTION_BOX_HEIGHT);
        scoreContainerBox.getStyleClass().add("cardDateContainer");
        scoreContainerBox.setAlignment(Pos.TOP_CENTER);
        scoreContainerBox.setPadding( new Insets(PADDING, NO_PADDING, NO_PADDING, NO_PADDING) );
        scoreContainerBox.setSpacing(SPACING_CONTENT);
        // T-O Ternary Operator here!
        Label scoreTitleLabel = new Label("Puntaje");
        scoreTitleLabel.getStyleClass().add("cardFirstLevelLabel");
        Label scoreDeliveryLabel = new Label( String.valueOf( delivery.getScore() ) );
        scoreDeliveryLabel.setFont( Font.font(FONT_SIZE) );
        scoreDeliveryLabel.getStyleClass().add("cardSecondLevelLabel");
        scoreContainerBox.getChildren().add(scoreTitleLabel);
        scoreContainerBox.getChildren().add(scoreDeliveryLabel);
        descriptionBox.getChildren().add(observationBox);
        descriptionBox.getChildren().add(scoreContainerBox);
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setSpacing(SPACING_CONTAINER);
        setStyleClass();
        setCursor(Cursor.HAND);
        setPadding(new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET));
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(titleBox);
        getChildren().add(descriptionBox);
    }

    private void setStyleClass() {
        getStylesheets().clear();
        getStyleClass().clear();
        getStylesheets().add(getClass().getResource("../" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        getStyleClass().add("card");
    }

}
