package ru.zakharov.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="limits")
public class Limits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "value")
    private BigDecimal value;

    public Limits(){};

    public Limits(Long userId, BigDecimal value) {
        this.userId = userId;
        this.value = value;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
