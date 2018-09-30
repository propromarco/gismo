package com.github.propromarco.weatherstation.jabx;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Forecast {

    private String code;
    private String message;
    private City city;
    private int cnt;
    private List<ForecastEntry> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<ForecastEntry> getList() {
        return list;
    }

    public void setList(List<ForecastEntry> list) {
        this.list = list;
    }
}
