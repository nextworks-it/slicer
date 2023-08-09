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

function getAllMANOPlugins(tableId, resId) {
	getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/manoManagement/manos", createPluginsTable, [tableId, resId]);
}

function getAllCataloguePlugins(tableId, resId) {
	getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/cat2catManagement/5gcatalogues", createCataloguePluginsTable, [tableId, resId]);
}

function getAllProjects(tableId, resId, callback) {
	getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/projectManagement/projects", callback, [tableId, resId]);
}

function getAllUsers(tableId, resId) {
	getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/userManagement/users", createUsersTable, [tableId, resId]);
}

function getUser(divId, resId, callback) {
    getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/userManagement/users/" + divId, callback, [divId, resId]);
}

function loadMANOPlugin(textId, elemId, resId) {
    
    var files = document.getElementById(elemId).files;

    if (files && files.length > 0) {
        var text = document.getElementById(textId).innerHTML;
        console.log(text);
        var manoId = JSON.parse(text)['manoId'];
        postMANO(manoId, text);
    } else {
        showResultMessage(false, "MANO descriptor not selected.");
    }
}

function loadCataloguePlugin(catalogueIdInput, catalogueUrlInput, opStateInput) {
    var catalogueId = document.getElementById(catalogueIdInput).value;
    var catalogueUrl = document.getElementById(catalogueUrlInput).value;
    var opState = document.getElementById(opStateInput).value;

    var jsonObj = JSON.parse('{}');

    jsonObj['catalogueId'] = catalogueId;
    jsonObj['url'] = catalogueUrl;
    jsonObj['pluginOperationalState'] = opState;

    var json = JSON.stringify(jsonObj, null, 4);

    postCatalogue(catalogueId, json);
}

function createNewProject(inputs) {
    var projectId = document.getElementById(inputs[0]).value;
    var projectDescription = document.getElementById(inputs[1]).value;

    var jsonObj = JSON.parse('{}');
    jsonObj['projectId'] = projectId;
    jsonObj['projectDescription'] = projectDescription;

    var json = JSON.stringify(jsonObj, null, 4);

    postProject(projectId, json);
}

function createNewUser(inputs) {
    var userName = document.getElementById(inputs[0]).value;
    var firstName = document.getElementById(inputs[1]).value;
    var lastName = document.getElementById(inputs[2]).value;
    var defaultProj = document.getElementById(inputs[3]).value;

    var jsonObj = JSON.parse('{}');
    if (userName)
        jsonObj['userName'] = userName.toLowerCase();
    console.log(userName);
    if (firstName)
        jsonObj['firstName'] = firstName;
    if (lastName)
        jsonObj['lastName'] = lastName;
    if (defaultProj)
        jsonObj['defaultProject'] = defaultProj;

    var json = JSON.stringify(jsonObj, null, 4);

    console.log("New User: " + json);

    postUser(userName, json);
}

function postMANO(manoId, data) {
    postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/manoManagement/manos", data, showResultMessage, ["MANO " + manoId + " has been successfully created."]);
}

function postCatalogue(catalogueId, data) {
    postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/cat2catManagement/5gcatalogues", data, showResultMessage, ["Catalogue " + catalogueId + " has been successfully created."]);
}

function putUserToProject(userNameInput, projectIdInput) {
    var userName = document.getElementById(userNameInput).value;
    var projecId = document.getElementById(projectIdInput).value;
    putToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/userManagement/projects/" + projecId + "/users/" + userName, showResultMessage, ["User" + userName + " has been successfully added to Project " + projecId + "."]);
}

function postProject(projectId, data) {
    postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/projectManagement/projects", data, showResultMessage, ["Project " + projectId + " has been successfully created."]);
}

function postUser(userName, data) {
    postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/userManagement/users", data, showResultMessage, ["User " + userName + " has been successfully created."]);
}

