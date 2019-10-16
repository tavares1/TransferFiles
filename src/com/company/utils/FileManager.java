package com.company.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileManager {
    private File folder;

    public FileManager(String pathname) {
        folder = new File(pathname);
    }

    public static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList <String> filesNames = new ArrayList<>();
        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    filesNames.add(fileEntry.toString());
                }
            }
            return filesNames;
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }

    }

    public static String[] getStringArray(ArrayList<String> arr)
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

    public void writeFileToFolder(String file, String filename, byte[] array)  {
        try {
            FileOutputStream fout = new FileOutputStream(folder+"/"+filename);
            //Write the file contents to the new file created
            fout.write(array);
            fout.close();
            System.out.println("Arquivo foi baixado com sucesso!");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
