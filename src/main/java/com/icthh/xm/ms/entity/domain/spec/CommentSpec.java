package com.icthh.xm.ms.entity.domain.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "key", "name" })
@Data
public class CommentSpec {

    @JsonProperty("key")
    private String key;
    @JsonProperty("name")
    private Map<String, String> name;

}
