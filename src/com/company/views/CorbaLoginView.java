package com.company.views;

import com.company.controllers.TransferFilesAppViewController;
import com.company.transfers.TransferClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class CorbaLoginView {

    private Scene scene;
    private StackPane pane;

    private TextField nickname;
    private TextField path;
    private TextField porta;
    private Button createClient;

    public CorbaLoginView(){
        this.pane = new StackPane();
    }

    public void createCorbaViewScene(final Stage stage){
        VBox corbaScreen = new VBox();
        GridPane loginForm = new GridPane();

        Label title = new Label("Corba File Manager");

        Text nickLabel = new Text("Nome do Cliente: ");
        nickname = new TextField();

        Text portaLabel = new Text("Porta do Cliente: ");
        porta = new TextField();

        Label actualPath = new Label("C:/");
        Button chooseFolderButton = new Button("Selecionar a pasta compartilhada");

        chooseFolderButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if(selectedDirectory == null){
                //No Directory selected
            }else{
                System.out.println(selectedDirectory.getAbsolutePath());
                actualPath.setText(selectedDirectory.getAbsolutePath());
            }
        });

        loginForm.add(nickLabel, 0, 0);
        loginForm.add(nickname, 1, 0);
        loginForm.add(portaLabel, 0, 2);
        loginForm.add(porta, 1, 2);
        loginForm.add(chooseFolderButton, 0,3);
        loginForm.add(actualPath, 1,3);
        loginForm.setMinSize(400, 200);
        loginForm.setPadding(new Insets(10, 10, 10, 10));
        loginForm.setVgap(5);
        loginForm.setHgap(5);

        createClient = new Button("Create");
        createClient.setOnAction(event -> {
            TransferClient client = new TransferClient(nickname.getText(), actualPath.getText(), porta.getText());
            TransferFilesAppViewController transferVC = new TransferFilesAppViewController(stage, client);
            transferVC.create();
        });

        corbaScreen.setAlignment(Pos.CENTER);
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
