package com.example.session1.models;

public class DepartmentLocations {
    private long id, departmentid, locationid;
    private String startdate, enddate;

    public DepartmentLocations(long id, long departmentid, long locationid, String startdate, String enddate) {
        this.id = id;
        this.departmentid = departmentid;
        this.locationid = locationid;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(long departmentid) {
        this.departmentid = departmentid;
    }

    public long getLocationid() {
        return locationid;
    }

    public void setLocationid(long locationid) {
        this.locationid = locationid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Long returnID() {
        return id;
    }
}
