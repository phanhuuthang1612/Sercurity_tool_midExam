package io;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteFile
{
    public static void write(final String path, final String message) throws IOException {
        final FileWriter fw = new FileWriter(path);
        fw.write(message);
        fw.close();
    }
    
    public static void writeObject(final String path, final Object object) throws FileNotFoundException, IOException {
        final ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
        oos.writeObject(object);
        oos.close();
    }
    
    public static void writeByte(final String desFile, final byte[] inputBytes) throws IOException {
        final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));
        bos.write(inputBytes);
        bos.close();
    }
}