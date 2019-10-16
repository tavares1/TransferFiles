package com.company.transfers;

import TransferApp.*;
import com.company.utils.FileManager;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class TransferClient
{
    static Transfer transferImpl;
    static TransferP2P transferP2PImpl;
    static boolean logged = false;
    private String name;
    private String[] files;
    private String folderPath;
    private FileManager fileManager;
    private String clientPort;
    static String staticPath;

    public TransferClient(String nickname, String path, String clientPort) {
        name = nickname;
        folderPath = path;
        this.staticPath = path;
        fileManager = new FileManager(path);
        this.clientPort = clientPort;
        this.files = getFiles();
        clientP2P();
    }

    public void clientP2P(){
        try {
            String[] port = new String[]{"-ORBInitialPort", clientPort};
            ORB orb=ORB.init(port,null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

            rootpoa.the_POAManager().activate(); //Activate POA manager
            P2PImpl p2pimpl = new P2PImpl();
            p2pimpl.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(p2pimpl);
            TransferP2P href= TransferP2PHelper.narrow(ref); //cast CORBA object reference to a proper type

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef=NamingContextExtHelper.narrow(objRef);
            String name= this.name;
            NameComponent path[]=ncRef.to_name(name);
            ncRef.rebind(path,href);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void connectWithCentralServer() {
        try {
            //Conexão com o servidor central.
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

    public void connectWithP2P(String ownerOfFileName ) {
        try {
            String[] port = new String[]{"-ORBInitialPort", clientPort};
            ORB orb = ORB.init(port, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = ownerOfFileName;
            transferP2PImpl = TransferP2PHelper.narrow(ncRef.resolve_str(name));
            System.out.println("obtained a handle on server object \n" + transferImpl);
        }
        catch(Exception e) {
            System.out.println("error"+e);
            e.printStackTrace(System.out);
        }
    }

    public boolean verifyIfNickNameIsValid() {
        return transferImpl.login(name);
    }

    public void sendLocalFilesToCentralServer() {
        transferImpl.enviarListaDeArquivos(name, fileManager.getFiles());
    }

    public UserFiles[] getFilesFromServer() {
        UserFiles[] arquivosDeOutrosClientes = transferImpl.requisitarListaDeArquivosComUser();
        if (arquivosDeOutrosClientes.length == 0) {
            return null;
        } else {
            return arquivosDeOutrosClientes;
        }
    }

    public void logout() {
        transferImpl.logout(name);
    }

    public String[] getFiles() {
         return fileManager.getFiles();
    }

    public String getName () {
        return name;
    }

    public boolean downloadFile(String filename,String file,byte[] array) {
        try {
            fileManager.writeFileToFolder(filename, file, array);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public byte[] downloadToP2P(String fileName) {
        return transferP2PImpl.downloadFile(fileName);
    }
}