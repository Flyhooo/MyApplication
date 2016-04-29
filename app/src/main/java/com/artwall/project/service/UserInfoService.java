package com.artwall.project.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.artwall.project.bean.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 保存用户基本信息到本地数据库操作
 *
 * @author hx
 */
public class UserInfoService {
    Context context;

    public UserInfoService(Context mcontext) {
        this.context = mcontext.getApplicationContext();
    }

    /**
     * 保存
     *
     * @param user
     */
    public void saveObject(User user) {
        clearFeedTable();// 每次保存之前清空数据表
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    arrayOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            Dbhelper dbhelper = Dbhelper.getInstens(context);
            SQLiteDatabase database = dbhelper.getWritableDatabase();
            database.execSQL(
                    "insert into usertable (usertabledata) values(?)",
                    new Object[]{data});
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getObject() {
        User user = null;
        Dbhelper dbhelper = Dbhelper.getInstens(context);
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from usertable",
                null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                byte data[] = cursor.getBlob(cursor
                        .getColumnIndex("usertabledata"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(
                        data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(
                            arrayInputStream);
                    user = (User) inputStream.readObject();
                    inputStream.close();
                    arrayInputStream.close();
                    break;// 这里为了测试就取一个数据
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        return user;

    }

    public void clearFeedTable() {
        String sql = "DELETE FROM " + "usertable" + ";";
        Dbhelper dbhelper = Dbhelper.getInstens(context);
        SQLiteDatabase database = dbhelper.getReadableDatabase();

        database.execSQL(sql);
        revertSeq();
    }

    private void revertSeq() {
        String sql = "update sqlite_sequence set seq=0 where name='"
                + "usertable" + "'";
        Dbhelper dbhelper = Dbhelper.getInstens(context);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        db.execSQL(sql);
    }
}
