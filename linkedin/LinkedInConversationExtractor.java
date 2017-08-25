package net.elenx.epomis.acceptor.applicant.linkedin;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.elenx.epomis.acceptor.applicant.linkedin.entity.InMailConversation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LinkedInConversationExtractor
{

    private final LinkedInConversationDetailsExtractor extractor;
    private final DateConverter dateConverter;

    @SneakyThrows
    InMailConversation extractConversation(net.elenx.epomis.acceptor.applicant.linkedin.json.conversation.LinkedinConversation linkedinConversation)
    {

        String id = extractor.fetchId(linkedinConversation);
        String latestMessageDate = dateConverter.dateConvert(extractor.fetchLatestMessageDate(linkedinConversation));
        String lastSenderCompanyName = StringUtils.EMPTY;
        String lastSenderName = StringUtils.EMPTY;
        String lastSenderLastName = StringUtils.EMPTY;

        if(extractor.fetchLastSenderCompanyName(linkedinConversation).isPresent())
        {
            lastSenderCompanyName = extractor.fetchMiniCompany(linkedinConversation).getName();
        }
        else
        {
            lastSenderName = extractor.fetchMiniProfile(linkedinConversation).getFirstName();
            lastSenderLastName = extractor.fetchMiniProfile(linkedinConversation).getLastName();
        }

        return InMailConversation
            .builder()
            .id(id)
            .latestSenderName(lastSenderName)
            .latestSenderLastName(lastSenderLastName)
            .latestMessageDate(latestMessageDate)
            .latestSenderCompanyName(lastSenderCompanyName)
            .build();
    }
}
