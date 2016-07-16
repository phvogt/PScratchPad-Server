package com.github.phvogt.pscratchpad.server.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.github.phvogt.pscratchpad.server.dao.entities.ScratchPad;
import com.google.appengine.api.datastore.Key;

/**
 * JPA repository for ScratchPad.
 */
@Transactional
public interface ScratchPadRepository extends JpaRepository<ScratchPad, Key> {

    /**
     * Finds the scratchpad by name.
     * 
     * @param name
     *            name of the scratchpad
     * @return the ScratchPads
     */
    List<ScratchPad> findByNameOrderByLastChangeDesc(final String name);

    /**
     * Get number of scratchpads.
     * 
     * @param name
     *            name
     * @return number of ScratchPads
     */
    @Query("select count(sp) from ScratchPad sp where sp.name = :name")
    Long countByName(@Param("name") final String name);

    /**
     * Get the scratchpad by name and before the provided last change.
     * 
     * @param name
     *            name
     * @param lastChange
     *            last change
     * @return the ScratchPads
     */
    List<ScratchPad> findByNameAndLastChangeLessThanOrderByLastChangeDesc(final String name, final Date lastChange);

}
