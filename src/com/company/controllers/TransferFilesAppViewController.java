package com.company.controllers;

import com.company.transfers.TransferClient;
import com.company.views.CorbaLoginView;
import com.company.views.TransferFilesApp;
import javafx.stage.Stage;

public class TransferFilesAppViewController {

    private Stage stage;
    private TransferFilesApp transferFilesApp;

    public TransferFilesAppViewController(Stage stage, TransferClient client){
        this.transferFilesApp = new TransferFilesApp(client, this);
        this.stage = stage;
    }


    public void create(){
        this.transferFilesApp.createScene();
        this.transferFilesApp.createCorbaViewScene(stage);

        this.stage.setTitle("Transfer File App");
        this.stage.setScene(this.transferFilesApp.getScene());
        this.stage.show();

    }

    public void reloadTableView() {
        transferFilesApp.reloadTableView();
    }

}
