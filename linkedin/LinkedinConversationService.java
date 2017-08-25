package net.elenx.epomis.acceptor.applicant.linkedin;

import lombok.RequiredArgsConstructor;
import net.elenx.epomis.acceptor.applicant.linkedin.entity.InMailConversation;
import net.elenx.epomis.acceptor.applicant.linkedin.json.conversation.LinkedinConversationList;
import net.elenx.epomis.service.connection6.ConnectionService6;
import net.elenx.epomis.service.connection6.request.ConnectionRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LinkedinConversationService
{

    private static final String CONVERSATION_URL = "https://www.linkedin.com/voyager/api/messaging/conversations";
    private static final String FETCH_CSRF_URL = "https://www.linkedin.com/uas/login?goback=&trk=hb_signin";
    private static final String SESSION = "li_at";
    private static final String SESSION2 = "JSESSIONID";

    private final LinkedinLoginService loginService;
    private final LinkedinConnector linkedinConnector;
    private final ConnectionService6 connectionService;
    private final LinkedInConversationExtractor linkedInConversationExtractor;

    public Set<InMailConversation> getAllConversations(String login, String password)
    {

        String sessionId = loginService.signIn(login, password);
        String csrf = linkedinConnector.fetchCsrfToken(FETCH_CSRF_URL, SESSION2);

        ConnectionRequest conversationRequest = ConnectionRequest
            .builder()
            .url(CONVERSATION_URL)
            .cookie(SESSION, sessionId)
            .cookie(SESSION2, csrf)
            .header("Csrf-Token", csrf)
            .shouldRedirect(true)
            .build();

        return connectionService
            .getForJson(conversationRequest, LinkedinConversationList.class)
            .join()
            .getJson()
            .getElements()
            .stream()
            .map(linkedInConversationExtractor::extractConversation)
            .collect(Collectors.toSet());
    }
}
