package com.tubeproject.view.administration;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.tubeproject.controller.User;
import com.tubeproject.model.ContextMap;
import com.tubeproject.model.interfaces.Injectable;
import com.tubeproject.utils.ImageUtils;
import com.tubeproject.view.Resources;
import com.tubeproject.view.StageManager;
import com.tubeproject.view.component.BurgerMenu;
import com.tubeproject.view.component.WebButton;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ViewAdministrator implements Initializable, Injectable {

    @FXML
    private ImageView imgView;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane webButtonPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger burger;

    @FXML
    private Button editLines;

    @FXML
    private Button editFares;

    @FXML
    private Button btnStats;

    private BurgerMenu burgerPane;

    private Map<String, Object> contextMap;

    @FXML
    private void handleButtonActionHomePage() {
        ContextMap.getContextMap().put("USER", null);
        StageManager.changeStage(anchorPane, Resources.ViewFiles.MAIN_SCREEN);
    }

    @Override
    public void injectMap(Map<String, Object> map) {
        contextMap = map;
        burgerPane.checkUserLoggedIn((User) contextMap.get("USER"));
        webButtonPane.getChildren().add(new WebButton((HostServices) contextMap.get("HOSTED")));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawer.setVisible(false);
        initializeImgView();
        initializeBackground();
        initializeBurger();
        editLines.setOnAction((event) -> StageManager.changeStage(anchorPane, Resources.ViewFiles.EDIT_LINES_SCREEN, Resources.Stylesheets.MENU));
        editFares.setOnAction((event) -> StageManager.changeStage(anchorPane, Resources.ViewFiles.EDIT_FARES_SCREEN, Resources.Stylesheets.MENU));
        btnStats.setOnAction((event) -> StageManager.changeStage(anchorPane, Resources.ViewFiles.STATISTICS_SCREEN, Resources.Stylesheets.MENU));

    }

    private void initializeBackground() {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, false);
        BackgroundImage bgImg = ImageUtils.loadBackgroundImage(Resources.Images.BACKGROUND, backgroundSize);
        anchorPane.setBackground(new Background(bgImg));
    }


    private void initializeImgView() {
        InputStream stream = getClass().getResourceAsStream(Resources.Images.LOGO1);
        Image img = new Image(stream);
        this.imgView.setImage(img);
    }


    public void initializeBurger() {
        burgerPane = new BurgerMenu();
        drawer.setSidePane(burgerPane);
        HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(burger);
        transition.setRate(-1);
        burger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (drawer.isShown()) {
                drawer.close();
                drawer.setVisible(false);
            } else {
                drawer.setVisible(true);
                drawer.open();
            }
        });
    }

}
