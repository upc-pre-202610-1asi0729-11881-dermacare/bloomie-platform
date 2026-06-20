package com.bloomie.platform.routinemanagement.application.internal.queryservices;

import com.bloomie.platform.routinemanagement.application.queryservices.DailyTrackingQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllDailyTrackingsQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingByPatientIdAndDateQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingsByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingsByRoutineIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetWeeklySummaryByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineId;
import com.bloomie.platform.routinemanagement.domain.repositories.DailyTrackingRepository;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.WeeklySummaryResource;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<DailyTracking> handle(GetDailyTrackingByPatientIdAndDateQuery query) {
        return dailyTrackingRepository.findByPatientIdAndDate(
                new PatientId(query.patientId()), query.date());
    }

    @Override
    public List<DailyTracking> handle(GetDailyTrackingsByPatientIdQuery query) {
        return dailyTrackingRepository.findAllByPatientId(new PatientId(query.patientId()));
    }

    @Override
    public List<DailyTracking> handle(GetDailyTrackingsByRoutineIdQuery query) {
        return dailyTrackingRepository.findAllByRoutineId(new RoutineId(query.routineId()));
    }

    @Override
    public WeeklySummaryResource handle(GetWeeklySummaryByPatientIdQuery query) {
        var weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        var weekEnd   = weekStart.plusDays(6);

        var completedDays = (int) dailyTrackingRepository
                .findAllByPatientId(query.patientId())
                .stream()
                .filter(t -> !t.getDate().isBefore(weekStart)
                          && !t.getDate().isAfter(weekEnd)
                          && t.isCompleted())
                .count();

        var missedDays     = 7 - completedDays;
        var completionRate = Math.round((completedDays / 7.0) * 100 * 10.0) / 10.0;

        return new WeeklySummaryResource(
                query.patientId().patientId(),
                weekStart.toString(),
                weekEnd.toString(),
                completedDays,
                missedDays,
                completionRate);
    }
}
