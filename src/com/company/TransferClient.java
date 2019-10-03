package com.company;

import java.io.*;
import TransferApp.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class TransferClient
{
    static Transfer transferImpl;

    public static void main (String args[])

    {
        try {
            ORB orb=ORB.init(args,null);

            /* Get a CORBA object reference to NameServer   */
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");

            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name="Transfer";

            /*get an object reference to the Hello Server and narrow it to Hello Object*/
            transferImpl=TransferHelper.narrow(ncRef.resolve_str(name));

            System.out.println("obtained a handle on server object \n" + transferImpl);
            System.out.println("Do you want to open or save the file?(o/s)");
            java.io.DataInputStream in = new java.io.DataInputStream(System.in);

            String c= in.readLine();

            /*Open the contents of the file*/
            if(c.equalsIgnoreCase("o"))
            {
                System.out.println("Contents of the file....");
                System.out.println();
                System.out.println(transferImpl.transfer(args[0]));
            }

            /*Transfer the file contents to the specified location */
            if(c.equalsIgnoreCase("s"))
            {
                System.out.println("Specify the path to store the file : ");
                String sPath=in.readLine(); //Input the destination path
                FileOutputStream fout = new FileOutputStream(sPath);
                //Write the file contents to the new file created
                new PrintStream(fout).println (transferImpl.transfer(args[0]));
                fout.close();
                System.out.println("File transferred to the specified location...");
            }
            transferImpl.shutdown(); //invoke shutdown method
        }
        catch(Exception e)
        {
            System.out.println("error"+e);
            e.printStackTrace(System.out);
        }
    }
}