package com.harlov.shopslist;


public class Shop {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String website;
    private int latitude;
    private int longitude;
    private int totalItems;

    public Shop(int id, String name, String address, String phone, String website,
                int latitude, int longitude){
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setPhone(phone);
        this.setWebsite(website);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id;}

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public int getLatitude() { return latitude; }
    public void setLatitude(int latitude) { this.latitude = latitude; }

    public int getLongitude() { return longitude; }
    public void setLongitude(int longitude) { this.longitude = longitude; }

    public int getTotalItems() { return totalItems; }

    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
}
