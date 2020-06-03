package org.gui.auth.resources.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Activity;
import org.domain.Delivery;

public class ActivityCard extends VBox {
    private final int CARD_HEIGHT = 138;
    private final int CARD_WIDTH = 1000;
    private final int FONT_SIZE = 22;
    private final int FONT_SMALL_SIZE = 13;
    private final int TITLE_BOX_HEIGHT = 43;
    private final int DESCRIPTION_BOX_HEIGHT = 93;
    private final int DESCRIPTION_CONTAINER_WIDTH = 856;
    private final int SCORE_BOX_WIDTH = 144;
    private final int SPACING = 15;
    private final int TOP_INSET = 5;
    private final int BOTTOM_INSET = 5;
    private final int LEFT_INSET = 5;
    private final int RIGHT_INSET = 5;
    private Activity activity;

    public ActivityCard(Activity activity) {
        this.activity = activity;
        initCard();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void repaint() {
        getChildren().clear();
        initCard();
    }

    private void initCard() {
        HBox titleBox = new HBox();
        titleBox.setStyle("-fx-background-color:#d5d599");
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setFillHeight(true);
        titleBox.setMaxSize(CARD_WIDTH, TITLE_BOX_HEIGHT);
        titleBox.setMinSize(CARD_WIDTH, TITLE_BOX_HEIGHT);
        Label activityTitleLabel = new Label(activity.getName() );
        activityTitleLabel.setFont( Font.font(FONT_SIZE) );
        titleBox.getChildren().add(activityTitleLabel);
        HBox descriptionBox = new HBox();
        descriptionBox.setMaxSize(CARD_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionBox.setMinSize(CARD_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionBox.setAlignment(Pos.CENTER);
        VBox descriptionContainerBox = new VBox();
        descriptionContainerBox.setAlignment(Pos.CENTER);
        descriptionContainerBox.setMaxSize(DESCRIPTION_CONTAINER_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionContainerBox.setMinSize(DESCRIPTION_CONTAINER_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionContainerBox.setSpacing(SPACING);
        Label descriptionLabel = new Label( activity.getDescription() );
        descriptionLabel.setFont( Font.font(FONT_SMALL_SIZE) );
        descriptionContainerBox.getChildren().add(descriptionLabel);
        Label deadlineLabel = new Label( String.valueOf( activity.getDeadline() ) );
        deadlineLabel.setFont( Font.font(FONT_SMALL_SIZE) );
        descriptionContainerBox.getChildren().add(deadlineLabel);
        VBox scoreContainerBox = new VBox();
        scoreContainerBox.setMaxSize(SCORE_BOX_WIDTH, DESCRIPTION_BOX_HEIGHT);
        scoreContainerBox.setMinSize(SCORE_BOX_WIDTH, DESCRIPTION_BOX_HEIGHT);
        scoreContainerBox.setStyle("-fx-background-color:#6a6a6a;");
        scoreContainerBox.setAlignment(Pos.CENTER);
        Delivery delivery = getDeliveryOfActualActivity();
        // T-O Ternary Operator here!
        Label scoreActivityLabel = new Label( (delivery != null) ? String.valueOf( delivery.getScore() ): "" );
        scoreActivityLabel.setFont(Font.font(FONT_SIZE));
        scoreContainerBox.getChildren().add(scoreActivityLabel);
        descriptionBox.getChildren().add(descriptionContainerBox);
        descriptionBox.getChildren().add(scoreContainerBox);
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setStyle("-fx-border-color:#323232;");
        setCursor(Cursor.HAND);
        setPadding(new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET));
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(titleBox);
        getChildren().add(descriptionBox);
        setOnMouseEntered((MouseEvent event) -> { setStyle("-fx-border-color:#0043ff;-fx-border-width:2 2 2 2;"); });
        setOnMouseExited((MouseEvent event) -> { setStyle("-fx-border-color:#323232;-fx-border-width:1 1 1 1;"); });
    }

    private Delivery getDeliveryOfActualActivity() {
        Delivery actualDelivery = null;
        for (Delivery delivery : activity.getDeliveries() ) {
            if(delivery.getActivity().getId() == activity.getId() ) {
                actualDelivery = delivery;
                break;
            }
        }
        return actualDelivery;
    }

}
