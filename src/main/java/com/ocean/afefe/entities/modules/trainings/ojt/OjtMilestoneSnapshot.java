package com.ocean.afefe.entities.modules.trainings.ojt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OjtMilestoneSnapshot {
    private String title;
    private String targetDate;
    private String status;
    private String milestoneType;
}
