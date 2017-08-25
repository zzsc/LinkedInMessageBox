package net.elenx.epomis.acceptor.applicant.linkedin

import net.elenx.epomis.service.connection6.ConnectionService6
import net.elenx.epomis.service.connection6.request.DataEntry
import net.elenx.epomis.service.connection6.response.HtmlResponse
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

class LinkedinLoginServiceTest extends Specification {


    private static final String SESSION_ID = "aaaazzzz1111"
    private static final Map<String,String> SESSION_COOKIES = new HashMap<String,String>()


    private  ConnectionService6 connectionService = Mock()
    private  LinkedinFactory linkedinFactory = Mock()
    private  LinkedinConnector linkedinConnector = Mock()

    void "test login"()
    {
        given:

        HtmlResponse response = Mock()
        CompletableFuture<HtmlResponse> htmlResponseCompletableFuture = CompletableFuture.completedFuture(response)

        SESSION_COOKIES.put("asd","asda")
        htmlResponseCompletableFuture.get().getCookies() >> ["c1" : "c", "li_at": SESSION_ID]
        linkedinConnector.fetchCsrfToken(_, _) >> SESSION_ID
        linkedinFactory.createLoginData(_, _, _) >> [new DataEntry("a", "a"), new DataEntry("li_at", SESSION_ID)]
        linkedinConnector.fetchCsrfToken(_, _) >> Optional.of(SESSION_ID)
        linkedinConnector.getAllCookies(_) >> SESSION_COOKIES
        connectionService.postForHtml(_) >> htmlResponseCompletableFuture

        LinkedinLoginService linkedinLoginService = new LinkedinLoginService(linkedinConnector,connectionService,linkedinFactory)

        when:

        String loginSessionId = linkedinLoginService.signIn("user", "pass")

        then:

        loginSessionId == SESSION_ID
    }
}