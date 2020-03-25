package com.elastic.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "dangdang2",type="book")
public class Book {
    @Id
    private String id;
    @Field(type= FieldType.Text,analyzer = "ik_max_word")
    private String name;
    @Field(type= FieldType.Double)
    private Double price;
    @Field(type= FieldType.Date)
    private Date pubDate;
    @Field(type= FieldType.Text,analyzer = "ik_max_word")
    private String content;
    @Field(type= FieldType.Keyword)
    private String author;

    public Book() {
    }

    public Book(String id, String name, Double price, Date pubDate, String content, String author) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.pubDate = pubDate;
        this.content = content;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", pubDate=" + pubDate +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
