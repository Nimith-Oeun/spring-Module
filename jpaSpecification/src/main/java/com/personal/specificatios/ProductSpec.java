package com.personal.specificatios;

import com.personal.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ProductSpec implements Specification<Product> {

    private final ProductFilter filter;

    @Override
    public Predicate toPredicate(Root<Product> product, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return null;
    }
}
