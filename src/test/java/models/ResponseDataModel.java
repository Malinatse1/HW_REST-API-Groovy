package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDataModel {
    String name, job, createdAt;
    Integer total,id;
}