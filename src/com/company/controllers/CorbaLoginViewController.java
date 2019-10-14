package com.company.controllers;

import com.company.views.CorbaLoginView;
import javafx.stage.Stage;

public class CorbaLoginViewController {

    private Stage stage;
    private CorbaLoginView corbaLoginView;

    public CorbaLoginViewController(Stage stage){
        this.corbaLoginView = new CorbaLoginView();
        this.stage = stage;
    }

    public void create(){
        this.corbaLoginView.createScene();
        this.corbaLoginView.createCorbaViewScene(stage);

        this.stage.setTitle("Corba File Manager");
        this.stage.setScene(this.corbaLoginView.getScene());
        this.stage.show();
    }

}
