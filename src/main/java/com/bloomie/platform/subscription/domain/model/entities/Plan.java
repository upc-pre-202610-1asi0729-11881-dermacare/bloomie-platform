// Plan.java
package com.bloomie.platform.subscription.domain.model.entities;

import com.bloomie.platform.subscription.domain.model.valueobjects.PlanType;
import java.util.List;

public class Plan {

    private Long id;
    private PlanType type;
    private String name;
    private Double price;
    private Integer durationDays;
    private List<String> modules;

    public Plan() {}

    public Plan(Long id, PlanType type, String name,
                Double price, Integer durationDays, List<String> modules) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.durationDays = durationDays;
        this.modules = modules;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PlanType getType() { return type; }
    public String getName() { return name; }
    public Double getPrice() { return price; }
    public Integer getDurationDays() { return durationDays; }
    public List<String> getModules() { return modules; }
}