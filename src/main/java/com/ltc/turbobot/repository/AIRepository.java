package com.ltc.turbobot.repository;

import com.ltc.turbobot.entity.AIEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIRepository extends JpaRepository<AIEntity, Long> {
}
