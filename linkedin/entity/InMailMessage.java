package net.elenx.epomis.acceptor.applicant.linkedin.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InMailMessage
{

    String date;
    String senderName;
    String senderLastName;
    String messageBody;

}
