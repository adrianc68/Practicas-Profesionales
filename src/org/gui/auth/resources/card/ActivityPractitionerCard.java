package org.gui.auth.resources.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Activity;
import org.domain.Delivery;
import org.util.CSSProperties;

public class ActivityPractitionerCard extends VBox {
    private final int CARD_WIDTH = 980;
    private final int FONT_SIZE = 22;
    private final int FONT_SMALL_SIZE = 13;
    private final int TITLE_BOX_HEIGHT = 43;
    private final int DESCRIPTION_BOX_HEIGHT = 73;
    private final int DESCRIPTION_CONTAINER_WIDTH = 795;
    private final int SCORE_BOX_WIDTH = 185;
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
    private Activity activity;

    public ActivityPractitionerCard(Activity activity) {
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
        titleBox.getStyleClass().add("cardTopDescription");
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.setFillHeight(true);
        titleBox.setMaxSize(CARD_WIDTH, TITLE_BOX_HEIGHT);
        titleBox.setMinSize(CARD_WIDTH, TITLE_BOX_HEIGHT);
        titleBox.setPadding( new Insets(DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING) );
        Label activityTitleLabel = new Label(activity.getName() );
        activityTitleLabel.setFont( Font.font(FONT_SIZE) );
        activityTitleLabel.getStyleClass().add("cardFirstLevelLabel");
        titleBox.getChildren().add(activityTitleLabel);
        Label deadLineTitle = new Label(" (Fecha límite: ");
        deadLineTitle.getStyleClass().add("cardSecondLevelLabel");
        Label deadlineLabel = new Label(String.valueOf( activity.getDeadline() ) );
        deadlineLabel.setFont( Font.font(FONT_SMALL_SIZE) );
        deadlineLabel.getStyleClass().add("cardThirdLevelLabel");
        Label deadLineTitleComplementation = new Label(")" );
        deadLineTitleComplementation.getStyleClass().add("cardSecondLevelLabel");
        titleBox.getChildren().add(deadLineTitle);
        titleBox.getChildren().add(deadlineLabel);
        titleBox.getChildren().add(deadLineTitleComplementation);
        HBox descriptionBox = new HBox();
        descriptionBox.setMaxWidth(CARD_WIDTH);
        descriptionBox.setMinSize(CARD_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionBox.setAlignment(Pos.CENTER);
        VBox descriptionContainerBox = new VBox();
        descriptionContainerBox.setAlignment(Pos.TOP_LEFT);
        descriptionContainerBox.setMaxWidth(DESCRIPTION_CONTAINER_WIDTH);
        descriptionContainerBox.setMinSize(DESCRIPTION_CONTAINER_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionContainerBox.setSpacing(SPACING);
        descriptionContainerBox.setPadding( new Insets(DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING));
        Label descriptionLabel = new Label( activity.getDescription() );
        descriptionLabel.setFont( Font.font(FONT_SMALL_SIZE) );
        descriptionLabel.getStyleClass().add("cardThirdLevelLabel");
        descriptionLabel.setWrapText(true);
        descriptionContainerBox.getChildren().add(descriptionLabel);
        VBox scoreContainerBox = new VBox();
        scoreContainerBox.setMaxWidth(SCORE_BOX_WIDTH);
        scoreContainerBox.setMinSize(SCORE_BOX_WIDTH, DESCRIPTION_BOX_HEIGHT);
        scoreContainerBox.getStyleClass().add("cardDateContainer");
        scoreContainerBox.setAlignment(Pos.TOP_CENTER);
        scoreContainerBox.setPadding( new Insets(PADDING, NO_PADDING, NO_PADDING, NO_PADDING) );
        scoreContainerBox.setSpacing(SPACING_CONTENT);
        Delivery delivery = getDeliveryOfActualActivity();
        // T-O Ternary Operator here!
        Label scoreTitleLabel = new Label("Puntaje");
        scoreTitleLabel.getStyleClass().add("cardFirstLevelLabel");
        Label scoreActivityLabel = new Label( (delivery != null) ? String.valueOf( delivery.getScore() ): "" );
        scoreActivityLabel.setFont( Font.font(FONT_SIZE) );
        scoreActivityLabel.getStyleClass().add("cardSecondLevelLabel");
        scoreContainerBox.getChildren().add(scoreTitleLabel);
        scoreContainerBox.getChildren().add(scoreActivityLabel);
        descriptionBox.getChildren().add(descriptionContainerBox);
        descriptionBox.getChildren().add(scoreContainerBox);
        setPrefWidth(CARD_WIDTH);
        setMaxWidth(CARD_WIDTH);
        setPrefHeight(USE_COMPUTED_SIZE);
        setSpacing(SPACING_CONTAINER);
        setStyleClass();
        setCursor(Cursor.HAND);
        setPadding( new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET) );
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(titleBox);
        getChildren().add(descriptionBox);
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

    private void setStyleClass() {
        getStylesheets().clear();
        getStyleClass().clear();
        getStylesheets().add(getClass().getResource("../" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        getStyleClass().add("card");
    }

}
