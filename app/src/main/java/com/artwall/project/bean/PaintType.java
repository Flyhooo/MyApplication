package com.artwall.project.bean;

import java.util.ArrayList;

/**
 * Created by 95 on 2016/4/26.
 */
public class PaintType {

//    id：ID
//    levelid：父栏目ID
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

    public class Tag {
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
