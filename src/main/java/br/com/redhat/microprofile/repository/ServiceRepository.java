package br.com.redhat.microprofile.repository;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.ws.rs.WebApplicationException;

import br.com.redhat.microprofile.model.Service;

@Stateful
@RequestScoped
public class ServiceRepository {
    
    @PersistenceContext
    EntityManager em;

    @PersistenceUnit
    EntityManagerFactory emf;

    public void createService(Service service) {
        em.persist(service);
        System.out.println("Created Service");

    }

    public void updateService( Service service ) {

        Service serviceToUpdate = findServiceById(service.getId());
        serviceToUpdate.setName(service.getName());
        serviceToUpdate.setTargetPort(service.getTargetPort());
        serviceToUpdate.setUrl(service.getUrl());
    }

    public void deleteService(Long serviceId) {
        Service c = findServiceById(serviceId);
        em.remove(c);
    }

    public Service findServiceById(Long id) {
        Service service = em.find(Service.class, id);

        if (service == null) {
            throw new WebApplicationException("Service with id of " + id + " does not exist.", 404);
        }
        return service;
    }

    public List<Service> findAllServices() {
        Query query = em.createQuery("SELECT c FROM Service c");
        List<Service> serviceList = query.getResultList();
        System.out.println(serviceList);
        return serviceList;
    }

    public Service findServiceByName(String name) {
        Query query = em
                .createQuery("SELECT c FROM Service c WHERE c.name = :name");
        query.setParameter("name", name);
        Service service = (Service) query.getSingleResult();
        return service;
    }

}