function updateMANOPlugin(manoId, manoType, elemId) {
    var opState = document.getElementById(elemId).value;

    var jsonObj = JSON.parse("{}");
    jsonObj['manoId'] = manoId;
    jsonObj['manoType'] = manoType;
    jsonObj['pluginOperationalState'] = opState;
    var json = JSON.stringify(jsonObj, null, 4);

    patchJsonRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/manoManagement/manos/" + manoId, json, showResultMessage, ["MANO Plugin with manoId " + manoId + " successfully updated."]);
}

function updateCataloguePlugin(catalogueId, elemId) {
    var opState = document.getElementById(elemId).value;

    var jsonObj = JSON.parse("{}");
    jsonObj['catalogueId'] = catalogueId;
    jsonObj['pluginOperationalState'] = opState;
    var json = JSON.stringify(jsonObj, null, 4);

    console.log(json);

    patchJsonRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/cat2catManagement/5gcatalogues/" + catalogueId, json, showResultMessage, ["Catalogue Plugin with catalogueId " + catalogueId + " successfully updated."]);
}

function createPluginsTable(data, params) {
	//console.log(JSON.stringify(data, null, 4));
    //console.log(params);

    var tableId = params[0];
    var resId = params[1];
    var table = document.getElementById(tableId);
    if (!table) {
        return;
    }
    if (!data || data.length == 0) {
	//console.log('No NS Instances');
        table.innerHTML = '<tr>No Plugins instantiated in Catalogue</tr>';
        return;
    }
    var btnFlag = true;
    var header = createTableHeaderByValues(['Id', 'Type', 'IP Address', 'Operational State'], btnFlag, false);
    var cbacks = ['updatePlugin_'];
    var names = ['Update Plugin'];
    var columns = [['manoId'], ['manoType'], ['ipAddress'], ['pluginOperationalState']];

    table.innerHTML = header + '<tbody>';

    var rows = '';
    for (var i in data) {
        rows +=  createPluginsTableRow(data[i], btnFlag, cbacks, names, columns, resId);
    }
    
    table.innerHTML += rows + '</tbody>';
}

function createPluginsTableRow(data, btnFlag, cbacks, names, columns, resId) {
    //console.log(JSON.stringify(data, null, 4));

    var text = '';
    var btnText = '';
    if (btnFlag) {
        btnText += createLinkSet(data['manoId'], resId, names, cbacks);
        createUpdatePluginModal(data, data['pluginOperationalState'], "updatePluginModals");
    }

	text += '<tr>';
	for (var i in columns) {
	    var values = [];
	    getValuesFromKeyPath(data, columns[i], values);
	    //console.log(values);

	    var subText = '<td>';
	    var subTable = '<table class="table table-borderless">';

	    if (data.hasOwnProperty(columns[i][0])) {
            if(values instanceof Array && values.length > 1) {
                for (var v in values) {
                    subTable += '<tr><td>' + values[v] + '</td><tr>';
                }
                subText += subTable + '</table>';
            } else {
                subText += values;
            }
	    }
	    subText += '</td>';
	    text += subText;
	}
	text += btnText + '</tr>';
    
    return text;
}

function createUpdatePluginModal(mano, opState, modalsContainerId) {

    var container = document.getElementById(modalsContainerId);
    var manoId = mano['manoId'];
    var manoType = mano['manoType'];

    if (container) {
        var text = '<div id="updatePlugin_' + manoId + '" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">\
                    <div class="modal-dialog modal-lg">\
                      <div class="modal-content">\
                        <div class="modal-header">\
                          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>\
                          </button>\
                          <h4 class="modal-title" id="myModalLabel">Enable / Disable / Delete Plugin</h4>\
                        </div>\
                        <div class="modal-body">\
                            <form class="form-horizontal form-label-left">\
								<div class="form-group">\
									<label class="control-label col-md-3 col-sm-3 col-xs-12">Operational State</label>\
									<div class="col-md-9 col-sm-9 col-xs-12">\
										<select id="ed_' + manoId + '" class="form-control">';
        if (opState == 'ENABLED') {
            text += '<option value="ENABLED">ENABLED</option>\
                    <option value="DISABLED">DISABLED</option>\
                    <option value="DELETING">DELETING</option>';
        } else if (opState == 'DISABLED') {
            text += '<option value="DISABLED">DISABLED</option>\
                    <option value="ENABLED">ENABLED</option>\
                    <option value="DELETIING">DELETING</option>';
        } else if (opState == 'DELETING') {
            text += '<option value="DELETING">DELETING</option>\
                    <option value="ENABLED">ENABLED</option>\
                    <option value="DISABLED">DISABLED</option>';
        }
		text += '</select>\
									</div>\
								</div>\
							</form>\
                        </div>\
                        <div class="modal-footer">\
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\
                          <button type="button" class="btn btn-primary" data-dismiss="modal" onclick=updateMANOPlugin("' + manoId + '","' + manoType + '","ed_' + manoId + '");>Submit</button>\
                        </div>\
                      </div>\
                    </div>\
                </div>';

        container.innerHTML += text;
    }
}

