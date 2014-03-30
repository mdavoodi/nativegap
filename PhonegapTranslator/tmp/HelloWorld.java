
package com.example.simplecalc;


import android.os.*;
import android.view.View;
import android.widget.*;
import org.apache.cordova.*;

public class HelloWorld extends CordovaActivity 
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.init();
        super.loadUrl("file:///android_asset/www/index.html");

    }
    
}

