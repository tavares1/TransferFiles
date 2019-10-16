package com.company.views;

import TransferApp.Transfer;
import TransferApp.UserFiles;
import com.company.controllers.FileModalDownloadViewController;
import com.company.controllers.TransferFilesAppViewController;
import com.company.models.FileModel;
import com.company.transfers.TransferClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
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
    private TableView tableView;
    private TransferFilesAppViewController transferVC;
    private ObservableList<FileModel> data = FXCollections.observableArrayList();
    private FilteredList<FileModel> listOfFiles;

    public TransferFilesApp(TransferClient client, TransferFilesAppViewController transferVC){
        this.client = client;
        this.pane = new StackPane();
        this.transferVC = transferVC;
    }

    public void createTransferFileAppScene(final Stage stage){

        //Criando a primeira tableview que ficam os arquivos locais
        tableView = new TableView();

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
        TextField searchBox = new TextField();
        searchBox.setPromptText("Procurar por nome do arquivo");
        searchBox.setOnKeyReleased(event -> {
            this.listOfFiles.setPredicate(p -> p.getFileName().toLowerCase().contains(searchBox.getText().toLowerCase().trim()));
        });
        boxDeAtualizacao.setSpacing(20);
        boxDeAtualizacao.setPadding(new Insets(10,0,10,0));
        boxDeAtualizacao.getChildren().addAll(refreshLabel, refreshButton, searchBox);

        this.tableViewServer = new TableView();

        TableColumn<String,FileModel> tableViewColumnServer = new TableColumn("Dono do Arquivo");
        tableViewColumnServer.setCellValueFactory(new PropertyValueFactory<>("owner"));


        TableColumn<String, FileModel> tableViewColumnServer2 = new TableColumn("Arquivo");
        tableViewColumnServer2.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        this.tableViewServer.getColumns().add(tableViewColumnServer);
        this.tableViewServer.getColumns().add(tableViewColumnServer2);


        tableViewServer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                FileModel model = (FileModel) newValue;
                FileModalDownloadViewController fileModalVC = new FileModalDownloadViewController(stage, client, model, transferVC);
                fileModalVC.create();
            }
        });

        setDataToPopulateTable(client.getFilesFromServer());

        VBox vbox = new VBox(title, tableView, title2, boxDeAtualizacao, tableViewServer);
        vbox.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));
        this.pane.getChildren().add(vbox);



        stage.setOnCloseRequest(event -> client.logout() );

    }

    private UserFiles[] retrieveListFromServer(TransferClient client){
        UserFiles[] serverFiles = client.getFilesFromServer();
        this.data.clear();
        setDataToPopulateTable(serverFiles);
        return serverFiles;
    }

    private void setDataToPopulateTable(UserFiles[] files){
        for(UserFiles file: files){
            for(String pathOfFile: file.files){
                if(!file.name.equalsIgnoreCase(this.client.getName())){
                    FileModel model = new FileModel(file.name, pathOfFile);
                    this.data.add(model);
                }
            }
        }

        this.listOfFiles = new FilteredList(this.data, p -> true);
        this.tableViewServer.setItems(listOfFiles);
    }

    public void createScene(){
        this.scene = new Scene(this.pane, 500,500);
    }

    public Scene getScene() {
        return scene;
    }

    public void reloadTableView() {
        tableView.getItems().clear();
        for (String fileName: client.getFiles()) {
            FileModel file = new FileModel(client.getName(),fileName);
            tableView.getItems().add(file);
        }
    }

}
