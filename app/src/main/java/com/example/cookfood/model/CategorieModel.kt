package com.example.cookfood.model

/**
 * Created by SANJEET KUMAR on 13,March,2021, sk698166@gmail.com
 */
class CategorieModel {
    var cuisine = 0
    var cuisineName: String? = null

    constructor() {}
    constructor(cuisine: Int, cuisineName: String?) {
        this.cuisine = cuisine
        this.cuisineName = cuisineName
    }
}