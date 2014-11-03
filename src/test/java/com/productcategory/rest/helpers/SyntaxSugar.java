package com.productcategory.rest.helpers;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by steven on 2014/11/03.
 */
public class SyntaxSugar {
    public static final int PRODUCT_ID = 100;
    public static final String PRODUCT_NAME = "A product";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.TEN;
    public static final Timestamp PRODUCT_LAST_UPDATE = new Timestamp(System.currentTimeMillis());
    public static final int PRODUCT_CATEGORY_ID = 10;
    public static final String PRODUCT_DESCRIPTION = "A product description";
}
