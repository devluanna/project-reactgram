package com.react.domain.repository;

import com.react.domain.model.User.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

}