package com.prometric.intelitesttptools.admin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.prometric.intelitesttptools.Repository.TPUser;
/**
 * @author Patrick.MacCnaimhin
 */
@Stateless
public class TPUserFacade extends AbstractFacade<TPUser> {
    @PersistenceContext(unitName = "IntelitestTPToolsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TPUserFacade() {
        super(TPUser.class);
    } 
}
