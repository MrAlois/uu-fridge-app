package cz.asen.unicorn.fridge.utils;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Iterator;

/**
 * Helper class to compress images to JPG for storage
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 * */
@UtilityClass
public class ImageCompressionUtils {
    private static final String ILLEGAL_COMPRESSION_RATIO = "Illegal compressed image quality (quality must be >= 0 and <= 1)";
    private static final String NULL_BYTES = "imageBytes cannot be null";
    private static final String IMAGE_FORMAT = "jpg";
    private static Base64.Encoder mEncoder = null;
    private static Base64.Decoder mDecoder = null;

    /**
     * Compresses an Image using the specified compression ratio
     * @param imageBytes The image bytes to compress
     * @param compressedImageQuality The compressed image quality, must be >= 0 and <= 1
     * @return byte array containing the compressed image bytes
     * @throws IllegalStateException if an error occurs
     * @throws IllegalArgumentException if there is an illegal argument
     */
    @Contract("null, _ -> fail")
    public static byte @NotNull [] compressImage(byte[] imageBytes, float compressedImageQuality){
        if(imageBytes == null){
            throw new IllegalArgumentException(NULL_BYTES);
        }

        if(!isCompressionRatioValid(compressedImageQuality)){
            throw new IllegalArgumentException(ILLEGAL_COMPRESSION_RATIO);
        }

        try(InputStream mInputStream = new ByteArrayInputStream(imageBytes);
            ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();
            ImageOutputStream mImageOutputStream = ImageIO.createImageOutputStream(mOutputStream)){

            BufferedImage mBufferedImage = ImageIO.read(mInputStream);

            Iterator<ImageWriter> mImageWriters = ImageIO.getImageWritersByFormatName(IMAGE_FORMAT);
            ImageWriter mImageWriter = mImageWriters.next();
            mImageWriter.setOutput(mImageOutputStream);

            ImageWriteParam mParam = mImageWriter.getDefaultWriteParam();
            if(mParam.canWriteCompressed()){
                mParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                mParam.setCompressionQuality(compressedImageQuality);
            }

            mImageWriter.write(null, new IIOImage(mBufferedImage, null, null), mParam);
            return mOutputStream.toByteArray();

        }catch(IOException e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * Compresses an Image to JPG using the specified compression ratio
     * @param imageBase64 The image bytes encoded as Base64
     * @param compressedImageQuality The compressed image quality, must be >= 0 and <= 1
     * @return byte array containing the compressed image bytes
     * @throws IllegalStateException if an error occurs
     * @throws IllegalArgumentException if there is an illegal argument
     */
    public static byte @NotNull [] compressImage(String imageBase64, float compressedImageQuality){
        if(mDecoder == null){
            mDecoder = Base64.getDecoder();
        }
        return compressImage(mDecoder.decode(imageBase64), compressedImageQuality);
    }


    private static boolean isCompressionRatioValid(float ratio){
        return ratio >= 0 && ratio <= 1;
    }
}