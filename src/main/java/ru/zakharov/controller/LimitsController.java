package ru.zakharov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.zakharov.dto.LimitsResponse;
import ru.zakharov.entity.Limits;
import ru.zakharov.service.LimitsService;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/v1/limits")
public class LimitsController {
    private final LimitsService limitsService;

    public LimitsController(LimitsService limitsService) {
        this.limitsService = limitsService;
    }

    @GetMapping(value = "/user")
    public LimitsResponse getUserLimit(@RequestParam("userId") Long userId){
        Limits limit = limitsService.getUserLimit(userId);
        return new LimitsResponse(limit);
    }

    @PostMapping(value = "/reduce")
    public LimitsResponse reduceUserLimit(@RequestParam("userId") Long userId, @RequestParam("sum") BigDecimal sum){
        Limits limit = limitsService.reduceUserLimit(userId,sum);
        return new LimitsResponse(limit);
    }

    @PostMapping(value ="/restore")
    public LimitsResponse restoreUserLimit(@RequestParam("userId") Long userId, @RequestParam("sum") BigDecimal sum){
        Limits limit = limitsService.restoreUserLimit(userId,sum);
        return new LimitsResponse(limit);
    }

}
