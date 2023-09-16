package com.react.domain.service;


import com.react.domain.model.User.Dashboard;

public interface DashboardService {
    Dashboard findByIdDashboard(Long idDashboard);

}