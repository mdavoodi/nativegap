    window.javascriptreceiver = function(str, callback) {
        var val = $("input[name=ReadOut]").val();
        cordova.exec(callback, function(err) {
            callback('Nothing to echo.');
        }, "JavascriptReceiver", "echo", [val]);
    };