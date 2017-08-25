package net.elenx.epomis.acceptor.applicant.linkedin

import net.elenx.epomis.service.connection6.ConnectionService6
import net.elenx.epomis.service.connection6.response.HtmlResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

class LinkedinConnectorTest extends Specification {

    ConnectionService6 connectionService = Mock()

    void "test getting csrf"()

    {

        given:

        InputStream documentStream = LinkedinConnectorTest.getResourceAsStream("LinkedinConnectorTest.html")
        Document document = Jsoup.parse(documentStream, "UTF-8", "https://www.linkedin.com/uas/login?goback=&trk=hb_signin")

        HtmlResponse htmlResponse = HtmlResponse.builder().document(document).build()
        CompletableFuture<HtmlResponse> htmlResponseCompletableFuture = CompletableFuture.completedFuture(htmlResponse)

        connectionService.getForHtml(_) >> htmlResponseCompletableFuture

        LinkedinConnector linkedinConnector = new LinkedinConnector(connectionService)

        when:

        String csrf = linkedinConnector.fetchCsrfToken("urlToGetCsrfFrom","loginCsrfParam-login")

        then:

        csrf == "7cbcc873-09b2-4eb0-8cf4-bb0b34c46fe9"

    }

    void "test getting session"()

    {


        given:

        InputStream documentStream = LinkedinConnectorTest.getResourceAsStream("LinkedinConnectorTest.html")
        Document document = Jsoup.parse(documentStream, "UTF-8", "https://www.linkedin.com/uas/login?goback=&trk=hb_signin")


        HtmlResponse htmlResponse = HtmlResponse.builder().document(document).build()
        CompletableFuture<HtmlResponse> htmlResponseCompletableFuture = CompletableFuture.completedFuture(htmlResponse)

        connectionService.getForHtml(_) >> htmlResponseCompletableFuture

        LinkedinConnector connector = new LinkedinConnector(connectionService)

        when:

        String sessionId = connector.fetchCsrfToken("urlToGetCsrfFrom","csrfToken-login")

        then:

        sessionId == "ajax:0393881977453729415"

    }

}
