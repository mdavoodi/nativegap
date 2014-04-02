package org.apache.cordova.plugin;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.simplecalc.HelloWorld;

/**
 * This class echoes a string called from JavaScript.
 */
public class JavascriptReceiver extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("ReadOut")) {
            final String message = args.getString(0);
        	final HelloWorld view = (HelloWorld) this.cordova.getActivity();
        	view.runOnUiThread(new Runnable() {

				@Override
				public void run() {
	            	view.text1.setText(message);					
				}

        	});
            return true;
        }
        return false;
    }
}
