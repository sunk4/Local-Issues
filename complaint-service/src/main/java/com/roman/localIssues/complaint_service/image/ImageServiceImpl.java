package com.roman.localIssues.complaint_service.image;

import com.roman.localIssues.complaint_service.complaint.ComplaintEntity;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${aws.s3.access-key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret-key}")
    private String awsS3SecretKey;

    @Value("${aws.s3.bucket-name}")
    private String awsS3BucketName;

    @Value("${aws.s3.region}")
    private String awsS3Region;

    @Override
    public void uploadFile (MultipartFile file, ComplaintEntity complaintEntity) {
        String saveFileUrl = uploadFileToS3(file);
        ImageEntity imageEntity = ImageEntity.builder()
                .url(saveFileUrl)
                .complaint(complaintEntity)
                .build();
        imageRepository.save(imageEntity);

    }

    public String uploadFileToS3 (MultipartFile file) {
        try {
            String s3FileName = file.getOriginalFilename();
            if (s3FileName.isEmpty()) {
                throw new IllegalArgumentException("File name cannot be empty");
            }


            String fileType = file.getContentType();

            if (!isValidImageMimeType(fileType)) {
                throw new IllegalArgumentException("File must be of type image/jpeg, image/png, image/gif, image/bmp");

            }

            long fileSizeInBytes = file.getSize();
            if (fileSizeInBytes > 3 * 1024 * 1024) {
                throw new IllegalArgumentException("File size must be less than 3MB");
            }

            LocalDate date = LocalDate.now();
            s3FileName = date + "_" + s3FileName;

            AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(awsS3AccessKey, awsS3SecretKey);

            S3Client s3Client = S3Client.builder()
                    .region(Region.of(awsS3Region))
                    .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                    .build();

            InputStream inputStream = file.getInputStream();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsS3BucketName)
                    .key(s3FileName)
                    .contentType(fileType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
            logger.info("File [{}] successfully uploaded to bucket [{}]", s3FileName, awsS3BucketName);
            return "https://" + awsS3BucketName + ".s3.amazonaws.com/" + s3FileName;

        } catch (Exception e) {
            logger.error("Failed to upload file to S3: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to upload file to S3: " + e.getMessage());
        }
    }

    private boolean isValidImageMimeType (String contentType) {
        List<String> supportedMimeTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/bmp");
        return supportedMimeTypes.contains(contentType);
    }

}
