package com.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private String active;

    @Lob
    @Column(name = "photo_data", columnDefinition = "MEDIUMBLOB")
    private byte[] photoData;

    @Column(name = "photo_name")
    private String photoName;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> prices;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @ManyToMany()
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Price> getCurrentPrices() {
        Date currentDate = new Date();
        return prices.stream()
                .filter(price -> !price.getStartDate().after(currentDate) && (price.getEndDate() == null || !price.getEndDate().before(currentDate)))
                .collect(Collectors.toList());
    }

    public BigDecimal getCurrentPriceValue() {
        List<Price> currentPrices = getCurrentPrices();
        if (!currentPrices.isEmpty()) {
            return currentPrices.get(0).getPrice();
        }
        return null;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public int getStock() {
        return stocks.stream().mapToInt(Stock::getQuantity).sum();
    }

    public void setStock(int quantity) {
        if (stocks.isEmpty()) {
            Stock stock = new Stock();
            stock.setQuantity(quantity);
            stock.setProduct(this);
            stocks.add(stock);
        } else {
            Stock stock = stocks.get(0);
            stock.setQuantity(quantity);
        }
    }

}
