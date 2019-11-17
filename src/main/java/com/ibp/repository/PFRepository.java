package com.ibp.repository;
import com.ibp.domain.PF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PFRepository extends JpaRepository<PF, Long> {

}
