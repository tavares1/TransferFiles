package com.company.controllers;

import com.company.transfers.TransferClient;
import com.company.views.FileModalDownloadView;
import com.company.views.FileModel;
import com.company.views.TransferFilesApp;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FileModalDownloadViewController {
    private Stage stage;
    private FileModalDownloadView fileModalDownloadView;

    public FileModalDownloadViewController(Stage stage, TransferClient client, FileModel model){
        this.fileModalDownloadView = new FileModalDownloadView(model, client);
        this.stage = stage;
    }


    public void create(){


        Stage mainStage = new Stage();

        this.fileModalDownloadView.createScene();
        this.fileModalDownloadView.createCorbaViewScene(mainStage);
        mainStage.setTitle("Fazer download.");
        mainStage.setScene(this.fileModalDownloadView.getScene());
        mainStage.initStyle(StageStyle.DECORATED);
        mainStage.show();
    }
}
