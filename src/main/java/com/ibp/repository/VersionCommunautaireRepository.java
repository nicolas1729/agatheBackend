package com.ibp.repository;
import com.ibp.domain.VersionCommunautaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VersionCommunautaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VersionCommunautaireRepository extends JpaRepository<VersionCommunautaire, Long> {

}
