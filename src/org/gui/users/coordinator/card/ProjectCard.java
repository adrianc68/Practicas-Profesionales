package org.gui.users.coordinator.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Project;

public class ProjectCard extends VBox {
    private final int CARD_HEIGHT = 121;
    private final int CARD_WIDTH = 278;
    private final int FONT_SIZE = 16;
    private final int FONT_SMALL_SIZE = 10;
    private final int SPACING = 10;
    private final int TOP_INSET = 5;
    private final int BOTTOM_INSET = 5;
    private final int LEFT_INSET = 5;
    private final int RIGHT_INSET = 5;
    private Project project;
    private VBox vbox;

    public ProjectCard(Project project) {
        this.project = project;
        initCard();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void repaint() {
        getChildren().clear();
        initCard();
    }

    private void initCard() {
        Label nameProject = new Label(project.getName());
        nameProject.setFont(Font.font(FONT_SIZE));
        nameProject.setWrapText(true);

        Label companyName = new Label(project.getCompany().getName());
        companyName.setFont(Font.font(FONT_SMALL_SIZE));
        companyName.setWrapText(true);

        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setStyle("-fx-border-color:#323232;");
        setCursor(Cursor.HAND);
        setPadding(new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET));
        setSpacing(SPACING);
        setAlignment(Pos.CENTER);
        getChildren().add(nameProject);
        getChildren().add(companyName);

        setOnMouseEntered((MouseEvent event) -> { setStyle("-fx-border-color:#0043ff;-fx-border-width:2 2 2 2;"); });
        setOnMouseExited((MouseEvent event) -> { setStyle("-fx-border-color:#323232;-fx-border-width:1 1 1 1;"); });
    }
}
