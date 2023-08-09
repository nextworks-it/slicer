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

//var catalogueAddr = '10.0.8.44';
var catalogueAddr = window.location.hostname;
var cataloguePort = '8083';
var isPublic = false;
var kcEnabled = false;

var stopRefreshing = false;

function useDefaultProject(project){
	return project=="";
}


function refresh(btnFlag) {
	if(stopRefreshing && !Boolean(btnFlag)) {
		//console.log("stop refreshing: "+ stopRefreshing);
		return;
	}
	$(document).ready(function () {
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
	if (body)
        body.scrollTop = offset;
	if (html)
        html.scrollTop = offset;
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

function redirectToError(errorType) {
	//var path = location.pathname;
	var hostname = '/5gcatalogue/';
	var new_path = '';
  
	/*var steps_back = path.split('/');
	  console.log(steps_back.length);
		  
	  for (var i = 0; i < steps_back.length - stepsToRoot; i++) {
		  new_path += '../';
	  }*/
  
	//new_path += errorType + '.html';
	new_path += hostname + errorType + '.html';
	//console.log(new_path);
	location.href = new_path;
}

function getURLParameter(name) {
	return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)')
							.exec(location.search) || [ , "" ])[1]
							.replace(/\+/g, '%20'))	|| null;
}

function showResultMessage(success, msg) {
    var flag = "true";
    var elem = document.getElementById("response");
    var text;
	if (success == null) {
		text = '<div class="alert alert-warning alert-dismissible fade in" role="alert">\
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>\
                </button>\
				<strong>WARNING!</strong> ' + msg + '</div>';
	} else {
		if (success) {
			text = '<div class="alert alert-info alert-dismissible fade in" role="alert">\
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>\
					</button>\
					<strong>SUCCESS!</strong> ' + msg + '</div>';
		} else {
			text =  '<div class="alert alert-danger alert-dismissible fade in" role="alert">\
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>\
					</button>\
					<strong>ERROR!</strong> ' + msg + '</div>';
		}
	}
	text += '<button class="btn btn-info" onclick="refresh(' + flag + ');"><i class="fa fa-refresh"></i> Refresh</button></div>';
    elem.innerHTML = text;
	scrollPageTo(elem.offsetTop);
}

function loadFromFile(type, evt, elementId, resId) {
    //Retrieve the first (and only!) File from the FileList object
    var file = evt.target.files;
		//console.log(file[0]);
    if (file.length < 1)
			return;
		if (type == 'ZIP') {
			readZipFile(file[0], elementId, resId);
		} else {
			//console.log("Json File in div: " + elementId);
			var elem = document.getElementById(elementId);
			//console.log("Json File in div: " + elementId + " " + elem);
		if(!elem)
			return;
		openTextFile(evt, elem);
	}
}

function readZipFile(file, containerId, resId) {
	//console.log(file);
	zip.workerScripts = {
		deflater: ['../../plugins/zip_js/z-worker.js', '../../plugins/zip_js/deflate.js'],
		inflater: ['../../plugins/zip_js/z-worker.js', '../../plugins/zip_js/inflate.js']
	};
	var model = (function() {
		var URL = this.webkitURL || this.mozURL || this.URL;

		return {
			getEntries : function(file, onend) {
				zip.createReader(new zip.BlobReader(file), function(zipReader) {
					zipReader.getEntries(onend);
					//console.log("onend: " + onend);
				}, onerror);
			},
			getEntryFile : function(entry, onend, onprogress) {
				var writer, zipFileEntry;
				function getData() {
					entry.getData(writer, function(blob) {
						var blobURL = URL.createObjectURL(blob);
						onend(blobURL);
					}, onprogress);
				}
				writer = new zip.BlobWriter();
				getData();
			}
		};
	})();
	model.getEntries(file, function(entries) {
		var cnt = 0;
		entries.forEach(function(entry) {
			if(entry.filename.indexOf('script')<0)
				cnt++;
		});
		if(cnt != 1) {
			onerror('Wrong package format');
			return false;
		}
		entries.forEach(function(entry) {
			//console.log('filename: ' + entry.filename);
			model.getEntryFile(entry, function(blobURL) {
				var subdiv = document.createElement('div');
				$(subdiv).load(blobURL, null, function() {
					if(entry.filename.indexOf('.json')<0)
						return true;
					subdiv.id = entry.filename;
					var container = document.getElementById(containerId);
					container.appendChild(subdiv);
				});
			}, function(current, total) {
			});
		});
	});

	function onerror(message) {
		if(!message || message == undefined)
			message = 'Error';
		console.log(message);
		showResultMessage(false, resId, message);
	}
}