function createCataloguePluginsTable(data, params) {
	//console.log(JSON.stringify(data, null, 4));
    //console.log(params);

    var tableId = params[0];
    var resId = params[1];
    var table = document.getElementById(tableId);
    if (!table) {
        return;
    }
    if (!data || data.length == 0) {
	//console.log('No NS Instances');
        table.innerHTML = '<tr>No Catalogue-to-Catalogue Plugins instantiated in Catalogue</tr>';
        return;
    }
    var btnFlag = true;
    var header = createTableHeaderByValues(['Id', 'Url', 'Operational State'], btnFlag, false);
    var cbacks = ['updateCataloguePlugin_'];
    var names = ['Update Plugin'];
    var columns = [['catalogueId'], ['url'], ['pluginOperationalState']];

    table.innerHTML = header + '<tbody>';

    var rows = '';
    for (var i in data) {
        rows +=  createCataloguePluginsTableRow(data[i], btnFlag, cbacks, names, columns, resId);
    }
    
    table.innerHTML += rows + '</tbody>';
}

function createCataloguePluginsTableRow(data, btnFlag, cbacks, names, columns, resId) {
    //console.log(JSON.stringify(data, null, 4));

    var text = '';
    var btnText = '';
    if (btnFlag) {
        btnText += createLinkSet(data['catalogueId'], resId, names, cbacks);
        createUpdateCataloguePluginModal(data['catalogueId'], data['pluginOperationalState'], "updatePluginModals");
    }

	text += '<tr>';
	for (var i in columns) {
	    var values = [];
	    getValuesFromKeyPath(data, columns[i], values);
	    //console.log(values);

	    var subText = '<td>';
	    var subTable = '<table class="table table-borderless">';

	    if (data.hasOwnProperty(columns[i][0])) {
            if(values instanceof Array && values.length > 1) {
                for (var v in values) {
                    subTable += '<tr><td>' + values[v] + '</td><tr>';
                }
                subText += subTable + '</table>';
            } else {
                subText += values;
            }
	    }
	    subText += '</td>';
	    text += subText;
	}
	text += btnText + '</tr>';
    
    return text;
}

function createUpdateCataloguePluginModal(catalogueId, opState, modalsContainerId) {

    var container = document.getElementById(modalsContainerId);

    if (container) {
        var text = '<div id="updateCataloguePlugin_' + catalogueId + '" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">\
                    <div class="modal-dialog modal-lg">\
                      <div class="modal-content">\
                        <div class="modal-header">\
                          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>\
                          </button>\
                          <h4 class="modal-title" id="myModalLabel">Enable / Disable / Delete Plugin</h4>\
                        </div>\
                        <div class="modal-body">\
                            <form class="form-horizontal form-label-left">\
								<div class="form-group">\
									<label class="control-label col-md-3 col-sm-3 col-xs-12">Operational State</label>\
									<div class="col-md-9 col-sm-9 col-xs-12">\
										<select id="ed_' + catalogueId + '" class="form-control">';
        if (opState == 'ENABLED') {
            text += '<option value="ENABLED">ENABLED</option>\
                    <option value="DISABLED">DISABLED</option>\
                    <option value="DELETING">DELETING</option>';
        } else if (opState == 'DISABLED') {
            text += '<option value="DISABLED">DISABLED</option>\
                    <option value="ENABLED">ENABLED</option>\
                    <option value="DELETIING">DELETING</option>';
        } else if (opState == 'DELETING') {
            text += '<option value="DELETING">DELETING</option>\
                    <option value="ENABLED">ENABLED</option>\
                    <option value="DISABLED">DISABLED</option>';
        }
		text += '</select>\
									</div>\
								</div>\
							</form>\
                        </div>\
                        <div class="modal-footer">\
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\
                          <button type="button" class="btn btn-primary" data-dismiss="modal" onclick=updateCataloguePlugin("' + catalogueId + '","ed_' + catalogueId + '");>Submit</button>\
                        </div>\
                      </div>\
                    </div>\
                </div>';

        container.innerHTML += text;
    }
}

