package com.company.views;

import TransferApp.UserFiles;
import com.company.transfers.TransferClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class TransferFilesApp {

    private Scene scene;
    private StackPane pane;
    private TransferClient client;

    public TransferFilesApp(TransferClient client){
        this.client = client;
        this.pane = new StackPane();
    }


    //TODO criar acao do botao de login
    public void createCorbaViewScene(final Stage stage){

        client.connectWithCentralServer();
        client.sendLocalFilesToCentralServer();

//        Criando a primeira tableview que ficam os arquivos locais
        TableView tableView = new TableView();

        TableColumn<String,FileModel> column1 = new TableColumn("Dono do Arquivo");
        column1.setCellValueFactory(new PropertyValueFactory<>("owner"));

        TableColumn<String, FileModel> column2 = new TableColumn("Arquivo");
        column2.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        Label title = new Label("Arquivos que estão na pasta compartilhada: ");

        for (String fileName: client.getFiles()) {
            FileModel file = new FileModel(client.getName(),fileName);
            tableView.getItems().add(file);
        }

// Criando a segunda tableView que ficam os arquivos do servidor.

        Label title2 = new Label("Arquivos que estão no sendo compartilhados:");
        TableView tableViewServer = new TableView();

        TableColumn<String,FileModel> tableViewColumnServer = new TableColumn("Dono do Arquivo");
        tableViewColumnServer.setCellValueFactory(new PropertyValueFactory<>("owner"));


        TableColumn<String, FileModel> tableViewColumnServer2 = new TableColumn("Arquivo");
        tableViewColumnServer2.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        tableViewServer.getColumns().add(tableViewColumnServer);
        tableViewServer.getColumns().add(tableViewColumnServer2);

        if (client.getFilesFromServer() != null) {
            for (UserFiles userfiles: client.getFilesFromServer()) {
                for (String pathOfFile: userfiles.files) {
                    if (!userfiles.name.equalsIgnoreCase(client.getName())) {
                        FileModel file = new FileModel(userfiles.name, pathOfFile);
                        tableViewServer.getItems().add(file);
                    }
                }
            }
        } else {
            System.out.println("Deu ruim");
//            tableViewServer.setPlaceholder(new Label("Nenhum arquivo para ser baixado."));
        }

        VBox vbox = new VBox(title,tableView,title2,tableViewServer);

        vbox.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));

        this.pane.getChildren().add(vbox);

    }


    public void createScene(){
        this.scene = new Scene(this.pane, 1000,
                500);
    }

    public Scene getScene() {
        return scene;
    }

}
