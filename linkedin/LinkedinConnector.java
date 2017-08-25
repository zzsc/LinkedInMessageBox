package net.elenx.epomis.acceptor.applicant.linkedin;

import lombok.AllArgsConstructor;
import net.elenx.epomis.service.connection6.ConnectionService6;
import net.elenx.epomis.service.connection6.request.ConnectionRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
class LinkedinConnector
{

    private final ConnectionService6 connectionService;

    String fetchCsrfToken(String urlToGetCsrfFrom, String csrfName)
    {

        ConnectionRequest connectionRequest = ConnectionRequest
            .builder()
            .url(urlToGetCsrfFrom)
            .build();

        return connectionService
            .getForHtml(connectionRequest)
            .join()
            .getDocument()
            .getElementById(csrfName)
            .attr("value");
    }

    Map<String, String> getAllCookies(String urlToGetCsrfFrom)
    {
        ConnectionRequest connectionRequest = ConnectionRequest
            .builder()
            .url(urlToGetCsrfFrom)
            .build();

        return connectionService
            .getForHtml(connectionRequest)
            .join()
            .getCookies();
    }
}


