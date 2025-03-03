//package org.example.dto.product;
//
//import lombok.Data;
//
//@Data
//public class ProductImageDto {
//    private Long id;
//    private String name;
//    private int priority;
//    private Integer productId;
//}

package org.example.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDto {
    private Integer id;
    private String name;
    private int priority;
    private Integer productId;
}