package com.ibp.repository;
import com.ibp.domain.RBUrl;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RBUrl entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RBUrlRepository extends JpaRepository<RBUrl, Long> {

}
