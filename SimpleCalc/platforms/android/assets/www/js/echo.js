window.javascriptreceiver = function(str, callback) {
    var val = $("input[name=time1]").val();
    cordova.exec(callback, function(err) {
    callback('Nothing to echo.');}, "JavascriptReceiver", "time1", [val]);
    var val = $("input[name=time2]").val();
    cordova.exec(callback, function(err) {
    callback('Nothing to echo.');}, "JavascriptReceiver", "time2", [val]);
    var val = $("input[name=time3]").val();
    cordova.exec(callback, function(err) {
    callback('Nothing to echo.');}, "JavascriptReceiver", "time3", [val]);
};