package com.ds.domain.dto;

import com.ds.domain.Assignment;

import java.util.List;

public class UpdateDataDto {

    Long syncId;

    List<Assignment> assignments;

    String deviceId;

    public Long getSyncId() {
        return syncId;
    }

    public void setSyncId(Long syncId) {
        this.syncId = syncId;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
