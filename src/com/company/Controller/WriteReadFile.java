package com.company.Controller;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class WriteReadFile <T>
{
    private ArrayList<T> objects;
    private T singleObject;
    private String fileName;

    public WriteReadFile(T singleObject, String fileName) {
        this.singleObject = singleObject;
        this.fileName = fileName;
    }

    public WriteReadFile(ArrayList<T> objects, String fileName) {
        this.objects = objects;
        this.fileName = fileName;
    }



    public void writeList () {

        try {
            FileOutputStream fileOutputStream=new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeInt(objects.size());
            for (T object : objects)
            {
                objectOutputStream.writeObject(object);
            }
            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }


    }

    public ArrayList<T> readList(){

        try
        {
            FileInputStream fileInputStream=new FileInputStream(fileName);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);


            int size=objectInputStream.readInt();

            for (int i=0 ; i<size ; i++)
            {
                T object = (T) objectInputStream.readObject();
                objects.add(object);
            }

            fileInputStream.close();
            objectInputStream.close();

            return objects;
        }catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return objects;




    }


    public void write() throws IOException {
        FileOutputStream fileOutputStream=new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(singleObject);

        fileOutputStream.close();
        objectOutputStream.close();
    }

    public T read() throws IOException, ClassNotFoundException {

        try
        {
            FileInputStream fileInputStream=new FileInputStream(fileName);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            T object=(T) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();

            return object;
        }catch (EOFException e)
        {
            System.out.println(e.getMessage());
        }catch (IOException | ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        return null;

    }

}
