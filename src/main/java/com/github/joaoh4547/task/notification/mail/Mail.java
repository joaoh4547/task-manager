package com.github.joaoh4547.task.notification.mail;

public class Mail {

    private String subject;
    private String body;
    private String to;

    public Mail(String subject, String body, String to) {
        this.subject = subject;
        this.body = body;
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getTo() {
        return to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", to='" + to + '\'' +
                '}';
    }


}
