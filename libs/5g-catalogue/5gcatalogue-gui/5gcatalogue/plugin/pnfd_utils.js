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

function getAllPnfdInfos(elemId, callback, resId) {
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
         getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors" , callback, [elemId, resId]);
    }else{
        getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors?project=" + getCookie("PROJECT"), callback, [elemId, resId]);
    }
   
}

function getPnfdInfo(pnfdInfoId, callback, elemId) {
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
        getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId, callback, [elemId]);
    }else{
        getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId + "?project=" + getCookie("PROJECT"), callback, [elemId]);
    }
    
}

function fillPNFDsCounter(data, params) {
    var countDiv = document.getElementById(params[0]);

	//console.log(JSON.stringify(data, null, 4));
	countDiv.innerHTML = data.length;
}

function deletePnfdInfo(pnfdInfoId, resId) {
     var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
        deleteRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId, showResultMessage, ["PNFD with pnfdInfoID " + pnfdInfoId + " successfully deleted."]);
    }else{
        deleteRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId + "?project=" + getCookie("PROJECT"), showResultMessage, ["PNFD with pnfdInfoID " + pnfdInfoId + " successfully deleted."]);
    }
    
}

function updatePnfdInfo(pnfdInfoId, elemId) {
    var opState = document.getElementById(elemId).value;

    var jsonObj = JSON.parse("{}");
    var json = JSON.stringify(jsonObj, null, 4);

    console.log("PnfdInfoModifications: " + json);
     var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
        patchJsonRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId, json, showResultMessage, ["PNFD with pnfdInfoId " + nsdInfoId + " successfully updated."]);
    }else{
        patchJsonRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId + "?project=" + getCookie("PROJECT"), json, showResultMessage, ["PNFD with pnfdInfoId " + nsdInfoId + " successfully updated."]);
    }
    
}

function loadPNFDFile(elemId, resId) {
    var files = document.getElementById(elemId).files;

    if (files && files.length > 0) {
        createPnfdInfoId(files[0], resId);
    } else {
        showResultMessage(false, "PNFD file/archive not selected.");
    }
}

function createPnfdInfoId(file, resId) {
    // TODO: handle also userDefinedData
    var jsonObj = {"userDefinedData" : {} };
    var json = JSON.stringify(jsonObj, null, 4);
    if(this.useDefaultProject(project)){
        postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors", json, uploadPnfdContent, [file, resId]);
    }else{
        postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors?project=" + getCookie("PROJECT"), json, uploadPnfdContent, [file, resId]);
    }
}

function uploadPnfdContent(data, params) {
    //console.log(JSON.stringify(data, null, 4));

    var formData = new FormData();
    formData.append("file", params[0]);
    formData.append("pippo","pluto");
    var pnfdInfoId = data['id'];
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
        putFileToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId + "/pnfd_content", formData, showResultMessage, ["PNFD with pnfdInfoId " + pnfdInfoId + " successfully updated."]);
    }else{
        putFileToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId + "/pnfd_content?project=" + getCookie("PROJECT"), formData, showResultMessage, ["PNFD with pnfdInfoId " + pnfdInfoId + " successfully updated."]);
    }
    
}

function getDescription(elemId, callback) {
    var pnfdId = getURLParameter('pnfdId');
    console.log(pnfdId);
    getPNFD(pnfdId, elemId, callback);
}

function printDescription(data, params){
    console.log(data);
    var yamlObj = jsyaml.load(data);
    console.log(yamlObj);
    document.getElementById(params[1]).innerHTML = ' - ' + params[0] + ' - ' + yamlObj['description'];
}

function readPNFD(elemId, callback) {
    var pnfdId = getURLParameter('pnfdId');
    console.log(pnfdId);
    getPNFD(pnfdId, elemId, callback);
}

function getPNFD(pnfdInfoId, elemId, callback) {
     var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
        getFileFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId + "/pnfd_content", callback, [pnfdInfoId, elemId]);
    }else{
        getFileFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/nsd/v1/pnf_descriptors/" + pnfdInfoId + "/pnfd_content?project=" + getCookie("PROJECT"), callback, [pnfdInfoId, elemId]);
    }

    
}

function exportPnfd(pnfdInfoId, resId) {
    
    postToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/cat2catOperation/exportPnfd/" + pnfdInfoId, showResultMessage, ["Request for uploading PNFD with pnfdInfoId " + pnfdInfoId + " successfully submitted to public 5G Catalogue."])
}

function showPnfdGraphCanvas(data,params) {
    console.log(params[0]);
    console.log(params[1]);
    console.log(data)
    console.log(data.lenght);
    for (var i in data) {
        console.log(data[i]['id']);
        if (data[i]['id'] === params[0]){
            var pnfdName= data[i]['pnfdName'];
            console.log(pnfdName);
        }
        document.getElementById("graphOf_"+ data[i]['id']).style.display = "none";
    }

    document.getElementById("graphOf_"+ params[0]).style.display = "block";
    var dataId ='cy_'+params[0];
    console.log("dataid="+dataId);
    document.getElementById(dataId).innerHTML = '<script>' + getPNFD(params[0],dataId, createPNFDGraph); + '</script>';
}

