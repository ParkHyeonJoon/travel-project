package com.group6.accommodation.domain.likes.repository;

import com.group6.accommodation.domain.likes.model.entity.UserLikeEntity;
import com.group6.accommodation.domain.likes.model.entity.UserLikeId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLikeEntity, UserLikeId> {

    Optional<UserLikeEntity> findByAccommodationIdAndUserId(Long accommodationId, Long userId);

    @Query("SELECT COUNT(u) FROM UserLikeEntity u WHERE u.accommodation.id = :accommodationId")
    int countByAccommodationId(Long accommodationId);
}
