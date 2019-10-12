package com.company;

import java.io.*;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Arrays;

import TransferApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

class FileManager {
    private File folder;

    FileManager(String pathname) {
        folder = new File(pathname);
    }

    private static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList <String> filesNames = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                filesNames.add(fileEntry.toString());
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

    public void writeFileToFolder(String file) throws IOException {
        FileOutputStream fout = new FileOutputStream(folder+"/\teste.txt");
        //Write the file contents to the new file created
        new PrintStream(fout).println (file);
        fout.close();
        System.out.println("Arquivo foi baixado com sucesso!");
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

            //            Recebendo o apelido do cliente.
            System.out.println("Qual o seu nickname? :");
            java.io.DataInputStream in=new java.io.DataInputStream(System.in);
            String nickName = in.readLine();
            System.out.println("Olá "+nickName+"!\n\n");

            //            Recebendo o caminho da pasta compartilhada do cliente.
            System.out.println("Qual o caminho da pasta compartilhada? :");
            java.io.DataInputStream in2 =new java.io.DataInputStream(System.in);
            String pathname = in2.readLine();

            //            Enviando arquivos locais para o servidor.

            FileManager fm = new FileManager(pathname);
            transferImpl.enviarListaDeArquivos(nickName, fm.getFiles());

            //            Recebendo a lista de todos os arquivos do servidor.

            String[] arquivosDeOutrosClientes = transferImpl.requisitarListaDeArquivos(nickName);
            if (arquivosDeOutrosClientes.length == 0) {
                System.out.println("Não tem nenhum arquivo no servidor");
            } else {
                for (String arquivo: arquivosDeOutrosClientes) {
                    String file = transferImpl.transfer(arquivo);
                    System.out.println(file);
                    fm.writeFileToFolder(file);
                }
            }

//            Fazer esperar pra sair

            System.out.println("Digite S para sair do programa");
            java.io.DataInputStream sair =new java.io.DataInputStream(System.in);
            String c=in.readLine();

            if(c.equalsIgnoreCase("s"))
            {
                transferImpl.shutdown();
            }

        }
        catch(Exception e)
        {
            System.out.println("error"+e);
            e.printStackTrace(System.out);
        }
    }
}
