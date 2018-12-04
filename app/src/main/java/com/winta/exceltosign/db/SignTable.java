package com.winta.exceltosign.db;

import org.litepal.crud.DataSupport;

public class SignTable extends DataSupport{

    private String projectName;
    private String number;
    private String process;
    private String testContent;
    private String control;
    private String standard;
    private String result;
    private String zhuXiu;
    private String shiGong;
    private String zhiJian;
    private String chengBen;
    private String dongSheBei;
    private long id;


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setZhuXiu(String zhuXiu) {
        this.zhuXiu = zhuXiu;
    }

    public void setShiGong(String shiGong) {
        this.shiGong = shiGong;
    }

    public void setZhiJian(String zhiJian) {
        this.zhiJian = zhiJian;
    }

    public void setChenBen(String chenBen) {
        this.chengBen = chenBen;
    }

    public void setDongSheBei(String dongSheBei) {
        this.dongSheBei = dongSheBei;
    }


    public String getProjectName() {
        return projectName;
    }

    public String getNumber() {
        return number;
    }

    public String getProcess() {
        return process;
    }

    public String getTestContent() {
        return testContent;
    }

    public String getControl() {
        return control;
    }

    public String getStandard() {
        return standard;
    }

    public String getResult() {
        return result;
    }

    public String getZhuXiu() {
        return zhuXiu;
    }

    public String getShiGong() {
        return shiGong;
    }

    public String getZhiJian() {
        return zhiJian;
    }

    public String getChenBen() {
        return chengBen;
    }

    public String getDongSheBei() {
        return dongSheBei;
    }

    public long getId() {
        return id;
    }
}
