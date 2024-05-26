package cz.asen.unicorn.fridge.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@UtilityClass
public class FileUtils {
    /**
     * Converts a file to Base64-encoded data.
     *
     * @param file The file to convert.
     * @return The Base64-encoded data as a string.
     * @throws IllegalStateException if there is a problem decoding the file.
     */
    public String fileToBase64Data(File file){
        try {
            byte[] fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(file);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("Problem decoding file.", e);
        }
    }
}
