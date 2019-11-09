package com.example.aptiv.Model.Helpers;

import android.content.Context;

import com.example.aptiv.Model.Classe.Profile;
import com.example.aptiv.View.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class DataReaderWriter {

    private static DataReaderWriter SINGLE_INSTANCE ;
    private String filename;
    private MainActivity activity;
    private DataReaderWriter(MainActivity activity) {
        this.activity = activity;
        this.filename  = "objectTest.txt";
    }

    public static DataReaderWriter getInstance(MainActivity activity) {
        if (SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new DataReaderWriter(activity);
        }
        return SINGLE_INSTANCE;
    }

    public void WriteObjectToFile(Object serObj) {

        File f = new File(filename);
        if (!f.exists()) {
            f.mkdir();
        }
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(serObj);
            oos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Profile> ReadObjectFromFile(){
        ArrayList<Profile> ObjectList = new ArrayList<>();
        File f = new File(filename);
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<Object> deserializeStudent = (ArrayList<Object> )ois.readObject();
            ois.close();

            Iterator<Object> iter = deserializeStudent.iterator();
            while((iter).hasNext()){
                Profile s = (Profile) (iter).next();
                ObjectList.add(s);
            }
            return ObjectList;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

