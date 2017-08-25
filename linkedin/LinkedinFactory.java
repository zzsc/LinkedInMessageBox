package net.elenx.epomis.acceptor.applicant.linkedin;

import net.elenx.epomis.service.connection6.request.DataEntry;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LinkedinFactory
{

    List<DataEntry> createLoginData(String login, String password, String csrfToken)
    {

        return Arrays.asList
            (
                new DataEntry("session_key", login),
                new DataEntry("session_password", password),
                new DataEntry("loginCsrfParam", csrfToken)
            );
    }
}
