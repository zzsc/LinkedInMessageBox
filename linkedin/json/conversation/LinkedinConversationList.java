package net.elenx.epomis.acceptor.applicant.linkedin.json.conversation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedinConversationList
{
    @JsonProperty
    public List<LinkedinConversation> elements;
}