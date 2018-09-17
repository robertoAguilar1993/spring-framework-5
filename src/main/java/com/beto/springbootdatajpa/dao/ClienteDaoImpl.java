package com.beto.springbootdatajpa.dao;

import com.beto.springbootdatajpa.domain.GenericDao;
import com.beto.springbootdatajpa.entity.Cliente;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * TODO clase dao para obtener las operaciones del cliente
 */
@Repository
public class ClienteDaoImpl extends GenericDao  {

    @PersistenceContext
    private EntityManager em;


    public static final String FIND_ALL = String.valueOf("from Cliente");

    /**
     * TODO metodo para obtener una lista de cliente
     * @return
     */
    //@Override
    public List<Cliente> findAll() {
        return   em.createQuery(FIND_ALL).getResultList();
    }

    //@Override
    public Cliente findOne(Long id) {
        if(validateObject(id)){
            return em.find(Cliente.class,id);
        }
        return null;
    }

    //@Override
    public void save(Cliente cliente) {
        if(validateObject(cliente)){
            if(validateObject(cliente.getId()) && cliente.getId() > 0){
                em.merge(cliente);
            }else{
                em.persist(cliente);
            }
        }
    }

   // @Override
    public void delete(Long id) {
        if(validateObject(id)){
            em.remove(findOne(id));
        }
    }
}
