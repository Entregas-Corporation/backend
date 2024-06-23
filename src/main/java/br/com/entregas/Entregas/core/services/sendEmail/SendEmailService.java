package br.com.entregas.Entregas.core.services.sendEmail;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.entregas.Entregas.core.constants.SendEmailMessageConstant;
import br.com.entregas.Entregas.core.dtos.sendEmail.SendEmailDto;
import br.com.entregas.Entregas.core.exceptions.DomainException;
import br.com.entregas.Entregas.modules.user.dtos.UserDetailDto;
import br.com.entregas.Entregas.modules.user.dtos.UserSaveDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SendEmailService {
    private JavaMailSender emailSender;

    public void sendEmail(SendEmailDto sendEmailDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sendEmailDto.emailFrom());
            message.setTo(sendEmailDto.emailTo());
            message.setSubject(sendEmailDto.title());
            message.setText(sendEmailDto.text());
            emailSender.send(message);
        } catch (MailException e) {
            throw new DomainException(e.toString());
        }
    }

    @Transactional
    public void sendWellcomeAccount(UserSaveDto userSaveDto) {
        SendEmailDto sendEmailDto = new SendEmailDto("{spring.mail.username}", userSaveDto.email(),
                SendEmailMessageConstant.titleWellcome,
                SendEmailMessageConstant.textWellcome(userSaveDto.id(), userSaveDto.name()));
        sendEmail(sendEmailDto);
    }

    @Transactional
    public void sendValidationAccount(UserSaveDto userSaveDto) {
        SendEmailDto sendEmailDto = new SendEmailDto("{spring.mail.username}", userSaveDto.email(),
                SendEmailMessageConstant.titleSuccessfullValidation,
                SendEmailMessageConstant.textSuccessfullValidation(userSaveDto.name()));
        sendEmail(sendEmailDto);
    }

    @Transactional
    public void sendDisableAccount(UserSaveDto userSaveDto) {
        SendEmailDto sendEmailDto = new SendEmailDto("{spring.mail.username}", userSaveDto.email(),
                SendEmailMessageConstant.titleDisableAccount,
                SendEmailMessageConstant.textDisableAccount(userSaveDto.name()));
        sendEmail(sendEmailDto);
    }


    @Transactional
    public void sendTreatmentBySupport(UserDetailDto userSaveDto) {
        SendEmailDto sendEmailDto = new SendEmailDto("{spring.mail.username}", userSaveDto.email(),
                SendEmailMessageConstant.titleTreatmentSupport,
                SendEmailMessageConstant.textTreatmentSupport(userSaveDto.name()));
        sendEmail(sendEmailDto);
    }

    @Transactional
    public void sendTreatmentByComplaint(UserDetailDto userSaveDto) {
        SendEmailDto sendEmailDto = new SendEmailDto("{spring.mail.username}", userSaveDto.email(),
                SendEmailMessageConstant.titleTreatmentComplaint,
                SendEmailMessageConstant.textTreatmentComplaint(userSaveDto.name()));
        sendEmail(sendEmailDto);
    }
}
