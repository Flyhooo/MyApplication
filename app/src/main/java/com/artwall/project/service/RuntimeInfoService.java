package com.artwall.project.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.artwall.project.bean.PaintType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95 on 2016/4/26.
 */
public class RuntimeInfoService {

    /**绘画列表缓存文件名*/
    public static String PAINT_LIST="paint_list";
    /**儿童列表缓存文件名*/
    public static String CHILDREN_LIST="children_list";

    /**绘画分类*/
    private static ArrayList<PaintType> PAINT_TYPE_LIST = new ArrayList<>();

    /**获取分类*/
    public static ArrayList<PaintType> getPaintTypeList(Context context) {

        //若当前序列为空则从本地获取
        if (PAINT_TYPE_LIST.size() == 0)
            getLocalPrintTypeList(context);

        return PAINT_TYPE_LIST;
    }

    /**存储分类*/
    public static void setPaintTypeList(Context context, ArrayList<PaintType> list) {

        if (list != null && list.size() != 0) {
            PAINT_TYPE_LIST.clear();
            for (PaintType t : list) {
                PAINT_TYPE_LIST.add(t);
            }
            //本地序列不为空的情况下保存起来
            saveLocalPrintTypeList(context, PAINT_TYPE_LIST);
        }
    }

    /**
     * 获取本地类型序列
     */
    private static void getLocalPrintTypeList(Context context) {

        SharedPreferences sp = context.getSharedPreferences("printTypeList",
                Context.MODE_PRIVATE);
        String typelist = sp.getString("printTypeList", "");
        if (typelist.isEmpty()) {
            typelist = "";
        }

        String[] arr = typelist.split("|");

        for (int i = 0; i < arr.length; i++) {
            String[] d = arr[i].split(",");
            PaintType t = new PaintType();
            t.setId(d[0]);
            t.setKeyname(d[1]);
            PAINT_TYPE_LIST.add(t);
        }
    }

    /**
     * 保存类型序列
     */
    private static void saveLocalPrintTypeList(Context context, ArrayList<PaintType> TYPE_LIST) {

        SharedPreferences sp = context.getSharedPreferences("printTypeList",
                Context.MODE_PRIVATE);

        StringBuilder typelist = new StringBuilder();

        for (PaintType type : TYPE_LIST) {
            typelist.append(type.getId()).append(",");
            typelist.append(type.getKeyname()).append("|");
        }

        String s = typelist.substring(0, typelist.length() - 1);// 去除最后一个|
        // 写入到SP
        SharedPreferences.Editor et = sp.edit();
        et.putString("printTypeList", s);
        et.commit();
    }

    private static String SceneList2String(List SceneList) throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    private static List String2SceneList(String SceneListString)
            throws  IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream.readObject();
        objectInputStream.close();
        return SceneList;
    }

    /**
     * 保存list
     * @param context
     * @param api  最好以API接口为文件名
     * @param list
     */
    public static void save(Context context,String api, List list) {
        // 最后通过
        SharedPreferences mySharedPreferences = context.getSharedPreferences(
                api, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        try {
            String liststr = SceneList2String(list);
            edit.putString(api, liststr);
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取list
     * @param context
     * @param api
     */
    public static List get(Context context,String api) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                api, Context.MODE_PRIVATE);
        String liststr = sharedPreferences.getString(api, "");
        try {
            return String2SceneList(liststr);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
