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
public class ProductReportSpec implements Specification<Product> {

    private final ReportFilter filter;

    /**
     *seaches for products by name
     */
    @Override
    public Predicate toPredicate(Root<Product> product, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(filter.getStartDate())) {
            predicates.add(
                    cb.greaterThanOrEqualTo(product.get("createdAt"), filter.getStartDate())
            );
        }

        if (Objects.nonNull(filter.getEndDate())) {
            predicates.add(
                    cb.lessThanOrEqualTo(product.get("createdAt"), filter.getEndDate())
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
