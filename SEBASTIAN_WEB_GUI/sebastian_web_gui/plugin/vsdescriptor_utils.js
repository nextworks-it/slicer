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

function fillVSDCounter(data, elemId) {
    var countDiv = document.getElementById(elemId);
	
	//console.log(JSON.stringify(data, null, 4));
	countDiv.innerHTML = data.length;
}

function fillVSICounter(data, elemId) {
    var countDiv = document.getElementById(elemId);
	
	//console.log(JSON.stringify(data, null, 4));
	countDiv.innerHTML = data.length;
}

function readVSDescriptors(tableId) {
    getAllVSDescriptors(tableId, createVSDescriptorsTable);
}

function getAllVSDescriptors(params, callback) {
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor', callback, params);
}

function readVSDescriptor(tableId) {
    getVSDescriptor(tableId, createVSDescriptorDetailsTable);    
}

function getVSDescriptor(elemId, callback) {
    var id = getURLParameter('Id');
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor/' + id, callback, elemId);
}

function deleteVSDescriptor(vsDescriptorId) {
    deleteRequestToURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor/' + vsDescriptorId, showResultMessage, ["VS descriptor successfully deleted", "Unable to delete VS descriptor"]);
}

function readVSInstances(tableId) {
    getVSInstanceIds(tableId, createVSInstancesTable);
}

function getVSInstanceIds(elemId, callback) {
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vsId', callback, elemId);
}

function readVSInstance(divId, vsiId, params) {
    params.push(divId);
    getVSInstance(vsiId, params, createVSInstancesTableContent);
}

function getVSInstance(vsiId, params, callback) {
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs/' + vsiId, callback, params);
}

function terminateVSInstance(vsiId) {
    postToURLWithAuth(
        'http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs/' + vsiId + '/terminate',
        showResultMessage, 
        [
            "VS instances with id " + vsiId + " successfully teminated",
            "Unable to terminate VS instance with id " + vsiId
        ]);
}

function purgeVSInstance(vsiId) {
    deleteRequestToURLWithAuth(
        'http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs/' + vsiId,
        showResultMessage, 
        [
            "VS instances with id " + vsiId + " successfully purged",
            "Unable to purge VS instance with id " + vsiId
        ]);
}

function instantiateVSDFromForm(params) {
    var vsdId = document.getElementById(params[0]).value;
    var name = document.getElementById(params[1]).value;
    var tenant = document.getElementById(params[2]).value;
    var description = document.getElementById(params[3]).value;
    
    var jsonObj = JSON.parse('{}');
    
    jsonObj.vsdId = vsdId;
    jsonObj.name = name;
    jsonObj.tenantId = tenant;
    jsonObj.description = description;
    
    var json = JSON.stringify(jsonObj, null, 4);
    console.log(json);
    
    postJsonToURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs', json, showResultMessage, ["Instatiation request for VS Descriptor " + vsdId + " successfully submitted", "Unable to instantiate VS descriptor with id " + vsdId]);
}

function createVSDescriptorsTable(data, tableId) {
    //console.log(JSON.stringify(data, null, 4));
    
    var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No VS Descriptors');
		table.innerHTML = '<tr>No VS Descriptors stored in database</tr>';
		return;
	}
    
    var role = getCookie('role');
    var cbacks = [];
    var names = [];
    
    if (role == 'ADMIN') {
        cbacks = ['vsd_details.html?Id='];
        names = ['View'];
    } else {
        cbacks = ['vsd_details.html?Id=', 'instantiateVSDescriptor', 'deleteVSDescriptor'];
        names = ['View', 'Instantiate', 'Delete'];
    }
    
	var btnFlag = true;
	var header = createTableHeaderByValues(['Id', 'Name', 'Version', 'Vsb Id', 'Slice Service Type', 'Management Type', 'QoS Parameters'], btnFlag, false);
    var columns = [['vsDescriptorId'], ['name'], ['version'], ['vsBlueprintId'], ['sst'], ['managementType'], ['qosParameters']];
    
    var conts = '<tbody>';
    for (var i = 0; i < data.length; i++) {
        conts += createVSDescriptorsTableContents(data[i], btnFlag, names, cbacks, columns);
    }
	table.innerHTML = header + conts + '</tbody>';
}

