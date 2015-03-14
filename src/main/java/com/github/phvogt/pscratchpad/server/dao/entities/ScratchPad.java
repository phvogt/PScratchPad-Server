// (c) 2014 by Philipp Vogt
package com.github.phvogt.pscratchpad.server.dao.entities;

import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * ScratchPad.
 */
@Entity
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ScratchPad {

    /** id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key id;

    /** name of scratchpad. */
    private String name;

    /** data. */
    private String data;

    /** last change. */
    private Date lastChange;

    /**
     * Get the id.
     * @return the id
     */
    public final Key getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id the id to set
     */
    public final void setId(final Key id) {
        this.id = id;
    }

    /**
     * Get the name.
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * Get the data.
     * @return the data
     */
    public final String getData() {
        return data;
    }

    /**
     * Sets the data.
     * @param data the data to set
     */
    public final void setData(final String data) {
        this.data = data;
    }

    /**
     * Get the lastChange.
     * @return the lastChange
     */
    public final Date getLastChange() {
        return lastChange;
    }

    /**
     * Sets the lastChange.
     * @param lastChange the lastChange to set
     */
    public final void setLastChange(final Date lastChange) {
        this.lastChange = lastChange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ScratchPad [super=");
        builder.append(super.toString());
        builder.append(", id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append(", data=");
        builder.append(data);
        builder.append(", lastChange=");
        builder.append(lastChange);
        builder.append("]");
        return builder.toString();
    }

}
