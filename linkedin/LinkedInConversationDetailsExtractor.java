package net.elenx.epomis.acceptor.applicant.linkedin;

import net.elenx.epomis.acceptor.applicant.linkedin.json.conversation.LinkedinConversation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkedInConversationDetailsExtractor
{

    private static final int FIRST = 0;

    String fetchId(LinkedinConversation linkedinConversation)
    {
        String[] splittedId = linkedinConversation.getEntityUrn().split(":");
        return splittedId[splittedId.length - 1];
    }

    String fetchLatestMessageDate(LinkedinConversation linkedinConversation)
    {
        return linkedinConversation
            .getEvents()
            .get(FIRST)
            .getCreatedAt();
    }

    LinkedinConversation.Event.From.MessagingCompany.MiniCompany fetchMiniCompany(LinkedinConversation linkedinConversation)
    {
        return fetchFrom(linkedinConversation)
            .getMessagingCompany()
            .getMiniCompany();
    }

    LinkedinConversation.Event.From fetchFrom(LinkedinConversation linkedinConversation)
    {
        return linkedinConversation
            .getEvents()
            .get(FIRST)
            .getFrom();
    }

    LinkedinConversation.Event.From.MessagingMember.MiniProfile fetchMiniProfile(LinkedinConversation linkedinConversation)
    {
        return fetchFrom(linkedinConversation)
            .getMessagingMember()
            .getMiniProfile();
    }

    Optional<LinkedinConversation.Event.From.MessagingCompany> fetchLastSenderCompanyName(LinkedinConversation linkedinConversation)
    {
        return Optional.ofNullable(fetchFrom(linkedinConversation)
                                       .getMessagingCompany());
    }
}