function createVSDescriptorDetailsTable(data, tableId) {
    //console.log(JSON.stringify(data, null, 4));
    
    var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No VS Descriptors');
		table.innerHTML = '<tr>No VS Descriptors stored in database</tr>';
		return;
	}
	var btnFlag = false;
	var header = createTableHeaderByValues(['Id', 'Name', 'Version', 'Vsb Id', 'Slice Service Type', 'Management Type', 'QoS Parameters'], btnFlag, false);
	var cbacks = [];
	var names = [];
    var columns = [['vsDescriptorId'], ['name'], ['version'], ['vsBlueprintId'], ['sst'], ['managementType'], ['qosParameters']];
    var conts = '<tbody>' + createVSDescriptorsTableContents(data, btnFlag, names, cbacks, columns);
	table.innerHTML = header + conts + '</tbody>';
}

function createVSDescriptorsTableContents(data, btnFlag, names, cbacks, columns) {
    console.log(JSON.stringify(data, null, 4));
    
	var text = '';
		
    var btnText = '';
    if (btnFlag) {
        btnText += createActionButton(data.vsDescriptorId, names, cbacks);
        createInstantiateVSDModalDialog(data.vsDescriptorId);
    }
    
    text += '<tr>' + btnText;
    for (var i = 0; i <  columns.length; i++) {
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
                    if (columns[i][0] == 'vsBlueprintId') {
                        subText += '<button type="button" class="btn btn-info btn-xs btn-block" onclick="location.href=\'blueprint_details.html?Id=' + values[0] + '\'">' + values[0] + '</button>';
                    } else {
                        subText += values[0];
                    }
                }
            }
        }			
        subText += '</td>';
        text += subText;
    }
    text += '</tr>';
	
	return text;
}

function createInstantiateVSDModalDialog(vsdId) {
    /*jshint multistr: true */
	var text = ' <div id="instantiateVSDescriptor_' + vsdId + '" class="modal fade bs-example-modal-md in" tabindex="-1" role="dialog" aria-hidden="true">\
              <div class="modal-dialog modal-md">\
                <div class="modal-content">\
                  <div class="modal-header">\
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel"><span aria-hidden="true">×</span>\
                    </button>\
                    <h4 class="modal-title" id="myModalLabel">Instantiate VS Descriptor</h4>\
                  </div>\
                  <div class="modal-body">\
                    <div class="form-group">\
                      <form id="createNSDIdForm" data-parsley-validate="" class="form-horizontal form-label-left" novalidate="">\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Id <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input type="text" id="instVSDId-id_' + vsdId + '" required="required" class="form-control col-md-7 col-xs-12" value="' + vsdId + '" readonly>\
                            </div>\
                          </div>\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Name <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input type="text" id="instVSDId-name_' + vsdId + '" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
                            </div>\
                          </div>\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Tenant <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input type="text" id="instVSDId-tenant_' + vsdId + '" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
                            </div>\
                          </div>\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Description <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input type="text" id="instVSDId-description_' + vsdId + '" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
                            </div>\
                          </div>\
                        </form>\
                    </div>\
                  </div>\
                  <div class="modal-footer">\
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick=clearForms("instantiateVSDescriptor_' + vsdId + '")>Cancel</button>\
                    <button type="submit" class="btn btn-info" data-dismiss="modal"\
                            onclick=instantiateVSDFromForm(["instVSDId-id_' + vsdId + '","instVSDId-name_' + vsdId + '","instVSDId-tenant_' + vsdId + '","instVSDId-description_' + vsdId + '"],"response")>Submit</button>\
                  </div>\
                </div>\
              </div>\
            </div>';
                  
    var instantiateModalDiv = document.getElementById('instantiateModalDiv');
	instantiateModalDiv.innerHTML += text;
}

function createVSInstancesTable(data, tableId) {
    //console.log(JSON.stringify(data, null, 4));
    //data = ["11", "13"];
    
    var l = data.length;
    
    var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || l < 1) {
		console.log('No VS Instances');
		table.innerHTML = '<tr>No VS Instances stored in database</tr>';
		return;
	}
    
    var role = getCookie('role');
    var cbacks = [];
    var names = [];
    var btnFlag = true;
    
    if (role == 'ADMIN') {
        cbacks = [];
        names = [];
        btnFlag = false;
    } else {
        cbacks = ['terminateVSInstance', 'purgeVSInstance'];
        names = ['Terminate', 'Remove Entry'];
    }
    	
	var header = createTableHeaderByValues(['Id', 'Name', 'Description', 'Vsd Id', 'Sap', 'Status'], btnFlag, false);
    var columns = [['vsiId'], ['name'], ['description'], ['vsdId'], ['externalInterconnections'], ['status']];
    table.innerHTML = header + '<tbody>';
    
    for (var i = 0; i < l; i++) {
        readVSInstance(tableId, data[i], [l, i, cbacks, names, columns, btnFlag]);
    }
}

