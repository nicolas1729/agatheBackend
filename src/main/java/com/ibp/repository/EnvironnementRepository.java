package com.ibp.repository;
import com.ibp.domain.Environnement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Environnement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnvironnementRepository extends JpaRepository<Environnement, Long> {

}
