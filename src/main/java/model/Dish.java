package model;

import javafx.scene.image.ImageView;

public class Dish {
    private String dish_id,
            name,
            cat_id,
            category,
            total_price,
            dishAmount,
            dish_price,
            dishTotalPrice,
            img_name;
    private ImageView image;

    private Integer qty;

    public Dish() {
    }

    public Dish(String dish_id, String name, String cat_id, String dish_price, String img_name) {
        this.dish_id = dish_id;
        this.name = name;
        this.cat_id = cat_id;
        this.dish_price = dish_price;
        this.img_name = img_name;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getDish_price() {
        return dish_price;
    }

    public void setDish_price(String dish_price) {
        this.dish_price = dish_price;
    }

    public String getImg_name() {
        return img_name;
    }

    public void setImg_name(String img_name) {
        this.img_name = img_name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dish_id='" + dish_id + '\'' +
                ", name='" + name + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", category='" + category + '\'' +
                ", total_price='" + total_price + '\'' +
                ", dish_price='" + dish_price + '\'' +
                ", img_name='" + img_name + '\'' +
                ", qty=" + qty +
                '}';
    }
}
