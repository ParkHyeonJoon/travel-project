package com.group6.accommodation.domain.likes.service;

import com.group6.accommodation.domain.accommodation.repository.AccommodationRepository;
import com.group6.accommodation.domain.auth.repository.UserRepository;
import com.group6.accommodation.domain.likes.model.dto.UserLikeDto;
import com.group6.accommodation.domain.likes.model.entity.UserLikeEntity;
import com.group6.accommodation.domain.likes.model.entity.UserLikeId;
import com.group6.accommodation.domain.likes.repository.UserLikeRepository;
import com.group6.accommodation.global.exception.error.ExampleErrorCode;
import com.group6.accommodation.global.exception.error.UserLikeErrorCode;
import com.group6.accommodation.global.exception.type.ExampleException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLikeService {

    private final UserLikeRepository userLikeRepository;
    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserLikeDto addLikes(
        Long accommodationId, Long userId
    ) {
        // 해당 숙박 정보가 있는지 확인
        var accommodationEntity = accommodationRepository.findById(accommodationId)
            .orElseThrow(() -> new ExampleException(UserLikeErrorCode.ACCOMMODATION_NOT_EXIST));

        // 로그인한 객체 가져오기
        var authEntity = userRepository.findById(userId)
            .orElseThrow(() -> new ExampleException(UserLikeErrorCode.UNAUTHORIZED));

        // userLikeId 생성
        UserLikeId userLikeId = new UserLikeId();
        userLikeId.setUserId(userId);
        userLikeId.setAccommodationId(accommodationId);

        // 이미 찜했는지 여부 확인
        Optional<UserLikeEntity> isExistUserLike = userLikeRepository.findByAccommodationId(accommodationId);

        if (isExistUserLike.isPresent()) {
            UserLikeEntity userLikeEntity = isExistUserLike.get();
            throw new ExampleException(UserLikeErrorCode.ALREADY_ADD_LIKE);
        } else {
            UserLikeEntity addUserLike = UserLikeEntity.builder()
                .id(userLikeId)
                .accommodation(accommodationEntity)
                .user(authEntity)
                .build()
                ;
            addUserLike = userLikeRepository.save(addUserLike);
//            accommodationRepository.incrementLikeCount(accommodationId);
            return UserLikeDto.toDto(addUserLike);
        }
    }
}
