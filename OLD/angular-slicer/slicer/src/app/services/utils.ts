
export class utils{
    constructor(){}
    postJsonToURLWithAuth(resourceUrl, jsonData, callback, params) {
        this.sendJsonToURLWithAuth(resourceUrl, jsonData, callback, params, 'POST');
    }
    
     checkUser(cname) {
      var cvalue = this.getCookie(cname);
      
      if (cvalue == '') {
          return false;
      }
      
      return true;
    }
     getCookie(cname) {
      var name = cname + '=';
      var decodedCookie = decodeURIComponent(document.cookie);
      var ca = decodedCookie.split(';');
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
          c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
          return c.substring(name.length, c.length);
        }
      }
      return '';
    }
    
    
     redirectToError(errorType) {
      //var path = location.pathname;
      var hostname = '/sebastian_web_gui/';
      var new_path = '';
    
      /*var steps_back = path.split('/');
        console.log(steps_back.length);
            
        for (var i = 0; i < steps_back.length - stepsToRoot; i++) {
            new_path += '../';
        }*/
    
      //new_path += errorType + '.html';
      new_path += hostname + errorType + '.html';
      console.log(new_path);
      location.href = new_path;
    }
    sendJsonToURLWithAuth(resourceUrl, jsonData, callback, params, verb) {
        if (verb === undefined) {
            verb = 'POST';
        }
        
        if (!this.checkUser('username')) {
            this.redirectToError('401');
        } else {
        
            var settings = {
                "async": true,
                "crossDomain": true,
                "url": resourceUrl,
                "method": verb,
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

     createTableHeaderByValues(cols, btnFlag, checkbFlag) {
        var text = '<thead><tr>';
      
        if (cols) {
          if (checkbFlag) {
            text +=
              '<th><input type="checkbox" class="checkbox-toggle purge-check" style="display: none"></th>';
            //<button type="button" class="btn btn-default btn-sm checkbox-toggle purge-check" style="display: none"><i class="fa fa-square-o"></i></button></th>'
          }
          if (btnFlag) {
            text += '<th></th>';
          }
          var value;
          for (value in cols) {
            text += '<th>' + cols[value] + '</th>';
          }
        }
        text += '</tr></thead>';
        return text;
      }








      
       setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
        var expires = 'expires=' + d.toUTCString();
        document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
      }

      
       deleteCookie(cname) {
        var cvalue = this.getCookie(cname);
        var d = new Date();
        d.setTime(d.getTime() - 3 * 24 * 60 * 60 * 1000);
        var expires = 'expires=' + d.toUTCString();
        document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
      }
      










}
