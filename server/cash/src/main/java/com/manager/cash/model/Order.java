package com.manager.cash.model;

import java.util.List;

public class Order {
    private int id;
    private List<Article> articles;
    private double total;

    public Order(int id, List<Article> articles) {
        this.id = id;
        this.articles = articles;
        this.total = 0;
        for (int i = 0; i < this.articles.size(); i++) {
            this.total = this.total + this.articles.get(i).getPrice();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addArticle(Article article) {
        articles.add(article);
        this.total = this.total + article.getPrice();
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        this.total = this.total - article.getPrice();
    }
}
