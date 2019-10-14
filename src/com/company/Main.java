package com.company;

import com.company.controllers.CorbaLoginViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            CorbaLoginViewController corbaLoginViewController = new CorbaLoginViewController(primaryStage);
            corbaLoginViewController.create();
        } catch (Exception e){
            throw new Exception(e);
        }
    }

    public static void main(String args[]){
        launch(args);
    }
}
