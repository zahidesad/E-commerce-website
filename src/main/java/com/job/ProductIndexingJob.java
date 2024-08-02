package com.job;

import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductIndexingJob {

    @Autowired
    private ProductService productService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void scheduleProductIndexing() {
        productService.indexAllProductsToSolr();
    }
}
