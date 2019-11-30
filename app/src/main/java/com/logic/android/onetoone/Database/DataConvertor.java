package com.logic.android.onetoone.Database;


import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.logic.android.onetoone.models.Questions;

import java.lang.reflect.Type;
import java.util.List;

public class DataConvertor  {

    @TypeConverter
    public String fromQuizValues(List<Questions>questions){

        if (questions==null){
            return (null);
        }
        Gson gson=new Gson();
        Type type =new TypeToken<List<Questions>>(){
        }.getType();
        String json=gson.toJson(questions,type);
        return json;
    }

    @TypeConverter
    public List<Questions> toValuesList(String values){
        if (values==null){
            return (null);
        }
        Gson gson=new Gson();
        Type type=new TypeToken<List<Questions>>(){

        }.getType();
        List<Questions> questionsList=gson.fromJson(values,type);
        return questionsList;
    }

}
