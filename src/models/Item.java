package models;

public class Item {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private int categoryId;
    private String categoryName;

    public Item(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(int id, String name, double price, int quantity, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void decreaseQuantity(int q) {
        quantity -= q;
    }

    public void increaseQuantity(int q) {
        quantity += q;
    }

    public static class ItemBuilder {
        private int id;
        private String name;
        private double price;
        private int quantity;
        private int categoryId;
        private String categoryName;

        public ItemBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ItemBuilder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public ItemBuilder setCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public ItemBuilder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Item build() {
            return new Item(id, name, price, quantity, categoryId, categoryName);
        }
    }
}