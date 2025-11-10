package com.personal.specificatios;

import com.personal.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ProductSpec implements Specification<Product> {

    private final ProductFilter filter;

    /**
     *seaches for products by name
     */
    @Override
    public Predicate toPredicate(Root<Product> product, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getName() != null && !filter.getName().isEmpty()) {
            predicates.add(
                    cb.like(cb.upper(product.get("name")), "%" + filter.getName().toUpperCase() + "%")
            );
        }

        if (filter.getCategory() != null && !filter.getCategory().isEmpty()) {
            predicates.add(
                    cb.equal(product.get("category"), filter.getCategory())
            );
        }

        if (filter.getSize() != null && !filter.getSize().isEmpty()) {
            predicates.add(
                    cb.equal(product.get("size"), filter.getSize())
            );
        }

        /**
        // Optional: avoid duplicates if your query joins other tables
        //Prevents repeated rows
            query.distinct(true);
        */

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
