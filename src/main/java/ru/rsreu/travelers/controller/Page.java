package ru.rsreu.travelers.controller;

public class Page {
    private String path;

    private TypeRedirect typeRedirect;

    public Page(String path, TypeRedirect typeRedirect) {
        this.path = path;
        this.typeRedirect = typeRedirect;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isSendRedirect() {
        return typeRedirect.equals(TypeRedirect.SEND_REDIRECT);
    }

    public TypeRedirect getTypeRedirect() {
        return typeRedirect;
    }

    public void setTypeRedirect(TypeRedirect typeRedirect) {
        this.typeRedirect = typeRedirect;
    }
}
