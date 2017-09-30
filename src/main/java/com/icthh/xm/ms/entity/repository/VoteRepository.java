package com.icthh.xm.ms.entity.repository;

import com.icthh.xm.ms.entity.domain.Vote;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Vote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    
}
