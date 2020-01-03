package com.ssm.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/productcategoryadmin",method = {RequestMethod.GET})
public class ProductCategoryAdminController {
    @RequestMapping(value = "/productcategory")
    public String productCategoryOperation(){
        return "shop/productcategory";

    }
}
