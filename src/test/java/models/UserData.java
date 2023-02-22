package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import models.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    @JsonProperty("data")
    private User user;
}
