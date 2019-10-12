package com.company;

import TransferApp.Transfer;
import TransferApp.TransferHelper;
import TransferApp.TransferPOA;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

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