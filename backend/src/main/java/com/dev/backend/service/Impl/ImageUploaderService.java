package com.dev.backend.service.Impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageUploaderService {

    private final AmazonS3 s3Client;

    @Value("${security.aws.bucketName}")
    private String bucketName;

    @Value("${security.aws.bucketRegion}")
    private String bucketRegion;

    private String fileName;

    @Transactional
    public String uploadFile(final MultipartFile multipartFile) {

        File file = null;
        try {
            file = convertMultiPartFileToFile(multipartFile);
            fileName = uploadFileToS3Bucket(bucketName, file);
        } catch (final AmazonServiceException ex) {
            System.out.println("Error while uploading file = " + ex.getMessage());
        } catch (final FileNotFoundException ex) {
            System.out.println("Cannot found the file = " + ex.getMessage());
        } finally {
            deleteOnExit(file);
        }

        return String.format("https://s3.%s.amazonaws.com/%s/%s", bucketRegion, bucketName, fileName);
    }

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) throws FileNotFoundException {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try(final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            System.out.println("Error converting the multi-part file to file= " + ex.getMessage());
        }
        return file;
    }

    @Transactional
    private String uploadFileToS3Bucket(final String bucketName, final File file) {

        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getName(), file);
        s3Client.putObject(putObjectRequest);

        return file.getName();
    }
    private static void deleteOnExit(File fileToDelete)
    {
        if (fileToDelete != null)
            fileToDelete.delete();
    }
}
