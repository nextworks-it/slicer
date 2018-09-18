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
var wizardCurrentStep = 1;
var nsds = [];
var translationRules = [];
var vnfds = [];
var apps = [];
var image_url = null;

var defaultImage = 'https://i.ytimg.com/vi/TSXXi2kvl_0/maxresdefault.jpg';
var fileserverPort = '8080';

function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function fillBlueprintCounter(data, elemId) {
    var countDiv = document.getElementById(elemId);
	
	//console.log(JSON.stringify(data, null, 4));
	countDiv.innerHTML = data.length;
}

function submitBlueprintCreationRequest(blueprintId) {
    var jsonObj  = JSON.parse('{}');
    
    var blueprint = document.getElementById(blueprintId).innerHTML;
    jsonObj.vsBlueprint = JSON.parse(blueprint);
    
    jsonObj.vsBlueprint.imgUrl = image_url;
    
    jsonObj.nsds = nsds;
    
    jsonObj.vnfPackages = vnfds;
    
    jsonObj.mecAppPackages = apps;
    
    jsonObj.translationRules = translationRules;
    
    var json = JSON.stringify(jsonObj, null, 4);
    
    console.log(json);
    
    postJsonToURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint', json, showResultMessage, ['VS Blueprint successfully onboarded', 'Error while onboarding VS Blueprint']);   
}

function createVSDFromForm(formIds, qosNum) {
    var jsonObj = JSON.parse('{}');
    
    var vsdObj = JSON.parse('{}');
    
    jsonObj.vsd = vsdObj;
    
    var vsbId = document.getElementById(formIds[0]).value;
    jsonObj.vsd.vsBlueprintId = vsbId;
    
    var tenantId = document.getElementById(formIds[1]).value;
    jsonObj.tenantId = tenantId;
    
    var name = document.getElementById(formIds[2]).value;
    jsonObj.vsd.name = name;
    
    var version = document.getElementById(formIds[3]).value;
    jsonObj.vsd.version = version;
    
    var sst = document.getElementById(formIds[5]).value;
    jsonObj.vsd.sst = sst;
    
    var mt = document.getElementById(formIds[6]).value;
    jsonObj.vsd.managementType = mt;
    
    var qos = JSON.parse('{}');
    
    console.log(qosNum);
    
    for (var i = 0; i < qosNum; i++) {
        var param = document.getElementById(formIds[4] + i).value;
        var paramName = document.getElementById(vsbId + '_qos' + i).innerHTML;
        qos[paramName] = param;
        
        console.log(param);
        console.log(paramName);
    }
    
    jsonObj.vsd.qosParameters = qos;
    
    var ispublic = document.getElementById(formIds[7]).checked;
    
    jsonObj.isPublic = ispublic;
    
    var json = JSON.stringify(jsonObj, null, 4);
    
    console.log(json);
    
    postJsonToURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor', json, showResultMessage, ['VS descriptor successfully created', 'Error while creating VS descriptor']);
}

function readVSBlueprints(tableId) {
    getVSBlueprints(tableId, createVSBlueprintsTable);    
}

function getVSBlueprints(params, callback) {
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint', callback, params);
}

function readVSBlueprint(params) {
    var id = getURLParameter('Id');
    getVSBlueprint(id, params, createVSBlueprintDetailsTable);
}

function getVSBlueprint(id, params, callback) {
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint/' + id, callback, params);
}

function deleteVSBlueprint(blueprintId) {
    deleteRequestToURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint/' + blueprintId, showResultMessage, ['VS Blueprint successfully deleted', 'Unable to delete VS Blueprint']);
}

function progressBlueprintWizard() {
    document.getElementById('step-' + wizardCurrentStep).style.display = 'none';
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.remove('selected');
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.add('done');
    wizardCurrentStep += 1;
    document.getElementById('step-' + wizardCurrentStep).style.display = 'block';
    document.getElementById('step-' + wizardCurrentStep + '_ball').setAttribute('isdone', "1");
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.remove('disabled');
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.add('selected');
    if (wizardCurrentStep == 6) {
        document.getElementById('progressBtn').classList.add('buttonDisabled');
        document.getElementById('finishBtn').classList.remove('buttonDisabled');
    } else if (wizardCurrentStep > 1) {
        document.getElementById('undoBtn').classList.remove('buttonDisabled');
    }
}

