package cz.asen.fridge.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@UtilityClass
public class FileUtils {
    public String fileToBase64Data(File file){
        try {
            byte[] fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(file);
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("Problem decoding file.", e);
        }
    }
}
