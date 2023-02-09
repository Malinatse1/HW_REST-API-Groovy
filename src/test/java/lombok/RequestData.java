package lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestData {
    public String name, job;
    public Integer total,id;
}
