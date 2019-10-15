package com.company.views;

import com.company.models.FileModel;
import com.company.transfers.TransferClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class FileModalDownloadView {

    private Scene scene;
    private StackPane pane;

    private TextField nickname;
    private TextField path;
    private TextField porta;
    private Button createClient;
    private TransferClient client;
    private FileModel model;

    public FileModalDownloadView(FileModel model, TransferClient client) {
        this.pane = new StackPane();
        this.model = model;
        this.client = client;
    }


    //TODO criar acao do botao de login
    public void createCorbaViewScene(final Stage stage){

        Label titleLabel = new Label("Usuário dono do arquivo:");
        Label fileOwnerLabel = new Label(model.getOwner());

        Label fileLabel = new Label("Path do arquivo: ");
        Label pathFileLabel = new Label(model.getFileName());

        Label downloadLabel = new Label("Status do Download: ");
        Label downloadStatusLabel = new Label("Esperando algo para ser baixado...");


        Button btn = new Button("Baixar arquivo!");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File(model.getFileName());
                downloadStatusLabel.setText("Baixando...");
                client.connectWithP2P(model.getOwner());
                byte[] fileInByte = client.downloadToP2P(file.getName());
                if (client.downloadFile(model.getFileName(), file.getName(), fileInByte)) {
                    downloadStatusLabel.setText("Arquivo foi baixado com sucesso!");

                }
            }
        });


        VBox corbaScreen = new VBox(titleLabel,fileOwnerLabel,fileLabel,pathFileLabel, btn, downloadLabel,downloadStatusLabel);

        corbaScreen.setAlignment(Pos.CENTER);
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

}
