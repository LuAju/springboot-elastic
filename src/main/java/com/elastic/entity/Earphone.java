package com.elastic.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.reflect.Type;
import java.util.Date;

@Document(indexName = "earphone",type = "earphones")
public class Earphone {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    // 商标
    private String band;
    @Field(type = FieldType.Text)
    private String category;
    @Field(type = FieldType.Text)
    private String function;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Date)
    private Date date;
    @Field(type = FieldType.Integer)
    private Integer sales;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Earphone() {
    }

    public Earphone(String id, String band, String category, String function, String name, Double price, Date date, Integer sales) {
        this.id = id;
        this.band = band;
        this.category = category;
        this.function = function;
        this.name = name;
        this.price = price;
        this.date = date;
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Earphone{" +
                "id='" + id + '\'' +
                ", band='" + band + '\'' +
                ", category='" + category + '\'' +
                ", function='" + function + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", sales=" + sales +
                '}';
    }
}
