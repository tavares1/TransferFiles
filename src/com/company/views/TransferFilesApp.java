package com.company.views;

import TransferApp.UserFiles;
import com.company.controllers.TransferFilesAppViewController;
import com.company.controllers.FileModalDownloadViewController;
import com.company.transfers.TransferClient;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TransferFilesApp {

    private Scene scene;
    private StackPane pane;
    private TransferClient client;
    private TableView tableViewServer;

    public TransferFilesApp(TransferClient client){
        this.client = client;
        this.pane = new StackPane();
    }

    public void createCorbaViewScene(final Stage stage){

        client.connectWithCentralServer();
        client.sendLocalFilesToCentralServer();

        //Criando a primeira tableview que ficam os arquivos locais
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

        //Criando a segunda tableView que ficam os arquivos do servidor.

        Label title2 = new Label("Arquivos que estão no sendo compartilhados:");


        //Criando funcionalidade de atualizacao da lista de arquivos do servidor
        HBox boxDeAtualizacao = new HBox();
        Label refreshLabel = new Label("Atualizar lista de arquivos do servidor");
        Button refreshButton = new Button("Atualizar");
        refreshButton.setOnAction(event -> {
            retrieveListFromServer(this.client);
        });
        boxDeAtualizacao.setSpacing(20);
        boxDeAtualizacao.setPadding(new Insets(10,0,10,0));
        boxDeAtualizacao.getChildren().addAll(refreshLabel, refreshButton);

        this.tableViewServer = new TableView();

        TableColumn<String,FileModel> tableViewColumnServer = new TableColumn("Dono do Arquivo");
        tableViewColumnServer.setCellValueFactory(new PropertyValueFactory<>("owner"));


        TableColumn<String, FileModel> tableViewColumnServer2 = new TableColumn("Arquivo");
        tableViewColumnServer2.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        this.tableViewServer.getColumns().add(tableViewColumnServer);
        this.tableViewServer.getColumns().add(tableViewColumnServer2);


        updateTableWithFiles(client.getFilesFromServer());

        VBox vbox = new VBox(title, tableView, title2, boxDeAtualizacao, tableViewServer);
        vbox.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));
        this.pane.getChildren().add(vbox);
    }

    private UserFiles[] retrieveListFromServer(TransferClient client){
        UserFiles[] serverFiles = client.getFilesFromServer();
        updateTableWithFiles(serverFiles);
        return serverFiles;
    }

    private void updateTableWithFiles(UserFiles[] files){
        if (client.getFilesFromServer() != null) {
            for (UserFiles userfiles: files) {
                for (String pathOfFile: userfiles.files) {
                    if (!userfiles.name.equalsIgnoreCase(client.getName())) {
                        FileModel file = new FileModel(userfiles.name, pathOfFile);
                        this.tableViewServer.getItems().add(file);
                    }
                }
            }
        } else {
            System.out.println("Deu ruim");
            //tableViewServer.setPlaceholder(new Label("Nenhum arquivo para ser baixado."));
        }

//        ADD EVENT LISTINER

        tableViewServer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                FileModel model = (FileModel) newValue;
                FileModalDownloadViewController fileModalVC = new FileModalDownloadViewController(stage, client, model);
                fileModalVC.create();
            }
        });

        VBox vbox = new VBox(title,tableView,title2,tableViewServer);

        vbox.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));

        this.pane.getChildren().add(vbox);

    }


    public void createScene(){
        this.scene = new Scene(this.pane, 1000,500);
    }

    public Scene getScene() {
        return scene;
    }

}
