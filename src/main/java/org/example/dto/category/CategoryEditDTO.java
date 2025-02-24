package org.example.dto.category;
import lombok.Data;
@Data

public class CategoryEditDTO extends CategoryCreateDTO {
    //наслідуємося в класу CategoryCreateDTO
    private int id;
//    private String name;
//    private String image;
//    private String description;
}