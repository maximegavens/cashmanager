package com.manager.cash.service;

import com.manager.cash.model.Article;
import com.manager.cash.model.Order;

import java.util.ArrayList;
import java.util.List;

public class RegisterService {
    private List<Order> orderList;

    private RegisterService() {
        List<Article> list1 = new ArrayList<>();
        list1.add(new Article("poire", 2));
        list1.add(new Article("pomme", 3));
        orderList.add(new Order(4, list1));
    }

    private static RegisterService INSTANCE = null;

    public static synchronized RegisterService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RegisterService();
        }
        return INSTANCE;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public Order getOrderById(int id) {
        return this.orderList.get(id);
    }

    public Order addArticleOnOrderById(int id, Article article) {
        this.orderList.get(id).addArticle(article);
        return this.orderList.get(id);
    }

    public Order removeArticle(int id, Article article) {
        this.orderList.get(id).removeArticle(article);
        return this.orderList.get(id);
    }
}
