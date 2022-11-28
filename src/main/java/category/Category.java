package category;

public class Category {
    private String cat_id,
            name,
            description,
            img;

    public Category() {
    }

    public Category(String cat_id, String name, String description, String img) {
        this.cat_id = cat_id;
        this.name = name;
        this.description = description;
        this.img = img;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cat_id='" + cat_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
