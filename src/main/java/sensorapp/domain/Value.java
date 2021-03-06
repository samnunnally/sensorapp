package sensorapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
public class Value {

    private Long id;
    private String quote;

}