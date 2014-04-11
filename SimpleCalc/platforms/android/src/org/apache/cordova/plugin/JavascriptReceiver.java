package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import com.example.simplecalc.HelloWorld;

public class JavascriptReceiver extends CordovaPlugin {
	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if (action.equals("time1")) {
			final String message = args.getString(0);
			final HelloWorld view = (HelloWorld) this.cordova.getActivity();
			view.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					view.text7.setText(message);
				}
			});
			return true;
		}
		if (action.equals("time2")) {
			final String message = args.getString(0);
			final HelloWorld view = (HelloWorld) this.cordova.getActivity();
			view.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					view.text10.setText(message);
				}
			});
			return true;
		}
		if (action.equals("time3")) {
			final String message = args.getString(0);
			final HelloWorld view = (HelloWorld) this.cordova.getActivity();
			view.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					view.text13.setText(message);
				}
			});
			return true;
		}
		return false;
	}
}