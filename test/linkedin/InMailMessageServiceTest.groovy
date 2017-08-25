package net.elenx.epomis.acceptor.applicant.linkedin

import com.fasterxml.jackson.databind.ObjectMapper
import net.elenx.epomis.acceptor.applicant.linkedin.entity.InMailConversation
import net.elenx.epomis.acceptor.applicant.linkedin.entity.InMailMessage
import net.elenx.epomis.acceptor.applicant.linkedin.json.conversation.LinkedinConversationList
import net.elenx.epomis.acceptor.applicant.linkedin.json.message.LinkedinMessageList
import net.elenx.epomis.service.connection6.ConnectionService6
import net.elenx.epomis.service.connection6.response.JsonResponse
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

class InMailMessageServiceTest extends Specification {

    void "test getting conversation"(){

        given:
        LinkedinLoginService linkedinLoginService = Mock()
        LinkedinConnector linkedinConnector = Mock()
        ConnectionService6 connectionService  = Mock()

        ObjectMapper mapper = new ObjectMapper()

        InputStream documentStream = LinkedinConnectorTest.getResourceAsStream("InMailConversation.txt")
        LinkedinConversationList document = mapper.readValue(documentStream, LinkedinConversationList.class)

        JsonResponse jsonResponse = JsonResponse.builder().json(document).build()
        CompletableFuture<JsonResponse> jsonResponseCompletableFuture = CompletableFuture.completedFuture(jsonResponse)

        connectionService.getForJson(_,_) >> jsonResponseCompletableFuture

        DateConverter dateConverter = new DateConverter()
        LinkedInConversationDetailsExtractor detailsExtractor = new LinkedInConversationDetailsExtractor()
        LinkedInConversationExtractor extractor = new LinkedInConversationExtractor(detailsExtractor,dateConverter)

        LinkedinConversationService linkedinConversationService = new LinkedinConversationService(linkedinLoginService,linkedinConnector,connectionService,extractor)

        when:
        Set<InMailConversation> conversationSet = linkedinConversationService.getAllConversations("login","password")

        then:
        conversationSet.size() == 2

    }

    void "test getting message"(){

        given:
        LinkedinLoginService linkedinLoginService = Mock()
        LinkedinConnector linkedinConnector = Mock()
        ConnectionService6 connectionService  = Mock()
        InMailConversation liConversation = Mock()

        ObjectMapper mapper = new ObjectMapper()

        InputStream documentStream = LinkedinConnectorTest.getResourceAsStream("InMailMessage.txt")
        LinkedinMessageList document = mapper.readValue(documentStream, LinkedinMessageList.class)

        JsonResponse jsonResponse = JsonResponse.builder().json(document).build()
        CompletableFuture<JsonResponse> jsonResponseCompletableFuture = CompletableFuture.completedFuture(jsonResponse)

        connectionService.getForJson(_,_) >> jsonResponseCompletableFuture

        DateConverter dateConverter = new DateConverter()
        LinkedinMessageService linkedinMessageService = new LinkedinMessageService(linkedinLoginService,linkedinConnector,connectionService,dateConverter)

        when:
        Set<InMailMessage> messageSet = linkedinMessageService.getMessage("login","password",liConversation)

        then:
        messageSet.size() == 4

    }
}
