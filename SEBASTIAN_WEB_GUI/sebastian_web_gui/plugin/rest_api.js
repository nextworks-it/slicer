
function getFromURL(resourceUrl, id, callback, param, trailFunction, objId) {
	//showProgress();
    $.ajax({
        url: resourceUrl,
        type: 'GET',
        dataCharset: 'utf-8',
        cache: false,
        success: function()  {
            if (callback) {
                callback(id, param);
            } 
			//hideProgress();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest, textStatus, errorThrown);
			//hideProgress();
        }
    });
}

function getJsonFromURL(resourceUrl, elemId, callback, param, trailFunction, objId) {
	//showProgress();
    $.ajax({
        url: resourceUrl,
        type: 'GET',
        dataType: 'json',
        dataCharset: 'utf-8',
        cache: false,
        success: function(data)  {
            if (trailFunction) {
                var num = parseInt(data);
                trailFunction(objId, num);
            }
			if (callback) {
                callback(elemId, data, param);
            } 
		//	hideProgress();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest, textStatus, errorThrown);
		//	hideProgress();
        }
    });
}

function postJsonToURL(resourceUrl, jsonData, resId, okMsg, errMsg, callback) {
	//showProgress(resId);
	$.ajax({
		url : resourceUrl,
		type : 'POST',
		contentType : 'application/json',
		data : jsonData,
		success : function(data) {
			if (callback) {
				callback(true, resId, okMsg);
				stopRefreshing=true;
			}
			//hideProgress();
			
			//Refresh doesn't work properly here (VNFD zip), add refresh inside ajaxStop after each post request
			refresh(false);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
            if (callback) {
                callback(false, resId, errMsg + '<br>' + errorThrown + ': ' + XMLHttpRequest.responseText);
            }
			stopRefreshing=true;
			console.log(XMLHttpRequest, textStatus, errorThrown);
			//hideProgress();
		}
	});
}

function postJsonToURLWithResponseData(resourceUrl, jsonData, elemId, resId, callback) {
	//showProgress(resId);
	$.ajax({
		url : resourceUrl,
		type : 'POST',
		contentType : 'application/json',
		data : jsonData,
		success : function(data) {
            
            console.log(data);
            
            if (!data) {
                if (callback && callback == showResultMessage) {
                    showResultMessage(true, resId, "Operation successful");
                }
            } else {
                if (callback) {
                    if (/*data.hasOwnProperty('responseCode') && */callback == showResultMessage) {
                        //if (data['responseCode'] == 'OK') {
                            showResultMessage(true, resId, "Operation successful");
                        //}
                    }
                    console.log(callback);
                    callback(elemId, data, resId);
                }
            }
			//hideProgress();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
            
			showResultMessage(false, resId, 'Operation failed' + '<br>' + errorThrown + ': ' + XMLHttpRequest.responseText);

			stopRefreshing=true;
			console.log(XMLHttpRequest, textStatus, errorThrown);
			//hideProgress();
		}
	});
}

function postToURL(resourceUrl, resId, okMsg, errMsg, trailFunction) {
	//showProgress();
    $.ajax({
        url: resourceUrl,
        type: 'POST',
        dataCharset: 'utf-8',
        cache: false,
        success: function()  {
			if (trailFunction) {
				trailFunction(true, resId, okMsg);
			}
			//hideProgress();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest, textStatus, errorThrown);
			if (trailFunction) {
				trailFunction(false, elemId, errMsg + '<br>' + errorThrown + ': ' + XMLHttpRequest.responseText);
			}
			stopRefreshing = true;
			//hideProgress();
        }
    });
}

function putToURL(resourceUrl, elemId, callback, params, trailFunction) {
	//showProgress();
    $.ajax({
        url: resourceUrl,
        type: 'PUT',
        dataCharset: 'utf-8',
        cache: false,
        success: function()  {
            if (callback) {
                callback(elemId, params);
            }
			if (trailFunction) {
				trailFunction(true, elemId, "Operation successful");
			}
			//hideProgress();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest, textStatus, errorThrown);
			if (trailFunction) {
				trailFunction(false, elemId, 'Operation failed' + '<br>' + errorThrown + ': ' + XMLHttpRequest.responseText);
			}
			stopRefreshing = true;
			//hideProgress();
        }
    });
}


function putJsonToURL(resourceUrl, jsonData, resId, okMsg, errMsg, callback) {
	//showProgress();
    $.ajax({
		url : resourceUrl,
		type : 'PUT',
		contentType : 'application/json',
		data : jsonData,
		success : function(data) {
			if (callback)
				callback(true, resId, okMsg);
			//hideProgress();
            //refresh(false);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
            if (callback) {
                callback(false, resId, errMsg + '<br>' + errorThrown + ': ' + XMLHttpRequest.responseText);
            }
			console.log(XMLHttpRequest, textStatus, errorThrown);
			//hideProgress();
		}
	});
}

function deleteRequestToURL(resourceUrl, resId, okMsg, errMsg, callback) {
	//showProgress(resId);
	//console.log(resourceUrl);
	$.ajax({
		url : resourceUrl,
		type : 'DELETE',
		cache : false,
		success : function(data) {
			/*if (callback)
				callback(true, resId, okMsg);*/
			//hideProgress();
            console.log("DEBUG");
			refresh(false);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
            if (callback) {
                callback(false, resId, errMsg + '<br>' + errorThrown + ': ' + XMLHttpRequest.responseText);
            }
			console.log(XMLHttpRequest, textStatus, errorThrown);
			stopRefreshing = true;
			//hideProgress();
		}
	});
}
