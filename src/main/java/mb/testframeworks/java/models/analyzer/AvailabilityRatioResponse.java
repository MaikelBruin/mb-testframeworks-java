package mb.testframeworks.java.models.analyzer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRatioResponse {
    private BigDecimal ratio;
    private Integer availablePets;
    private Integer soldPets;

}
