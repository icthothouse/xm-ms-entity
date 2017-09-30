package com.icthh.xm.ms.entity.domain.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "key", "builderType", "name", "icon", "typeKey", "max" })
@Data
public class LinkSpec {

    @JsonProperty("key")
    private String key;
    /**
     * Link constructor on interface. It could be: NEW - add new xmEntity dialog will be shown, SEARCH - xmEntity
     * search dialog will be show.
     * */
    @JsonProperty("builderType")
    private String builderType;
    @JsonProperty("name")
    private Map<String, String> name;
    /** Name of Google material system icon https://material.io/icons/ */
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("typeKey")
    private String typeKey;
    @JsonProperty("max")
    private Integer max;

}
