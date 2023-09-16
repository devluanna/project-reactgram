package com.react.domain.service.ServiceImp;

import com.react.domain.service.DashboardService;
import com.react.domain.model.User.Dashboard;
import com.react.domain.repository.DashboardRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardServiceImpl(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Override
    public Dashboard findByIdDashboard(Long idDashboard) {
        return dashboardRepository.findById(idDashboard).orElseThrow(NoSuchElementException::new);

    }


}