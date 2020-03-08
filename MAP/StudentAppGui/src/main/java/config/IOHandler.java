package config;

import java.io.*;
import java.util.Properties;

public class IOHandler {

    /**
     * Initializes properties object for reading property file
     * @return
     * @throws IOException
     */
    public static Properties getProperties() throws IOException {
        InputStream input = new FileInputStream("./src/main/resources/files/config.properties");
        Properties properties = new Properties();
        properties.load(input);
        return properties;
    }

    /**
     * Initializes a file writer for writing data into a text file based on a
     * property to the pathname
     *
     * @param property
     *            related to the pathname
     * @return the file writer
     */
    public static FileWriter initializeDataWriter(String property) {
        Properties properties;
        try {
            properties = IOHandler.getProperties();
            String fileName = properties.getProperty(property);
            FileWriter fileWriter = new FileWriter(fileName,false);// we append
            // data to
            // the file
            return fileWriter;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Initializes a  buffered reader for reading data from a text file based on a
     * property to the pathname
     *
     * @param property
     *            related to the pathname
     * @return the initialized buffered reader
     */
    public static BufferedReader initializeBufferReader(String property) {
        Properties properties;
        try {
            properties = IOHandler.getProperties();
            String fileName = properties.getProperty(property);
            File file = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
