package me.timur.findguideback.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.CriteriaSearchResult;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.repository.GuideRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

@Repository
public class GuideRepositoryCustomImpl implements GuideRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public CriteriaSearchResult<Guide> findAllFiltered(GuideFilterDto filter) {
        try {
            //init count query objects
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Guide> root = countQuery.from(Guide.class);
            //count query
            countQuery.select(cb.count(root))
                    .where(buildPredicate(filter, cb, root));
            var totalResults = em.createQuery(countQuery)
                    .getSingleResult();

            //init select query objects
            var dataQuery = cb.createQuery(Guide.class);
            root = dataQuery.from(Guide.class);
            //select query
            dataQuery.select(root)
                    .where(buildPredicate(filter, cb, root));
            var dataTypedQuery = em.createQuery(dataQuery);
            dataTypedQuery.setFirstResult(filter.getPageNumber() * filter.getPageSize());
            dataTypedQuery.setMaxResults(filter.getPageSize());

            var resultList = dataTypedQuery.getResultList();

            return new CriteriaSearchResult<>(totalResults, resultList);
        } finally {
            em.close();
        }
    }

    private Predicate buildPredicate(GuideFilterDto filter, CriteriaBuilder cb, Root<Guide> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(root.get("isVerified"), true));
        predicates.add(cb.equal(root.get("isActive"), true));
        predicates.add(cb.equal(root.get("isBlocked"), false));
        if (filter.getRegion() != null && !filter.getRegion().isEmpty()) {
            predicates.add(root.join("regions").get("engName").in(filter.getRegion()));
        }
        if (filter.getLanguage() != null && !filter.getLanguage().isEmpty()) {
            predicates.add(root.join("languages").get("engName").in(filter.getLanguage()));
        }
        if (filter.getHasCar() != null) {
            predicates.add(cb.equal(root.get("hasCar"), filter.getHasCar()));
        }
        return predicates.get(0);
    }
}
