package net.elenx.epomis.acceptor.applicant.linkedin.json.conversation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedinConversation
{
    public String entityUrn;
    public List<Event> events;

    @Data
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Event
    {
        public String createdAt;
        public From from;

        @Data
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class From
        {
            @JsonProperty("com.linkedin.voyager.messaging.MessagingMember")
            public MessagingMember messagingMember;
            @JsonProperty("com.linkedin.voyager.messaging.MessagingCompany")
            public MessagingCompany messagingCompany;

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

            @Data
            @Builder
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class MessagingCompany
            {
                public MiniCompany miniCompany;

                @Data
                @Builder
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class MiniCompany
                {
                    public String name;
                }
            }
        }
    }
}



