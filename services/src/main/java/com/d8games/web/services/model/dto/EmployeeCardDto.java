package com.d8games.web.services.model.dto;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EmployeeCardDto {

    private String employeeFullName;
    private String titleName;
    private String departmentName;
    private Long timeSinceJoin;

    public EmployeeCardDto(String employeeName, String employeeSurname, String titleName,
                           String departmentName, Date employeeJoinDate) {
        this.employeeFullName = getEmployeeFullName(employeeName, employeeSurname);
        this.titleName = titleName;
        this.departmentName = departmentName;
        this.timeSinceJoin = getEmployeeTimeSinceJoin(employeeJoinDate);
    }

    private String getEmployeeFullName(String employeeName, String employeeSurname) {
        return employeeName + " " + employeeSurname;
    }

    private Long getEmployeeTimeSinceJoin(Date employeeJoinDate) {
        long timeInMillies = new Date().getTime() - employeeJoinDate.getTime();
        return TimeUnit.DAYS.convert(timeInMillies, TimeUnit.MILLISECONDS);
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getTimeSinceJoin() {
        return timeSinceJoin;
    }

    public void setTimeSinceJoin(Long timeSinceJoin) {
        this.timeSinceJoin = timeSinceJoin;
    }
}
