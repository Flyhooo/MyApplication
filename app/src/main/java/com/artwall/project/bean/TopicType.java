package com.artwall.project.bean;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/28.
 */
public class TopicType {

//    id：ID
//    childlist：二级栏目
//    keyname：名称

    private String id;
    private String keyname;
    private ArrayList<Tag> childlist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyname() {
        return keyname;
    }

    public void setKeyname(String keyname) {
        this.keyname = keyname;
    }

    public ArrayList<Tag> getChildlist() {
        return childlist;
    }

    public void setChildlist(ArrayList<Tag> childlist) {
        this.childlist = childlist;
    }

    class Tag{
        private String id;
        private String keyname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKeyname() {
            return keyname;
        }

        public void setKeyname(String keyname) {
            this.keyname = keyname;
        }
    }
}
