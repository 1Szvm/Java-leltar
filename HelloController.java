package com.example.leltargui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class HelloController {

    @FXML private ListView<String> lsEszkoz;
    @FXML private ComboBox<String> cmEvek;
    @FXML private ListView<String> listEv;

    private FileChooser fc = new FileChooser();

    public void initialize(){
        fc.setInitialDirectory(new File("./"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Fájlok","*.csv"));
    }

    @FXML private void onMegnyitasClick(){
        File fbe=fc.showOpenDialog(lsEszkoz.getScene().getWindow());
        if(fbe!=null){
            list.clear();
            lsEszkoz.getItems().clear();
            betolt(fbe);
            TreeSet<Integer> evek = new TreeSet<>();
            for (Eszkoz e: list){
                lsEszkoz.getItems().add(String.format("%d : %s (%d x %,d,-Ft)",e.ev,e.nev,e.darab,e.ar));
                evek.add(e.ev);
            }
            for(Integer ev:evek){
                cmEvek.getItems().add(ev+"adatai:");
            }
            cmEvek.getSelectionModel().select(0);
        }
    }

    @FXML private void onEvekChange(){
        listEv.getItems().clear();
        for (Eszkoz e:list){
            if(e.ev==Integer.parseInt(cmEvek.getSelectionModel().getSelectedItem().split(" ")[0])){
                listEv.getItems().add(String.format("%d x %s = %,d,-Ft",e.darab,e.nev,e.ar));
            }
        }
    }

    private void betolt(File fajl){
        Scanner be = null;
        try{
            be= new Scanner(fajl,"utf-8");
            be.nextLine();
            while (be.hasNextLine()){
                list.add(new Eszkoz(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public class Eszkoz{
        public String nev;
        public int ev;
        public int darab;
        public int ar;

        public Eszkoz(String sor){
            String[] s =sor.split(";");
            nev=s[0];
            ev=Integer.parseInt(s[1]);
            darab=Integer.parseInt(s[2]);
            ar=Integer.parseInt(s[3]);
        }
    }

    public ArrayList<Eszkoz> list = new ArrayList<>();

    @FXML private void onKilépesClick(){
        Platform.exit();
    }

    @FXML private void onNevjegyzekClikc(){
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Névjegy");
        info.setHeaderText(null);
        info.setContentText("Leltár v1.0.0\n(C) Kandó");
        info.showAndWait();
    }
}