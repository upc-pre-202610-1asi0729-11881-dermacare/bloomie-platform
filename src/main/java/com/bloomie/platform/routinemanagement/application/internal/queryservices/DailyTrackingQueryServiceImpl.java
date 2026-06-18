package com.bloomie.platform.routinemanagement.application.internal.queryservices;

import com.bloomie.platform.routinemanagement.application.queryservices.DailyTrackingQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllDailyTrackingsQuery;
import com.bloomie.platform.routinemanagement.domain.repositories.DailyTrackingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that resolves daily tracking read queries.
 */
@Service
public class DailyTrackingQueryServiceImpl implements DailyTrackingQueryService {

    private final DailyTrackingRepository dailyTrackingRepository;

    public DailyTrackingQueryServiceImpl(DailyTrackingRepository dailyTrackingRepository) {
        this.dailyTrackingRepository = dailyTrackingRepository;
    }

    @Override
    public List<DailyTracking> handle(GetAllDailyTrackingsQuery query) {
        return dailyTrackingRepository.findAll();
    }
}