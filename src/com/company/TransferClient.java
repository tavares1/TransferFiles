package com.company;

import java.io.*;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Arrays;

import TransferApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

class FileManager {
    final File folder = new File("/Users/lucastavares/files_of_folder");

    private static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList <String> filesNames = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                filesNames.add(fileEntry.getName());
            }
        }
        return filesNames;
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

    public String[] getFiles() {
        return getStringArray(listFilesForFolder(folder));
    }
}

public class TransferClient
{
    static Transfer transferImpl;

    public static void main (String args[])

    {
        try {

//            Conexão com o servidor central.
            ORB orb=ORB.init(args,null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name="Transfer";
            transferImpl = TransferHelper.narrow(ncRef.resolve_str(name));
            System.out.println("obtained a handle on server object \n" + transferImpl);
//            Enviando arquivos locais para o servidor.
            FileManager fm = new FileManager();
            transferImpl.enviarListaDeArquivos("tavares", fm.getFiles());
//            Recebendo a lista de todos os arquivos do servidor.
        }
        catch(Exception e)
        {
            System.out.println("error"+e);
            e.printStackTrace(System.out);
        }
    }
}