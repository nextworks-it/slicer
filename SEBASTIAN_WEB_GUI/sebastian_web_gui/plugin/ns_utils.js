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

function readNSInstances(tableId) {
    getNSInstanceIds(tableId, createNSInstancesTable);
}

function fillNSICounter(data, elemId) {
    var countDiv = document.getElementById(elemId);
	
	countDiv.innerHTML = data.length;
}

function readNSInstance(divId, nsiId, params) {
    params.push(divId);
    getNSInstance(nsiId, params, createNSInstancesTableContent);
}

function getNSInstanceIds(elemId, callback) {  // TODO url
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/admin/nsmf/networksliceids', callback, elemId);
}

function getNSInstance(nsiId, params, callback) {  // TODO url
    getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/admin/nsmf/networkslice/' + nsiId, callback, params);
}

function createNSInstancesTable(data, tableId) {

    var l = data.length;
    
    var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || l < 1) {
		console.log('No NS Instances');
		table.innerHTML = '<tr>No NS Instances stored in database</tr>';
		return;
	}
    
    var btnFlag = false;
        
    // TODO check structure
	var header = createTableHeaderByValues(['Id', 'Name', 'Description', 'Network Service Descriptor', 'Network Service Details', 'Status'], btnFlag, false);
    var columns = [['nsiId'], ['name'], ['description'], ['nsdId'], ['dfId'], ['status']];
    table.innerHTML = header + '<tbody>';
    
    for (var i = 0; i < l; i++) {
        readNSInstance(tableId, data[i], [l, i, columns]);
    }
}

function createNSInstancesTableContent(data, params) {

    var l = params[0];
    var i = params[1];
    var columns = params[2];
    var tableId = params[3];
    
    var table = document.getElementById(tableId);
    
    var text = '';
		    
    text += '<tr>';
    for (var i in columns) {
        var values = [];
        getValuesFromKeyPath(data, columns[i], values);
        //console.log(values);
        
        var subText = '<td>';
        var subTableHead = '<table class="table table-borderless">';
        
        if (data.hasOwnProperty(columns[i][0])) {
            if (columns[i][0] == 'vsdId') {  // TODO change to nsi details
                subText += '<button type="button" class="btn btn-info btn-xs btn-block" onclick="location.href=\'vsd_details.html?Id=' + values + '\'">' + values + '</button>';
            } else if (columns[i][0] == 'dfId') {
                subText += subTableHead.repeat(1); // clone string
                subText += '<tr><td> Deployment flavour: ' + values + '</td></tr>';
                subText += '<tr><td> Instantiation level: ' + data.instantiationLevelId + '</td></tr>';
                subText += '</table>';
            } else if(columns[i][0] == 'status' && values == 'FAILED') {
                    subText += values + '<br>' + data.errorMessage;
            } else {
                subText += values;
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

function fillVSICounter(data, elemId) {
    var countDiv = document.getElementById(elemId);
	
	//console.log(JSON.stringify(data, null, 4));
	countDiv.innerHTML = data.length;
}
