package com.pirtol.lab.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.pirtol.lab.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.pirtol.lab.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.pirtol.lab.domain.User.class.getName());
            createCache(cm, com.pirtol.lab.domain.Authority.class.getName());
            createCache(cm, com.pirtol.lab.domain.User.class.getName() + ".authorities");
            createCache(cm, com.pirtol.lab.domain.Dossier.class.getName());
            createCache(cm, com.pirtol.lab.domain.Dossier.class.getName() + ".evaluationSurfaceBaties");
            createCache(cm, com.pirtol.lab.domain.Dossier.class.getName() + ".evaluationBatiments");
            createCache(cm, com.pirtol.lab.domain.Dossier.class.getName() + ".evaluationClotures");
            createCache(cm, com.pirtol.lab.domain.Dossier.class.getName() + ".evaluationCoursAmenages");
            createCache(cm, com.pirtol.lab.domain.Dossier.class.getName() + ".locataires");
            createCache(cm, com.pirtol.lab.domain.Locataire.class.getName());
            createCache(cm, com.pirtol.lab.domain.EvaluationSurfaceBatie.class.getName());
            createCache(cm, com.pirtol.lab.domain.EvaluationCoursAmenage.class.getName());
            createCache(cm, com.pirtol.lab.domain.EvaluationCloture.class.getName());
            createCache(cm, com.pirtol.lab.domain.EvaluationBatiments.class.getName());
            createCache(cm, com.pirtol.lab.domain.Region.class.getName());
            createCache(cm, com.pirtol.lab.domain.Region.class.getName() + ".departements");
            createCache(cm, com.pirtol.lab.domain.Departement.class.getName());
            createCache(cm, com.pirtol.lab.domain.Departement.class.getName() + ".arrondissements");
            createCache(cm, com.pirtol.lab.domain.Arrondissement.class.getName());
            createCache(cm, com.pirtol.lab.domain.Arrondissement.class.getName() + ".communes");
            createCache(cm, com.pirtol.lab.domain.Commune.class.getName());
            createCache(cm, com.pirtol.lab.domain.Commune.class.getName() + ".quartiers");
            createCache(cm, com.pirtol.lab.domain.Commune.class.getName() + ".refParcelaires");
            createCache(cm, com.pirtol.lab.domain.Quartier.class.getName());
            createCache(cm, com.pirtol.lab.domain.Quartier.class.getName() + ".lotissements");
            createCache(cm, com.pirtol.lab.domain.Lotissement.class.getName());
            createCache(cm, com.pirtol.lab.domain.Lotissement.class.getName() + ".lotissements");
            createCache(cm, com.pirtol.lab.domain.Nature.class.getName());
            createCache(cm, com.pirtol.lab.domain.UsageDossier.class.getName());
            createCache(cm, com.pirtol.lab.domain.UsageDossier.class.getName() + ".dossiers");
            createCache(cm, com.pirtol.lab.domain.RefParcelaire.class.getName());
            createCache(cm, com.pirtol.lab.domain.RefParcelaire.class.getName() + ".dossiers");
            createCache(cm, com.pirtol.lab.domain.Refcadastrale.class.getName());
            createCache(cm, com.pirtol.lab.domain.Refcadastrale.class.getName() + ".dossiers");
            createCache(cm, com.pirtol.lab.domain.Proprietaire.class.getName());
            createCache(cm, com.pirtol.lab.domain.Proprietaire.class.getName() + ".dossiers");
            createCache(cm, com.pirtol.lab.domain.Proprietaire.class.getName() + ".representants");
            createCache(cm, com.pirtol.lab.domain.Representant.class.getName());
            createCache(cm, com.pirtol.lab.domain.CategorieBatie.class.getName());
            createCache(cm, com.pirtol.lab.domain.CategorieBatie.class.getName() + ".evaluationSurfaceBaties");
            createCache(cm, com.pirtol.lab.domain.CategorieCloture.class.getName());
            createCache(cm, com.pirtol.lab.domain.CategorieCloture.class.getName() + ".evaluationClotures");
            createCache(cm, com.pirtol.lab.domain.CategorieCoursAmenage.class.getName());
            createCache(cm, com.pirtol.lab.domain.CategorieCoursAmenage.class.getName() + ".evaluationCoursAmenages");
            createCache(cm, com.pirtol.lab.domain.CategorieNature.class.getName());
            createCache(cm, com.pirtol.lab.domain.CategorieNature.class.getName() + ".evaluationBatiments");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
