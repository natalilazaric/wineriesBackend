package com.appwineries.Wineries.dto;

import lombok.Data;


@Data
public class UpdateWineryDTO {
    private AllWineriesDTO winery;
    private String wines;

    // getteri i setteri
    public AllWineriesDTO getWinery() {
        return winery;
    }

    public void setWinery(AllWineriesDTO winery) {
        this.winery = winery;
    }

    public String getWines() {
        return wines;
    }

    public void setWines(String wines) {
        this.wines = wines;
    }
}
