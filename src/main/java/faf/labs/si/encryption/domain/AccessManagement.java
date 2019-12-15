package faf.labs.si.encryption.domain;

import faf.labs.si.encryption.domain.util.ActionType;

public class AccessManagement {
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
