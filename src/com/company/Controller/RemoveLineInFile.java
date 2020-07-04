package com.company.Controller;

import java.io.*;
import java.util.ArrayList;

public class RemoveLineInFile {

    public void removeLine(String fileName , String oldLine) throws IOException {

        oldLine=oldLine.trim();

        BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));

        //save all lines in a arraylist except the line we want to remove
        ArrayList<String> tempData=new ArrayList<>();

        String line;
        for (int i=0; (line =bufferedReader.readLine()) !=null ; i++)
        {
            line=line.trim();
            if (!oldLine.equals(line))
            {
                tempData.add(line);
            }

        }

        //clean the file and save the first lineif it exists
        BufferedWriter badBufferedWriter=new BufferedWriter(new FileWriter(fileName));
        if (tempData.size()>0)
        {
            badBufferedWriter.write(tempData.get(0));
            badBufferedWriter.newLine();
            badBufferedWriter.close();
        }

        //writing the rest of the lines in the file if they exist
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName, true));

        for (int i=1 ; i<tempData.size() ; i++)
        {
            bufferedWriter.write(tempData.get(i));
            bufferedWriter.newLine();
        }
        bufferedWriter.close();

    }
}
