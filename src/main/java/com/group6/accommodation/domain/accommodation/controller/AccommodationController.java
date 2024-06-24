package com.group6.accommodation.domain.accommodation.controller;

import com.group6.accommodation.domain.accommodation.model.dto.AccommodationDetailDto;
import com.group6.accommodation.domain.accommodation.model.dto.AccommodationDto;
import com.group6.accommodation.global.model.dto.PagedDto;
import com.group6.accommodation.domain.accommodation.service.AccommodationService;
import com.group6.accommodation.global.exception.error.AccommodationErrorCode;
import com.group6.accommodation.global.exception.type.AccommodationException;
import com.group6.accommodation.global.util.ResponseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open-api")
public class AccommodationController {

    private final AccommodationService accommodationService;

    // 숙소 전체 조회
    @GetMapping(path = "/accommodation")
    public ResponseEntity<ResponseApi<PagedDto<AccommodationDto>>> readAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String category
    ) {

        ResponseApi<PagedDto<AccommodationDto>> accommodationPage;

        // 임시 기본 사이즈 설정
        int customSize = (area == null && category == null) ? 12 : 9;

        if (area != null && category == null) {
            accommodationPage = accommodationService.findByAreaPaged(area, page, customSize);
        } else if (category != null && area == null) {
            accommodationPage = accommodationService.findByCategoryPaged(category, page, customSize);
        } else if (area == null && category == null){
            accommodationPage = accommodationService.findAllPage(page, customSize);
        } else {
            throw new AccommodationException(
                    AccommodationErrorCode.NOT_BOTH_AREA_CATEGORY);
        }

        return ResponseEntity.status(HttpStatus.OK).body(accommodationPage);
    }

    // 숙소 단건 조회
    @GetMapping(path = "/accommodation/{id}")
    public ResponseEntity<ResponseApi<AccommodationDetailDto>> read(
            @PathVariable(name = "id") Long id
    ) {
        ResponseApi<AccommodationDetailDto> accommodationDetail = accommodationService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(accommodationDetail);
    }

    // 키워드로 숙소 조회
    @GetMapping(path = "/accommodation/search")
    public ResponseEntity<ResponseApi<PagedDto<AccommodationDto>>> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page
    ) {
        int customSize = 9; // 기본 페이지 사이즈 설정

        ResponseApi<PagedDto<AccommodationDto>> accommodationPage = accommodationService.findByKeywordPaged(keyword, page, customSize);
        return ResponseEntity.status(HttpStatus.OK).body(accommodationPage);
    }
}
