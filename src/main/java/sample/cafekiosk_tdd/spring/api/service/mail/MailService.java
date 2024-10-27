package sample.cafekiosk_tdd.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk_tdd.spring.client.MailSendClient;
import sample.cafekiosk_tdd.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk_tdd.spring.domain.history.mail.MailSendHistoryRepository;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);

        if (result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                            .fromEmail(fromEmail)
                            .toEmail(toEmail)
                            .subject(subject)
                            .content(content)
                    .build());
            return true;
        };
        return false;
    }
}
