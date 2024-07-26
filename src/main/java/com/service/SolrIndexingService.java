package com.service;

import com.model.Category;
import com.model.Price;
import com.model.Product;
import com.model.Stock;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


public class SolrIndexingService {


    private SolrClient solrClient;

    public void indexProduct(Product product) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", product.getId());
        document.addField("name", product.getName());
        document.addField("active", product.getActive());
        document.addField("photo_name", product.getPhotoName());
        document.addField("categories", product.getCategories().stream().map(Category::getName).toArray());
        document.addField("prices", product.getPrices().stream().map(Price::getPrice).toArray());
        document.addField("stocks", product.getStocks().stream().map(Stock::getQuantity).toArray());
        try {
            solrClient.add("products", document);
            solrClient.commit("products");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductFromSolr(Long productId) {
        try {
            solrClient.deleteById("products", productId.toString());
            solrClient.commit("products");
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }
}
