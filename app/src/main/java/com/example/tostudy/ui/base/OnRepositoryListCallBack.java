package com.example.tostudy.ui.base;

import java.util.List;

public interface OnRepositoryListCallBack {
    <T> void onSuccessLoad(List<T> list);
    void onDeleteSuccess(String msg);
    void onSuccess(String msg);
    void onFailure(String msg);
}
