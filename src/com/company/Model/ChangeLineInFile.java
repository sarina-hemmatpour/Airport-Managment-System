package com.company.Model;

import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.FileReader;
        import java.io.IOException;
        import java.io.OutputStreamWriter;
        import java.io.UnsupportedEncodingException;
        import java.io.Writer;

public class ChangeLineInFile {

    public void changeALineInATextFile(String fileName, String newLine, int lineNumber) {
        String content = new String();
        String editedContent = new String();
        content = readFile(fileName);
        editedContent = editLineInContent(content, newLine, lineNumber);
        writeToFile(fileName, editedContent);

    }

    private static int numberOfLinesInFile(String content) {
        int numberOfLines = 0;
        int index = 0;
        int lastIndex = 0;

        lastIndex = content.length() - 1;

        while (true) {

            if (content.charAt(index) == '\n') {
                numberOfLines++;

            }

            if (index == lastIndex) {
                numberOfLines = numberOfLines + 1;
                break;
            }
            index++;

        }

        return numberOfLines;
    }

    private static String[] turnFileIntoArrayOfStrings(String content, int lines) {
        String[] array = new String[lines];
        int index = 0;
        int tempInt = 0;
        int startIndext = 0;
        int lastIndex = content.length() - 1;

        while (true) {

            if (content.charAt(index) == '\n') {
                tempInt++;

                String temp2 = new String();
                for (int i = 0; i < index - startIndext; i++) {
                    temp2 += content.charAt(startIndext + i);
                }
                startIndext = index;
                array[tempInt - 1] = temp2;

            }

            if (index == lastIndex) {

                tempInt++;

                String temp2 = new String();
                for (int i = 0; i < index - startIndext + 1; i++) {
                    temp2 += content.charAt(startIndext + i);
                }
                array[tempInt - 1] = temp2;

                break;
            }
            index++;

        }

        return array;
    }

    private static String editLineInContent(String content, String newLine, int line) {

        int lineNumber = 0;
        lineNumber = numberOfLinesInFile(content);

        String[] lines = new String[lineNumber];
        lines = turnFileIntoArrayOfStrings(content, lineNumber);

        if (line != 1) {
            lines[line - 1] = "\n" + newLine;
        } else {
            lines[line - 1] = newLine;
        }
        content = new String();

        for (int i = 0; i < lineNumber; i++) {
            content += lines[i];
        }

        return content;
    }

    private static void writeToFile(String file, String content) {

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))) {
            writer.write(content);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String readFile(String filename) {
        String content = null;
        File file = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

}