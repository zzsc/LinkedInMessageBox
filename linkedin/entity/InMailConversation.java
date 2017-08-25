package net.elenx.epomis.acceptor.applicant.linkedin.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InMailConversation
{

    String id;
    String latestSenderName;
    String latestSenderLastName;
    String latestMessageDate;
    String latestSenderCompanyName;

}
