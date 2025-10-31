package mb.testframeworks.java.models.petstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {
    private Long id; // Using Long for object-level data binding
    private String name;
    private Category category; // Nested object
    private List<String> photoUrls; // Using List<String> instead of String[]
    private List<Tag> tags; // Using List<Tag> instead of Tag[]
    private String status;
}