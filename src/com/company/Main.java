package com.company;

import com.company.controllers.CorbaViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CorbaViewController corbaViewController = new CorbaViewController(primaryStage);
        corbaViewController.create();
    }
    public static void main(String args[]){
        launch(args);
    }
}
