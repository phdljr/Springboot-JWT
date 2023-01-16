package kr.ac.phdljr.springbootjwt.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
public class SecurityUtil {
    private SecurityUtil(){}

    // SecurityContext의 Authentication 객체를 이용해 username을 리턴해주는 유틸 메소드
    public static Optional<String> getCurrentUsername() {
        // JwtFilter#doFilter에 의해 저장된 Authentication 객체를 꺼내서 사용
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if(authentication.getPrincipal() instanceof String){
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}
