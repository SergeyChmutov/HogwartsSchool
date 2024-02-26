package ru.hogwarts.school.service;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.SchoolMethodArgumentNotValidException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {
    private final StudentService studentService;
    private final AvatarRepository repository;
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentService studentService, AvatarRepository repository) {
        this.studentService = studentService;
        this.repository = repository;
    }

    public void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar");
        logger.debug("Has invoked method for upload avatar by id: {}", id);
        final Student student = studentService.getStudentById(id);
        final Path filePath = Path.of(
                avatarsDir,
                student.getId() + "." + getExtension(avatarFile.getOriginalFilename())
        );

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        final Avatar avatar = findAvatarByStudentIdOrCreateWhenNotFound(id);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        repository.save(avatar);
    }

    public ResponseEntity<byte[]> downloadFromDb(Long id) {
        logger.info("Was invoked method for download avatar from DB");
        logger.debug("Was invoked method for download avatar from DB by id: {}", id);
        Avatar avatar = findAvatarByStudentId(id);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    public void downloadAvatarFromFile(Long id, HttpServletResponse response) throws IOException {
        logger.info("Was invoked method for download avatar from file");
        logger.debug("Was invoked method for download avatar from file by id: {}", id);
        Avatar avatar = findAvatarByStudentId(id);
        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    public Avatar findAvatarByStudentId(Long id) {
        logger.info("Was invoked method for find avatar by student id");
        logger.debug("Was invoked method for find avatar by student id: {}", id);
        return repository.findByStudentId(id).orElseThrow(
                () -> new SchoolMethodArgumentNotValidException("Аватарка студента с указанным идентификатором не найдена")
        );
    }

    public Collection<Avatar> getAllAvatar(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for get all avatars");
        logger.debug(
                "Was invoked method for get all avatars with page number: {} and page size: {}",
                pageNumber,
                pageSize
        );
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return repository.findAll(pageRequest).getContent();
    }

    private Avatar findAvatarByStudentIdOrCreateWhenNotFound(Long id) {
        return repository.findByStudentId(id).orElse(new Avatar());
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}