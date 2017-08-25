package net.elenx.epomis.acceptor.applicant.linkedin;

import lombok.AllArgsConstructor;
import net.elenx.epomis.service.connection6.ConnectionService6;
import net.elenx.epomis.service.connection6.request.ConnectionRequest;
import net.elenx.epomis.service.connection6.request.DataEntry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
class LinkedinLoginService
{

    private static final String LOGIN_URL = "https://www.linkedin.com/uas/login-submit";
    private static final String FETCH_CSRF_URL = "https://www.linkedin.com/uas/login?goback=&trk=hb_signin";
    private static final String LOGIN_TOKEN = "loginCsrfParam-login";
    private static final String SESSION_COOKIE = "li_at";

    private final LinkedinConnector linkedinConnector;
    private final ConnectionService6 connectionService;
    private final LinkedinFactory linkedinFactory;

    String signIn(String login, String password)
    {

        String csrfLoginToken = linkedinConnector.fetchCsrfToken(FETCH_CSRF_URL, LOGIN_TOKEN);
        List<DataEntry> data = linkedinFactory.createLoginData(login, password, csrfLoginToken);
        Map<String, String> cookies = linkedinConnector.getAllCookies(FETCH_CSRF_URL);

        ConnectionRequest connectionRequest = ConnectionRequest
            .builder()
            .cookies(cookies)
            .url(LOGIN_URL)
            .data(data)
            .shouldRedirect(true)
            .build();

        return connectionService
            .postForHtml(connectionRequest)
            .join()
            .getCookies()
            .get(SESSION_COOKIE);
    }
}
