package net.elenx.epomis.acceptor.applicant.linkedin

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import spock.lang.Specification

class TestTest extends Specification {

    void "test Test"()

    {

        given:

        String url = "https://www.linkedin.com/uas/login?goback=&trk=hb_signin"
        Connection.Response response = Jsoup
            .connect(url)
            .method(Connection.Method.GET)
            .execute()
        Document responseDocument = response.parse()

        when:

         String csrfSessionToken = responseDocument.getElementById("csrfToken-login").attr("value")

            Element loginCsrfParam = responseDocument
                .select("input[name=loginCsrfParam]")
                .first()

            response = Jsoup.connect("https://www.linkedin.com/uas/login-submit")
                .cookies(response.cookies())
                .data("loginCsrfParam", loginCsrfParam.attr("value"))
                .data("session_key", "***********")
                .data("session_password", "************")
                .method(Connection.Method.POST)
                .followRedirects(true)
                .execute()

        String sessionCookie = response.cookie("li_at")

        String InboxJson = Jsoup.connect("https://www.linkedin.com/voyager/api/messaging/conversations")
            .cookie("li_at", sessionCookie)
            .cookie("JSESSIONID", csrfSessionToken)
            .header("Csrf-Token",csrfSessionToken)
            .ignoreContentType(true)
            .method(Connection.Method.GET)
            .followRedirects(true)
            .execute().body().toString()

        then:

        csrfSessionToken != null
        sessionCookie != null
        InboxJson != null

    }
}
