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

package com.example.hangman;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.apache.cordova.*;

public class Hangman extends CordovaActivity {
	public EditText text2;
	public EditText text7;
	public EditText text10;
	public EditText text13;

	public CordovaActivity ac;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init();
		// Set by <content src="index.html" /> in config.xml
		super.loadUrl(Config.getStartUrl());
		// super.loadUrl("file:///android_asset/www/index.html")
		ac = this;
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT);
		TableLayout layout = new TableLayout(this);
		layout.setLayoutParams(tableParams);
		TextView text0 = new TextView(this);
		text0.setWidth(100);
		text0.setText("Hello World");
		layout.addView(text0);
		TextView text1 = new TextView(this);
		text1.setWidth(100);
		text1.setText("Please enter your age:");
		layout.addView(text1);
		text2 = new EditText(this);
		text2.setWidth(100);
		layout.addView(text2);
		TextView text3 = new TextView(this);
		text3.setWidth(100);
		text3.setText("Example: (november 1,1966)");
		layout.addView(text3);
		Button button4 = new Button(this);
		button4.setText("Start Timer");
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:$('#age').val(\"September 30, 1992\");");
				ac.loadUrl("javascript:lifetimer(this.form);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		layout.addView(button4);
		TableRow tableRow5 = new TableRow(this);
		tableRow5.setLayoutParams(tableParams);
		TextView text6 = new TextView(this);
		text6.setWidth(100);
		text6.setText("You are days old:");
		tableRow5.addView(text6);
		text7 = new EditText(this);
		text7.setWidth(100);
		tableRow5.addView(text7);
		layout.addView(tableRow5);
		TableRow tableRow8 = new TableRow(this);
		tableRow8.setLayoutParams(tableParams);
		TextView text9 = new TextView(this);
		text9.setWidth(100);
		text9.setText("Plus hours old:");
		tableRow8.addView(text9);
		text10 = new EditText(this);
		text10.setWidth(100);
		tableRow8.addView(text10);
		layout.addView(tableRow8);
		TableRow tableRow11 = new TableRow(this);
		tableRow11.setLayoutParams(tableParams);
		TextView text12 = new TextView(this);
		text12.setWidth(100);
		text12.setText("Plus minutes old:");
		tableRow11.addView(text12);
		text13 = new EditText(this);
		text13.setWidth(100);
		tableRow11.addView(text13);
		layout.addView(tableRow11);
		setContentView(layout);
	}
}