function createPnfdInfosTable(data, params) {
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
        table.innerHTML = '<tr>No PNFDs stored in Catalogue</tr>';
        return;
    }
    var btnFlag = true;
    var header = "";
    var cbacks = [];
    var names = [];
    var columns = [];

    if (isPublic) {
        console.log("PUBLIC CATALOGUE");
        header = createTableHeaderByValues(['Name', 'Version', 'Designer', 'Onboarding State', 'Actions'], btnFlag, false);
        cbacks = ['openPNFD_', 'showPnfdGraphCanvas', 'deletePnfdInfo'];
        names = ['View PNFD', 'View PNFD Graph', 'Delete PNFD'];
        columns = [['pnfdName'], ['pnfdVersion'], ['pnfdProvider'], ['pnfdOnboardingState', 'manosOnboardingStatus']];   
    } else {
        console.log("PRIVATE CATALOGUE");
        header = createTableHeaderByValues(['Name', 'Version', 'Designer', 'Onboarding State', 'Actions'], btnFlag, false);
        cbacks = ['openPNFD_', 'showPnfdGraphCanvas', 'exportPnfd', 'deletePnfdInfo'];
        names = ['View PNFD', 'View PNFD Graph', 'Upload PNFD', 'Delete PNFD'];
        columns = [['pnfdName'], ['pnfdVersion'], ['pnfdProvider'], ['pnfdOnboardingState', 'c2cOnboardingState', 'manosOnboardingStatus']]; 
    }

    table.innerHTML = header + '<tbody>';

    //var rows = '';
    for (var i in data) {
        table.innerHTML +=  createPnfdInfosTableRow(data[i], btnFlag, cbacks, names, columns, resId);

        if(isPublic == false && data[i]['c2cOnboardingState'].indexOf("UNPUBLISHED") < 0) {
            disableExpBtn("expBtn_" + data[i]['id']);
        }
    }

    table.innerHTML += '</tbody>';
}

function createPnfdInfosTableRow(data, btnFlag, cbacks, names, columns, resId) {
    //console.log(JSON.stringify(data, null, 4));

    var text = '';
    var btnText = '';
    if (btnFlag) {
        btnText += createLinkSet(data['id'], resId, names, cbacks);
        cretePNFDViewModal(data['id'], "pnfdViewModals");
    }

  	text += '<tr>';
  	for (var i in columns) {
        var values = [];
        if (columns[i][0].indexOf('pnfdOnboardingState') >= 0) {
            for (var j in columns[i]) {
                values.push(data[columns[i][j]]);
            }
        } else {
            getValuesFromKeyPath(data, columns[i], values);
        }
  	    //console.log(values);

  	    var subText = '<td>';
  	    var subTable = '<table class="table">';

  	    if (data.hasOwnProperty(columns[i][0])) {
            if(values instanceof Array && values.length > 1) {
                if (columns[i][0].indexOf('pnfdOnboardingState') >= 0 && isPublic) {
                    subTable += createTableHeaderByValues(['Local', 'MANOs'], false, false);
                } else {
                    subTable += createTableHeaderByValues(['Local', 'Public 5G Catalogue', 'MANOs'], false, false);
                }
                subTable += '<tr>';
                for (var v in values) {
                    if (values[v] instanceof Object) {
                        var subSubTable = '<td><table class="borderless">'; 
                        $.each(values[v], function(key, value) {
                            subSubTable += '<tr><td>'+ key + ' > ' + value + '</td><tr>';
                        });
                        subSubTable += '</table></td>';
                        subTable += subSubTable;
                    } else {
                        subTable += '<td>' + values[v] + '</td>';
                    }
                }
                subText += subTable + '</tr></table>';
            } else if (values[0] instanceof Object) {
                var acks = values[0];
                $.each(acks, function(key, value) {
                    subTable += '<tr><td>'+ key + '</td><td> > </td><td>' + value + '</td><tr>';
                });
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

function cretePNFDViewModal(pnfdInfoId, modalsContainerId) {

    //console.log('Creating view modal for pnfdInfoId: ' + pnfdInfoId);
    var container = document.getElementById(modalsContainerId);

    if (container) {
        var text = '<div id="openPNFD_' + pnfdInfoId + '" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">\
                    <div class="modal-dialog modal-lg">\
                      <div class="modal-content">\
                        <div class="modal-header">\
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>\
                            </button>\
                            <h4 class="modal-title" id="myModalLabel">PNFD with pnfdInfoId: ' + pnfdInfoId + '</h4>\
                        </div>\
                        <div class="modal-body">\
                            <textarea id="viewPNFDContent_' + pnfdInfoId + '" class="form-control" rows="30" readonly></textarea>\
                        </div>\
                        <div class="modal-footer">\
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\
                        </div>\
                      </div>\
                    </div>\
                </div>';

            container.innerHTML += text;
            getPNFD(pnfdInfoId, 'viewPNFDContent_' + pnfdInfoId, fillPNFDViewModal);
    }
}

function fillPNFDViewModal(data, params) {

    var yamlObj = jsyaml.load(data);
    //console.log(yamlObj);

    var yaml = jsyaml.dump(data, {
        indent: 4,
        styles: {
        '!!int'  : 'decimal',
        '!!null' : 'camelcase'
        }
    });

    //console.log(yaml);
    var pnfdInfoId = params[0];
    var textArea = document.getElementById(params[1]);
    textArea.value = yaml;
}