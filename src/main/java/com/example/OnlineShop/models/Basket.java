package com.example.OnlineShop.models;

import javax.persistence.*;

@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private long idUser;
    @Column
    private String idItems;
    @Column
    private long itemCount;
    @Column
    private long price;

    public Basket(){}
    public Basket(long idUser) {
        this.idUser = idUser;
        this.idItems = "";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdItems() {
        return idItems;
    }
    public void addIdItems(long idItem){
        this.idItems += idItem + ",";
    }
    public void subIdItems(long idItem){this.idItems = this.idItems.replaceFirst(idItem+",", "");}


    public void setIdItems(String idItems) {
        this.idItems = idItems;
    }
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        itemCount = itemCount;
    }
    public void addItemCount(){this.itemCount++;}
    public void subItemCount(){this.itemCount--;}

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
    public void addPrice(long price){this.price += price;}
    public void subPrice(long price){this.price -= price;}
}
