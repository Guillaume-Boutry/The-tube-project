package com.tubeproject.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.tubeproject.utils.FXMLUtils;
import com.tubeproject.utils.ImageUtils;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreen extends Application implements Initializable {

    @FXML
    private ImageView imgView;

    @FXML
    private ImageView imgViewUser;

    @FXML
    private ImageView imgViewPassword;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton facebookIcon;

    @FXML
    private JFXButton twitterIcon;

    @FXML
    private JFXButton instagramIcon;

    @FXML
    private JFXButton mailIcon;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Label txtLabel;

    @FXML
    private Label lbConnectToServer;

    @FXML
    private void handleButtonActionHomePage() {
        AnchorPane homePage;
        try {
            homePage = FXMLLoader.load(getClass().getResource(Resources.ViewFiles.MAIN_SCREEN));

        } catch (IOException e) {
            System.out.println("Warning unandled exeption.");
            return;
        }
        Scene homeScene = new Scene(homePage);
        Stage homeStage = (Stage) anchorPane.getScene().getWindow();
        homeStage.setScene(homeScene);
        homeStage.show();
    }


    @FXML
    private void handleButtonActionSignUp(ActionEvent event) {
        AnchorPane signUpPage;
        try {
            signUpPage = FXMLLoader.load(getClass().getResource(Resources.ViewFiles.SIGN_UP_SCREEN));

        } catch (IOException e) {
            System.out.println("Warning unandle exeption.");
            return;
        }
        Scene signUpScene = new Scene(signUpPage);
        Stage signUpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        signUpStage.setScene(signUpScene);
        signUpStage.show();
    }


    @FXML
    private void handleButtonActionTryLogin(ActionEvent event) {
        checkFields();
    }

    public static void startWindow() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLUtils.loadFXML(Resources.ViewFiles.LOGIN_SCREEN);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeImgView();
        initializeBackground();
        initializeIcons();
    }

    private void initializeImgView() {
        InputStream stream = getClass().getResourceAsStream(Resources.Images.LOGO1);
        Image img = new Image(stream);
        imgView.setImage(img);
        stream = getClass().getResourceAsStream(Resources.Images.PASSWORD);
        img = new Image(stream);
        imgViewPassword.setImage(img);
        stream = getClass().getResourceAsStream(Resources.Images.USERNAME);
        img = new Image(stream);
        imgViewUser.setImage(img);
    }

    private void initializeBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, false);
        BackgroundImage bgImg = ImageUtils.loadBackgroundImage(Resources.Images.BACKGROUND, backgroundSize);
        anchorPane.setBackground(new Background(bgImg));
    }

    private void initializeIcons() {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage bgImg = ImageUtils.loadBackgroundImage(Resources.Images.FACEBOOK, backgroundSize);
        facebookIcon.setBackground(new Background(bgImg));

        backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        bgImg = ImageUtils.loadBackgroundImage(Resources.Images.TWITTER, backgroundSize);
        twitterIcon.setBackground(new Background(bgImg));


        backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        bgImg = ImageUtils.loadBackgroundImage(Resources.Images.INSTAGRAM, backgroundSize);
        instagramIcon.setBackground(new Background(bgImg));

        backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        bgImg = ImageUtils.loadBackgroundImage(Resources.Images.MAIL, backgroundSize);
        mailIcon.setBackground(new Background(bgImg));

    }

    private void checkFields() {
        String red = "#ef5353";
        String black = "#151928";
        if (txtPassword.getText().isEmpty() || !txtPassword.getText().equals("mdp")) {
            changeNodeColor(txtPassword, red);
            changeLabelVisibility(true);
        }
        if (txtUsername.getText().isEmpty() || !txtUsername.getText().equals("Sophie")) {
            changeNodeColor(txtUsername, red);
            changeLabelVisibility(true);
        }

        //if bon
        if (txtUsername.getText().equals("Sophie") && txtPassword.getText().equals("mdp")) {
            changeLabelVisibility(false);
            System.out.println("ON EST SUR LA SCENE SUIVANTE WALLAHs");
            changeNodeColor(txtPassword, black);
            changeNodeColor(txtUsername, black);
        }
        tryingToConnectToServer();
    }


    //red #ef5353
    //black #151928
    private void changeNodeColor(JFXTextField txtField, String color) {
        txtField.setFocusColor(Paint.valueOf(color));
        txtField.setUnFocusColor(Paint.valueOf(color));
        txtField.setStyle(String.format("-fx-text-inner-color : %s", color));
        txtField.setStyle(String.format("-fx-prompt-text-fill : %s", color));
        txtField.setStyle(String.format("-fx-text-inner-color: %s", color));
    }

    private void changeNodeColor(JFXPasswordField txtField, String color) {
        txtField.setFocusColor(Paint.valueOf(color));
        txtField.setUnFocusColor(Paint.valueOf(color));
        txtField.setStyle(String.format("-fx-text-inner-color : %s", color));
        txtField.setStyle(String.format("-fx-prompt-text-fill : %s", color));
        txtField.setStyle(String.format("-fx-text-inner-color: %s", color));
    }


    private void changeLabelVisibility(boolean value) {
        txtLabel.setVisible(value);

    }

    private void tryingToConnectToServer() {
        lbConnectToServer.setVisible(true);
        TranslateTransition translate = new TranslateTransition(Duration.seconds(.4), lbConnectToServer);
        translate.setFromX(0.0);
        translate.setFromY(0);
        translate.setToX(0);
        translate.setToY(-50);
        translate.play();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), lbConnectToServer);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }
}