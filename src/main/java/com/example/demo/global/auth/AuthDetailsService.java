package com.example.demo.global.auth;

import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthDetails authDetails = userRepository.findByEmail(email)
                .map(AuthDetails::new)
                .orElseThrow(() -> EmailNotFoundException.EXCEPTION);
        System.out.println(authDetails.getUser().getEmail());
        return authDetails;
    }
}
