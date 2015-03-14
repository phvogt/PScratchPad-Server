// (c) 2015 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
     * @param name name of the scratchpad
     * @return the ScratchPad
     */
    List<ScratchPad> findByName(final String name);

}
