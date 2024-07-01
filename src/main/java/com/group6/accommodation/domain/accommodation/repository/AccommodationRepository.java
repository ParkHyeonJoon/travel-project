package com.group6.accommodation.domain.accommodation.repository;

import com.group6.accommodation.domain.accommodation.model.entity.AccommodationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, Long> {
    Page<AccommodationEntity> findByAreacode(String areaCode, Pageable pageable);
    Page<AccommodationEntity> findByCategory(String categoryCode, Pageable pageable);

    // 숙소명이나 주소에 키워드를 포함한 숙소 조회
    @Query("SELECT a FROM AccommodationEntity a WHERE a.title LIKE %:keyword% OR a.address LIKE %:keyword%")
    Page<AccommodationEntity> findByTitleOrAddressContainingKeyword(@Param("keyword") String keyword, Pageable pageable);
    Page<AccommodationEntity> findByIdIn(List<Long> ids, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE AccommodationEntity a SET a.likeCount = a.likeCount + 1 WHERE a.id = :accommodationId")
    void incrementLikeCount(@Param("accommodationId")Long accommodationId);

    @Transactional
    @Modifying
    @Query("UPDATE AccommodationEntity a SET a.likeCount = a.likeCount - 1 WHERE a.id = :accommodationId")
    void decrementLikeCount(@Param("accommodationId")Long accommodationId);

    @Query("SELECT a FROM AccommodationEntity a " +
            "JOIN a.rooms r " +
            "LEFT JOIN ReservationEntity res ON res.room = r " +
            "WHERE a.address = :area " +
            "AND r.roomMaxCount >= :headcount " +
            "AND (res IS NULL OR res.startDate > :endDate OR res.endDate < :startDate) " +
            "GROUP BY a.id " +
            "HAVING COUNT(res.reservationId) < r.roomCount")
    Page<AccommodationEntity> findAvailableAccommodations(@Param("area") String area,
                                                          @Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate,
                                                          @Param("headcount") int headcount,
                                                          Pageable pageable);
}
