package com.ibp.repository;
import com.ibp.domain.VersionEnvironnement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VersionEnvironnement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersionEnvironnementRepository extends JpaRepository<VersionEnvironnement, Long> {

}
