package com.montreal.codingninja.cdnuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)

public interface CdnUserRepository extends JpaRepository<CdnUser   , Long> {

    Optional<CdnUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE CdnUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableCdnUser(String email);

}