function undoBlueprintWizard() {
    document.getElementById('step-' + wizardCurrentStep).style.display = 'none';
    document.getElementById('step-' + wizardCurrentStep + '_ball').setAttribute('isdone', "0");
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.remove('selected');
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.add('disabled');
    wizardCurrentStep -= 1;
    document.getElementById('step-' + wizardCurrentStep).style.display = 'block';
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.remove('done');
    document.getElementById('step-' + wizardCurrentStep + '_ball').classList.add('selected');
    if (wizardCurrentStep == 1) {
        document.getElementById('undoBtn').classList.add('buttonDisabled');
    } else if (wizardCurrentStep < 6) {
        document.getElementById('finishBtn').classList.add('buttonDisabled');
        document.getElementById('progressBtn').classList.remove('buttonDisabled');
    }
}

function loadBlueprintFromFileIntoForm(evt, elemId) {
	var type = 'JSON';
	loadFromFile(type, evt, elemId);
}

function loadImageFromFileIntoForm(evt, elemId) {
    file = evt.target.files[0];

    // Now upload it on the file storage
    var storageUrl = 'http://' + window.location.hostname + ':' + fileserverPort;

    var image_name = uuidv4() + '.jpg';

    var actualUrl = storageUrl + '/' + image_name;
    image_url = actualUrl;
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": actualUrl,
        "method": "PUT",
        "headers": {
            "Content-Type": "image/jpeg"
        },
        /*xhrFields: {
            withCredentials: true
        },*/
        "data": file,
        "processData": false
    };
      
    $.ajax(settings).done(function (response) {
        console.log(response);
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

function loadNSDsFromFileIntoArray(evt, inputId) {
    var input = document.getElementById(inputId);
    
    nsds = [];
    
    for (var i = 0; i < input.files.length; i++) {
        var file = input.files[i];
        var reader = new FileReader();
        reader.readAsText(file, "UTF-8");
        reader.onload = function (evt) {
            nsds.push(JSON.parse(evt.target.result));
        } 
    }
    
    console.log(nsds);
}

function uploadVNFDFromForm(formId, formIds) {
	var jsonObj = JSON.parse('{}');
	jsonObj.name = document.getElementById(formIds[0]).value;
	jsonObj.version = document.getElementById(formIds[1]).value;
	jsonObj.provider = document.getElementById(formIds[2]).value;
	jsonObj.checksum = document.getElementById(formIds[3]).value;
	jsonObj.vnfPackagePath = document.getElementById(formIds[4]).value;
	var json = JSON.stringify(jsonObj, null, 4);
    
    vnfds.push(jsonObj);
    
    window.alert("VNF Package successfully submitted.");
    
    clearForms(formId, false);
    
    console.log(json);
    console.log(vnfds);
    
    document.getElementById(formIds[5]).innerHTML += '<tr><td>' + jsonObj.name + '</td></tr>';
    
    return json;
}

function uploadAppFromForm(formId, formIds) {
	var jsonObj = JSON.parse('{}');
	jsonObj.name = document.getElementById(formIds[0]).value;
	jsonObj.version = document.getElementById(formIds[1]).value;
	jsonObj.provider = document.getElementById(formIds[2]).value;
	jsonObj.checksum = document.getElementById(formIds[3]).value;
	jsonObj.appPackagePath = document.getElementById(formIds[4]).value;
	var json = JSON.stringify(jsonObj, null, 4);
    
    apps.push(jsonObj);
    
    window.alert("App Package successfully submitted.");
    
    clearForms(formId, false);
    
    document.getElementById(formIds[5]).innerHTML += '<tr><td>' + jsonObj.name + '</td></tr>';
    
    return json;
}

function uploadTRFromForm(formId, formIds, paramNum) {
    var jsonObj = JSON.parse('{}');
	
	jsonObj.nsdId = document.getElementById(formIds[0]).value;
    jsonObj.nsdVersion = document.getElementById(formIds[1]).value;
    jsonObj.nsFlavourId = document.getElementById(formIds[2]).value;
    jsonObj.nsInstantiationLevelId = document.getElementById(formIds[3]).value;
	
	var params = [];
		
	var pnum = document.getElementById(paramNum).innerHTML;
	
	for (var i = 0; i < pnum; i++) {		
		var tempParam = JSON.parse('{}');
				
		var tempId = document.getElementById(formIds[4] + i).value;
		var tempMinVal = document.getElementById(formIds[5] + i).value;
		var tempMaxVal = document.getElementById(formIds[6] + i).value;
		tempParam.parameterId = tempId;
		tempParam.minValue = tempMinVal;
		tempParam.maxValue = tempMaxVal;
		
		params.push(tempParam);
	}
	
	jsonObj.input = params;
	
	var json = JSON.stringify(jsonObj, null, 4);
	
	console.log(json);
	translationRules.push(jsonObj);
    
    window.alert("Translation rule successfully submitted.");
    
    clearForms(formId, false);
    return json;
}

function addParameterForm(paramCounter, paramDiv) {
    var pnum = parseInt(document.getElementById(paramCounter).innerHTML);
    
    var plusBtnNum = pnum - 1;
	var plusBtnId = "parameterPlus" + plusBtnNum;
	document.getElementById(plusBtnId).style.visibility='hidden';
    document.getElementById('paramSubmit' + plusBtnNum).style.visibility='hidden';
	
	var minusBtnId = "parameterMinus" + plusBtnNum;
	
	if (document.getElementById(minusBtnId)) {
		document.getElementById(minusBtnId).style.visibility='hidden';
	}
    
    var text = '<div id="paramRemovable' + pnum +'"></br><div class="form-group">\
        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Parameter Id <!-- span class="required">*</span -->\
        </label>\
        <div class="col-md-6 col-sm-6 col-xs-12">\
          <input type="text" id="createTR-parId' + pnum +'" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
        </div>\
    </div>\
    <div class="form-group">\
        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Min Value <!-- span class="required">*</span -->\
        </label>\
        <div class="col-md-6 col-sm-6 col-xs-12">\
          <input type="text" id="createTR-minVal' + pnum +'" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
        </div>\
    </div>\
    <div class="form-group">\
        <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Max Value <!-- span class="required">*</span -->\
        </label>\
        <div class="col-md-6 col-sm-6 col-xs-12">\
          <input type="text" id="createTR-maxVal' + pnum +'" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
        </div>\
        <button id="parameterPlus' + pnum + '" type="button" class="btn btn-info" style="width:24px; margin-left: 0; margin-top: 0; z-index: 3; position: relative; opacity: 0.9;"\
            onclick=addParameterForm("parameterCounter","parameterAdditionals");>\
            <i class="fa fa-plus" alt="Add parameters" title="Add parameters" style="margin-left: -5px"></i>\
        </button>\
        <button type="button" class="btn btn-info" id="parameterMinus' + pnum + '" style="width:24px; margin-left: 0; margin-top: 0; z-index: 3; position: relative; opacity: 0.9;"\
			onclick=removeParameterForm("parameterCounter","parameterAdditionals");>\
            <i class="fa fa-minus" alt="Remove parameters" title="Remove parameters" style="margin-left: -5px"></i>\
        <button id="paramSubmit' + pnum + '" type="button" class="btn btn-info" onclick=uploadTRFromForm("step-5",["createTR-nsdId","createTR-nsdVersion","createTR-nsFlav","createTR-nsInstLev","createTR-parId","createTR-minVal","createTR-maxVal"],"parameterCounter","response")>Submit</button>\
    </div></div>';
    
    $('#' + paramDiv).append(text);
    document.getElementById(paramCounter).innerHTML = pnum + 1;
}

function removeParameterForm(paramCounter) {
    var pnum = parseInt(document.getElementById(paramCounter).innerHTML);
    
	var plusBtnNum = pnum - 2;
	var plusBtnId = "parameterPlus" + plusBtnNum;
	document.getElementById(plusBtnId).style.visibility='visible';
    document.getElementById('paramSubmit' + plusBtnNum).style.visibility='visible';
	
	var minusBtnId = "parameterMinus" + plusBtnNum;
	
	if (document.getElementById(minusBtnId)) {
		document.getElementById(minusBtnId).style.visibility='visible';
	}
	
	var divNum = pnum - 1;
	var divId = "paramRemovable" + divNum;
	
	document.getElementById(paramCounter).innerHTML = pnum - 1;
	
	$('#' + divId).remove();
}

function createVSBlueprintsTable(data, tableId) {
    //console.log(JSON.stringify(data, null, 4));
    
    var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No VS Blueprints');
		table.innerHTML = '<tr>No VS Blueprints stored in database</tr>';
		return;
	}
    
    var role = getCookie('role');
    var cbacks = [];
    var names = [];
    
    if (role == 'ADMIN') {
        cbacks = ['blueprint_details.html?Id=', 'deleteVSBlueprint'];
        names = ['View Blueprint', 'Delete'];
    } else {
        cbacks = ['blueprint_details.html?Id=', 'createVSDForm'];
        names = ['View Blueprint', 'Create VS Descriptor'];
    }
    
	var btnFlag = true;
	var header = createTableHeaderByValues(['Id', 'Version', 'Name', 'Description', 'Configurable parameters', 'Vsd'], btnFlag, false);
	
    var columns = [['vsBlueprintId'], ['vsBlueprintVersion'], ['name'], ['vsBlueprint', 'description'], ['vsBlueprint', 'parameters'], ['activeVsdId']];
    var params = [data, tableId, btnFlag, names, cbacks, columns];
	//var conts = createVSBlueprintsTableContents(data, btnFlag, resId, names, cbacks, columns);
	table.innerHTML = header; // + conts;
    
    getAllVSDescriptors(params, createVSBlueprintsTableContents);
}

function createVSBlueprintsTableContents(vsDescriptors,  params) {
    //console.log(JSON.stringify(vsDescriptors, null, 4));
   
    var data = params[0];
    var tableId = params[1];
    var btnFlag = params[2];
    var names = params[3];
    var cbacks = params[4];
    var columns = params[5];
       
    var table = document.getElementById(tableId);
    
	var text = '<tbody>';
	
	for (var j = 0; j < data.length; j++) {
		
		var btnText = '';
		if (btnFlag) {
			btnText += createActionButton(data[j].vsBlueprintId, names, cbacks);
            
            var role = getCookie('role');
            
            if(role == 'ADMIN') {
                getAllTenants(['vsdModals', data[j].vsBlueprintId, data[j].vsBlueprint.parameters], createVSDescriptorModals);
            } else {
                var tenant = getCookie('username');
                createVSDescriptorModals(tenant, ['vsdModals', data[j].vsBlueprintId, data[j].vsBlueprint.parameters]);
            }
		}
		
		text += '<tr>' + btnText;
		for (var i = 0; i < columns.length; i ++) {
			var values = [];
			getValuesFromKeyPath(data[j], columns[i], values);
			//console.log(values);
			
			var subText = '<td>';
			var subTable = '<table class="table table-borderless">';
			
			if (data[j].hasOwnProperty(columns[i][0])) {
				if(values[0] instanceof Array) {
					for (var v in values[0]) {
                        if (values[0][v] instanceof Object){
                            //console.log(JSON.stringify(values[0], null, 4));
                            var value = values[0][v];
                            subTable += '<tr><td>' + value.parameterId + '</td><tr>';
                        } else {
                            if (columns[i][0] == 'activeVsdId') {
                                for (var h = 0; h < vsDescriptors.length; h++) {
                                    if (values[0][v] == vsDescriptors[h].vsDescriptorId) {
                                        subTable += '<tr><td><button type="button" class="btn btn-info btn-xs btn-block" onclick="location.href=\'vsd_details.html?Id=' + vsDescriptors[h].vsDescriptorId + '\'">' + vsDescriptors[h].name + '</button></td><tr>';
                                    }
                                }
                            } else {
                                subTable += '<tr><td>' + values[0][v] + '</td><tr>';
                            }
                        }
					}
                    subText += subTable + '</table>';
				} else {
					if (values[0] instanceof Object){
						//console.log(JSON.stringify(values[0], null, 4));
						var value = values[0];
						$.each(value, function(key, val){
							subTable += '<tr><td><b>' + key + '</b></td><td>' + val + '</td><tr>';
						});
						subText += subTable + '</table>';
					} else {
						subText += values[0];
					}
				}
			}			
			subText += '</td>';
			text += subText;
		}
		text += '</tr>';
	}
	
	text += '</tbody>';
	table.innerHTML += text;
}

function createVSBlueprintDetailsTable(data, params) {
    //console.log(JSON.stringify(data, null, 4));
    
    var table = document.getElementById(params[0]);
	if (!table) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No VS Blueprint');
		table.innerHTML = '<tr>No VS Blueprint stored in database</tr>';
		return;
	}
    console.log(JSON.stringify(data, null, 4));
	var btnFlag = false;
	var header = createTableHeaderByValues(['Id', 'Version', 'Name', 'Description', 'Configurable Parameters', 'VSD Id'], btnFlag, false);
	var cbacks = [];
	var names = [];
    var columns = [['vsBlueprintId'], ['vsBlueprintVersion'], ['name'], ['vsBlueprint', 'description'], ['vsBlueprint', 'parameters'], ['activeVsdId']];
	var conts = createVSBlueprintTableContents(data, btnFlag, names, cbacks, columns);
    table.innerHTML = header + conts;
    var image = document.getElementById(params[1]);
    image.src = data.vsBlueprint.imgUrl || defaultImage;
}

