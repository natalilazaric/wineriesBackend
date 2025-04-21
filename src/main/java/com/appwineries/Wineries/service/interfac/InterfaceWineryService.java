package com.appwineries.Wineries.service.interfac;

import com.appwineries.Wineries.dto.*;
import com.appwineries.Wineries.entity.Schedule;
import com.appwineries.Wineries.entity.Winery;
import com.appwineries.Wineries.entity.WineryWine;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InterfaceWineryService {

    Response addNewWinery(Long userId, MultipartFile photo, String name, String location, double latitude, double longitude, BigDecimal price, boolean food, String description, List<String> wines, Map<String, String> extras);

    Response getWinesByWineryId(Long wineryId);

    Response getAllWineries();

    Response deleteWinery(Long wineryId);

    Response updateWinery(Long wineryId, AllWineriesDTO wineryDTO, List<String> wines);

    Response getWineryById(Long wineryId);


    Response getWineryByUserId(Long userId);

    Response addSchedule(Long wineryId, List<ScheduleDTO> scheduleDTOs);

    Response getScheduleByWineryId(Long wineryId);

    Response updateSchedule(Long wineryId, List<ScheduleDTO> scheduleDTOs);
}
