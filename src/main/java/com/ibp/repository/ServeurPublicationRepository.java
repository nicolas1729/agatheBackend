package com.ibp.repository;
import com.ibp.domain.ServeurPublication;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServeurPublication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServeurPublicationRepository extends JpaRepository<ServeurPublication, Long> {

}
