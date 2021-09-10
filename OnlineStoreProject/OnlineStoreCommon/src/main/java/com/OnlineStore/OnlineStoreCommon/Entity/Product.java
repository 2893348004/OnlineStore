package com.OnlineStore.OnlineStoreCommon.Entity;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="products")
public class Product {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column(unique = true, length = 256, nullable = false)
private String name;

@Column(unique = true, length = 256, nullable = false)
private String alias;

@Column(length = 500, nullable = false, name = "short_description")
private String shortDescription;

@Column( length = 4000, nullable = false, name = "full_description")
private String fullDescription;

@Column(name="created_time")
private Date createTime;

@Column(name = "updateTime")
private Date updateTime;


private boolean enabled;

@Column(name="in_stock")
private boolean inStock;

@ManyToOne
@JoinColumn(name="category_id")
private Category category;

private float cost;
private float price;

@Column(name="discount_percent")
private float discountPercent;

private float length;
private float width;
private float height;
private float weight;

@Column(name = "main_image", nullable = true)
private String mainImage;

@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
private Set<ProductImage> images = new HashSet<>();

    public Product(Integer productId) {
        this.id = productId;
    }



    public Product() {

    }


    public void addExtraImage(String imageName){
    this.images.add(new ProductImage(imageName, this ));
}

public void clearExtraImages(){
        this.images.clear();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) {
        this.images = images;
    }



    public String imagePath = getImagePath();


    @Transient
    public String getImagePath(){
        if(id == null || mainImage == null) return "/images/folder-my-pictures-icon.png";
    return  "/product-images/" + this.id + "/" + this.mainImage;
    }
}
