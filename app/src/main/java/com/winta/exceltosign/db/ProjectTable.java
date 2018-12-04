package com.winta.exceltosign.db;

import org.litepal.crud.DataSupport;

public class ProjectTable extends DataSupport {


    private String projectName;
    private String beginDate;
    private String recentDate;
    private int title_type;
    private int head_type;
    private String image_a;
    private String image_b;
    private String beizhu;
    private long id;


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setRecentDate(String recentDate) {

        this.recentDate = recentDate;
    }

    public void setTitle_type(int title_type) {
        this.title_type = title_type;
    }

    public void setHead_type(int head_type) {
        this.head_type = head_type;
    }

    public void setImage_a(String image_a) {
        this.image_a = image_a;
    }

    public void setImage_b(String image_b) {
        this.image_b = image_b;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getRecentDate() {
        return recentDate;
    }

    public int getTitle_type() {
        return title_type;
    }

    public int getHead_type() {
        return head_type;
    }

    public String getImage_a() {
        return image_a;
    }

    public String getImage_b() {
        return image_b;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public long getId() {
        return id;
    }
}
