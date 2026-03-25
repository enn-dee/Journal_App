package net.enndee.journalapp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChuckNorrisResponse {

    @JsonProperty("icon_url")
    private String iconUrl;

    private String id;
    private String url;
    private String value;

}
