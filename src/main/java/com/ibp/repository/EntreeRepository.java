package com.ibp.repository;
import com.ibp.domain.Entree;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Entree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntreeRepository extends JpaRepository<Entree, Long> {

}
