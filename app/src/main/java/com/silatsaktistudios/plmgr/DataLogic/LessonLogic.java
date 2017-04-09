package com.silatsaktistudios.plmgr.DataLogic;

import com.silatsaktistudios.plmgr.Models.Lesson;

import io.realm.Realm;

/**
 * Created by SilatJedi on 4/9/17.
 */

public class LessonLogic {


    public static void delete(int id) {
        Realm realm = Realm.getDefaultInstance();

        final Lesson lesson = realm.where(Lesson.class).equalTo("id", id).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                lesson.deleteFromRealm();
            }
        });

        realm.close();
    }
}
