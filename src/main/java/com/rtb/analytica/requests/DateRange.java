package com.rtb.analytica.requests;

import lombok.Data;
import lombok.NonNull;

@Data
public class DateRange {
    private String startDate;
    private String endDate;
}