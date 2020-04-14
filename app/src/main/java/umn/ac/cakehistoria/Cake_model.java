package umn.ac.cakehistoria;

public class Cake_model {

    int imgCake;
    String category;
    int likes;
    int imgUser;
    String namaUser;
    int harga;

    /*public Cake_model(int imgCake, String category, int likes, int imgUser, String namaUser, int harga) {
        this.imgCake = imgCake;
        this.category = category;
        this.likes = likes;
        this.imgUser = imgUser;
        this.namaUser = namaUser;
        this.harga = harga;
    }*/

    public int getImgCake() {
        return imgCake;
    }

    public void setImgCake(int imgCake) {
        this.imgCake = imgCake;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getImgUser() {
        return imgUser;
    }

    public void setImgUser(int imgUser) {
        this.imgUser = imgUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
