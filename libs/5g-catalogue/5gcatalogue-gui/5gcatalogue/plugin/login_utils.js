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

function login(userNameId, passwordId){
    
}

function logout() {
    deleteCookie('USERNAME');
    deleteCookie('ROLE');
    deleteCookie('TOKEN');
    deleteCookie('PROJECT');

    refresh(false);
    
    keycloak.logout();
}

function getUserInfo() {
    
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/whoami', storeUserInfo, null, true);
}

function storeUserInfo(data) {
    console.log(JSON.stringify(data, null, 4));
    
    /*var format = {
        "username": "admin",
        "password": "$2a$10$A9otFvMvvr2wmqTSAQhZt.UA/0boTa7j4fa4WvLiqwpSQzRSD0fga",
        "allocatedResources": {
            "diskStorage": 0,
            "vCPU": 0,
            "memoryRAM": 0
        },
        "role": "ADMIN"
    };*/
    
    setCookie("USERNAME", data.username, 1);
    setCookie("ROLE", data.role, 1);
    
    if (data.role == 'ADMIN') {
        location.href = './admin/index.html';
    } else {
        location.href = './tenant/index.html';
    }
}

function displayUserInfo(userInfoId) {
    var elem = document.getElementById(userInfoId);
    
    var value = getCookie("USERNAME");
    
    elem.innerHTML = value;
}

function selectProject(projectName) {
    setCookie("PROJECT", projectName, 1);

    document.getElementById('project').innerHTML = '<b>' + projectName + '</b>';
    document.getElementById('projectBar').innerHTML = '<b>' + projectName + '</b>';

    refresh(false);
}

function setCookie(cname, cvalue, exdays) {
    
    /*var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));

    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";*/

    localStorage.setItem(cname, cvalue);
}

function getCookie(cname) {

    /*var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";*/

    return localStorage.getItem(cname);
}

function deleteCookie(cname) {
    
    /*var cvalue = getCookie(cname);
    var d = new Date();
    d.setTime(d.getTime() - (3*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";*/

    localStorage.removeItem(cname);
}

function deleteAllCookies() {
    
    /*var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }*/

    localStorage.clear();
}