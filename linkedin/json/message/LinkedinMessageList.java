package net.elenx.epomis.acceptor.applicant.linkedin.json.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedinMessageList
{
    @JsonProperty
    public List<LinkedinMessage> elements;
}
