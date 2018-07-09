
function login(userNameId, passwordId){
    var username = document.getElementById(userNameId).value;
    var password = document.getElementById(passwordId).value;
    
    loginToURL('http://' + vsAddr + ':' + vsPort + '/login', username, password, getUserInfo);   
}

function getUserInfo() {
    
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/whoami', storeUserInfo);
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
    
    setCookie("username", data.username, 1);
    
    if (data.role == 'ADMIN') {
        location.href = './admin/index.html';
    } else {
        location.href = './tenant/index.html';
    }
}

function displayUserInfo(userInfoId) {
    var elem = document.getElementById(userInfoId);
    
    var value = getCookie("username");
    
    elem.innerHTML = value;
}

function setCookie(cname, cvalue, exdays) {
    
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
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
    return "";
}