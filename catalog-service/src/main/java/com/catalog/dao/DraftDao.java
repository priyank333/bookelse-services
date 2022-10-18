/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.dao;

import com.catalog.model.Draft;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Priyank Agrawal
 */
@Repository
public interface DraftDao extends CrudRepository<Draft, Long> {

    @Transactional
    @Modifying
    public Integer deleteByDraftId(Long draftId);

    @Transactional
    @Modifying
    public Integer deleteByCustomerId(String customerId);

    @Query("SELECT COUNT(D.draftId) FROM Draft D " + "where D.customerId = :customerId")
    public Integer countDraft(@Param("customerId") String customerId);

    public Boolean existsByCustomerId(String customerId);
}
