package android.alex.showofftaskex;


import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class StreamIO {

    public static void write(String fileName, String data) throws IOException {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            //2) call the write method.
            writer.write(data);
            //writer.newLine();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static String read(String fileName) throws IOException {
        //data = content read the file
        //init a file reader.
        InputStream in = new FileInputStream(fileName);
        return read(in);
    }

    //copy
    public static void copy(String srcFileName, String destFileName) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFileName);
            out = new FileOutputStream(destFileName);
            byte[] buffer = new byte[8192];
            int length = 0;

            while ((length = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, buffer.length);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    //read from the web
    //https://www.reddit.com/r/Android/.json
    public static String readWebSite(String address) throws IOException {
        URL url = null;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            throw new IOException("failed at parsing the URL.", e);
        }
        Log.d("Ness", address);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        return read(in);
    }

    public static String read(InputStream in) throws IOException {
        StringBuilder builder = new StringBuilder();//mutable String (appendable String)
        //A) wrap the inputStream in an InputStreamReader
        //B) wrap the reader in a buffered reader
        //C)read the data
        //D)close everything
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append(lineSeparator);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return builder.toString();
    }

    private final static String lineSeparator = "\n";
}
