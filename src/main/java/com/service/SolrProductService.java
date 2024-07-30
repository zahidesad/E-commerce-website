package com.service;

import com.model.Product;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SolrProductService {

    @Autowired
    private SolrClient solrClient;

    public List<Product> getAllProductsFromSolr() throws IOException, SolrServerException {
        List<Product> products = new ArrayList<>();
        SolrQuery query = new SolrQuery("*:*");
        QueryResponse response = solrClient.query("products", query);
        SolrDocumentList documents = response.getResults();

        for (SolrDocument document : documents) {
            Product product = new Product();
            product.setId(Long.parseLong((String) document.getFieldValue("id")));
            product.setName((String) document.getFieldValue("name"));

            Object activeField = document.getFieldValue("active");
            product.setActive(((Boolean) activeField).toString());

            product.setPhotoName((String) document.getFieldValue("photo_name"));

            Object stockField = document.getFieldValue("stock");
            product.setStock(((Long) stockField).intValue());

            if (document.getFieldValue("current_price") != null) {
                product.setCurrentPrice(new BigDecimal(document.getFieldValue("current_price").toString()));
            }
            products.add(product);
        }
        return products;
    }

    public List<Product> searchProductsInSolr(String queryString) throws IOException, SolrServerException {
        List<Product> products = new ArrayList<>();
        SolrQuery query = new SolrQuery();
        query.setQuery("name:" + queryString + " OR categories:" + queryString);
        QueryResponse response = solrClient.query("products", query);
        SolrDocumentList documents = response.getResults();

        for (SolrDocument document : documents) {
            Product product = new Product();
            product.setId(Long.parseLong((String) document.getFieldValue("id")));
            product.setName((String) document.getFieldValue("name"));

            Object activeField = document.getFieldValue("active");
            product.setActive(((Boolean) activeField).toString());

            product.setPhotoName((String) document.getFieldValue("photo_name"));

            Object stockField = document.getFieldValue("stock");
            product.setStock(((Long) stockField).intValue());

            if (document.getFieldValue("current_price") != null) {
                product.setCurrentPrice(new BigDecimal(document.getFieldValue("current_price").toString()));
            }
            products.add(product);
        }
        return products;
    }
}
