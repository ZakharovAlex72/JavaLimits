package ru.zakharov.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.zakharov.config.LimitsProperties;
import ru.zakharov.entity.Limits;
import ru.zakharov.repository.LimitsRepository;

import java.math.BigDecimal;


@Service
public class LimitsService {
    private static final Logger log = LoggerFactory.getLogger(LimitsService.class);

    private final LimitsRepository limitsRepository;
    private final LimitsProperties limitsProperties;

    public LimitsService(LimitsRepository limitsRepository, LimitsProperties limitsProperties) {
        this.limitsRepository = limitsRepository;
        this.limitsProperties = limitsProperties;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Limits getUserLimit(Long userId){
        return limitsRepository.findByUserId(userId).orElseGet(() ->{
            Limits newLimit = new Limits(userId, limitsProperties.getInitialLimit());
            return limitsRepository.save(newLimit);
        });
    }

    @Transactional
    public Limits reduceUserLimit(Long userId, BigDecimal sum){
        Limits limit = getUserLimit(userId);

        BigDecimal newLimit = limit.getValue().subtract(sum);
        log.info("newLimit = "+newLimit);
        if (newLimit.intValue()<0) {
            throw new IllegalArgumentException("Сумма превышает ежедневный лимит");
        }
        limitsRepository.updateLimit(userId, newLimit);
        log.info("Обновили лимит!");
        return getUserLimit(userId);
    }

    @Transactional
    public Limits restoreUserLimit(Long userId, BigDecimal sum){
        Limits limit = getUserLimit(userId);
        BigDecimal newLimit = limit.getValue().add(sum);
        //если при восстановлении превышаем дневной лимит - ставим лимит по умолчанию
        if (newLimit.intValue()>limitsProperties.getInitialLimit().intValue())
            newLimit = limitsProperties.getInitialLimit();
        limitsRepository.updateLimit(userId, newLimit);
        return getUserLimit(userId);
    }

    @Scheduled(fixedRateString = "PT01M") // для теста, обновление каждую минуту
    //@Scheduled(cron = "@daily") // для ежедневного запуска
    @Transactional
    public void resetAllLimits(){
        log.info("Обновление лимитов");
        limitsRepository.updateAllLimits(limitsProperties.getInitialLimit());
    }
}
