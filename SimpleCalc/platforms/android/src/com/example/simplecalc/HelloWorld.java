package com.example.simplecalc;

import android.os.*;
import android.view.View;
import android.widget.*;
import org.apache.cordova.*;

public class HelloWorld extends CordovaActivity {
	public TextView text1;

	public CordovaActivity ac;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init();
		super.loadUrl("file:///android_asset/www/index.html");

		ac = this;
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(
				TableLayout.LayoutParams.WRAP_CONTENT,
				TableLayout.LayoutParams.WRAP_CONTENT);
		TableLayout layout = new TableLayout(this);
		layout.setLayoutParams(tableParams);
		TableRow tableRow0 = new TableRow(this);
		tableRow0.setLayoutParams(tableParams);
		text1 = new TextView(this);
		text1.setWidth(100);
		tableRow0.addView(text1);
		Button button2 = new Button(this);
		button2.setText("  C  ");
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Clear();");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow0.addView(button2);
		Button button3 = new Button(this);
		button3.setText("  CE ");
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:ClearEntry();");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow0.addView(button3);
		layout.addView(tableRow0);
		TableRow tableRow4 = new TableRow(this);
		tableRow4.setLayoutParams(tableParams);
		Button button5 = new Button(this);
		button5.setText("  7  ");
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(7);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow4.addView(button5);
		Button button6 = new Button(this);
		button6.setText("  8  ");
		button6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(8);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow4.addView(button6);
		Button button7 = new Button(this);
		button7.setText("  9  ");
		button7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(9);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow4.addView(button7);
		Button button8 = new Button(this);
		button8.setText(" +/- ");
		button8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Neg();");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow4.addView(button8);
		Button button9 = new Button(this);
		button9.setText("  % ");
		button9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Percent();");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow4.addView(button9);
		layout.addView(tableRow4);
		TableRow tableRow10 = new TableRow(this);
		tableRow10.setLayoutParams(tableParams);
		Button button11 = new Button(this);
		button11.setText("  4  ");
		button11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(4);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow10.addView(button11);
		Button button12 = new Button(this);
		button12.setText("  5  ");
		button12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(5);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow10.addView(button12);
		Button button13 = new Button(this);
		button13.setText("  6  ");
		button13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(6);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow10.addView(button13);
		Button button14 = new Button(this);
		button14.setText("  +  ");
		button14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Operation('+');");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow10.addView(button14);
		Button button15 = new Button(this);
		button15.setText("   -   ");
		button15.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Operation('-');");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow10.addView(button15);
		layout.addView(tableRow10);
		TableRow tableRow16 = new TableRow(this);
		tableRow16.setLayoutParams(tableParams);
		Button button17 = new Button(this);
		button17.setText("  1  ");
		button17.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(1);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow16.addView(button17);
		Button button18 = new Button(this);
		button18.setText("  2  ");
		button18.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(2);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow16.addView(button18);
		Button button19 = new Button(this);
		button19.setText("  3  ");
		button19.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(3);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow16.addView(button19);
		Button button20 = new Button(this);
		button20.setText("  *  ");
		button20.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Operation('*');");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow16.addView(button20);
		Button button21 = new Button(this);
		button21.setText("   /   ");
		button21.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Operation('/');");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow16.addView(button21);
		layout.addView(tableRow16);
		TableRow tableRow22 = new TableRow(this);
		tableRow22.setLayoutParams(tableParams);
		Button button23 = new Button(this);
		button23.setText("  0  ");
		button23.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:NumPressed(0);");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow22.addView(button23);
		Button button24 = new Button(this);
		button24.setText("   .  ");
		button24.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Decimal();");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow22.addView(button24);
		Button button25 = new Button(this);
		button25.setText("  =  ");
		button25.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ac.loadUrl("javascript:Operation('=');");
				ac.loadUrl("javascript:window.javascriptreceiver(\"echome\", function(echoValue) {alert(echoValue == \"echome\");});");
			}
		});
		tableRow22.addView(button25);
		layout.addView(tableRow22);
		setContentView(layout);
	}

}
