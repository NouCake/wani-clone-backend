package de.abstractolotl.shadycrab.waniclone.exception;

public class CrabUserNotCreatedException extends Exception {

    public enum Reasons {
        USERNAME_ALREADY_EXISTS,
        EMAIL_AREADY_EXISTS,
        OTHER
    }

    private Reasons reason;

    public CrabUserNotCreatedException(Reasons reason) {
        super("Couldn't create User: " + reason.toString());
        this.reason = reason;
    } 

    public Reasons getReason() {
        return reason;
    }
}
