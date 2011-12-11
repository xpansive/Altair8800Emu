package com.xpansive.altair8800.io;

import java.io.*;

public class FileReader {
    public int[] read(String filename) throws IOException {
        File file = new File(filename);
        InputStream stream = new FileInputStream(file);

        long length = file.length();

        // No point in trying to handle this, it won't fit in the Altair's memory anyways :)
        if (length > Integer.MAX_VALUE) {
            throw new IOException(filename + " (File is too large)");
        }

        byte[] data = new byte[(int) length];
        int offset = 0;
        int bytesRead = 0;
        while (offset < data.length && (bytesRead = stream.read(data, offset, data.length - offset)) >= 0) {
            offset += bytesRead;
        }

        // Ensure the whole file has been read
        if (offset < data.length) {
            throw new IOException(filename + " (Could not completely read file)");
        }

        stream.close();

        int[] intData = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            intData[i] = data[i] & 0xFF; // Convert to unsigned
        }

        return intData;
    }
}
