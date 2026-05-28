package com.ocean.afefe.entities.modules.trainings.ojt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OjtOverviewSnapshot {
    private Integer daysLeft;
    private long totalHoursLogged;
    private Integer minOjtHours;
    private List<OjtMilestoneSnapshot> milestones;
}
