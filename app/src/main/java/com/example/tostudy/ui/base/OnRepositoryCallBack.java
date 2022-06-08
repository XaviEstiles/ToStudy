package com.example.tostudy.ui.base;

import com.example.tostudy.data.model.User;

public interface OnRepositoryCallBack {
    void onSuccess(String msg, User user);
    void onFailure(String msg);
}
