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

function fillTenantCounter(elemId, data, resId) {
	var countDiv = document.getElementById(elemId);
	
	//console.log(JSON.stringify(data, null, 4));
	countDiv.innerHTML = data.length;
}

function uploadTenantFromForm(formIds, resId) {
	var jsonObj = JSON.parse('{}');
	var groupId = document.getElementById(formIds[0]).value;
	//console.log(groupId);
	jsonObj['username'] = document.getElementById(formIds[1]).value;
	jsonObj['password'] = document.getElementById(formIds[2]).value;
	var json = JSON.stringify(jsonObj, null, 4);
	postTenant(json, groupId, resId, 'Tenant have been successfully created','Error while creating Tenant', showResultMessage);
		
}

function postTenant(data, groupId, resId, okMsg, errMsg, callback) {
    postJsonToURL('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId + '/tenant', data, resId, okMsg, errMsg, callback);
}

function readTenants(tableId, resId) {
	var groupId = document.getElementById('selectedGroup').value;
	getTenants(groupId, tableId, resId , createTenantTable);
}

function getTenants(groupId, elemId, resId, callback) {
	getJsonFromURL('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId + '/tenant', elemId, callback, resId, null, null);
}

function getAllTenants(elemId, params, callback) {
	getJsonFromURL('http://' + vsAddr + ':' + vsPort + '/vs/admin/groups/tenants', elemId, callback, params, null, null);
}

function deleteTenant(tenantId, resId) {
	var groupId = document.getElementById('selectedGroup').value;
	var id = tenantId.split('|')[0];
	deleteRequestToURL('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId + '/tenant/' + id, resId, "Tenant successfully deleted", "Unable to delete Tenant", showResultMessage);
}

function createTenantSLAFromForm(formIds, resId, constrNum, tenantId, type) {
	var jsonObj = JSON.parse('{}');
	
	jsonObj['tenant'] = tenantId;
	
	var status = document.getElementById(formIds[0]).value;
	jsonObj['slaStatus'] = status;
	
	var constrs = [];
	var scope = document.getElementById(formIds[1]).value;
		
	var cnum = document.getElementById(constrNum).innerHTML;
	
	for (var i = 0; i < cnum; i++) {
		var location = 'nextworks';
		if (type == 'MEC')
			location = document.getElementById(formIds[2] + i).value;
		
		var tempConstr = JSON.parse('{}');
		tempConstr['scope'] = scope;
		tempConstr['location'] = location;
		
		var tempRes = JSON.parse('{}');
		var tempRam = document.getElementById(formIds[3] + i).value;
		var tempCpu = document.getElementById(formIds[4] + i).value;
		var tempStorage = document.getElementById(formIds[5] + i).value;
		tempRes['memoryRAM'] = tempRam;
		tempRes['vCPU'] = tempCpu;
		tempRes['diskStorage'] = tempStorage;
		tempConstr['maxResourceLimit'] = tempRes
		
		constrs.push(tempConstr);
	}
	
	jsonObj['slaConstraints'] = constrs;
	
	var json = JSON.stringify(jsonObj, null, 4);
	
	console.log(json);
	createTenantSLA(json, 'selectedGroup', tenantId, resId);
}

function createTenantSLA(data, groupId, tenantId, resId) {
	postJsonToURL('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId + '/tenant/' + tenantId + '/sla', data, resId, 'SLA for tenant ' + tenantId + ' successfully created.', 'Unable to create SLA for tenant ' + tenantId, showResultMessage);
}

function readTenantSLAs(tableIds, resId) {
	var ids = getURLParameter('Ids').split('|');
	
	var tenantId = ids[0];
	var groupId = ids[1];
	
	getTenantSLAs(tableIds, tenantId, groupId, resId, createTenantSLAsTable);	
}

function getTenantSLAs(elemIds, tenantId, groupId, resId, callback) {
	getJsonFromURL('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId + '/tenant/' + tenantId + '/sla', elemIds, callback, resId, null, null);
}

function deleteTenantSLA(slaId, resId) {
	var ids = getURLParameter('Ids').split('|');
	
	var tenantId = ids[0];
	var groupId = ids[1];
	
	deleteRequestToURL('http://' + vsAddr + ':' + vsPort + '/vs/admin/group/' + groupId + '/tenant/' + tenantId + '/sla/' + slaId, resId, "Tenant SLA successfully deleted", "Unable to delete Tenant SLA", showResultMessage);
}

function createTenantTable(tableId, data, resId) {
  var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No Tenant');
		table.innerHTML = '<tr>No Tenant stored in database</tr>';
		return;
	}
  //console.log(JSON.stringify(data, null, 4));
	var btnFlag = true;
	var header = createTableHeaderByValues(['Username', 'Used Resources'], btnFlag, false);
	var cbacks = ['sla.html?Ids=', 'createTenantSLAMECForm', 'createTenantSLACloudForm','createTenantSLAGlobalForm', 'deleteTenant'];
	var names = ['View SLA', 'Create SLA for MEC Resources', 'Create SLA for Cloud Resources', 'Create SLA for Global Resources','Delete'];
  var columns = [['username'], ['allocatedResources']];
	var conts = createTenantTableContents(data, btnFlag, resId, names, cbacks, columns);
	table.innerHTML = header + conts;
}

function createTenantTableContents(data, btnFlag, resId, names, cbacks, columns) {	
	var text = '<tbody>';
	
	for (var j in data) {
		
		var btnText = '';
		if (btnFlag) {
			btnText += createActionButton(data[j]['username'], resId, names, cbacks);
			
			var cenabled = true;
			var genabled = true;
			createTenantSLAModals(data[j]['username'], 'MEC', true);
			
			var slas = data[j]['sla'];
			
			for (var s in slas) {
				var constrs = slas[s]['slaConstraints'];
				for (var c in constrs) {
					if (constrs[c]['scope'] == 'CLOUD_RESOURCE') {
						cenabled = false;
					}
					if (constrs[c]['scope'] == 'GLOBAL_VIRTUAL_RESOURCE') {
						genabled = false;
					}
				}
			}
			createTenantSLAModals(data[j]['username'], 'Cloud', cenabled);
			createTenantSLAModals(data[j]['username'], 'Global', genabled);
		}
		
		text += '<tr>' + btnText;
		for (var i in columns) {
			var values = [];
			getValuesFromKeyPath(data[j], columns[i], values);
			//console.log(values);
			
			var subText = '<td>';
			var subTable = '<table class="table table-borderless">';
			
			if (data[j].hasOwnProperty(columns[i][0])) {
				if(values[0] instanceof Array) {
					for (var v in values[0]) {
						subTable += '<tr><td>' + values[0][v] + '</td><tr>';
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

function createTenantSLAsTable(tableIds, data, resId) {
	var tableMEC = document.getElementById(tableIds[0]);
	var tableCloud = document.getElementById(tableIds[1]);
	var tableGlobal = document.getElementById(tableIds[2]);
	
	console.log(JSON.stringify(data, null, 4));
	
	console.log(tableIds);
	
	if (!tableMEC || !tableCloud || !tableGlobal) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No Tenant SLA');
		tableMEC.innerHTML = '<tr>No Tenant MEC SLA stored in database</tr>';
		tableCloud.innerHTML = '<tr>No Tenant Cloud SLA stored in database</tr>';
		tableGlobal.innerHTML = '<tr>No Tenant Global SLA stored in database</tr>';
		return;
	}
  //console.log(JSON.stringify(data, null, 4));
	var btnFlag = true;
	var header = createTableHeaderByValues(['Id', 'Constraints', 'Status'], btnFlag, false);
	var cbacks = ['deleteTenantSLA'];
	var names = ['Delete'];
  var columns = [['id'], ['slaConstraints'], ['slaStatus']];
	
	tableMEC.innerHTML += header + '<tbody>';
	tableCloud.innerHTML += header + '<tbody>';
	tableGlobal.innerHTML += header + '<tbody>';
	
	var text = '';
	var type = 0;
	for (var j in data) {
		
		var btnText = '';
		if (btnFlag) {
			btnText += createActionButton(data[j]['id'], resId, names, cbacks);
		}
		
		text += '<tr>' + btnText;
		for (var i in columns) {
			var values = [];
			getValuesFromKeyPath(data[j], columns[i], values);
			//console.log(values);
			
			var subText = '<td>';
			var subTable = '<table class="table table-borderless">';
						
			if (data[j].hasOwnProperty(columns[i][0])) {
				if(values[0] instanceof Array) {
					var subHeader = '';
					if (values[0][0]['scope'] == 'MEC_RESOURCE') {
						subHeader = createTableHeaderByValues(['Max Ram', 'Max vCPUs', 'Max vStorage', 'Location'], false, false);
					} else {
						subHeader = createTableHeaderByValues(['Max Ram', 'Max vCPUs', 'Max vStorage'], false, false);
						if (values[0][0]['scope'] == 'CLOUD_RESOURCE') {
							type = 1;
						} else {
							type = 2;
						}
					}
					
					subTable += subHeader + '<tbody>';
					for (var v in values[0]) {
						var value = values[0][v];
						subTable += '<tr><td>' + value['maxResourceLimit']['memoryRAM'] + '</td><td>' + value['maxResourceLimit']['vCPU'] + '</td><td>' + value['maxResourceLimit']['diskStorage'] + '</td>';
						if (type == 0)
							subTable += '<td>' + value['location'] + '</td>';
						subTable += '</tr>';
					}
					subText += subTable + '</tbody></table>';
				} else {
					subText += values[0];
				}
			}			
			subText += '</td>';
			text += subText;
		}
		text += '</tr>';
		
		if (type == 0)
			tableMEC.innerHTML += text;
		else if (type == 1)
			tableCloud.innerHTML += text;
		else if (type == 2)
			tableGlobal.innerHTML += text;
			
		text = '';
		type = 0;
	}
	
	tableMEC.innerHTML += '</tbody>';
	tableCloud.innerHTML += '</tbody>';
	tableGlobal.innerHTML +='</tbody>';
}

function createTenantSLAModals(tenantId, type, enabled) {
		var modalsDiv = document.getElementById("createSLAModalsDiv");
		
		var formtype = '';
		
		if (type == 'MEC')
			formtype = 'MEC_RESOURCE';
		if (type == 'Cloud')
			formtype = 'CLOUD_RESOURCE';
		if (type == 'Global')
			formtype = 'GLOBAL_VIRTUAL_RESOURCE';
		
		var text = '<div id="createTenantSLA' + type + 'Form_' + tenantId + '" class="modal fade bs-example-modal-md in" tabindex="-1" role="dialog" aria-hidden="true">\
              <div class="modal-dialog modal-md">\
                <div class="modal-content">\
                  <div class="modal-header">\
                    <button type="button" class="close" data-dismiss="modal" aria-label="Cancel"><span aria-hidden="true">×</span>\
                    </button>\
                    <h4 class="modal-title" id="myModalLabel">Add ' + type + 'SLA for tenant ' + tenantId + '</h4>\
                  </div>\
                  <div class="modal-body">\
                    <div class="form-group">\
                      <form id="' + type + 'SLAForm_' + tenantId + '" data-parsley-validate="" class="form-horizontal form-label-left" novalidate="">\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Operational State <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <select id="' + type + 'createSLA_' + tenantId +'_status" autocomplete="off" name="input-flavour" class="form-control col-md-7 col-xs-12">\
																<option value="ENABLED">ENABLED</option>\
																<option value="DISABLED">DISABLED</option>\
                              </select>\
                            </div>\
                          </div>\
													</br>\
													<div id="' + type + 'SLAconstraints_' + tenantId +'">\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Scope <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
															<input id="' + type + 'createSLA_' + tenantId +'_scope" value="' + formtype + '" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text" disabled>\
                            </div>\
                          </div></br>';
		if (type == 'MEC') {
			text += '<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Location <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_location0" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>\
                          </div>';
		}
		
		text += '<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">vRAM <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_ram0" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>\
                          </div>\
													<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">vCPU <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_cpu0" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>\
                          </div>\
													<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">vStorage <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_storage0" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>';
		if (type == 'MEC') {
			text +=	'<button id="createSLA_' + tenantId + 'plus0" type="button" class="btn btn-info" style="width:24px; margin-left: 0; margin-top: 0; z-index: 3; position: relative; opacity: 0.9;"\
															onclick=addSLAConstraintsForm("' + type + 'SLAconstraints_' + tenantId +'","' + type + 'constraintsNum_' + tenantId + '","' + tenantId + '","' + type +'");>\
                              <i class="fa fa-plus" alt="Add constraints" title="Add constraints" style="margin-left: -5px"></i>\
                            </button>';
		}
    text += '</div>\
													</div>\
													<div id="' + type + 'constraintsNum_' + tenantId + '" style="display:none">1</div>\
													<div id="' + type + 'createSLA_' + tenantId +'_additionals"></div>\
                        </form>\
                    </div>\
                  </div>\
                  <div class="modal-footer">\
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick=clearForms("AddTenantForm")>Cancel</button>\
                    <button id="' + type + 'createSLA_' + tenantId +'_submit" type="submit" class="btn btn-info" data-dismiss="modal"\
                            onclick=createTenantSLAFromForm(["' + type + 'createSLA_' + tenantId +'_status","' + type + 'createSLA_' + tenantId +'_scope","' + type + 'createSLA_' + tenantId +'_location","' + type + 'createSLA_' + tenantId +'_ram","' + type + 'createSLA_' + tenantId +'_cpu","' + type + 'createSLA_' + tenantId +'_storage"],"response","' + type + 'constraintsNum_' + tenantId + '","' + tenantId + '","' + type + '")>Submit</button>\
                  </div>\
                </div>\
              </div>\
            </div>';
		modalsDiv.innerHTML += text;
		if (!enabled) {
			document.getElementById(type + 'createSLA_' + tenantId +'_submit').disabled = true;
		}
}

function addSLAConstraintsForm(formId, constrNum, tenantId, type) {
	var cnum = parseInt(document.getElementById(constrNum).innerHTML);
	var plusBtnNum = cnum - 1;
	var plusBtnId = "createSLA_" + tenantId + "plus" + plusBtnNum;
	//console.log(plusBtnId);
	document.getElementById(plusBtnId).style.visibility='hidden';
	//console.log(cnum);
	
	var minusBtnId = "createSLA_" + tenantId + "minus" + plusBtnNum;
	
	if (document.getElementById(minusBtnId)) {
		document.getElementById(minusBtnId).style.visibility='hidden';
	}
	
	var slaForm = document.getElementById(formId);
	var text = '';
	
	console.log(type + 'createSLA_' + tenantId +'_remove'+ cnum);
	text += '<div id="' + type + 'createSLA_' + tenantId +'_remove'+ cnum +'"></br>';
	if (type == 'MEC') {
		text += '<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Location <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_location'+ cnum +'" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>\
                          </div>';
	}	
	text += '<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">vRAM <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_ram'+ cnum +'" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>\
                          </div>\
													<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">vCUP <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_cpu'+ cnum +'" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>\
                          </div>\
													<div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">vStorage <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input id="' + type + 'createSLA_' + tenantId +'_storage'+ cnum +'" required="required" class="date-picker form-control col-md-7 col-xs-12" type="text">\
                            </div>\
														<button type="button" class="btn btn-info" id="createSLA_' + tenantId + 'plus' + cnum + '" style="width:24px; margin-left: 0; margin-top: 0; z-index: 3; position: relative; opacity: 0.9;"\
															onclick=addSLAConstraintsForm("' + type + 'SLAconstraints_' + tenantId +'","' + type + 'constraintsNum_' + tenantId + '","' + tenantId + '","' + type + '");>\
                              <i class="fa fa-plus" alt="Add constraints" title="Add constraints" style="margin-left: -5px"></i>\
                            </button>\
														<button type="button" class="btn btn-info" id="createSLA_' + tenantId + 'minus' + cnum + '" style="width:24px; margin-left: 0; margin-top: 0; z-index: 3; position: relative; opacity: 0.9;"\
															onclick=removeSLAConstraintsForm("' + type + 'SLAconstraints_' + tenantId +'","' + type + 'constraintsNum_' + tenantId + '","' + tenantId + '","' + type + '");>\
                              <i class="fa fa-minus" alt="Add constraints" title="Add constraints" style="margin-left: -5px"></i>\
                            </button>\
                          </div></div>';
	
	$('#' + formId).append(text);
	document.getElementById(constrNum).innerHTML = cnum + 1;	
}

function removeSLAConstraintsForm(formId, constrNum, tenantId, type) {	
	var cnum = parseInt(document.getElementById(constrNum).innerHTML);
	var plusBtnNum = cnum - 2;
	var plusBtnId = "createSLA_" + tenantId + "plus" + plusBtnNum;
	//console.log(plusBtnId);
	document.getElementById(plusBtnId).style.visibility='visible';
	//console.log(cnum);
	
	var minusBtnId = "createSLA_" + tenantId + "minus" + plusBtnNum;
	
	if (document.getElementById(minusBtnId)) {
		document.getElementById(minusBtnId).style.visibility='visible';
	}
	
	var divNum = cnum - 1;
	var divId = '#' + type + 'createSLA_' + tenantId + '_remove' + divNum;
	
	console.log(divId);
	
	document.getElementById(constrNum).innerHTML = cnum - 1;
	
	$(divId).remove();
}