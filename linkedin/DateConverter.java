package net.elenx.epomis.acceptor.applicant.linkedin;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
class DateConverter
{

    String dateConvert(String date)
    {

        ZoneId zoneId = ZoneId.of("Europe/Warsaw");
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(date)), zoneId).toString();
    }
}