function parseSap(data, status) {
    var tableHead = '<table class="table table-borderless">';
    if (!(data instanceof Array)) {
        console.log('Error: ' + json.stringify(data) + ' is not an array.');
        return 'ERROR: Could not parse SAP info';
    }
    // External table
    var tableHtml = tableHead.repeat(1); // clone string
    for (var i in data) {
        var sap = data[i];
        tableHtml += '<tr><td>'
        // Internal table (sap table)
        tableHtml += tableHead.repeat(1); // clone string
        tableHtml += '<tr><th>' + sap.sapdId + '</th></tr>';
        if (status == 'INSTANTIATED') {
            for (var j in sap.userAccessInfo) {
                var uai = sap.userAccessInfo[j];
                tableHtml += '<tr><td>' + uai.vnfdId + '</td><td>' + uai.address + '</td></tr>';
            }
        }
        tableHtml += '</table></td></tr>';
        // close sap table and new row in external table
    }
    tableHtml += '</table>';
    // close external table
    return tableHtml;
}

function createVSInstancesTableContent(data, params) {
        
    /*data = JSON.parse('{\
        "vsiId": "13",\
        "name": "CDN_small",\
        "description": "CDN service for max 1000 users",\
        "vsdId": "9",\
        "status": "INSTANTIATED",\
        "errorMessage": null,\
        "externalInterconnections": [{\
            "sapInstanceId": "5e4b258e-6be0-46c2-96fe-207a4c97eb0d",\
            "sapdId": "mgtSap",\
            "sapName": "SAP-CDN_small-mgtSap440",\
            "description": "SAP-CDN_small-mgtSap440",\
            "address": null\
        }, {\
            "sapInstanceId": "02b31b09-bcc4-4fbd-ae41-d0b09f0c6b9f",\
            "sapdId": "videoSap",\
            "sapName": "SAP-CDN_small-videoSap440",\
            "description": "SAP-CDN_small-videoSap440",\
            "address": null\
        }],\
        "internalInterconnections": {}\
    }');*/
    
    var l = params[0];
    var i = params[1];
    var cbacks = params[2];
    var names = params[3];
    var columns = params[4];
    var btnFlag = params[5];
    var tableId = params[6];
    
    var table = document.getElementById(tableId);
    
    var text = '';
                   
    var btnText = '';
    if (btnFlag) {
        btnText += createActionButton(data.vsiId, names, cbacks);
    }
    
    text += '<tr>' + btnText;
    for (var i in columns) {
        var values = [];
        getValuesFromKeyPath(data, columns[i], values);
        //console.log(values);
        
        var subText = '<td>';
        var subTableHead = '<table class="table table-borderless">';
        
        if (data.hasOwnProperty(columns[i][0])) {
            if (values instanceof Array && values.length > 1) {
                var subTable = subTableHead.repeat(1); // clone
                for (var v in values) {
                    subTable += '<tr><td>' + values[v] + '</td><tr>';
                }
                subText += subTable + '</table>';
            } else {
                if (columns[i][0] == 'vsdId') {
                    subText += '<button type="button" class="btn btn-info btn-xs btn-block" onclick="location.href=\'vsd_details.html?Id=' + values + '\'">' + values + '</button>';
                } else if (columns[i][0] == 'externalInterconnections') {
                    var saps = values[0];
                    subText += parseSap(saps, data.status);
                } else {
                    if(columns[i][0] == 'status' && values == 'FAILED') {
                        subText += values + '<br>' + data.errorMessage;
                    } else {
                        subText += values;
                    }
                }
            }
        }			
        subText += '</td>';
        text += subText;
    }
    text += '</tr>';
	
	table.innerHTML += text;
    
    if (i == l - 1) {
        table.innerHTML += '</tbody>';
    }
    
}