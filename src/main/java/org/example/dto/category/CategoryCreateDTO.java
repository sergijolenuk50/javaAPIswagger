package org.example.dto.category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryCreateDTO {
    private String name;
    private MultipartFile image;
    private String description;
}
