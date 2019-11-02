package com.example.studentsportal;

public class ModelClass {
    private String donated_by;
    private String productname;
    private String price;
    private String years_used;
    private String description;
    private String image;
    private String borrowed_by;
    private boolean isImageSaved = false;

    public String getDonated_by() {
        return donated_by;
    }

    public String getBorrowed_by() {
        return borrowed_by;
    }

    public void setBorrowed_by(String borrowed_by) {
        this.borrowed_by = borrowed_by;
    }

    public void setDonated_by(String donated_by) {
        this.donated_by = donated_by;
    }
    public ModelClass(String donated_by, String productname, String price, String years_used, String description, String image, String borrowed_by) {
        this.donated_by = donated_by;
        this.productname = productname;
        this.price = price;
        this.years_used = years_used;
        this.description = description;
        this.image = image;
        this.borrowed_by = borrowed_by;
        this.isImageSaved = isImageSaved;
    }
    public ModelClass(String donated_by, String productname, String price, String years_used, String description, String image, String borrowed_by, Boolean isImageSaved) {
        this.donated_by = donated_by;
        this.productname = productname;
        this.price = price;
        this.years_used = years_used;
        this.description = description;
        this.image = image;
        this.borrowed_by = borrowed_by;
        this.isImageSaved = isImageSaved;
    }
    public boolean isImageSaved() {
        return isImageSaved;
    }

    public void setImageSaved(boolean imageSaved) {
        isImageSaved = imageSaved;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getYears_used() {
        return years_used;
    }

    public void setYears_used(String years_used) {
        this.years_used = years_used;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
