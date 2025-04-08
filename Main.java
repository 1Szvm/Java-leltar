package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.DoubleToIntFunction;

public class Main {

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

    public Main() throws FileNotFoundException, UnsupportedEncodingException {
        //0.feladat
        betolt("leltar.csv");
        System.out.printf("0) A beolvasott leltári tételek száma: %d\n",list.size());

        int szumAr=0;
        for (Eszkoz e: list) szumAr += e.darab * e.ar;
        System.out.printf("   A benne lévő eszközök összára: %,d,-Ft\n", szumAr);

        //1.feladat
        Eszkoz legdragabb= list.get(0);
        for (Eszkoz e:list){
            if(e.ar>legdragabb.ar){
                legdragabb=e;
            }
        }
        System.out.printf("1) A legdrágább eszköz: %s (%,d,-Ft)\n",legdragabb.nev,legdragabb.ar);

        //2.feladat
        int minEv=list.get(0).ev;
        int maxEv=list.get(0).ev;

        for (Eszkoz e:list){
            if(e.ev<minEv) minEv=e.ev;
            if(e.ev>maxEv) maxEv=e.ev;
        }
        System.out.printf("2) A leltár a %d-%d éveket tartalmazza\n",minEv,maxEv);

        //3.feladat
        TreeMap<Integer, Integer> evDarab = new TreeMap<>();
        for (int ev=minEv; ev<=maxEv; ev++){
            evDarab.put(ev,0);
        }
        for (Eszkoz e:list){
            if(!evDarab.containsKey(e.ev)){
                evDarab.put(e.ev,e.darab);
            }else{
                evDarab.put(e.ev,evDarab.get(e.ev)+e.darab);
            }
        }
        for( Integer ev : evDarab.keySet()){
            if(evDarab.get(ev)!=0) System.out.printf("   %d : %d darab eszköz\n",ev,evDarab.get(ev));
            else System.out.printf("   %d : Nincs eszköz\n",ev);

        }

        //4.feladat
        TreeMap<Integer,Integer> evSzum=new TreeMap<>();
        for (Eszkoz e:list){
            if(!evSzum.containsKey(e.ev)){
                evSzum.put(e.ev,e.darab*e.ar);
            }else{
                evSzum.put(e.ev,evSzum.get(e.ev)+e.darab*e.ar);
            }
        }

        int legEv=list.get(0).ev;
        for (Integer ev:evSzum.keySet()){
            if(evSzum.get(ev)> evSzum.get(legEv)) legEv=ev;
        }
        System.out.printf("4) A legnagyobb öszértékű beszerzés éve: %d\n",legEv);
        System.out.printf("   Ekkor a beszerzési összeg: %,d,-Ft\n",evSzum.get(legEv));

        //5.feladat
        Eszkoz legEszk=list.get(0);
        for (Eszkoz e:list){
            if(e.darab*e.ar>legEszk.darab*legEszk.ar){
                legEszk=e;
            }
        }
        System.out.printf("5) A legnagyobb értékű beszerzés:\n");
        System.out.printf("   %d darab %s = %,d,-Ft\n",legEszk.darab,legEszk.nev,legEszk.darab*legEszk.ar);

        //6.feladat
        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("kezdes.txt"),"utf-8");
            for(Eszkoz e:list){
                if (e.ev==minEv) ki.printf("%d x %s = %d,-Ft\r\n",e.darab,e.nev,e.darab*e.ar);
            }
        }catch (UnsupportedEncodingException e){
            System.out.println(e);
        }finally {
            if(ki !=null) ki.close();
        }
        System.out.printf("6) Az első év adatai kiírva a kezdes.txt-be");
    }

    private void betolt(String FajNev){
        Scanner be = null;
        try{
            be= new Scanner(new File(FajNev),"utf-8");
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

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        new Main();
    }
}
