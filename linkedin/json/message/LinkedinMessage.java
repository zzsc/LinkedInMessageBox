package net.elenx.epomis.acceptor.applicant.linkedin.json.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedinMessage
{
    public String createdAt;
    public EventContent eventContent;
    public From from;

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EventContent
    {
        public String createdAt;
        @JsonProperty("com.linkedin.voyager.messaging.event.MessageEvent")
        public MessageEvent messageEvent;

        @Data
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class MessageEvent
        {
            public String body;
        }
    }

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class From
    {
        @JsonProperty("com.linkedin.voyager.messaging.MessagingMember")
        public MessagingMember messagingMember;

        @Data
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class MessagingMember
        {
            public MiniProfile miniProfile;

            @Data
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class MiniProfile
            {
                public String firstName;
                public String lastName;
            }
        }
    }
}



