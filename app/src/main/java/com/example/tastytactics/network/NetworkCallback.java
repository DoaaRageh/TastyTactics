package com.example.tastytactics.network;

import java.util.List;

public interface NetworkCallback<T> {
    public void onSuccessResult(List<T> meals);
    public void onFailureResult(String errorMsg);
}
