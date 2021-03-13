package com.example.cookfood.model

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */
class RecipeModel {
    var name: String? = null
    var price: String? = null
    var img = 0

    constructor() {}
    constructor(name: String?, img: Int, price: String?) {
        this.name = name
        this.img = img
        this.price = price
    }
}