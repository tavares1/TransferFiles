package com.company.transfers;

import TransferApp.TransferPOA;
import TransferApp.UserFiles;
import org.omg.CORBA.ORB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;


class TransferImpl extends TransferPOA
{
    private ORB orb;
    private ArrayList<UserFiles> arrayDeUserFiles = new ArrayList<>();
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
        UserFiles userFiles = new UserFiles(user, list);
        arrayDeUserFiles.add(userFiles);

        System.out.println("**********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************\n");
        System.out.println("*************** TOTAL DE USUÁRIOS ONLINE: "+clientesOnline.toArray().length+" ***********\n");
        System.out.println("!!!!!! TEMOS UMA ATUALIZAÇÃO NA LISTA DE ARQUIVOS !!!!!!!!!!!\n");
        for (UserFiles userFile: arrayDeUserFiles) {
            System.out.println("ARQUIVOS DO USUÁRIOS: "+userFile.name);
            for (String file: userFile.files) {
                System.out.println(file);
            }
        }
        System.out.println("**********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************\n");
        return true;
    }

    @Override
    public boolean login(String user) {
        if (clientesOnline.isEmpty()) {
            clientesOnline.add(user);
            System.out.println("#############################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################################\n");
            System.out.println("## UM NOVO USUÁRIO ENTROU: ##\n"+user);
            System.out.println("####################################################################################################################################################################################################################################################################################################################################################################################################################################\n");
            System.out.println("Total de clientes online = ["+getTotalOfClients()+"]\n");
            return true;
        } else {
            if (clientesOnline.contains(user)) {
                return false;
            } else {
                clientesOnline.add(user);
                System.out.println("#######################################################################################################################################################################################################################################################################################################################################################################################################################################################################\n");
                System.out.println("## UM NOVO USUÁRIO ENTROU: ["+user+"] ##");
                System.out.println("#######################################################################################################################################################################################################################################################################################################################################################################################################################################################################\n");
                System.out.println("Total de clientes online = ["+getTotalOfClients()+"]\n");

                return true;
            }
        }
    }

    @Override
    public void logout(String user) {
        System.out.println("\n");
        System.out.println("UM USUÁRIO SAIU DA APLICAÇÃO: "+user);
        System.out.println("\n");
        clientesOnline.remove(user);


        Iterator<UserFiles> i = arrayDeUserFiles.iterator();

        while(i.hasNext()) {
            UserFiles next = i.next();
            if (next.name.equalsIgnoreCase(user)) {
                i.remove();
                break;
            }
        }

        System.out.println("\n");
        System.out.println("**********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************\n");
        System.out.println("*************** TOTAL DE USUÁRIOS ONLINE: "+clientesOnline.toArray().length+" ***********\n");
        for (UserFiles userFile: arrayDeUserFiles) {
            System.out.println("ARQUIVOS DO USUÁRIOS: "+userFile.name);
            for (String file: userFile.files) {
                System.out.println(file);
            }
        }
        System.out.println("**********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************\n");
        System.out.println("\n");

        System.out.println("Total de clientes online = ["+getTotalOfClients()+"]");
        if (clientesOnline.isEmpty()) {
            shutdown();
        }
    }

    @Override
    public UserFiles[] requisitarListaDeArquivosComUser() {
        UserFiles[] castArrayDeUserFiles = new UserFiles[this.arrayDeUserFiles.toArray().length];
        return arrayDeUserFiles.toArray(castArrayDeUserFiles);
    }

    @Override
    public String[] requisitarListaDeArquivos(String user) {
        return null;
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

    public void shutdown()
    {
        if (clientesOnline.isEmpty()) {
            orb.shutdown(false);
        }
    }
}