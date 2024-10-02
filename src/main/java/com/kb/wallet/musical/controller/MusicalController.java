package com.kb.wallet.musical.controller;

import com.kb.wallet.global.common.response.ApiResponse;
import com.kb.wallet.member.domain.Member;
import com.kb.wallet.musical.domain.Musical;
import com.kb.wallet.musical.dto.request.MusicalCreationRequest;
import com.kb.wallet.musical.dto.request.MusicalInfoUpdateRequest;
import com.kb.wallet.musical.dto.response.MusicalCreationResponse;
import com.kb.wallet.musical.dto.response.MusicalDetailResponse;
import com.kb.wallet.musical.dto.response.MusicalResponse;
import com.kb.wallet.musical.dto.response.MusicalSeatAvailabilityResponse;
import com.kb.wallet.musical.service.MusicalService;
import java.util.List;
import javax.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Builder
@RestController
@Slf4j
@RequestMapping("/musicals")
public class MusicalController {

  private final MusicalService musicalService;

  @Autowired
  public MusicalController(MusicalService musicalService) {
    this.musicalService = musicalService;
  }

  @GetMapping
  public ApiResponse<Page<MusicalResponse>> findAll(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size) {
    Page<MusicalResponse> responses = musicalService.findAllMusicals(page, size);
    return ApiResponse.ok(responses);
  }

  @GetMapping("/{musicalId}")
  public ApiResponse<MusicalDetailResponse> findById(
    @PathVariable(name = "musicalId") Long musicalId) {
    Musical musical = musicalService.findById(musicalId);
    return ApiResponse.ok(MusicalDetailResponse.convertToResponse(musical));
  }

  @PostMapping
  public ResponseEntity<MusicalCreationResponse> createMusical(
    @RequestBody @Valid MusicalCreationRequest request) {
    MusicalCreationResponse savedMusical = musicalService.saveMusical(request);
    return ResponseEntity.ok(savedMusical);
  }

  @DeleteMapping("/{musicalId}")
//  @PreAuthorize("hasRole('ADMIN')") // 관리자만 뮤지컬 정보 삭제 가능
  public ResponseEntity<Void> delete(@PathVariable(name = "musicalId") Long musicalId) {
    musicalService.deleteMusical(musicalId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{musicalId}")
//  @PreAuthorize("hasRole('ADMIN')") // 관리자만 뮤지컬 정보 업데이트 가능
  public ResponseEntity<Void> updateMusicalInfo(
    @PathVariable(name = "musicalId") Long musicalId,
    @RequestBody MusicalInfoUpdateRequest request) {
    /**
     * TODO : Login Authentication 추가 예정
     */
    musicalService.updateMusicalInfo(musicalId, request);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{musicalId}/seats-availability")
  public ApiResponse<List<MusicalSeatAvailabilityResponse>> checkSeatAvailability(
    @AuthenticationPrincipal Member member,
    @PathVariable(name = "musicalId") Long musicalId,
    @RequestParam("date") String date) {

    List<MusicalSeatAvailabilityResponse> responses = musicalService.checkSeatAvailability(
      musicalId,
      date);

    return ApiResponse.ok(responses);
  }
}
