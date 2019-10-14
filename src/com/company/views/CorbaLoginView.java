package com.company.views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CorbaLoginView {

    private Scene scene;
    private StackPane pane;

    private TextField nickname;
    private TextField path;
    private Button createClient;

    public CorbaLoginView(){
        this.pane = new StackPane();
    }


    //TODO criar acao do botao de login
    public void createCorbaViewScene(){
        VBox corbaScreen = new VBox();
        GridPane loginForm = new GridPane();

        Label title = new Label("Corba File Manager");

        Text nickLabel = new Text("Nickname: ");
        nickname = new TextField();
        Text pathLabel = new Text("Path: ");
        path = new TextField();

        loginForm.add(nickLabel, 0, 0);
        loginForm.add(nickname, 1, 0);
        loginForm.add(pathLabel, 0,2);
        loginForm.add(path, 1,2);

        createClient = new Button("Create");


        corbaScreen.getChildren().addAll(title, loginForm, createClient);
        this.pane.getChildren().add(corbaScreen);
    }

    public void createScene(){
        this.scene = new Scene(this.pane, 400,300);
    }

    public Scene getScene() {
        return scene;
    }

    public StackPane getPane() {
        return pane;
    }

    public TextField getNickname() {
        return nickname;
    }

    public TextField getPath() {
        return path;
    }

    public Button getCreateClient() {
        return createClient;
    }
}
