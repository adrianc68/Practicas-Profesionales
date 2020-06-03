package org.gui.auth.resources.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.domain.Organization;
import org.util.CSSProperties;

public class OrganizationCard extends VBox{
    private final int CARD_HEIGHT = 305;
    private final int CARD_WIDTH = 235;
    private final int IMAGE_HEIGHT = 100;
    private final int IMAGE_WIDTH = 100;
    private final int FONT_SIZE = 12;
    private final int SPACING = 5;
    private final int TOP_INSET = 15;
    private final int BOTTOM_INSET = 15;
    private final int LEFT_INSET = 10;
    private final int RIGHT_INSET = 10;
    private Organization organization;

    public OrganizationCard(Organization organization) {
        this.organization = organization;
        initCard();
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
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
        Image profilePicture = new Image(getClass().getResourceAsStream("/org/gui/auth/resources/images/profileOrganizationDefault.png"));
        ImageView imageView = new ImageView(profilePicture);
        imageView.setFitHeight(IMAGE_HEIGHT);
        imageView.setFitWidth(IMAGE_WIDTH);
        imageBox.getChildren().add(imageView);
        Label companyNameLabel = new Label( organization.getName() );
        companyNameLabel.getStyleClass().add("cardLabel");
        companyNameLabel.setFont(Font.font(FONT_SIZE));
        companyNameLabel.setWrapText(true);
        Label companyAddressLabel = new Label( organization.getAddress() );
        companyAddressLabel.getStyleClass().add("cardLabel");
        companyAddressLabel.setFont(Font.font(FONT_SIZE));
        companyAddressLabel.setWrapText(true);
        Label companyCityLabel = new Label( organization.getCity() );
        companyCityLabel.getStyleClass().add("cardLabel");
        companyCityLabel.setFont(Font.font(FONT_SIZE));
        companyCityLabel.setWrapText(true);
        Label companyStateLabel = new Label( organization.getState() );
        companyStateLabel.getStyleClass().add("cardLabel");
        companyStateLabel.setFont(Font.font(FONT_SIZE));
        companyStateLabel.setWrapText(true);
        Label companyEmailLabel = new Label( organization.getEmail() );
        companyEmailLabel.getStyleClass().add("cardLabel");
        companyEmailLabel.setFont(Font.font(FONT_SIZE));
        companyEmailLabel.setWrapText(true);
        Label companyPhoneNumberLabel = new Label( organization.getPhoneNumber() );
        companyPhoneNumberLabel.getStyleClass().add("cardLabel");
        companyPhoneNumberLabel.setFont( Font.font(FONT_SIZE) );
        companyPhoneNumberLabel.setWrapText(true);
        Label companyDirectUsersLabel = new Label( String.valueOf( organization.getDirectUsers() ) );
        companyDirectUsersLabel.getStyleClass().add("cardLabel");
        companyDirectUsersLabel.setFont( Font.font(FONT_SIZE) );
        companyDirectUsersLabel.setWrapText(true);
        Label companyIndirectUsersLabel = new Label (String.valueOf( organization.getIndirectUsers() ) );
        companyIndirectUsersLabel.getStyleClass().add("cardLabel");
        companyIndirectUsersLabel.setFont( Font.font(FONT_SIZE) );
        companyIndirectUsersLabel.setWrapText(true);
        Label companySectorLabel = new Label ( organization.getSector().getSector() );
        companySectorLabel.getStyleClass().add("cardLabel");
        companySectorLabel.setFont( Font.font(FONT_SIZE) );
        companySectorLabel.setWrapText(true);
        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setCursor(Cursor.HAND);
        setPadding(new Insets(TOP_INSET, RIGHT_INSET, BOTTOM_INSET, LEFT_INSET));
        setSpacing(SPACING);
        setAlignment(Pos.TOP_CENTER);
        getChildren().add(imageBox);
        getChildren().add(companyNameLabel);
        getChildren().add(companyAddressLabel);
        getChildren().add(companyCityLabel);
        getChildren().add(companyStateLabel);
        getChildren().add(companyEmailLabel);
        getChildren().add(companyPhoneNumberLabel);
        getChildren().add(companyDirectUsersLabel);
        getChildren().add(companySectorLabel);
        setStyleClass();
    }
    
    private void setStyleClass() {
        getStylesheets().clear();
        getStyleClass().clear();
        getStylesheets().add(getClass().getResource("../" + CSSProperties.readTheme().getTheme() ).toExternalForm() );
        getStyleClass().add("card");
    }

}
