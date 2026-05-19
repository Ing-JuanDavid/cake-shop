package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.service.EmailService;
import com.resend.Resend;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService{

    private final Resend resend;
    private final String from;

    public EmailServiceImp(
            @Value("${resend.api-key}") String apiKey,
            @Value("${resend.from-email}") String from
    )
    {
        this.resend = new Resend(apiKey);
        this.from =  from;
    }

    @Override
    public void sendPasswordResetLink(String to, String resetLink) {
        CreateEmailOptions params =
                CreateEmailOptions.builder()
                        .from(from)
                        .to(to)
                        .subject("Reset your password")
                        .html(
                                "<html>" +
                                        "<body style=\"margin:0;padding:0;background-color:#f9fafb;font-family:Arial,sans-serif;\">" +
                                        "<div style=\"background-color:#f9fafb;padding:40px 20px;\">" +
                                        "<div style=\"max-width:520px;margin:0 auto;background:#ffffff;border-radius:12px;padding:30px;text-align:center;\">" +

                                        "<h1 style=\"color:#92400e;font-size:24px;margin:0 0 10px;\">&#x1F370; Reestablece tu contrase&ntilde;a</h1>" +

                                        "<p style=\"color:#6b7280;font-size:14px;line-height:1.5;margin:0 0 25px;\">" +
                                        "Hemos recibido una solicitud para reestablecer tu contrase&ntilde;a.<br>" +
                                        "Haz click en el bot&oacute;n para continuar." +
                                        "</p>" +

                                        "<a href=\"" + resetLink + "\" style=\"display:inline-block;background:#f59e0b;color:#ffffff;padding:12px 22px;border-radius:8px;text-decoration:none;font-weight:bold;font-size:14px;\">" +
                                        "Reestablecer contrase&ntilde;a</a>" +

                                        "<p style=\"margin:25px 0 0;font-size:12px;color:#9ca3af;\">" +
                                        "Este enlace expira en 15 minutos.<br>" +
                                        "Si no solicitaste este cambio, puedes ignorar este correo." +
                                        "</p>" +

                                        "</div>" +
                                        "</div>" +
                                        "</body></html>"

                        )
                        .build();

        try {
            resend.emails().send(params);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to send email",
                    e
            );
        }
    }

}