function openTextFile(event, containerId) {
	var file = event.target.files[0];
	console.log("File: " + file);
	if (!file) {
		alert("Invalid file");
		return;
	}
	//console.log("type = " + f.type);
	var reader = new FileReader();

	var container = document.getElementById(containerId);

	reader.onload = function(event) {
		console.log("File read: " + event.target.result);
		container.innerHTML = event.target.result;
		console.log(container.innerHTML);
	};

	reader.readAsText(file);
}

function createTableHeaderFromObject(data, btnFlag, cols) {
	// creating header
	var text = '<thead><tr>';
	if(typeof(data) == 'object') {
        // data length has already been checked
		$.each(data, function(key, val) {
			if (cols && cols.indexOf(key) < 0)
				return true;
            if (key.indexOf('password') >= 0)
				return true;

			text += '<th>' + key + '</th>';
		});
		if (btnFlag) {
			text += '<th></th>';
		}
	}
	text += '</tr></thead>';

	return text;
}

function createTableHeaderByValues(cols, btnFlag, checkbFlag) {
    var text = '<thead><tr>';

	if (cols) {
		if(checkbFlag) {
			text +='<th><input type="checkbox" class="checkbox-toggle purge-check" style="display: none"></th>'
			//<button type="button" class="btn btn-default btn-sm checkbox-toggle purge-check" style="display: none"><i class="fa fa-square-o"></i></button></th>'
		}
		var value;
		for (value in cols) {
            text += '<th>' + cols[value] + '</th>';
        }
		if (btnFlag) {
			text += '<th></th>';
		}
    }
	text += '</tr></thead>';
	return text;
}

function hideElement(elem, flag) {
	if (flag) {
		elem.style.display = 'none';
	} else {
		if(elem.tagName.toLowerCase() == 'table')
			elem.style.display = 'table';
		else
			elem.style.display = 'block';
	}
}

