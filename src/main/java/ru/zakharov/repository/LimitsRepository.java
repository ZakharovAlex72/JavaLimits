package ru.zakharov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zakharov.entity.Limits;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LimitsRepository extends JpaRepository<Limits,Long> {

    Optional<Limits> findByUserId( Long userId);

    @Modifying
    @Query(value = "UPDATE Limits l SET l.value = :value where l.userId = :userId")
    void updateLimit(@Param("userId") Long userId, @Param("value") BigDecimal value);

    @Modifying
    @Query(value = "UPDATE Limits l SET l.value = :value")
    void updateAllLimits(@Param("value") BigDecimal value);
}
