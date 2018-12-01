package com.anelsoftware.beer.repository;

import com.anelsoftware.beer.domain.UserProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
