package com.service;

import com.model.Category;
import com.model.Price;
import com.model.Product;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolrIndexingService {

    @Autowired
    private SolrClient solrClient;

    public void indexProduct(Product product) throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", product.getId());
        document.addField("name", product.getName());
        document.addField("active", product.getActive());
        document.addField("photo_name", product.getPhotoName());

        BigDecimal currentPrice = product.getCurrentPriceValue();
        if (currentPrice != null) {
            document.addField("current_price", currentPrice.doubleValue());
        }

        document.addField("stock", product.getStock());

        String categories = product.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.joining(","));
        document.addField("categories", categories);

        // Add prices if they are not null or empty
        if (product.getPrices() != null && !product.getPrices().isEmpty()) {
            List<Double> prices = product.getPrices().stream()
                    .map(price -> price.getPrice().doubleValue())
                    .collect(Collectors.toList());
            document.addField("prices", prices);
        }

        solrClient.add("products", document);
        solrClient.commit("products");
    }

    public void deleteProduct(Long productId) throws IOException, SolrServerException {
        solrClient.deleteById("products",productId.toString());
        solrClient.commit("products");
    }
}
