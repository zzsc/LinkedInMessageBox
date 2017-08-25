package net.elenx.epomis.acceptor.applicant.linkedin;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.elenx.epomis.acceptor.applicant.linkedin.entity.InMailConversation;
import net.elenx.epomis.acceptor.applicant.linkedin.entity.InMailMessage;
import net.elenx.epomis.acceptor.applicant.linkedin.json.message.LinkedinMessage;
import net.elenx.epomis.acceptor.applicant.linkedin.json.message.LinkedinMessageList;
import net.elenx.epomis.service.connection6.ConnectionService6;
import net.elenx.epomis.service.connection6.request.ConnectionRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class LinkedinMessageService
{

    private static final String FETCH_CSRF_URL = "https://www.linkedin.com/uas/login?goback=&trk=hb_signin";
    private static final String SESSION = "li_at";
    private static final String SESSION2 = "JSESSIONID";

    private final LinkedinLoginService loginService;
    private final LinkedinConnector linkedinConnector;
    private final ConnectionService6 connectionService;
    private final DateConverter dateConverter;

    public Set<InMailMessage> getMessage(String login, String password, InMailConversation inMailConversation)
    {

        String sessionId = loginService.signIn(login, password);
        String csrf = linkedinConnector.fetchCsrfToken(FETCH_CSRF_URL, SESSION2);
        String MESSAGE_URL = String.format("https://www.linkedin.com/voyager/api/messaging/conversations/%1$s/events", inMailConversation.getId());

        ConnectionRequest messagesRequest = ConnectionRequest
            .builder()
            .url(MESSAGE_URL)
            .cookie(SESSION, sessionId)
            .cookie(SESSION2, csrf)
            .build();

        return connectionService
            .getForJson(messagesRequest, LinkedinMessageList.class)
            .join()
            .getJson()
            .getElements()
            .stream()
            .map(this::extractMessage)
            .collect(Collectors.toSet());
    }

    @SneakyThrows
    private InMailMessage extractMessage(LinkedinMessage linkedinMessage)
    {

        String senderName = getMiniProfile(linkedinMessage).getFirstName();

        String senderLastName = getMiniProfile(linkedinMessage).getLastName();

        String messageBody = linkedinMessage
            .getEventContent()
            .getMessageEvent()
            .getBody();

        String date = dateConverter.dateConvert(linkedinMessage.getCreatedAt());

        return InMailMessage
            .builder()
            .date(date)
            .senderName(senderName)
            .senderLastName(senderLastName)
            .messageBody(messageBody)
            .build();
    }

    private LinkedinMessage.From.MessagingMember.MiniProfile getMiniProfile(LinkedinMessage linkedinMessage)
    {
        return linkedinMessage
            .getFrom()
            .getMessagingMember()
            .getMiniProfile();
    }
}
