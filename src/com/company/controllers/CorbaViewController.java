package com.company.controllers;

import com.company.views.CorbaView;
import javafx.stage.Stage;

public class CorbaViewController {

    private Stage stage;
    private CorbaView corbaView;

    public CorbaViewController(Stage stage){
        this.corbaView = new CorbaView();
        this.stage = stage;
    }

    public void create(){
        this.corbaView.createScene();
        this.corbaView.createCorbaViewScene();

        this.stage.setTitle("Corba File Manager");
        this.stage.setScene(this.corbaView.getScene());
        this.stage.show();
    }
}
