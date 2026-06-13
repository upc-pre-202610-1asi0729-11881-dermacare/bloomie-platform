// Plan.java
package com.bloomie.platform.subscription.domain.model.entities;

import com.bloomie.platform.subscription.domain.model.valueobjects.PlanType;
import lombok.Setter;

import java.util.List;

public class Plan {

    @Setter
    private Long id;

    @Setter
    private PlanType type;

    @Setter
    private String name;

    @Setter
    private Double price;

    @Setter
    private Integer durationDays;

    @Setter
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