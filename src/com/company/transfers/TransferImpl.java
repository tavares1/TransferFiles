package com.company.transfers;

import TransferApp.TransferPOA;
import org.omg.CORBA.ORB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TransferImpl extends TransferPOA
{
    private ORB orb;
    private Map<String,String[]> files = new HashMap<>();
    private ArrayList<String> clientesOnline = new ArrayList<>();

    public void setORB(ORB orb_val) {
        orb=orb_val;
    }

    public String transfer(String file1)
    {
        int i;
        String sValue="";
        try {
            FileInputStream fin= new FileInputStream(file1);

            /*Transfer the file to a string variable*/
            do
            {
                i = fin.read(); //Read the file contents
                if(i!= -1)
                    sValue=sValue+((char)i);
            } while(i != -1);
            //Until end of the file is reached, copy the contents
            fin.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Error");
        }
        catch(Exception e){System.exit(0);}
        return sValue; //Return the file as string to the client
    }

    @Override
    public boolean enviarListaDeArquivos(String user, String[] list) {
        files.put(user,list);
        printMap();
        return true;
    }

    @Override
    public boolean login(String user) {
        if (clientesOnline.isEmpty()) {
            clientesOnline.add(user);
            return true;
        } else {
            if (clientesOnline.contains(user)) {
                return false;
            } else {
                clientesOnline.add(user);
                return true;
            }
        }
    }

    @Override
    public void logout(String user) {
        clientesOnline.remove(user);
        System.out.println("Total de clientes online = ["+getTotalOfClients()+"]");
        if (clientesOnline.isEmpty()) {
            shutdown();
        }
    }

    @Override
    public String[] requisitarListaDeArquivos(String user) {
        ArrayList<String> todosOsArquivos = new ArrayList<String>();
        files.entrySet().forEach(entry->{
            if (!entry.getKey().equalsIgnoreCase(user)) {
                for (String path : files.get(entry.getKey())) {
                    todosOsArquivos.add(path);
                }
            }
        });
        return getStringArray(todosOsArquivos);
    }

    public int getTotalOfClients() {
        return clientesOnline.toArray().length;
    }

    private static String[] getStringArray(ArrayList<String> arr)
    {

        String str[] = new String[arr.size()];

        Object[] objArr = arr.toArray();

        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String)obj;
        }

        return str;
    }

    public void printMap() {
        files.entrySet().forEach(entry->{
            System.out.println(entry.getKey());
            for (String path: files.get(entry.getKey())) {
                System.out.println(path);
            };
        });
    }

    public void shutdown()
    {
        if (clientesOnline.isEmpty()) {
            orb.shutdown(false);
        }

    }
}