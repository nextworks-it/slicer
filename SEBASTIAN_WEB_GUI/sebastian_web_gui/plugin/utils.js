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

/* jshint esversion: 6 */

var vsAddr = window.location.hostname;
var vsPort = '8082';
var stepsToRoot = 4;

var stopRefreshing = false;
var isShowingProgress = false;

var popUp = function(url) {
  window.open(url, '_blank');
};

class Button {
  constructor(name, callback, params) {
    if (params === undefined) {
      params = null;
    }
    this.name = name;
    this.callback = callback;
    this.params = params;
  }

  setParams(params) {
    this.params = params;
  }

  make_onclick() {
    return () => {
      if (this.params === null) {
        throw new Error(`Undefined parameters in button ${this.name}.`);
      }
      this.callback.apply(null, this.params);
    };
  }

  renderIn(container) {
    let b = document.createElement('button');
    b.type = 'button';
    b.classList.add('btn', 'btn-info', 'btn-sm', 'btn-block');
    b.onclick = this.make_onclick();
    b.append(this.name);
    container.append(b);
  }
}

function refresh(btnFlag) {
  if (stopRefreshing && !Boolean(btnFlag)) {
    // console.log("stop refreshing: "+ stopRefreshing);
    return;
  }
  $(document).ready(function() {
    for (i = 0; i < document.forms.length; i++) {
      document.forms[i].reset();
    }
  });
  location.reload();
  scrollPageTo(0);
}

function scrollPageTo(offset) {
  // For Chrome, Safari and Opera
  var body = document.body;
  // Firefox and IE places the overflow at the <html> level, unless else is specified.
  // Therefore, we use the documentElement property for these two browsers
  var html = document.documentElement;
  if (body) body.scrollTop = offset;
  if (html) html.scrollTop = offset;
}

