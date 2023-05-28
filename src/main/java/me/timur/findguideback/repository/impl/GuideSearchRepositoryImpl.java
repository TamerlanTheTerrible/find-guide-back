package me.timur.findguideback.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import me.timur.findguideback.entity.Guide;
import me.timur.findguideback.model.dto.GuideFilterDto;
import me.timur.findguideback.repository.GuideSearchRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Temurbek Ismoilov on 28/05/23.
 */

@Repository
public class GuideSearchRepositoryImpl implements GuideSearchRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Guide> findAllFiltered(GuideFilterDto filter) {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(Guide.class);
            var root = cq.from(Guide.class);

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getRegion() != null && !filter.getRegion().isEmpty()) {
                predicates.add(root.join("regions").get("name").in(filter.getRegion()));
            }
            if (filter.getLanguage() != null && !filter.getLanguage().isEmpty()) {
                predicates.add(root.join("languages").get("name").in(filter.getLanguage()));
            }
            if (filter.getHasCar() != null) {
                predicates.add(cb.equal(root.get("hasCar"), filter.getHasCar()));
            }
            cq.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Guide> query = em.createQuery(cq);
            query.setFirstResult(filter.getPageNumber() * filter.getPageSize());
            query.setMaxResults(filter.getPageSize());

            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