function createVSBlueprintTableContents(data, btnFlag, names, cbacks, columns) {	
	var text = '<tbody>';
	
    var btnText = '';
    if (btnFlag) {
        btnText += createActionButton(data[j].vsBlueprintId, names, cbacks);
    }
    
    text += '<tr>' + btnText;
    for (var i = 0; i < columns.length; i++) {
        var values = [];
        getValuesFromKeyPath(data, columns[i], values);
        //console.log(values);
        
        var subText = '<td>';
        var subTable = '<table class="table table-borderless">';
        
        if (data.hasOwnProperty(columns[i][0])) {
            if(values[0] instanceof Array) {
                for (var v in values[0]) {
                    if (values[0][v] instanceof Object){
                        //console.log(JSON.stringify(values[0], null, 4));
                        var value = values[0][v];
                        subTable += '<tr><td>' + value.parameterId + '</td><tr>';
                    } else {
                        subTable += '<tr><td>' + values[0][v] + '</td><tr>';
                    }
                }
                subText += subTable + '</table>';
            } else {
                if (values[0] instanceof Object){
                    //console.log(JSON.stringify(values[0], null, 4));
                    var value = values[0];
                    $.each(value, function(key, val){
                        subTable += '<tr><td><b>' + key + '</b></td><td>' + val + '</td><tr>';
                    });
                    subText += subTable + '</table>';
                } else {
                    subText += values[0];
                }
            }
        }			
        subText += '</td>';
        text += subText;
    }
    text += '</tr>';
	
	text += '</tbody>';
	return text;
}

