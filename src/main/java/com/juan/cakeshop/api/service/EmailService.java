package com.juan.cakeshop.api.service;

public interface EmailService {
    void sendPasswordResetLink(String to, String resetLink);
}
