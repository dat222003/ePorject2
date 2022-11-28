package dish;

public class Dish {
    private String dish_id,
            name,
            cat_id,
            dish_price,
            img;

    public Dish() {
    }

    public Dish(String dish_id, String name, String cat_id, String dish_price, String img) {
        this.dish_id = dish_id;
        this.name = name;
        this.cat_id = cat_id;
        this.dish_price = dish_price;
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dish_id='" + dish_id + '\'' +
                ", name='" + name + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", dish_price='" + dish_price + '\'' +
                '}';
    }
}
