package com.xfkj.utils;

public class PriceUtil {
    public static Integer[] priceformat(String type_id){
        Integer[] doubles = new Integer[2];
        if (type_id!=null&&!type_id.equals("")&&!type_id.equals("0")){
            if (!type_id.contains("-")){
                doubles[0]=0;
                doubles[1]=1000;
            }else{
                String[] strings = type_id.split("-");
                for (int i = 0;i<strings.length;i++){
                    doubles[i]=Integer.valueOf(strings[i]);
                }
            }
        }
        return doubles;

    }
}
