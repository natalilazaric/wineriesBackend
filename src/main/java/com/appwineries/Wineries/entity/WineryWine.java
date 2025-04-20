package com.appwineries.Wineries.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "winery_wines")
public class WineryWine {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String wineName;

   @ManyToOne
   @JoinColumn(name = "winery_id", nullable = false)
   private Winery winery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWineName() {
        return wineName;
    }

    public void setWineName(String wineName) {
        this.wineName = wineName;
    }

    public Winery getWinery() {
        return winery;
    }

    public void setWinery(Winery winery) {
        this.winery = winery;
    }

    @Override
    public String toString() {
        return "WineryWine{" +
                "id=" + id +
                ", wineName='" + wineName + '\'' +
                ", winery=" + winery +
                '}';
    }

}
