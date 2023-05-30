package com.mobileclient.domain;

import java.io.Serializable;

public class LatState implements Serializable {
    /*×´Ì¬id*/
    private String stateId;
    public String getStateId() {
        return stateId;
    }
    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    /*×´Ì¬Ãû³Æ*/
    private String stateName;
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}