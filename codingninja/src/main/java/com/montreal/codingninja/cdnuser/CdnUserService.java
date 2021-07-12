package com.montreal.codingninja.cdnuser;

import com.montreal.codingninja.registration.token.ConfirmationToken;
import com.montreal.codingninja.registration.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
//@AllArgsConstructor

public class CdnUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final CdnUserRepository cdnUserRepository;
    public CdnUserService(CdnUserRepository cdnUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                ConfirmationTokenService confirmationTokenService) {
        this.cdnUserRepository = cdnUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
}
private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return cdnUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(CdnUser cdnUser) {
        boolean userExists = cdnUserRepository
                .findByEmail(cdnUser.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(cdnUser.getPassword());

        cdnUser.setPasswords(encodedPassword);

        cdnUserRepository.save(cdnUser);

        String token = UUID.randomUUID().toString();


        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                cdnUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        //TODO: SEND EMAIL
        return token;
    }
            public int enableCdnUser (String email) {
             return cdnUserRepository.enableCdnUser(email);
            }
}
