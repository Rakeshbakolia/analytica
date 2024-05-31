package com.rtb.analytica.manager.specifications;

import com.rtb.analytica.models.Satellite;
import jakarta.persistence.criteria.Path;
import liquibase.util.StringUtil;
import org.springframework.data.jpa.domain.Specification;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class SatelliteSpecification {

    public static Specification<Satellite> launcherCodeIn(Collection<String> launcherCodes){
        return (root, query, builder) -> {
            Path<Satellite> launcher = root.get("launcher");
            return launcher.get("launcherCode")
                    .in(launcherCodes);
        };
    }

    public static Specification<Satellite> satelliteCodeIn(Collection<String> satelliteCodes){
        return (root, query, builder) -> root.get("satelliteCode").in(satelliteCodes);
    }

    public static Specification<Satellite> countries(Collection<String> countries){
        return (root, query, builder) -> root.get("country").in(countries);
    }

    public static Specification<Satellite>  massRange( Double min, Double max){
        try {
            if(Objects.nonNull(min) && Objects.nonNull(max)){
                return (root, query, builder) -> builder.between(root.get("mass"),min,max);
            }else if(Objects.nonNull(min)) {
                return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("mass"), min);
            }else{
                return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("mass"), max);
            }
        }catch (Exception e){
            throw new RuntimeException("Wrong Date format it should be dd-MM-yyyy");
        }
    }

    public static Specification<Satellite> dateRange( String startDate, String endDate){
        Date start = null;
        Date end = null;
        try {
            if(StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)){
                start = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
                end = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
                Date finalStart = start;
                Date finalEnd = end;
                return (root, query, builder) -> builder.between(root.get("launchDate"), finalStart, finalEnd);
            }else if(StringUtil.isNotEmpty(startDate)) {
                start = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
                Date finalStart = start;
                return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("launchDate"), finalStart);
            }else{
                end = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
                Date finalEnd = end;
                return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("launchDate"), finalEnd);
            }
        }catch (Exception e){
            throw new RuntimeException("Wrong Date format it should be dd-MM-yyyy");
        }
    }
}
