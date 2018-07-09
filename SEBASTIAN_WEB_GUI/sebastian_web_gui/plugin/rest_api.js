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

function loginToURL(resourceUrl, username, password, callback) {
    
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
    });
    
}

function getJsonFromURLWithAuth(resourceUrl, callback, params) {
    
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
    });
}

function getFromURLWithAuth(resourceUrl, callback, params) {
    
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
    });
}

function postJsonToURLWithAuth(resourceUrl, jsonData, callback, params) {
    
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
    }).fail(function () {
        callback(false, params[1]);
    });
}

function postToURLWithAuth(resourceUrl, callback, params) {
    
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
    }).fail(function () {
        callback(false, params[1]);
    });
}

function putJsonToURLWithAuth(resourceUrl, jsonData, callback, params) {
    
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
    });
}

function putToURLWithAuth(resourceUrl, callback, params) {
	
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
    });
}

function deleteRequestToURLWithAuth(resourceUrl, callback, params) {
    
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
    }).fail(function () {
        callback(false, params[1]);
    });
}
