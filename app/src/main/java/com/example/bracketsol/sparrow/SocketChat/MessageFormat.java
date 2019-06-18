package com.example.bracketsol.sparrow.SocketChat;

public class MessageFormat {

    private String Username;
    private String Message;
    private String img,mime,filename;
    private int UniqueId;

    public MessageFormat(int uniqueId,String username, String message, String img ) {
        Username = username;
        Message = message;
        this.img = img;
        UniqueId = uniqueId;
    }


    public MessageFormat( int uniqueId,String username, String message, String img, String mime, String filename) {
        Username = username;
        Message = message;
        this.img = img;
        this.mime = mime;
        this.filename = filename;
        UniqueId = uniqueId;
    }

    public MessageFormat(int uniqueId, String username) {
        Username = username;
        UniqueId = uniqueId;
    }


    public MessageFormat(int uniqueId, String username, String message) {
        Username = username;
        Message = message;
        UniqueId = uniqueId;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(int uniqueId) {
        UniqueId = uniqueId;
    }
}
