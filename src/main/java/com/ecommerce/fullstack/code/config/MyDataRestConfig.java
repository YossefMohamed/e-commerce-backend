package com.ecommerce.fullstack.code.config;

import com.ecommerce.fullstack.code.entity.Product;
import com.ecommerce.fullstack.code.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


// RepositoryRestConfigurer: The library that will allow you to expose your database as a Simple REST API
@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] httpMethodsUnUsed = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // disable some http methods for Products
        config.getExposureConfiguration().
                forDomainType(Product.class)
                .withItemExposure(((metdata, httpMethods) ->
                        httpMethods.disable(httpMethodsUnUsed)))
                .withCollectionExposure(((metdata, httpMethods) ->
                        httpMethods.disable(httpMethodsUnUsed)));

        // disable some http methods for Products Category
        config.getExposureConfiguration().
                forDomainType(ProductCategory.class)
                .withItemExposure(((metdata, httpMethods) ->
                        httpMethods.disable(httpMethodsUnUsed)))
                .withCollectionExposure(((metdata, httpMethods) ->
                        httpMethods.disable(httpMethodsUnUsed)));


        exposeIds(config);

    }

    // return id in response
    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();
        List<Class> classes = new ArrayList<>();
        for (EntityType t: entityTypes
             ) {
            classes.add(t.getJavaType());
        }
        Class[] domainTypes =  classes.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
