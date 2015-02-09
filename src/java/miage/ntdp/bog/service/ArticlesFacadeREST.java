/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.ntdp.bog.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import miage.ntdp.bog.entities.Articles;

/**
 *
 * @author Lotus
 */
@Stateless
@Path("/articles")
public class ArticlesFacadeREST extends AbstractFacade<Articles> {
    @PersistenceContext(unitName = "Blog-Kraria-SweyllamPU")
    private EntityManager em;
 //private Object uriInfo;
 @Context 
  UriInfo uriInfo;
 
    public ArticlesFacadeREST() {
        super(Articles.class);
    }
 
  

  @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, "application/json","application/xml"})
    public String create(MultivaluedMap<String, String> inFormParams) {
        Articles a = new Articles(inFormParams.getFirst("title"), inFormParams.getFirst("content"));
        super.create(a);
        return "/Blog-Kraria-Sweyllam/resources/articles/"+a.getArticleId();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, "application/json"})
    @Produces({"application/json"})
    public Articles edit(MultivaluedMap<String, String> inFormParams) {
        Articles a = super.find(Long.parseLong(inFormParams.getFirst("id")));
        a.setContent(inFormParams.getFirst("content"));
        a.setTitle(inFormParams.getFirst("title"));
        super.edit(a);
        return a;
    }

    @DELETE
    @Path("{id}")
    public String remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
        return ""+id;
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Articles find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Articles> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Articles> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        int[] range = new int[]{from, to};
        Query q = em.createQuery("select a from Articles a ORDER BY a.time ASC", Articles.class);
        q.setMaxResults(range[1]-range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @java.lang.Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
