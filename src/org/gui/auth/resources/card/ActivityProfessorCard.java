package org.gui.auth.resources.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Activity;
import org.util.CSSProperties;

public class ActivityProfessorCard extends VBox {
    private final int CARD_HEIGHT = 100;
    private final int CARD_WIDTH = 1000;
    private final int FONT_SIZE = 35;
    private final int FONT_SMALL_SIZE = 15;
    private final int TITLE_BOX_HEIGHT = 43;
    private final int DESCRIPTION_BOX_HEIGHT = 73;
    private final int DESCRIPTION_CONTAINER_WIDTH = 815;
    private final int SCORE_BOX_WIDTH = 185;
    private final int SPACING = 15;
    private final int SPACING_CONTAINER = 1;
    private final int SPACING_CONTENT = 5;
    private final int TOP_INSET = 5;
    private final int BOTTOM_INSET = 5;
    private final int LEFT_INSET = 5;
    private final int RIGHT_INSET = 5;
    private final int DESCRIPTION_PADDING = 10;
    private final int PADDING = 15;
    private final int NO_PADDING = 0;
    private Activity activity;

    public ActivityProfessorCard(Activity activity) {
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
        HBox descriptionBox = new HBox();
        descriptionBox.setMaxSize(CARD_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionBox.setMinSize(CARD_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionBox.setAlignment(Pos.TOP_CENTER);
        VBox descriptionContainerBox = new VBox();
        descriptionContainerBox.setAlignment(Pos.TOP_LEFT);
        descriptionContainerBox.setMaxSize(DESCRIPTION_CONTAINER_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionContainerBox.setMinSize(DESCRIPTION_CONTAINER_WIDTH, DESCRIPTION_BOX_HEIGHT);
        descriptionContainerBox.setSpacing(SPACING);
        descriptionContainerBox.setPadding( new Insets(DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING, DESCRIPTION_PADDING) );
        Label descriptionLabel = new Label( activity.getDescription() );
        descriptionLabel.getStyleClass().add("cardThirdLevelLabel");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setFont( Font.font(FONT_SMALL_SIZE) );
        descriptionContainerBox.getChildren().add(descriptionLabel);
        VBox dateContainerBox = new VBox();
        dateContainerBox.setMaxSize(SCORE_BOX_WIDTH, DESCRIPTION_BOX_HEIGHT);
        dateContainerBox.setMinSize(SCORE_BOX_WIDTH, DESCRIPTION_BOX_HEIGHT);
        dateContainerBox.getStyleClass().add("cardDateContainer");
        dateContainerBox.setAlignment(Pos.TOP_CENTER);
        dateContainerBox.setSpacing(SPACING_CONTENT);
        dateContainerBox.setPadding( new Insets(PADDING, NO_PADDING, NO_PADDING, NO_PADDING) );
        // T-O Ternary Operator here!
        Label titleDeadlineLabel = new Label("Fecha límite");
        titleDeadlineLabel.getStyleClass().add("cardSecondLevelLabel");
        Label deadLineLabel = new Label( activity.getDeadline().toString() );
        deadLineLabel.getStyleClass().add("cardThirdLevelLabel");
        deadLineLabel.setFont( Font.font(FONT_SMALL_SIZE) );
        deadLineLabel.getStyleClass().add("cardLabel");
        dateContainerBox.getChildren().add(titleDeadlineLabel);
        dateContainerBox.getChildren().add(deadLineLabel);
        descriptionBox.getChildren().add(descriptionContainerBox);
        descriptionBox.getChildren().add(dateContainerBox);
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setSpacing(SPACING_CONTAINER);
        setStyleClass();
        setCursor(Cursor.HAND);
        setPadding(new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET) );
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

