package com.rdzero.nuproject.db;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ricardohnn on 2015-12-26.
 */
@com.raizlabs.android.dbflow.annotation.TypeConverter
public class DateConverter extends TypeConverter<String,Date> {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String getDBValue(Date model) {
        return model == null ? null : df.format(model);
    }

    @Override
    public Date getModelValue(String date) {
        if(date == "" || date.equals(null)){
            return null;
        }
        try{
            return df.parse(date);
        } catch (ParseException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
