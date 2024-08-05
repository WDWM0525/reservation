package zerobase.reservation.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.reservation.model.Auth;
import zerobase.reservation.security.TokenProvider;
import zerobase.reservation.service.MemberService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    /* **************************************************************************************
     * POST /auth/signup
     * signup : 회원 가입
     * @param request
     * ************************************************************************************** */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SingUp request) {
        // 회원가입을 위한 API
        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    /* **************************************************************************************
     * POST /auth/signin
     * signup : 회원 로그인
     * @param request
     * ************************************************************************************** */
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SingIn request) {
        // 로그인용 API
        var member = this.memberService.authenticate(request);
        var token = this.tokenProvider.generateToken(member.getUsername(), member.getRoles());
        log.info("user login -> " + request.getUsername());
        return ResponseEntity.ok(token);
    }
}
