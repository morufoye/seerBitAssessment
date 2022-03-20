package seerBit.backend.assessment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Statistic{
    private String sum;
    private String avg;
    private String max;
    private String min;
    private String count;
}
