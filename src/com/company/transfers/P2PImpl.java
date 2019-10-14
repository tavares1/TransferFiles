package com.company.transfers;

import TransferApp.TransferP2P;
import TransferApp.TransferP2PPOA;
import org.omg.CORBA.ORB;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

class P2PImpl extends TransferP2PPOA {
    private ORB orb;
    private byte[] array;

    public void setORB(ORB orb_val) {
        orb=orb_val;
    }

    @Override
    public byte[] downloadFile(String fileName) {
        String sharedFolder = TransferClient.staticPath;
        //Write the file contents to the new file created
        File newFile = new File(sharedFolder+"/"+fileName);
        try {
            array = Files.readAllBytes(Paths.get(newFile.getAbsolutePath()));
        } catch (Exception e) {
            System.out.println(e);
        }
        return array;
    }
}
