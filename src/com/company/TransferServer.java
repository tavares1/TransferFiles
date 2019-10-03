package com.company;

import TransferApp.Transfer;
import TransferApp.TransferHelper;
import TransferApp.TransferPOA;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.io.*;
import java.util.Properties;

class TransferImpl extends TransferPOA
{
    private ORB orb;

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
    public void shutdown()
    {
        orb.shutdown(false);
    }
}
public class TransferServer
{
    public static void main(String args[])
    {
        try
        {
            /*Create and initialize ORB*/
            ORB orb=ORB.init(args,null);
            POA rootpoa = POAHelper.narrow    (orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate(); //Activate POA manager
            TransferImpl transferimpl = new TransferImpl();
            transferimpl.setORB(orb);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(transferimpl);
            Transfer href= TransferHelper.narrow(ref); //cast CORBA object reference to a proper type
            org.omg.CORBA.Object objRef=orb.resolve_initial_references("NameService");
            NamingContextExt ncRef=NamingContextExtHelper.narrow(objRef);
            String name="Transfer";
            NameComponent path[]=ncRef.to_name(name);
            ncRef.rebind(path,href);
            System.out.println("TransferServer ready and wating");
            orb.run(); //Waits until an invocation comes from ORB client
        }
        catch(Exception e)
        {
            System.err.println("Error"+e);
            e.printStackTrace(System.out);
        }
        System.out.println("TransferServer Exiting");
    }
}