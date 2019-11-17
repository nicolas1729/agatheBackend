package com.ibp.repository;
import com.ibp.domain.WebApp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WebApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WebAppRepository extends JpaRepository<WebApp, Long> {

}
