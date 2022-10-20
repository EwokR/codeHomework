package com.example.codehomework.service;

import com.example.codehomework.component.RecordComponent;
import com.example.codehomework.entity.Avatar;
import com.example.codehomework.entity.Student;
import com.example.codehomework.record.AvatarRecord;
import com.example.codehomework.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {

    @Value("${students.avatar.dir.path}$")
    private String avatarDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final RecordComponent recordComponent;

    public AvatarService(AvatarRepository avatarRepository, StudentService studentService,RecordComponent recordComponent) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        this.recordComponent=recordComponent;
    }

    public void uploadAvatar(Long idStudent, MultipartFile file) throws IOException {
        Student student = studentService.getStudentById(idStudent);

        Path filePath = Path.of(avatarDir, idStudent + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(idStudent);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(avatar.getData());

        avatarRepository.save(avatar);
    }

    public Avatar findAvatar(Long idStudent) {
        return avatarRepository.findByStudent_idStudent(idStudent).orElse(new Avatar());
    }

    public byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int heights = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, heights, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image,0,0,100,heights,null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<AvatarRecord> findByPagination(int page, int size) {
        return avatarRepository.findAll(PageRequest.of(page, size)).get()
                .map(recordComponent::toRecordAvatar)
                .collect(Collectors.toList());
    }
}
