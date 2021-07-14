package com.montreal.codingninja.email;

public interface EmailSender {
    void send(String to, String email);
}