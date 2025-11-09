package com.personal.service;

import com.personal.model.Product;
import com.personal.repository.ProductRepository;
import com.personal.specificatios.ProductFilter;
import com.personal.specificatios.ProductSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> findProductOperations(Map<String, String> filter) {

        ProductFilter productFilter = checkProductFilter(filter);

        ProductSpec productSpec = new ProductSpec(productFilter);

        return productRepository.findAll(productSpec);
    }

    protected ProductFilter checkProductFilter(Map<String, String> filter) {

        ProductFilter productFilter = new ProductFilter();
        //TO-DO: validate filter params

        if (filter.containsKey("name")) {
            String name = filter.get("name");
            if (StringUtils.hasText(name)) {
                productFilter.setName(name);
            }
        }

        if (filter.containsKey("category")) {
            String category = filter.get("category");
            if (StringUtils.hasText(category)) {
                productFilter.setCategory(category);
            }
        }

        if (filter.containsKey("size")) {
            String size = filter.get("size");
            if (StringUtils.hasText(size)) {
                productFilter.setSize(size);
            }
        }

        return productFilter;

    }
}
