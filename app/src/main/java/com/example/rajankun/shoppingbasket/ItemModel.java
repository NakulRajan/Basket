package com.example.rajankun.shoppingbasket;

/**
 * This represents an Item in the List.
 */
public class ItemModel {
    private String uniqueId;
    private String itemName;

    //default constructor is needed by Firebase
    public ItemModel(){

    }

    public ItemModel(String uniqueId, String itemName){
        this.uniqueId = uniqueId;
        this.itemName = itemName;
    }

    public String getItemName(){
        return this.itemName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

}
