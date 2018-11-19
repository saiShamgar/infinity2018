package com.example.sss.infinity.models;

public class Items
{
    private String name,description,cost;
    private int image;
    private Category category;

    public Items(String name, String description, int image,String cost,Category category)
    {
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.cost=cost;
    }

    public String getName()
    {
        return name;
    }
    public String getCost()
    {
        return cost;
    }

    public String getDescription()
    {
        return description;
    }

    public int getImage()
    {
        return image;
    }

    public int getCategoryId()
    {
        return category.getId();
    }

    public String getCategoryName()
    {
        return category.getName();
    }
}


