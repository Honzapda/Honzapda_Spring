package Honzapda.Honzapda_server.auth.service;

public interface EmailService {
    void sendTempPassword(String toEmail, String temporaryPw);
}
