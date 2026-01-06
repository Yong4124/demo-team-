package com.example.personal.controller;

import com.example.personal.dto.PersonalDetailResponse;
import com.example.personal.model.ApprovalYn;
import com.example.personal.model.Personal;
import com.example.personal.repository.PersonalRepository;
import com.example.personal.service.PersonalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
public class PersonalController {

    private final PersonalService personalService;
    private final PersonalRepository personalRepository;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Personal personal) {
        Map<String, Object> response = new HashMap<>();

        try {
            Personal saved = personalService.register(personal);
            response.put("success", true);
            response.put("message", "회원가입이 완료되었습니다.");
            response.put("data", saved);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ID 중복 체크
    @GetMapping("/check-id/{id}")
    public ResponseEntity<Map<String, Object>> checkId(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        boolean exists = personalService.checkIdDuplication(id);
        response.put("exists", exists);
        response.put("message", exists ? "이미 사용중인 ID입니다." : "사용 가능한 ID입니다.");

        return ResponseEntity.ok(response);
    }

    // 이메일 중복 체크
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Map<String, Object>> checkEmail(@PathVariable String email) {
        Map<String, Object> response = new HashMap<>();

        boolean exists = personalService.checkEmailDuplication(email);
        response.put("exists", exists);
        response.put("message", exists ? "이미 사용중인 이메일입니다." : "사용 가능한 이메일입니다.");

        return ResponseEntity.ok(response);
    }

    // 회원 조회
    @GetMapping("/{id}")
    public ResponseEntity<Personal> getPersonal(@PathVariable String id) {
        return personalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 전체 회원 조회
    @GetMapping("/list")
    public ResponseEntity<Iterable<Personal>> getAllPersonals() {
        return ResponseEntity.ok(personalService.findAll());
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<Map<String, Object>>> adminUsers() {

        List<Map<String, Object>> result = personalService.adminList().stream()
                .map(p -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("no", p.getSeqNoM100());
                    m.put("name", p.getName());
                    m.put("id", p.getLoginId());
                    m.put("gender", p.getGender());       // M/F/O/N
                    m.put("residence", p.getResidence()); // O/K/U
                    m.put("date", p.getInsertDate());
                    m.put("approve", p.getApprovalYn());  // Y/N
                    return m;
                })
                .toList();

        return ResponseEntity.ok(result);
    }
    @GetMapping("/admin/users/{seq}")
    public ResponseEntity<PersonalDetailResponse> detail(@PathVariable Integer seq) {

        Personal p = personalRepository.findById(seq)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(PersonalDetailResponse.from(p));
    }


    @PutMapping("/admin/users/{seq}/approval")
    public ResponseEntity<Void> updateApproval(
            @PathVariable Integer seq,
            @RequestBody Map<String, String> body
    ) {
        String approvalYn = body.get("approvalYn"); // "Y" or "N"
        if (!"Y".equalsIgnoreCase(approvalYn) && !"N".equalsIgnoreCase(approvalYn)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "approvalYn must be Y or N");
        }

        Personal p = personalRepository.findById(seq)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        p.setApprovalYn("Y".equalsIgnoreCase(approvalYn) ? ApprovalYn.Y : ApprovalYn.N);
        personalRepository.save(p);

        return ResponseEntity.ok().build();
    }
    // admin 통계부분
    @GetMapping("/admin/stats/join-monthly")
    public ResponseEntity<Map<String, Object>> joinMonthly(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(personalService.getJoinStats(from, to));
    }

}