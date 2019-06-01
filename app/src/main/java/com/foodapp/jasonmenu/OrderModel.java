package com.foodapp.jasonmenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderModel {


        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("price")
        @Expose
        private String price;
    @SerializedName("description")
    @Expose
    private String description;
        @SerializedName("amount")
        @Expose
        private Integer amount;

        public void setParams(Integer id, String name, String weight, String time, String price, String description, Integer amount) {
            this.id = id;
            this.name = name;
            this.weight = weight;
            this.time = time;
            this.price = price;
            this.description = description;
            this.amount = amount;
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

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String price) {
            this.description = description;
        }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }



}
