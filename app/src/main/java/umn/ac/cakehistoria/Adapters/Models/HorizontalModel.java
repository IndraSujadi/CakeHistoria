package umn.ac.cakehistoria.Adapters.Models;

public class HorizontalModel {

    String  namaUser, category;
    int imgPath_cake, imgPath_user;
    int likes;
    double harga;

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getImgPath_cake() {
        return imgPath_cake;
    }

    public void setImgPath_cake(int imgPath_cake) {
        this.imgPath_cake = imgPath_cake;
    }

    public int getImgPath_user() {
        return imgPath_user;
    }

    public void setImgPath_user(int imgPath_user) {
        this.imgPath_user = imgPath_user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
