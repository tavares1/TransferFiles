package com.company.transfers;

import java.io.*;
import java.lang.Object;
import java.util.ArrayList;

import TransferApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

class FileManager {
    private File folder;

    FileManager(String pathname) {
        folder = new File(pathname);
    }

    private static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList <String> filesNames = new ArrayList<>();
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
    static boolean logged = false;
    private String name;
    private String folderPath;
    private FileManager fileManager;

    public TransferClient(String nickname, String path) {
        name = nickname;
        folderPath = path;
        fileManager = new FileManager(path);
    }

    public void connectWithCentralServer(String clientPort) {
        try {
            //            Conexão com o servidor central.
            String[] port = new String[]{"-ORBInitialPort", clientPort};
            ORB orb = ORB.init(port, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Transfer";
            transferImpl = TransferHelper.narrow(ncRef.resolve_str(name));
            System.out.println("obtained a handle on server object \n" + transferImpl);
        }
        catch(Exception e) {
                System.out.println("error"+e);
                e.printStackTrace(System.out);
        }
    }

    public boolean verifyIfNickNameIsValid() {
        boolean result = transferImpl.login(name);
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    public void sendLocalFilesToCentralServer() {
        transferImpl.enviarListaDeArquivos(name, fileManager.getFiles());
    }

    public void getFilesFromServer() {
        String[] arquivosDeOutrosClientes = transferImpl.requisitarListaDeArquivos(name);
        if (arquivosDeOutrosClientes.length == 0) {
            System.out.println("Não tem nenhum arquivo no servidor");
        } else {
            for (String arquivo: arquivosDeOutrosClientes) {
                String file = transferImpl.transfer(arquivo);
                System.out.println(file);
                try {
                    fileManager.writeFileToFolder(file);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public void logout() {
        transferImpl.logout(name);
    }
}

