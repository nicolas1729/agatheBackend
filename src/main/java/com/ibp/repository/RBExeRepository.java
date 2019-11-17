package com.ibp.repository;
import com.ibp.domain.RBExe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RBExe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RBExeRepository extends JpaRepository<RBExe, Long> {

}