function createProjectsTable(data, params) {
	//console.log(JSON.stringify(data, null, 4));
    //console.log(params);

    var tableId = params[0];
    var resId = params[1];
    var table = document.getElementById(tableId);
    if (!table) {
        return;
    }
    if (!data || data.length == 0) {
	//console.log('No NS Instances');
        table.innerHTML = '<tr>No Projects created in Catalogue</tr>';
        return;
    }
    var btnFlag = false;
    var header = createTableHeaderByValues(['Id', 'Description', 'Users'], btnFlag, false);
    var cbacks = [];
    var names = [];
    var columns = [['projectId'], ['projectDescription'], ['users']];

    table.innerHTML = header + '<tbody>';

    var rows = '';
    for (var i in data) {
        rows +=  createProjectsTableRow(data[i], btnFlag, cbacks, names, columns, resId);
    }
    
    table.innerHTML += rows + '</tbody>';
}

function createProjectsTableRow(data, btnFlag, cbacks, names, columns, resId) {
    //console.log(JSON.stringify(data, null, 4));

    var text = '';
    var btnText = '';
    if (btnFlag) {
        btnText += createActionButton(data['projectId'], resId, names, cbacks);
    }

	text += '<tr>';
	for (var i in columns) {
	    var values = [];
	    getValuesFromKeyPath(data, columns[i], values);
	    //console.log(values);

	    var subText = '<td>';
	    var subTable = '<table class="table table-borderless">';

	    if (data.hasOwnProperty(columns[i][0])) {
            if(values instanceof Array && values.length > 1) {
                for (var v in values) {
                    subTable += '<tr><td>' + values[v] + '</td><tr>';
                }
                subText += subTable + '</table>';
            } else if (values[0] instanceof Array && values[0].length >= 1) {
                for (var vv in values[0]) {
                    subTable += '<tr><td>' + values[0][vv] + '</td><tr>';
                }
                subText += subTable + '</table>';
            } else {
                subText += values;
            }
	    }
	    subText += '</td>';
	    text += subText;
	}
	text += btnText + '</tr>';
    
    return text;
}

function createUsersTable(data, params) {
	//console.log(JSON.stringify(data, null, 4));
    //console.log(params);

    var tableId = params[0];
    var resId = params[1];
    var table = document.getElementById(tableId);
    if (!table) {
        return;
    }
    if (!data || data.length == 0) {
	//console.log('No NS Instances');
        table.innerHTML = '<tr>No Users in Catalogue</tr>';
        return;
    }
    var btnFlag = true;
    var header = createTableHeaderByValues(['Username', 'First Name', 'Last name', 'Default Project'], btnFlag, false);
    var cbacks = ['addUserToProject_'];
    var names = ['Add to Project'];
    var columns = [['userName'], ['firstName'], ['lastName'], ['defaultProject']];

    table.innerHTML = header + '<tbody>';

    var rows = '';
    for (var i in data) {
        rows +=  createUsersTableRow(data[i], btnFlag, cbacks, names, columns, resId);
    }
    
    table.innerHTML += rows + '</tbody>';
    getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/projectManagement/projects", fillCreateUserModal, ["defaultProject", "response"]);
}

