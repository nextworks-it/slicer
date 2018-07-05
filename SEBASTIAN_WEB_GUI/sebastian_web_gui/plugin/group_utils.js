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

function fillGroupCounter(data, elemId) {
	var countDiv = document.getElementById(elemId);
			
	countDiv.innerHTML = data.length;
}

function uploadGroupFromForm(formIds) {
	var groupId = document.getElementById(formIds[0]).value;
	postGroup(groupId, 'Group have been successfully created','Error while creating Group', showResultMessage);
}

function postGroup(groupId, okMsg, errMsg, callback) {
    postToURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId, callback, [okMsg, errMsg]);
}

function readGroups(tableId) {
	getGroups(tableId, createGroupTable);
}

function readGroupsForTenants(formIds) {
    getGroups(formIds, fillTenantsGroupSelects);   
}

function getGroups(elemId, callback) {
	getJsonFromURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/admin/group', callback, elemId);
}

function deleteGroup(groupId) {
	deleteRequestToURLWithAuth('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId, showResultMessage, ["Group successfully deleted", "Unable to delete Group"]);
}

function fillTenantsGroupSelects(data, formIds) {
    var selectedGroups = document.getElementById(formIds[0]);
    var groupsForForm = document.getElementById(formIds[1]);
    
    if(data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            selectedGroups.innerHTML += '<option value="' + data[i].name + '">' + data[i].name + '</option>';
            groupsForForm.innerHTML += '<option value="' + data[i].name + '">' + data[i].name + '</option>';
        }
        readTenants("tenantTable");
    } else {
        document.getElementById("tenantTable").innerHTML += '<tr>No Tenant stored in database</tr>';
    }
}

function createGroupTable(data, tableId) {
    var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No Group');
		table.innerHTML = '<tr>No Group stored in database</tr>';
		return;
	}
    //console.log(JSON.stringify(data, null, 4));
	var btnFlag = true;
	var header = createTableHeaderByValues(['Name', 'Tenants'], btnFlag, false);
	var cbacks = ['deleteGroup'];
	var names = ['Delete'];
    var columns = [['name'], ['tenants']];
	var conts = createGroupTableContents(data, btnFlag, names, cbacks, columns);
	table.innerHTML = header + conts;
}

function createGroupTableContents(data, btnFlag, names, cbacks, columns) {
    var text = '<tbody>';
	
	for (var j = 0; j < data.length; j++) {
		
		var btnText = '';
		if (btnFlag) {
			btnText += createActionButton(data[j].name, names, cbacks);
		}
		
		text += '<tr>' + btnText;
		for (var i = 0; i < columns.length; i++) {
			var values = [];
			getValuesFromKeyPath(data[j], columns[i], values);
			//console.log(values);
			
			var subText = '<td>';
			var subTable = '<table class="table table-borderless">';
			
			if (data[j].hasOwnProperty(columns[i][0])) {
				if(values[0] instanceof Array) {
                    //console.log(JSON.stringify(values[0], null, 4));
					for (var v = 0; v < values[0].length; v++) {
						subTable += '<tr><td>' + values[0][v].username + '</td><tr>';
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
	return text;
}