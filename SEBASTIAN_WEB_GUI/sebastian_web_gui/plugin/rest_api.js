/*
* Copyright 2018 Nextworks s.r.l.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

function checkUser(cname) {
    var cvalue = getCookie(cname);
    
    if (cvalue == '') {
        return false;
    }
    
    return true;
}

function loginToURL(resourceUrl, username, password, callback, failedCallback) {
    
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": resourceUrl,
        "method": "POST",
        "headers": {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        xhrFields: {
            withCredentials: true
        },
        "data": {
            "username": username,
            "password": password
        }
    };
      
    $.ajax(settings).done(function (response) {
        console.log(response);
        callback();
    }).fail(failedCallback ? failedCallback : function() {});
    
}

function getJsonFromURLWithAuth(resourceUrl, callback, params, loginFlag) {
    
    if (!loginFlag && !checkUser('username')) {
        redirectToError('401');
    } else {
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": resourceUrl,
            "method": "GET",
            xhrFields: {
                withCredentials: true
            }
        };
    
        $.ajax(settings).done(function (response) {
            console.log(response);
            callback(response, params);
        }).fail(function (response) {
            console.log(response);
            if (response.status == 401) {
                redirectToError('401');
            } else if (response.status == 403) {
                redirectToError('403');
            }
        });
    }
}

function getFromURLWithAuth(resourceUrl, callback, params) {
    
    if (!checkUser('username')) {
        redirectToError('401');
    } else {
    
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": resourceUrl,
            "method": "GET",
            xhrFields: {
                withCredentials: true
            }
        };
    
        $.ajax(settings).done(function () {
            callback(params);
        }).fail(function (response) {
            console.log(response);
            if (response.status == 401) {
                location.href = '/sebastian_web_gui/401.html';
            } else if (response.status == 403) {
                location.href = '/sebastian_web_gui/403.html';
            }
        });
    
    }
}

function postJsonToURLWithAuth(resourceUrl, jsonData, callback, params) {
    
    if (!checkUser('username')) {
        redirectToError('401');
    } else {
    
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": resourceUrl,
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            xhrFields: {
                withCredentials: true
            },
            "data": jsonData
        };
          
        $.ajax(settings).done(function (response) {
            console.log(response);
            callback(true, params[0]);
        }).fail(function (response) {
            console.log(response);
            if (response.status == 401) {
                location.href = '/sebastian_web_gui/401.html';
            } else if (response.status == 403) {
                location.href = '/sebastian_web_gui/403.html';
            } else 
                callback(false, params[1]);
        });
    
    }
}

function postToURLWithAuth(resourceUrl, callback, params) {
    
    if (!checkUser('username')) {
        redirectToError('401');
    } else {
        
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": resourceUrl,
            "method": "POST",
            xhrFields: {
                withCredentials: true
            }
        };
          
        $.ajax(settings).done(function (response) {
            console.log(response);
            callback(true, params[0]);
        }).fail(function (response) {
            console.log(response);
            if (response.status == 401) {
                location.href = '/sebastian_web_gui/401.html';
            } else if (response.status == 403) {
                location.href = '/sebastian_web_gui/403.html';
            } else
                callback(false, params[1]);
        });
        
    }
}

function putJsonToURLWithAuth(resourceUrl, jsonData, callback, params) {
    
    if (!checkUser('username')) {
        redirectToError('401');
    } else {
    
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": resourceUrl,
            "method": "PUT",
            "headers": {
                "Content-Type": "application/json"
            },
            xhrFields: {
                withCredentials: true
            },
            "data": jsonData
        };
          
        $.ajax(settings).done(function (response) {
            console.log(response);
            callback(response, params);
        }).fail(function (response) {
            console.log(response);
            if (response.status == 401) {
                location.href = '/sebastian_web_gui/401.html';
            } else if (response.status == 403) {
                location.href = '/sebastian_web_gui/403.html';
            }
        });
        
    }
}

function putToURLWithAuth(resourceUrl, callback, params) {
	
    if (!checkUser('username')) {
        redirectToError('401');
    } else {
        
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": resourceUrl,
            "method": "PUT",
            xhrFields: {
                withCredentials: true
            }
        };
          
        $.ajax(settings).done(function (response) {
            console.log(response);
            callback(response);
        }).fail(function (response) {
            console.log(response);
            if (response.status == 401) {
                location.href = '/sebastian_web_gui/401.html';
            } else if (response.status == 403) {
                location.href = '/sebastian_web_gui/403.html';
            }
        });
        
    }
}

function deleteRequestToURLWithAuth(resourceUrl, callback, params) {
    
    if (!checkUser('username')) {
        redirectToError('401');
    } else {
    
        var settings = {
            "async": true,
            "crossDomain": true,
            "url": resourceUrl,
            "method": "DELETE",
            xhrFields: {
                withCredentials: true
            }
        };
          
        $.ajax(settings).done(function (response) {
            console.log(response);
            callback(true, params[0]);
        }).fail(function (response) {
            console.log(response);
            if (response.status == 401) {
                location.href = '/sebastian_web_gui/401.html';
            } else if (response.status == 403) {
                location.href = '/sebastian_web_gui/403.html';
            } else 
                callback(false, params[1]);
        });
        
    }
}
