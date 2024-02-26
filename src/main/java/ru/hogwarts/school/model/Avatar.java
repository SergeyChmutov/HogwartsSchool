package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @JsonIgnore
    @Lob
    private byte[] data;
    @OneToOne
    private Student student;

    public Avatar() {
    }

    public Long getId() {
        return id;
    }

    public Avatar setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public Avatar setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public long getFileSize() {
        return fileSize;
    }

    public Avatar setFileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Avatar setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public Avatar setData(byte[] data) {
        this.data = data;
        return this;
    }

    public Student getStudent() {
        return student;
    }

    public Avatar setStudent(Student student) {
        this.student = student;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return fileSize == avatar.fileSize
                && Objects.equals(id, avatar.id)
                && Objects.equals(filePath, avatar.filePath)
                && Objects.equals(mediaType, avatar.mediaType)
                && Arrays.equals(data, avatar.data)
                && Objects.equals(student, avatar.student);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, student);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Avatar {" +
                "id = " + id +
                ", filePath = '" + filePath + '\'' +
                ", fileSize = " + fileSize +
                ", mediaType = '" + mediaType + '\'' +
                ", student = " + student +
                '}';
    }
}
