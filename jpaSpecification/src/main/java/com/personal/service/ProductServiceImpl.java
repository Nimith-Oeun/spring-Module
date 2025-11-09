package com.personal.service;

import com.personal.model.Product;
import com.personal.paginations.PageUtils;
import com.personal.repository.ProductRepository;
import com.personal.specificatios.ProductFilter;
import com.personal.specificatios.ProductSpec;
import com.personal.specificatios.utils.ProductUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Without Pagination
     *
     * @Override public List<Product> findProductOperations(Map<String, String> filter) {
     * <p>
     * ProductFilter productFilter = checkProductFilter(filter);
     * <p>
     * ProductSpec productSpec = new ProductSpec(productFilter);
     * <p>
     * return productRepository.findAll(productSpec);
     * }
     */

    @Override
    public Page<Product> findProductOperations(Map<String, String> filter) {


        ProductFilter productFilter = checkProductFilter(filter);

        ProductSpec productSpec = new ProductSpec(productFilter);

        /**
         * Pagination
         */
        int pageNumber = PageUtils.DEFAULT_PAGE_NUMBER;
        int pageSize = PageUtils.DEFAULT_PAGE_SIZE;

        if (filter.containsKey(PageUtils.PAGE_NUMBER)) {
            pageNumber = parseOrDefault(
                    filter.get(PageUtils.PAGE_NUMBER), PageUtils.DEFAULT_PAGE_NUMBER);
        }

        if (filter.containsKey(PageUtils.PAGE_SIZE)) {
            pageSize = parseOrDefault(filter.get(PageUtils.PAGE_SIZE), PageUtils.DEFAULT_PAGE_SIZE);
        }

        Pageable pageable = PageUtils.getPageable(pageNumber, pageSize);


        return productRepository.findAll(productSpec, pageable);
    }

    /**
     * Helper method
     */

    //checks and parses filter values
    private ProductFilter checkProductFilter(Map<String, String> filter) {

        ProductFilter productFilter = new ProductFilter();


        if (filter.containsKey(ProductUtils.KEY_NAME)) {
            String name = filter.get(ProductUtils.KEY_NAME);
            if (StringUtils.hasText(name)) {
                productFilter.setName(name);
            }
        }

        if (filter.containsKey(ProductUtils.KEY_CATEGORY)) {
            String category = filter.get(ProductUtils.KEY_CATEGORY);
            if (StringUtils.hasText(category)) {
                productFilter.setCategory(category);
            }
        }

        if (filter.containsKey(ProductUtils.KEY_SIZE)) {
            String size = filter.get(ProductUtils.KEY_SIZE);
            if (StringUtils.hasText(size)) {
                productFilter.setSize(size);
            }
        }

        return productFilter;

    }

    //checks and parses pagination values
    private int parseOrDefault(String value, int defaultValue) {
        try {
            return StringUtils.hasText(value) ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException ex) {
            log.warn("Invalid pagination value '{}', using default {}", value, defaultValue);
            return defaultValue;
        }
    }
}
