package com.demo.panguso.mvp_mode.mvp.bean;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/11/4.
 */

public class GirData {
    private boolean isError;
    private List<PhotoGirl> result;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public List<PhotoGirl> getResult() {
        return result;
    }

    public void setResult(List<PhotoGirl> result) {
        this.result = result;
    }
}
