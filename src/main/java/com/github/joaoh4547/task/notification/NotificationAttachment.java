package com.github.joaoh4547.task.notification;

import java.io.ByteArrayOutputStream;

public class NotificationAttachment {

    private String fileName;
    private ByteArrayOutputStream content;
    private FileType fileType;


    protected enum FileType {
        CSV,
        XLS,
        XLSX,
        DOCX,
        TXT
    }

}
