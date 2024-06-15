package com.adjudicat.repository.repository;

import com.adjudicat.repository.entity.ChatbotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatbotRepository extends JpaRepository<ChatbotEntity, Long> {

    Optional<ChatbotEntity> findByTexto(String mensaje);
}