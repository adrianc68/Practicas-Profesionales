package org.gui.auth.resources.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Project;
import org.util.CSSProperties;

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
        Label nameProject = new Label( project.getName() );
        nameProject.setFont( Font.font(FONT_SIZE) );
        nameProject.setWrapText(true);
        nameProject.getStyleClass().add("cardLabel");
        Label companyName = new Label( project.getOrganization().getName() );
        companyName.setFont( Font.font(FONT_SMALL_SIZE) );
        companyName.getStyleClass().add("cardSmallLabel");
        companyName.setWrapText(true);
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setCursor(Cursor.HAND);
        setPadding(new Insets( TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET) );
        setSpacing(SPACING);
        setAlignment(Pos.CENTER);
        getChildren().add(nameProject);
        getChildren().add(companyName);
        setStyleClass();
    }

    private void setStyleClass() {
        getStylesheets().clear();
        getStyleClass().clear();
        getStylesheets().add(getClass().getResource("../" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        getStyleClass().add("card");
    }

}
