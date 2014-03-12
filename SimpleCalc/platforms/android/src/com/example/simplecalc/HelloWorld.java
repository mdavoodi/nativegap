/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package com.example.simplecalc;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.apache.cordova.*;

public class HelloWorld extends CordovaActivity 
{
	public CordovaActivity ac;
	public EditText box;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.init();
       
        // Set by <content src="index.html" /> in config.xml
        //super.loadUrl(Config.getStartUrl());
        super.loadUrl("file:///android_asset/www/index.html");
        ac = this;
        LinearLayout lin = new LinearLayout(this);

        
        box = new EditText(this);
        box.setWidth(100);
        lin.addView(box);

        
        Button myButton = new Button(this);
        //myButton.setLayoutParams(params);
        //View container = (View)findViewById(R.drawable.icon);
        myButton.setText("1");
        RelativeLayout.LayoutParams myParams = new RelativeLayout.LayoutParams(50, 50);
        myParams.leftMargin = 100;
        lin.addView(myButton, myParams);
        setContentView(lin);

        myButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
		        ac.loadUrl("javascript:NumPressed(1);");
		        ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
    }
    
}

