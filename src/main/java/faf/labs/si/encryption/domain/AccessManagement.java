package faf.labs.si.encryption.domain;

import faf.labs.si.encryption.domain.util.ActionType;

import javax.validation.constraints.Pattern;

public class AccessManagement {
    @Pattern(regexp = "^((https?|ftp|smtp):\\/\\/)?(www.)?[a-z0-9]+\\.[a-z]+(\\/[a-zA-Z0-9#]+\\/?)*$", message = "siteName must follow the standard site structure. Example: www.facebook.com")
    private String siteName;
    private ActionType actionType;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}
