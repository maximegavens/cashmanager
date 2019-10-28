package com.example.cashmanager.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\"\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00032\b\b\u0001\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\tH\'\u00a8\u0006\u000b"}, d2 = {"Lcom/example/cashmanager/model/ApiService;", "", "getUsers", "Lretrofit2/Call;", "", "Lcom/example/cashmanager/model/User;", "login", "Lcom/example/cashmanager/model/Server;", "id", "", "password", "app_debug"})
public abstract interface ApiService {
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.GET(value = "users")
    public abstract retrofit2.Call<java.util.List<com.example.cashmanager.model.User>> getUsers();
    
    @org.jetbrains.annotations.NotNull()
    @retrofit2.http.POST(value = "server/sign_in")
    @retrofit2.http.FormUrlEncoded()
    public abstract retrofit2.Call<com.example.cashmanager.model.Server> login(@org.jetbrains.annotations.NotNull()
    @retrofit2.http.Field(value = "id")
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    @retrofit2.http.Field(value = "password")
    java.lang.String password);
}