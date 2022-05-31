package com.hanul.alcoholic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cocktail {
    private String strAlcoholic;
    private String strCategory;
    private String strDrink;
    private String strDrinkThumb;
    private String strGlass;
    private String strIngredient1;
    private String strIngredient10;
    private String strIngredient11;
    private String strIngredient12;
    private String strIngredient13;
    private String strIngredient14;
    private String strIngredient15;
    private String strIngredient2;
    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;
    private String strIngredient6;
    private String strIngredient7;
    private String strIngredient8;
    private String strIngredient9;
    private String strInstructions;
    private String strMeasure1;
    private String strMeasure10;
    private String strMeasure11;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure14;
    private String strMeasure15;
    private String strMeasure2;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure9;
    private String strTags;

    public String getStrDrink() {
        return strDrink;
    }

    public void setStrDrink(String strDrink) {
        this.strDrink = strDrink;
    }

    public String getStrDrinkThumb() {
        return strDrinkThumb;
    }

    public void setStrDrinkThumb(String strDrinkThumb) {
        this.strDrinkThumb = strDrinkThumb;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public void setStrIngredient1(String strIngredient1) {
        this.strIngredient1 = strIngredient1;
    }

    Cocktail(){} // 제일 중요


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("strAlcoholic", strAlcoholic);
        result.put("strCategory", strCategory);
        result.put("strDrink", strDrink);
        result.put("strDrinkThumb", strDrinkThumb);
        result.put("strGlass", strGlass);
        result.put("strIngredient1", strIngredient1);
        result.put("strIngredient10", strIngredient10);
        result.put("strIngredient11", strIngredient11);
        result.put("strIngredient12", strIngredient12);
        result.put("strIngredient13", strIngredient13);
        result.put("strIngredient14", strIngredient14);
        result.put("strIngredient15", strIngredient15);
        result.put("strIngredient2", strIngredient2);
        result.put("strIngredient3", strIngredient3);
        result.put("strIngredient4", strIngredient4);
        result.put("strIngredient5", strIngredient5);
        result.put("strIngredient6", strIngredient6);
        result.put("strIngredient7", strIngredient7);
        result.put("strIngredient8", strIngredient8);
        result.put("strIngredient9", strIngredient9);
        result.put("strInstructions", strInstructions);
        result.put("strMeasure1", strMeasure1);
        result.put("strMeasure10", strMeasure10);
        result.put("strMeasure11", strMeasure11);
        result.put("strMeasure12", strMeasure12);
        result.put("strMeasure13", strMeasure13);
        result.put("strMeasure14", strMeasure14);
        result.put("strMeasure15", strMeasure15);
        result.put("strMeasure2", strMeasure2);
        result.put("strMeasure3", strMeasure3);
        result.put("strMeasure4", strMeasure4);
        result.put("strMeasure5", strMeasure5);
        result.put("strMeasure6", strMeasure6);
        result.put("strMeasure7", strMeasure7);
        result.put("strMeasure8", strMeasure8);
        result.put("strMeasure9", strMeasure9);
        result.put("strTags", strTags);
        return result;
    }
}
