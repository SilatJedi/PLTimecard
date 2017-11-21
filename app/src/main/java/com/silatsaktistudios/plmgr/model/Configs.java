package com.silatsaktistudios.plmgr.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SilatJedi on 4/2/17.
 */

public class Configs extends RealmObject {
    @PrimaryKey
    private int key = 0;

    private String emailForReporting;

    public Configs(){}

    public Configs(String email) {
        setEmailForReporting(email);
    }

    public String getEmailForReporting() {
        return emailForReporting;
    }

    public void setEmailForReporting(String emailForReporting) {
        this.emailForReporting = emailForReporting;
    }
}
