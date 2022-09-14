package com.benben.carmanager.viewmodel;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

public class BaseViewModel extends ViewModel{
    protected Gson gson = new Gson();
}