function getValuesFromKeyPath(data, keys, result) {
    if(keys.length <= 1) {
        if (data instanceof Array) {
            for (k=0; k<data.length; k++) {
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
        for (h=1; h<keys.length; h++) {
            newKeys.push(keys[h]);
        }
        if (data instanceof Array) {
            for (k=0; k<data.length; k++) {
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

function createActionButton(id, resId, btnNames, btnCallbacks) {
	var text = '<td>';
	if(btnNames instanceof Array) {
		if(btnNames.length == btnCallbacks.length) {
			text += createDropdownButton(id, resId, btnNames, btnCallbacks);
		}
	} else {
		text +=  createButton(id, resId, btnNames, btnCallbacks);
	}
	text += '</td>';
	return text;
}

function disableExpBtn(btnId) {
    var btnElem = document.getElementById(btnId);
    btnElem.classList = "isDisabled";
}

function createLinkSet(id, resId, btnNames, btnCallbacks) {
	var text = '<td>';
	if(btnNames instanceof Array) {
		if(btnNames.length == btnCallbacks.length) {
			for(var i=0; i<btnNames.length; i++) {
				if (btnCallbacks[i].toLowerCase().indexOf("delete") >= 0 ||
					btnCallbacks[i].toLowerCase().indexOf("get") >= 0) {
					text += '<a title="Delete" class="btn btn-link">\
								<i class="fa fa-close"  onclick=' + btnCallbacks[i] + '("' + id + '","' + resId + '")></i>\
							</a>';
				} 	else if (btnCallbacks[i].toLowerCase().indexOf("update") >= 0 ||
							btnCallbacks[i].toLowerCase().indexOf("project") >= 0) {
						text += '<a title="' + btnNames[i] + '" class="btn btn-link">\
									<i class="fa fa-gear" "buttonModal_'+ btnCallbacks[i] + '"  data-toggle="modal" data-target="#' + btnCallbacks[i] + id + '" data-id="' + id + '"></i>\
								</a>';
				} 	else if (btnCallbacks[i].toLowerCase().indexOf("open") >= 0) {
						text += '<a title="View" class="btn btn-link">\
									<i class="fa fa-file-code-o" "buttonModal_'+ btnCallbacks[i] + '"  data-toggle="modal" data-target="#' + btnCallbacks[i] + id + '" data-id="' + id + '"></i>\
								</a>';
				}  else if (btnNames[i].toLowerCase().indexOf("nsd graph") >= 0) {
						text += '<a title="Graph" class="btn btn-link">\
									<i class="fa fa-eye"  onclick=getAllNsdInfos("' + id + '",' + btnCallbacks[i] + ',"response")></i>\
								</a>';
				} 	else if (btnNames[i].toLowerCase().indexOf("vnf graph") >= 0) {
						text += '<a title="Graph" class="btn btn-link">\
									<i class="fa fa-eye"  onclick=getAllVnfInfos("' + id + '",' + btnCallbacks[i] + ',"response")></i>\
								</a>';
				}   else if (btnCallbacks[i].toLowerCase().indexOf("export") >= 0) {
						text += '<a id="expBtn_' + id + '" title="Upload" class="btn btn-link">\
									<i class="fa fa-cloud-upload"  onclick=' + btnCallbacks[i] + '("' + id + '","' + resId + '")></i>\
								</a>';
				}
			}
		}
	}	else {
		if (btnCallbacks.toLowerCase().indexOf("delete") >= 0 ||
					btnCallbacks.toLowerCase().indexOf("get") >= 0) {
					text += '<a class="btn btn-link">\
							<i class="fa fa-close" onclick=' + btnCallbacks + '("' + id + '","' + resId + '")></i>\
							</a>';
				} else if (btnCallbacks.toLowerCase().indexOf("view") >= 0) {
						text += '<a class="btn btn-link">\
								<i class="fa fa-eye" onclick="location.href=\'' + btnCallbacks + id + '\'"></i>\
								</a>';
				} else if (btnCallbacks.toLowerCase().indexOf("open") >= 0 ||
						btnCallbacks.toLowerCase().indexOf("update") >= 0) {
							text += '<a class="btn btn-link">\
										<i class="fa fa-gear buttonModal_'+ btnCallbacks + '" data-toggle="modal" data-target="#' + btnCallbacks + id + '" data-id="' + id + '"></i>\
									</a>';
				}  else if (btnNames.toLowerCase().indexOf("graph") >= 0) {
					text += '<a class="btn btn-link">\
								<i class="fa fa-area-chart" onclick=getAllNsdInfos("' + id + '",' + btnCallbacks + ',"response")></i>\
							</a>';
				}
			}
	text += '</td>';
	return text;
}

function createButton(id, resId, btnName, btnCallback) {

	var text = 	'<button type="button" class="btn btn-info btn-sm btn-block';
	if (btnCallback.toLowerCase().indexOf("delete") >= 0 ||
		btnCallback.toLowerCase().indexOf("get") >= 0) {
        text += '" onclick=' + btnCallback + '("' + id + '","' + resId + '")>';
    } else if (btnCallback.toLowerCase().indexOf("view") >= 0) {
        text += '" onclick="location.href=\'' + btnCallback + id + '\'">';
    } else if (btnCallback.toLowerCase().indexOf("open") >= 0 ||
			   btnCallback.toLowerCase().indexOf("update") >= 0) {
			text += ' buttonModal_'+ btnCallback + '" data-toggle="modal" data-target="#' + btnCallback + id + '" data-id="' + id + '">';
    }
	text += btnName + '</button>';
	//console.log("button: \n" + text);

	return text;
}

function createDropdownButton(id, resId, btnNames, btnCallbacks) {
	var text = '<div class="btn-group">\
				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Action\
				<span class="fa fa-caret-down"></span>\
				</button><ul class="dropdown-menu" role="menu">'; //style="position: static;"
	for(var i=0; i<btnNames.length; i++) {
		text += '<li>' + createButton(id, resId, btnNames[i], btnCallbacks[i]) + '</li>';
		console.log(btnNames[i]);
	}
	text += '</ul></div>';		//<input type="text" class="form-control">

	return text;
}
