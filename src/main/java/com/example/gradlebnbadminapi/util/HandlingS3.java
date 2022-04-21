package com.example.gradlebnbadminapi.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.gradlebnbadminapi.model.entity.Photo;
import com.example.gradlebnbadminapi.model.entity.User;
import com.example.gradlebnbadminapi.model.enumClass.PhotoDeleteFlag;
import com.example.gradlebnbadminapi.model.network.Header;
import com.example.gradlebnbadminapi.model.network.response.PhotoApiResponse;
import com.example.gradlebnbadminapi.repository.PhotoRepository;
import com.example.gradlebnbadminapi.repository.UserRepository;
import com.example.gradlebnbadminapi.service.PhotoLogicServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@Component
@Slf4j
public class HandlingS3 {

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private PhotoLogicServiceImpl photoLogicService;

    public void execute(MultipartFile mpPhoto, String email) throws Exception {

        User user = userRepository.findByEmail(email).orElseThrow(NoSuchElementException::new);
        log.info("Create user: " + user.getEmail());

        String photoName = makePhotoName(Objects.requireNonNull(mpPhoto.getOriginalFilename()));
        log.info("Upload photo name: " + photoName);

        File photo = upload(mpPhoto, photoName);
        removeNewFile(photo);

        String S3Url = getS3Photo(photoName);
        log.info("S3Url: " + S3Url);

        photoLogicService.setCallback(new PhotoLogicServiceImpl.PhotoCallback() {
            @Override
            public Header<PhotoApiResponse> create(MultipartFile mpPhoto, String email) {
                log.info("Create CALLBACK start");

                Photo photo = Photo.builder()
                        .name(photoName)
                        .size(mpPhoto.getSize())
                        .url(S3Url)
                        .delFlg(PhotoDeleteFlag.STORED)
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .build();
                log.info("Create Photo: " + photo);

                return Header.OK(photoLogicService.response(photoRepository.save(photo)));
            }

            @Override
            public Header<PhotoApiResponse> update(MultipartFile mpPhoto, String reqEmail, Long id) {
                log.info("Update CALLBACK start");

                Photo targetPhoto = user.getPhotoList().stream().filter(photo -> photo.getId().equals(id)).findFirst().orElseThrow(NoSuchElementException::new);
                delS3Photo(targetPhoto.getName());
                log.info("Delete Photo Name: " + targetPhoto.getName());

                Photo updatePhoto = targetPhoto
                        .setName(photoName)
                        .setUrl(S3Url)
                        .setUpdatedAt(LocalDateTime.now());
                log.info("updated Photo: " + updatePhoto);

                return Header.OK(photoLogicService.response(photoRepository.save(updatePhoto)));
            }
        });
    }

    public File upload(MultipartFile mpPhoto, String photoName) throws Exception {
        log.info("Photo: " + mpPhoto);

        try {
            File photo = convert(mpPhoto);

            s3Client.putObject(new PutObjectRequest(bucket, photoName, photo)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return photo;
        } catch (IOException e) {
            log.error(e.getMessage());
            log.error("File convert error");
            throw new IllegalArgumentException("File convert Error");
        } catch (AmazonServiceException e) {
            log.error(e.getMessage());
            log.error("AWS S3 upload error");
            throw new Exception();
        }
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File Deleted.");
        } else {
            log.info("Deleting file error.");
        }
    }


    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    public String getS3Photo(String photoName) {
        return s3Client.getUrl(bucket, photoName).toString();
    }

    public void delS3Photo(String photoName) {
        s3Client.deleteObject(bucket, photoName);
    }

    public File convert(MultipartFile file) throws IOException {

        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        }
        return convertFile;
    }

    public String makePhotoName(String photoName) {

        String[] photoNameWithExtension = photoName.split("\\.");

        return photoNameWithExtension[0] + "_" + System.currentTimeMillis() + "." + photoNameWithExtension[1];
    }
}
