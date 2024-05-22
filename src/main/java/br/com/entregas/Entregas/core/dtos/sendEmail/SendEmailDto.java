package br.com.entregas.Entregas.core.dtos.sendEmail;

public record SendEmailDto(
        String emailFrom,
        String emailTo,
        String title,
        String text) {}
