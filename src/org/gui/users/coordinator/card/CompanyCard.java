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
import org.domain.Company;

public class CompanyCard extends VBox{
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
    private Company company;

    public CompanyCard(Company company) {
        this.company = company;
        initCard();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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
        Image profilePicture = new Image(getClass().getResourceAsStream("/org/gui/resources/profileCompanyDefault.png"));
        ImageView imageView = new ImageView(profilePicture);
        imageView.setFitHeight(IMAGE_HEIGHT);
        imageView.setFitWidth(IMAGE_WIDTH);
        imageBox.getChildren().add(imageView);

        Label companyNameLabel = new Label( company.getName() );
        companyNameLabel.setFont(Font.font(FONT_SIZE));
        companyNameLabel.setWrapText(true);

        Label companyAddressLabel = new Label( company.getAddress() );
        companyAddressLabel.setFont(Font.font(FONT_SIZE));
        companyAddressLabel.setWrapText(true);

        Label companyCityLabel = new Label( company.getCity() );
        companyCityLabel.setFont(Font.font(FONT_SIZE));
        companyCityLabel.setWrapText(true);

        Label companyStateLabel = new Label( company.getState() );
        companyStateLabel.setFont(Font.font(FONT_SIZE));
        companyStateLabel.setWrapText(true);

        Label companyEmailLabel = new Label( company.getEmail() );
        companyEmailLabel.setFont(Font.font(FONT_SIZE));
        companyEmailLabel.setWrapText(true);

        Label companyPhoneNumberLabel = new Label( company.getPhoneNumber() );
        companyPhoneNumberLabel.setFont(Font.font(FONT_SIZE));
        companyPhoneNumberLabel.setWrapText(true);

        Label companyDirectUsersLabel = new Label( String.valueOf( company.getDirectUsers() ) );
        companyDirectUsersLabel.setFont(Font.font(FONT_SIZE));
        companyDirectUsersLabel.setWrapText(true);

        Label companyIndirectUsersLabel = new Label (String.valueOf( company.getIndirectUsers() ) );
        companyIndirectUsersLabel.setFont(Font.font(FONT_SIZE));
        companyIndirectUsersLabel.setWrapText(true);

        Label companySectorLabel = new Label ( company.getSector().getSector() );
        companySectorLabel.setFont(Font.font(FONT_SIZE));
        companySectorLabel.setWrapText(true);

        setPrefWidth(CARD_WIDTH);
        setPrefHeight(CARD_HEIGHT);
        setMaxWidth(CARD_WIDTH);
        setMaxHeight(CARD_HEIGHT);
        setStyle("-fx-border-color:#323232;");
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
        setOnMouseEntered((MouseEvent event) -> { setStyle("-fx-border-color:#0043ff;-fx-border-width:2 2 2 2;"); });
        setOnMouseExited((MouseEvent event) -> { setStyle("-fx-border-color:#323232;-fx-border-width:1 1 1 1;"); });
    }

}
