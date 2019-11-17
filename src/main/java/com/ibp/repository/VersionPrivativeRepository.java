package com.ibp.repository;
import com.ibp.domain.VersionPrivative;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VersionPrivative entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersionPrivativeRepository extends JpaRepository<VersionPrivative, Long> {

}
