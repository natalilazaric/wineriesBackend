package com.appwineries.Wineries.dto;

import java.util.List;

public class WineryScheduleDTO {

    private Long wineryId;
    private List<ScheduleDTO> slots;

    public Long getWineryId() {
        return wineryId;
    }

    public void setWineryId(Long wineryId) {
        this.wineryId = wineryId;
    }

    public List<ScheduleDTO> getSlots() {
        return slots;
    }

    public void setSlots(List<ScheduleDTO> slots) {
        this.slots = slots;
    }
}