function redirectToError(errorType) {
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

function clearForms(parentId, flag) {
  //console.log(parentId);
  var elems = $('#' + parentId).find('form');
  //console.log(elems.length)
  for (var i = 0; i < elems.length; i++) {
    //console.log(elems[i])
    elems[i].reset();
  }

  if (flag) {
    var modal = document.getElementById(parentId);
    modal.style = 'display:none';
  }
}

function getURLParameter(name) {
  return (
    decodeURIComponent(
      (new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(
        location.search
      ) || [, ''])[1].replace(/\+/g, '%20')
    ) || null
  );
}

function showResultMessage(success, msg) {
  var flag = 'true';
  var elem = document.getElementById('response');
  var text;
  if (success) {
    text =
      '<div class="alert alert-info alert-dismissible fade in" role="alert">\
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>\
                </button>\
				<strong>SUCCESS!</strong> ' +
      msg +
      '</div>';
  } else {
    text =
      '<div class="alert alert-danger alert-dismissible fade in" role="alert">\
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>\
                </button>\
				<strong>ERROR!</strong> ' +
      msg +
      '</div>';
  }
  text +=
    '<button class="btn btn-info" onclick="refresh(' +
    flag +
    ');"><i class="fa fa-refresh"></i> Refresh</button></div>';
  elem.innerHTML = text;
  scrollPageTo(elem.offsetTop);
}

function loadFromFile(type, evt, elementId) {
  //Retrieve the first (and only!) File from the FileList object
  var file = evt.target.files;
  console.log(file[0]);
  if (file.length < 1) return;
  if (type == 'ZIP') {
    readZipFile(file[0], elementId);
  } else {
    console.log('Json File in div: ' + elementId);
    var elem = document.getElementById(elementId);
    console.log('Json File in div: ' + elementId + ' ' + elem);
    if (!elem) return;
    openTextFile(evt, elem);
  }
}

function readZipFile(file, containerId) {
  console.log(file);
  zip.workerScripts = {
    deflater: [
      '../../plugins/zip_js/z-worker.js',
      '../../plugins/zip_js/deflate.js'
    ],
    inflater: [
      '../../plugins/zip_js/z-worker.js',
      '../../plugins/zip_js/inflate.js'
    ]
  };
  var model = (function() {
    var URL = this.webkitURL || this.mozURL || this.URL;

    return {
      getEntries: function(file, onend) {
        zip.createReader(
          new zip.BlobReader(file),
          function(zipReader) {
            zipReader.getEntries(onend);
            //console.log("onend: " + onend);
          },
          onerror
        );
      },
      getEntryFile: function(entry, onend, onprogress) {
        var writer, zipFileEntry;
        function getData() {
          entry.getData(
            writer,
            function(blob) {
              var blobURL = URL.createObjectURL(blob);
              onend(blobURL);
            },
            onprogress
          );
        }
        writer = new zip.BlobWriter();
        getData();
      }
    };
  })();
  model.getEntries(file, function(entries) {
    var cnt = 0;
    entries.forEach(function(entry) {
      if (entry.filename.indexOf('script') < 0) cnt++;
    });
    if (cnt != 1) {
      onerror('Wrong package format');
      return false;
    }
    entries.forEach(function(entry) {
      //			console.log('filename: ' + entry.filename);
      model.getEntryFile(
        entry,
        function(blobURL) {
          var subdiv = document.createElement('div');
          $(subdiv).load(blobURL, null, function() {
            if (entry.filename.indexOf('.json') < 0) return true;
            subdiv.id = entry.filename;
            var container = document.getElementById(containerId);
            container.appendChild(subdiv);
          });
        },
        function(current, total) {}
      );
    });
  });

  function onerror(message) {
    if (!message || message == undefined) message = 'Error';
    console.log(message);
    showResultMessage(false, message);
  }
}

function openTextFile(event, container) {
  var file = event.target.files[0];
  console.log('File: ' + file);
  if (!file) {
    alert('Invalid file');
    return;
  }
  //  console.log("type = " + f.type);
  var reader = new FileReader();

  reader.onload = function(event) {
    console.log('File read: ' + event.target.result);
    container.innerHTML = event.target.result;
    console.log(container.innerHTML);
  };

  reader.readAsText(file);
}

function createTableHeaderFromObject(data, btnFlag, cols) {
  // creating header
  var text = '<thead><tr>';
  if (typeof data == 'object') {
    if (btnFlag) {
      text += '<th></th>';
    }
    // data length has already been checked
    $.each(data, function(key, val) {
      if (cols && cols.indexOf(key) < 0) return true;
      if (key.indexOf('password') >= 0) return true;

      text += '<th>' + key + '</th>';
    });
  }
  text += '</tr></thead>';

  return text;
}

function createTableHeaderByValues(cols, btnFlag, checkbFlag) {
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

function fillFlavourTableRow(header, values, data) {
  //	console.log('Flavour row data: ' + JSON.stringify(data, null, 4));
  var text = '<table class="table table-bordered table-striped">' + header;
  var conts = '<tbody>';

  if (values == null) {
    if (data instanceof Array) {
      for (var e in data) {
        conts += '<tr><td>' + data[e] + '</td></tr>';
      }
    } else {
      if (data) {
        conts += '<tr><td>' + data + '</td></tr>';
      } else {
        conts += '<tr><td></td></tr>';
      }
    }
  } else {
    for (var i in data) {
      conts += '<tr>';
      for (var j in values) {
        var subText = '<td>';
        var value = [];
        getValuesFromKeyPath(data[i], values[j], value);
        //				console.log('key: ' + values[j] + ' values: ' + value);
        if (value[0] instanceof Array) {
          //					console.log('Array lev1: ' + values[j]);
          var subTable = '<table class="table">';
          for (var v in value[0]) {
            if (value[0][v] instanceof Array) {
              //							console.log('Array lev2: ' + values[j]);
              for (var h in value[0][v]) {
                if (typeof value[0][v][h] == 'object') {
                  subTable +=
                    '<tr><td>' +
                    JSON.stringify(value[0][v][h], null, 4) +
                    '</td></tr>';
                } else {
                  subTable += '<tr><td>' + value[0][v][h] + '</td></tr>';
                }
              }
            } else if (typeof value[0][v] == 'object') {
              subTable +=
                '<tr><td>' +
                JSON.stringify(value[0][v], null, 4) +
                '</td></tr>';
            } else {
              subTable += '<tr><td>' + value[0][v] + '</td></tr>';
            }
          }
          subTable += '</table>';
          subText += subTable + '</td>';
          //					console.log("subTable: ", + subText);
        } else if (value.length !== 0) {
          if (typeof value[0] == 'object') {
            subText += JSON.stringify(value[0], null, 4) + '</td>';
          } else {
            subText += value[0] + '</td>';
          }
        } else {
          subText += '</td>';
        }
        conts += subText;
      }
      conts += '</tr>';
    }
  }

  text += conts + '</tbody></table>';

  return text;
}

function showDeploymentFlavour(selectorId, tableDivId) {
  var sel = document.getElementById(selectorId);
  if (!sel) {
    return;
  } else {
    var value = sel.value;
    var tables = $('#' + tableDivId).children('.hidable-table');
    for (var k = 0; k < tables.length; k++) {
      hideElement(tables[k], !(tables[k].id.indexOf(value) >= 0));
    }
  }
}

function hideElement(elem, flag) {
  if (flag) {
    elem.style.display = 'none';
  } else {
    if (elem.tagName.toLowerCase() == 'table') elem.style.display = 'table';
    else elem.style.display = 'block';
  }
}

function extract(key, data) {
  if (key.length == 0) {
    throw new Error('Empty key chain.');
  }
  if (!data.hasOwnProperty(key[0])) {
    throw new Error(`Data ${data} does not contain key ${key[0]}`);
  }
  if (key.length > 1) {
    return extract(data[key[0]], key.slice(1));
  } else {
    return data[key[0]];
  }
}

function getValuesFromKeyPath(data, keys, result) {
  if (keys.length <= 1) {
    if (data instanceof Array) {
      for (k = 0; k < data.length; k++) {
        if (data[k].hasOwnProperty(keys[0])) {
          result.push(data[k][keys[0]]);
        }
      }
    } else {
      if (data && data.hasOwnProperty(keys[0])) {
        result.push(data[keys[0]]);
      }
    }
  } else {
    var newKeys = [];
    for (h = 1; h < keys.length; h++) {
      newKeys.push(keys[h]);
    }
    if (data instanceof Array) {
      for (k = 0; k < data.length; k++) {
        if (data[k].hasOwnProperty(keys[0])) {
          getValuesFromKeyPath(data[k][keys[0]], newKeys, result);
        }
      }
    } else {
      if (data.hasOwnProperty(keys[0])) {
        getValuesFromKeyPath(data[keys[0]], newKeys, result);
      }
    }
  }
}

function createActionButton(id, btnNames, btnCallbacks) {
  var text = '<td>';
  if (btnNames instanceof Array) {
    if (btnNames.length == btnCallbacks.length) {
      text += createDropdownButton(id, btnNames, btnCallbacks);
    }
  } else {
    text += createButton(id, btnNames, btnCallbacks);
  }
  text += '</td>';
  return text;
}

function createButton(id, btnName, btnCallback) {
  var text = '<button type="button" class="btn btn-info btn-sm btn-block';
  if (
    btnCallback.toLowerCase().indexOf('delete') >= 0 ||
    btnCallback.toLowerCase().indexOf('purge') >= 0 ||
    btnCallback.toLowerCase().indexOf('enable') >= 0 ||
    btnCallback.toLowerCase().indexOf('disable') >= 0 ||
    btnCallback.toLowerCase().indexOf('subscribe') >= 0 ||
    btnCallback.toLowerCase().indexOf('unsubscribe') >= 0 ||
    btnCallback.toLowerCase().indexOf('terminate') >= 0 ||
    btnCallback.toLowerCase().indexOf('purge') >= 0
  ) {
    text += '" onclick=' + btnCallback + '("' + id + '")>';
  } else if (btnName.toLowerCase().indexOf('view') >= 0) {
    if (btnName.toLowerCase().indexOf('view sla') >= 0) {
      id += '|' + document.getElementById('selectedGroup').value;
    }
    text += '" onclick="location.href=\'' + btnCallback + id + '\'">';
  } else {
    if (btnCallback.toLowerCase().indexOf('instantiate') >= 0) {
      //var infoId = id.split('|')[2];
      text +=
        ' buttonModal_' +
        btnCallback +
        '" data-toggle="modal" data-target="#' +
        btnCallback +
        '_' +
        id +
        '" data-id="' +
        id +
        '">';
    } else if (
      btnCallback.toLowerCase().indexOf('createtenantsla') >= 0 ||
      btnCallback.toLowerCase().indexOf('createvsdform') >= 0
    ) {
      //console.log(id);
      text +=
        ' buttonModal_' +
        btnCallback +
        '" data-toggle="modal" data-target="#' +
        btnCallback +
        '_' +
        id +
        '" data-id="' +
        id +
        '">';
    } else {
      text +=
        ' buttonModal_' +
        btnCallback +
        '" data-toggle="modal" data-target="#' +
        btnCallback +
        '" data-id="' +
        id +
        '">';
    }
  }
  text += btnName + '</button>';
  //	console.log("button: \n" + text);

  return text;
}

function createDropdownButton(id, btnNames, btnCallbacks) {
  var text =
    '<div class="btn-group">\
				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Action\
				<span class="fa fa-caret-down"></span>\
				</button><ul class="dropdown-menu" role="menu">'; //style="position: static;"
  for (var i = 0; i < btnNames.length; i++) {
    text += '<li>' + createButton(id, btnNames[i], btnCallbacks[i]) + '</li>';
  }
  text += '</ul></div>'; //<input type="text" class="form-control">

  return text;
}

function showProgress(divId) {
  // Loading (remove the following to stop the loading)
  if (!isShowingProgress) {
    var progress = document.createElement('div');
    progress.className = 'overlay';
    progress.id = 'loadingDiv';
    var icon = document.createElement('i');
    icon.className = 'fa fa-refresh fa-spin';
    progress.appendChild(icon);
    var divs = document.getElementsByClassName('box');
    //	console.log("show progress");
    divs[0].appendChild(progress);
    isShowingProgress = true;
  }
}

function hideProgress() {
  var divs = document.getElementsByClassName('box');
  var div = divs[0];
  $(div).ready(function() {
    var progDiv = document.getElementById('loadingDiv');
    //		console.log("prog div: " + progDiv);
    if (progDiv) {
      div.removeChild(progDiv);
      //			console.log("hide progress");
    }
    isShowingProgress = false;
  });
}
