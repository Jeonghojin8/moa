package com.example.moa_ex;

public class contacts_dept_VO {
    // 필드변수
    private String dept_name;
    private String dept_tel;

    public contacts_dept_VO(){}

    public contacts_dept_VO(String dept_name, String dept_tel) {
        this.dept_name = dept_name;
        this.dept_tel = dept_tel;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_tel() {
        return dept_tel;
    }

    public void setDept_tel(String dept_tel) {
        this.dept_tel = dept_tel;
    }
}


