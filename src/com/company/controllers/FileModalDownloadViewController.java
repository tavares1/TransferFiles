package com.company.controllers;

import com.company.transfers.TransferClient;
import com.company.views.FileModalDownloadView;
import com.company.models.FileModel;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FileModalDownloadViewController {
    private Stage stage;
    private FileModalDownloadView fileModalDownloadView;
    private TransferFilesAppViewController transferVC;

    public FileModalDownloadViewController(Stage stage, TransferClient client, FileModel model, TransferFilesAppViewController transferVC){
        this.fileModalDownloadView = new FileModalDownloadView(model, client);
        this.transferVC = transferVC;
        this.stage = stage;
    }


    public void create(){


        Stage mainStage = new Stage();

        this.fileModalDownloadView.createScene();
        this.fileModalDownloadView.createCorbaViewScene(mainStage);
        mainStage.setTitle("Fazer download.");
        mainStage.setScene(this.fileModalDownloadView.getScene());
        mainStage.initStyle(StageStyle.DECORATED);
        mainStage.setOnCloseRequest(event -> transferVC.reloadTableView() );
        mainStage.show();

    }



}