function createVSDescriptorModals(data, params) {
    
    var elemId = params[0];
    var vsbId = params[1];
    var qos = params[2];
    
    var div = document.getElementById(elemId);
    
    var text = '<div id="createVSDForm_' + vsbId + '" class="modal fade bs-example-modal-md in" tabindex="-1" role="dialog" aria-hidden="true">\
        <div class="modal-dialog modal-md">\
            <div class="modal-content">\
                <div class="modal-header">\
                  <button type="button" class="close" data-dismiss="modal" aria-label="Cancel"><span aria-hidden="true">×</span>\
                  </button>\
                  <h4 class="modal-title" id="myModalLabel">Create VS Descriptor for Blueprint ' + vsbId + '</h4>\
                </div>\
                <div class="modal-body">\
                  <div class="form-group">\
                    <form id="VSDForm_' + vsbId + '" data-parsley-validate="" class="form-horizontal form-label-left" novalidate="">\
                        <div class="form-group">\
                          <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">VSB Id <!-- span class="required">*</span -->\
                          </label>\
                          <div class="col-md-6 col-sm-6 col-xs-12">\
                            <input id="createVSD_' + vsbId +'_vsbId" value="' + vsbId + '" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text" disabled>\
                          </div>\
                        </div>\
                        <div class="form-group">\
                          <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Tenant Id <!-- span class="required">*</span -->\
                          </label>\
                          <div class="col-md-6 col-sm-6 col-xs-12">\
                            <select id="createVSD_' + vsbId +'_tenantId" autocomplete="off" name="input-flavour" class="form-control col-md-7 col-xs-12">';
    if (data instanceof Array) {
        for (var i in data) {
            var tenant = data[i].username;
            text += '<option value="' + tenant + '">' + tenant + '</option>';
        }
    } else {
        text += '<option value="' + data + '">' + data + '</option>';
    }
    text += '</select>\
            </div></div>\
            <div class="form-group">\
                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Name <!-- span class="required">*</span -->\
                </label>\
                <div class="col-md-6 col-sm-6 col-xs-12">\
                    <input id="createVSD_' + vsbId +'_name" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                </div>\
            </div>\
            <div class="form-group">\
                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Version <!-- span class="required">*</span -->\
                </label>\
                <div class="col-md-6 col-sm-6 col-xs-12">\
                    <input id="createVSD_' + vsbId +'_version" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                </div>\
          </div></br><h4 class="modal-title" id="myModalLabel">QoS Parameters</h4>';
    var paramNum = 0;
    for (var j in qos) {
        $.each(qos[j], function(key, val){
            if (key == 'parameterName') {
                text += '<div class="form-group">\
                       <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">' + val + ' <!-- span class="required">*</span -->\
                       </label>\
                       <div id="' + vsbId +'_qos' + j + '" style="display:none;">' + val + '</div>\
                       <div class="col-md-6 col-sm-6 col-xs-12">\
                         <input id="createVSD_' + vsbId +'_qos' + j + '" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                       </div>\
                     </div>';
            }
        });
        paramNum += 1;
    }
    		
    text += '</br><div class="form-group">\
                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Slice Service Type <!-- span class="required">*</span -->\
                </label>\
                <div class="col-md-6 col-sm-6 col-xs-12">\
                    <select id="createVSD_' + vsbId +'_sst" autocomplete="off" name="input-flavour" class="form-control col-md-7 col-xs-12">\
                        <option value="NONE">NONE</option>\
                        <option value="EMBB">EMBB</option>\
                        <option value="URLLC">URLLC</option>\
                        <option value="M_IOT">M_IOT</option>\
                        <option value="ENTERPRISE">ENTERPRISE</option>\
                        <option value="NFV_IAAS">NFV_IAAS</option>\
                    </select>\
                </div>\
            </div>\
            <div class="form-group">\
                <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Management Type <!-- span class="required">*</span -->\
                </label>\
                <div class="col-md-6 col-sm-6 col-xs-12">\
                    <select id="createVSD_' + vsbId +'_mt" autocomplete="off" name="input-flavour" class="form-control col-md-7 col-xs-12">\
                        <option value="PROVIDER_MANAGED">PROVIDER_MANAGED</option>\
                        <option value="TENANT_MANAGED">TENANT_MANAGED</option></select></div></div>';
   
    text += '<div class="form-group">\
            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Public <!-- span class="required">*</span -->\
            </label>\
            <div class="col-md-6 col-sm-6 col-xs-12">\
                <input id="createVSD_' + vsbId +'_public" type="checkbox" name="public" value="true">\
            </div></form>\
        </div>\
    </div>\
    <div class="modal-footer">\
        <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick=clearForms("VSDForm_' + vsbId + '")>Cancel</button>\
        <button type="submit" class="btn btn-info" data-dismiss="modal" \
        onclick=createVSDFromForm(["createVSD_' + vsbId +'_vsbId","createVSD_' + vsbId +'_tenantId","createVSD_' + vsbId +'_name","createVSD_' + vsbId +'_version","createVSD_' + vsbId +'_qos","createVSD_' + vsbId +'_sst","createVSD_' + vsbId +'_mt","createVSD_' + vsbId +'_public"],' + paramNum + ',"response")>Submit</button>\
    </div></div></div></div>';
    
    div.innerHTML += text;
}


