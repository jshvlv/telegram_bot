package com.app;

import java.util.Date;
import java.util.List;

public class ParserData {
    private String channel;
    private List<String> programms;
    private Date data;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<String> getProgramms() {
        return programms;
    }

    public void setProgramms(List<String> programms) {
        this.programms = programms;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