function createUsersTableRow(data, btnFlag, cbacks, names, columns, resId) {
    //console.log(JSON.stringify(data, null, 4));

    var text = '';
    var btnText = '';
    if (btnFlag) {
        btnText += createLinkSet(data['userName'], resId, names, cbacks);

        getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/projectManagement/projects", createAddToProjectModal, [data['userName'], "addToProjectModals", "response"]);
    }

	text += '<tr>';
	for (var i in columns) {
	    var values = [];
	    getValuesFromKeyPath(data, columns[i], values);
	    //console.log(values);

	    var subText = '<td>';
	    var subTable = '<table class="table table-borderless">';

	    if (data.hasOwnProperty(columns[i][0])) {
            if(values instanceof Array && values.length > 1) {
                for (var v in values) {
                    subTable += '<tr><td>' + values[v] + '</td><tr>';
                }
                subText += subTable + '</table>';
            } else {
                subText += values;
            }
	    }
	    subText += '</td>';
	    text += subText;
	}
	text += btnText + '</tr>';
    
    return text;
}

function fillCreateUserModal(data, params) {
    var select = document.getElementById(params[0]);

    for (var i in data) {
        select.innerHTML += '<option value=' + data[i]['projectId'] + '>' + data[i]['projectId'] + '</option>';
    }
}

function createAddToProjectModal(data, params) {
    var container = document.getElementById(params[1]);

    if (container) {
        var text = '<div id="addUserToProject_' + params[0] + '" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">\
                    <div class="modal-dialog modal-lg">\
                      <div class="modal-content">\
                        <div class="modal-header">\
                          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>\
                          </button>\
                          <h4 class="modal-title" id="myModalLabel">Add User to Project</h4>\
                        </div>\
                        <div class="modal-body">\
                            <form class="form-horizontal form-label-left">\
                                <div class="form-group">\
                                    <label class="control-label col-md-3 col-sm-3 col-xs-12" for="first-name">Username <span class="required">*</span>\
                                    </label>\
                                    <div class="col-md-6 col-sm-6 col-xs-12">\
                                        <input type="text" value="' + params[0] + '" id="userName_' + params[0] + '" required="required" class="form-control col-md-7 col-xs-12" disabled>\
                                    </div>\
                                </div>\
								<div class="form-group">\
									<label class="control-label col-md-3 col-sm-3 col-xs-12">Project </label>\
									<div class="col-md-6 col-sm-6 col-xs-12">\
										<select id="projectId_' + params[0] + '" class="form-control">';
        for (var i in data) {
            text += '<option value=' + data[i]['projectId'] + '>' + data[i]['projectId'] + '</option>';
        }
		text += '</select>\
									</div>\
								</div>\
							</form>\
                        </div>\
                        <div class="modal-footer">\
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\
                          <button type="button" class="btn btn-primary" data-dismiss="modal" onclick=putUserToProject("userName_' + params[0] + '","projectId_' + params[0] + '");>Submit</button>\
                        </div>\
                      </div>\
                    </div>\
                </div>';

        container.innerHTML += text;
    }
}

function fillProjectsData(data, params) {
    var projCookie = getCookie("PROJECT");
    //console.log("Current project: " + projCookie);

    var service_role = getCookie("ROLE");
    //console.log("Current service role: " + service_role);
    if(service_role.indexOf("ROLE_SERVICE_ADMIN") >= 0) {
        if (projCookie == null) {
            setCookie("PROJECT", "admin", 1);
            projCookie = "admin";
        }
    } else {
        if (projCookie == null) {
            if (data['projects'].length >= 1) {
                setCookie("PROJECT", data['projects'][0], 1);
                projCookie = data['projects'][0];
            }
        }
    }

    document.getElementById('project').innerHTML = projCookie;
    document.getElementById('projectBar').innerHTML = '<b>' + projCookie + '</b>';
    
    var projectsDropDown = document.getElementById("userProjects");
    
    for (var i = 0 ; i < data['projects'].length; i++) {
        console.log("Project #" + i + ": "  + data['projects'][i]);
        projectsDropDown.innerHTML += '<li><a onclick=selectProject("' + data['projects'][i] + '"); href="#">' + data['projects'][i] + '</a></li>';
    }    
}