package com.company;

import TransferApp.TransferPOA;
import org.omg.CORBA.ORB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class TransferImpl extends TransferPOA
{
    private ORB orb;
    private Map<String,String[]> files = new HashMap<>();


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
        orb.shutdown(false);
    }
}