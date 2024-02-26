package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(
            @PathVariable(name = "studentId") Long id,
            @RequestParam MultipartFile avatar
    ) throws IOException {
        service.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/from-db")
    public ResponseEntity<byte[]> downloadAvatarFromDb(@PathVariable(name = "studentId") Long id) {
        return service.downloadFromDb(id);
    }

    @GetMapping(value = "/{studentId}/from-file")
    public void downloadAvatarFromFile(
            @PathVariable(name = "studentId") Long id,
            HttpServletResponse response
    ) throws IOException {
        service.downloadAvatarFromFile(id, response);
    }

    @GetMapping()
    public ResponseEntity<Collection<Avatar>> getAllAvatar(
            @RequestParam("page") Integer pageNumber,
            @RequestParam("size") Integer pageSize
    ) {
        final Collection<Avatar> avatars = service.getAllAvatar(pageNumber, pageSize);
        return ResponseEntity.ok(avatars);
    }
}
