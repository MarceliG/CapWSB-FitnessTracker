package com.capgemini.wsb.fitnesstracker.statistics.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.wsb.fitnesstracker.statistics.api.Statistics;

interface StatisticsRepository extends JpaRepository<Statistics, Long> {

}
