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

function getAllVnfInfos(elemId, callback, resId) {
    var project = document.getElementById('project').innerHTML;
    console.log("PROJECT COOKIE: " + getCookie("PROJECT"));
    if(this.useDefaultProject(project)){
    	getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages", callback, [elemId, resId]);
    }else{
    	getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages?project=" + getCookie("PROJECT"), callback, [elemId, resId]);getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages?project=" + getCookie("PROJECT"), callback, [elemId, resId]);
    	
    }
    
}

function getVnfInfo(vnfInfoId, callback, elemId) {
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
		getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId +"", callback, [elemId]);
    }else{
    	getJsonFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId + "?project=" + getCookie("PROJECT"), callback, [elemId]);
    }
    
}

function fillVNFsCounter(data, params) {
    var countDiv = document.getElementById(params[0]);

	  //console.log(JSON.stringify(data, null, 4));
	  countDiv.innerHTML = data.length;
}

function deleteVnfInfo(vnfInfoId, resId) {
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
		deleteRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId , showResultMessage, ["VNF with vnfInfoID " + vnfInfoId + " successfully deleted."]);
    }else{
    	deleteRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId + "?project=" + getCookie("PROJECT"), showResultMessage, ["VNF with vnfInfoID " + vnfInfoId + " successfully deleted."]);
    }
    
}

function updateVnfInfo(vnfInfoId, elemId) {
    var opState = document.getElementById(elemId).value;

    var jsonObj = JSON.parse("{}");
    jsonObj['operationalState'] = opState;
    var json = JSON.stringify(jsonObj, null, 4);

    //console.log("VnfInfoModifications: " + json);
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
		patchJsonRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId, json, showResultMessage, ["VNF with vnfInfoId " + vnfInfoId + " successfully updated."]);
    }else{
    	patchJsonRequestToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId + "?project=" + getCookie("PROJECT"), json, showResultMessage, ["VNF with vnfInfoId " + vnfInfoId + " successfully updated."]);
    }
    
}

function loadVNFFile(elemId, resId) {
    var files = document.getElementById(elemId).files;

    if (files && files.length > 0) {
        createVnfInfoId(files[0], resId);
    } else {
        showResultMessage(false, "VNF file/archive not selected.");
    }
}

function createVnfInfoId(file, resId) {
    // TODO: handle also userDefinedData
    var jsonObj = {"userDefinedData" : {} };
    var json = JSON.stringify(jsonObj, null, 4);
    //console.log(json)
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
		postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages", json, uploadVnfContent, [file, resId]);
    }else{
    	postJsonToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages?project=" + getCookie("PROJECT"), json, uploadVnfContent, [file, resId]);
    }
    
}

function uploadVnfContent(data, params) {
    //console.log(JSON.stringify(data, null, 4));

    var formData = new FormData();
    formData.append("file", params[0]);
    formData.append("pippo","pluto");
    var vnfInfoId = data['id'];

    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
		putFileToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId + "/package_content", formData, showResultMessage, ["VNF with vnfInfoId " + vnfInfoId + " successfully updated."]);
    }else{
    	putFileToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId + "/package_content?project=" + getCookie("PROJECT"), formData, showResultMessage, ["VNF with vnfInfoId " + vnfInfoId + " successfully updated."]);
    }
    
}

function getDescription(descrId) {
    var vnfId = getURLParameter('vnfId');
    //console.log(vnfId);
    getVNF(vnfId, descrId, printDescription);
}

function printDescription(data, params){
    //console.log(data);
    var yamlObj = jsyaml.load(data);
    //console.log(yamlObj);
    document.getElementById(params[1]).innerHTML = ' - ' + params[0] + ' - ' + yamlObj['description'];
}

function readVNF(graphId) {
    var vnfId = getURLParameter('vnfId');
    //console.log(vnfId);
    getVNF(vnfId, graphId, createVNFGraph);
}

function getVNF(vnfInfoId, elemId, callback) {
    var project = document.getElementById('project').innerHTML;
    if(this.useDefaultProject(project)){
		getFileFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId + "/vnfd", callback, [vnfInfoId, elemId]);
    }else{
    	getFileFromURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/vnfpkgm/v1/vnf_packages/" + vnfInfoId + "/vnfd?project=" + getCookie("PROJECT"), callback, [vnfInfoId, elemId]);
    }
    
}

function exportVnfPkg(vnfPkgInfoId, resId) {
    postToURLWithAuth("http://" + catalogueAddr + ":" + cataloguePort + "/catalogue/cat2catOperation/exportVnfPkg/" + vnfPkgInfoId, showResultMessage, ["Request for uploading VNF Pkg with vnfPkgInfoId " + vnfPkgInfoId + " successfully submitted to public 5G Catalogue."])
}

function showVnfGraphCanvas(data,params) {
    //console.log(params[0]);
    //console.log(params[1]);
    //console.log(data)
    //console.log(data.lenght);
    for (var i in data) {
        //console.log(data[i]['id']);
        if (data[i]['id'] === params[0]){
            var vnfProductName= data[i]['vnfProductName'];
            //console.log(vnfProductName);
        }
        document.getElementById("graphOf_"+ data[i]['id']).style.display = "none";
    }

    document.getElementById("graphOf_"+ params[0]).style.display = "block";
    var dataId ='cy_'+params[0];
    //console.log("dataid="+dataId);
    document.getElementById(dataId).innerHTML = '<script>'+getVNF(params[0],dataId, createVNFGraph);+'</script>';
}

function createVnfInfosTable(data, params) {
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
        table.innerHTML = '<tr>No VNFs stored in Catalogue</tr>';
        return;
    }
    var btnFlag = true;
    var header = "";
    var cbacks = [];
    var names = [];
    var columns = [];

    if (isPublic) {
        console.log("PUBLIC CATALOGUE");
        header = createTableHeaderByValues(['Name', 'Version', 'Provider', 'Operational State', 'Onboarding State', 'Actions'], btnFlag, false);
        cbacks = ['openVNF_', 'showVnfGraphCanvas','updateVnfInfo_', 'deleteVnfInfo'];
        names = ['View VNF', 'View VNF Graph', 'Change VNF OpState', 'Delete VNF'];
        columns = [['vnfProductName'], ['vnfdVersion'], ['vnfProvider'], ['operationalState'], ['onboardingState', 'manosOnboardingStatus']];
    } else {
        console.log("PRIVATE CATALOGUE");
        header = createTableHeaderByValues(['Name', 'Version', 'Provider', 'Operational State', 'Onboarding State', 'Actions'], btnFlag, false);
        cbacks = ['openVNF_', 'showVnfGraphCanvas','updateVnfInfo_', 'exportVnfPkg', 'deleteVnfInfo'];
        names = ['View VNF', 'View VNF Graph', 'Change VNF OpState', 'Upload VNF Pkg', 'Delete VNF'];
        columns = [['vnfProductName'], ['vnfdVersion'], ['vnfProvider'], ['operationalState'], ['onboardingState', 'c2cOnboardingState', 'manosOnboardingStatus']]; 
    }
    

    table.innerHTML = header + '<tbody>';

    //var rows = '';
    for (var i in data) {
        table.innerHTML +=  createVnfInfosTableRow(data[i], btnFlag, cbacks, names, columns, resId);

        if(isPublic == false && data[i]['c2cOnboardingState'].indexOf("UNPUBLISHED") < 0) {
            disableExpBtn("expBtn_" + data[i]['id']);
        }
    }

    table.innerHTML += '</tbody>';
}

function createVnfInfosTableRow(data, btnFlag, cbacks, names, columns, resId) {
    //console.log(JSON.stringify(data, null, 4));

    var text = '';
    var btnText = '';
    if (btnFlag) {
        //btnText += createActionButton(data['id'], resId, names, cbacks);
        btnText += createLinkSet(data['id'], resId, names, cbacks);
        createUpdateVnfInfoModal(data['id'], data['operationalState'], "updateVnfInfosModals");
        creteVNFViewModal(data['id'], "vnfViewModals");
        createVnfCanvas(data); 
    }

	text += '<tr>';
	for (var i in columns) {
	    var values = [];
	    if (columns[i][0].indexOf('onboardingState') >= 0) {
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
                if (columns[i][0].indexOf('onboardingState') >= 0 && isPublic) {
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
                $.each(values[0], function(key, value) {
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

function createVnfCanvas(data){
    //console.log(data);
    var dataId ='cy_'+data['id'];
    var vnfProductName=data['vnfProductName'];
    var graphName="graphOf_" + data['id'];
    document.getElementById("vnfGraphCanvas").innerHTML += '<div id="' + graphName + '" class="graph_canvas" style="display: none";>\
                                                            <div class="col-md-12">\
                                                            <div class="x_panel">\
                                                                <div class="x_title">\
                                                                <h2>VIRTUAL NETWORK FUNCTION GRAPH - ' + vnfProductName + ' <small></small></h2>\
                                                                <div class="clearfix"></div>\
                                                                </div>\
                                                                <div class="x_content">\
                                                                <style>\
                                                                    #' + dataId + '{\
                                                                    min-height: 600px;\
                                                                    width: auto;\
                                                                    left: 0;\
                                                                    top: 0;\
                                                                    }\
                                                                </style>\
                                                                <div id="'+dataId+'"></div>\
                                                                </div>\
                                                            </div>\
                                                            </div>\
                                                        </div>';

}


function creteVNFViewModal(vnfInfoId, modalsContainerId) {
    //console.log('Creating view modal for vnfInfoId: ' + vnfInfoId);
    var container = document.getElementById(modalsContainerId);

    if (container) {
        var text = '<div id="openVNF_' + vnfInfoId + '" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">\
                    <div class="modal-dialog modal-lg">\
                      <div class="modal-content">\
                        <div class="modal-header">\
                          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>\
                          </button>\
                          <h4 class="modal-title" id="myModalLabel">VNF with vnfInfoId: ' + vnfInfoId + '</h4>\
                        </div>\
                        <div class="modal-body">\
                            <textarea id="viewVNFContent_' + vnfInfoId + '" class="form-control" rows="30" readonly></textarea>\
                        </div>\
                        <div class="modal-footer">\
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\
                          <!--button type="button" class="btn btn-primary" data-dismiss="modal" onclick=updateVnfInfo("' + vnfInfoId + '","ed_' + vnfInfoId + '");>Submit</button-->\
                        </div>\
                      </div>\
                    </div>\
                </div>';

            container.innerHTML += text;
            getVNF(vnfInfoId, 'viewVNFContent_' + vnfInfoId, fillVNFViewModal);
    }
}

function fillVNFViewModal(data, params) {

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
    var vnfInfoId = params[0];
    var textArea = document.getElementById(params[1]);
    textArea.value = yaml;
}

function createUpdateVnfInfoModal(vnfInfoId, opState, modalsContainerId) {

    var container = document.getElementById(modalsContainerId);

    if (container) {
        var text = '<div id="updateVnfInfo_' + vnfInfoId + '" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">\
                    <div class="modal-dialog modal-lg">\
                      <div class="modal-content">\
                        <div class="modal-header">\
                          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>\
                          </button>\
                          <h4 class="modal-title" id="myModalLabel">Enable / Disable VNF</h4>\
                        </div>\
                        <div class="modal-body">\
                            <form class="form-horizontal form-label-left">\
								<div class="form-group">\
									<label class="control-label col-md-3 col-sm-3 col-xs-12">Operational State</label>\
									<div class="col-md-9 col-sm-9 col-xs-12">\
										<select id="ed_' + vnfInfoId + '" class="form-control">';
        if (opState == 'ENABLED') {
            text += '<option value="ENABLED">ENABLED</option>\
					<option value="DISABLED">DISABLED</option>';
        } else {
            text += '<option value="DISABLED">DISABLED</option>\
                    <option value="ENABLED">ENABLED</option>';
        }
		text += '</select>\
									</div>\
								</div>\
							</form>\
                        </div>\
                        <div class="modal-footer">\
                          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>\
                          <button type="button" class="btn btn-primary" data-dismiss="modal" onclick=updateVnfInfo("' + vnfInfoId + '","ed_' + vnfInfoId + '");>Submit</button>\
                        </div>\
                      </div>\
                    </div>\
                </div>';

        container.innerHTML += text;
    }
}

function createVNFGraph(data, params) {
    //console.log(params[1]);
    var yamlObj = jsyaml.load(data);
    //console.log(yamlObj);
    var nodeTempl=yamlObj['topologyTemplate']['nodeTemplates'];
    //console.log(nodeTempl);

    var nodes = [];
    var edges = [];
    var col=0;
    var ypos=50;

    $.each( nodeTempl, function(key, value){
        //console.log(key + " " + value['type']);
        if (value['type'] === 'tosca.nodes.nfv.VNF'){
            nodes.push({group: 'nodes', data:{id: key, name: key, label: key, faveShape: 'rectangle', faveColor: '#DDDDDD'}});
            nodes.push({group: 'nodes', data:{id: 'externalCp',parent: key, name: 'externalCp', label: 'externalCp', faveShape: 'rectangle', faveColor: '#CCCCCC'}});
            $.each( nodeTempl, function(key1, value1){
                if (value1['type'] === 'tosca.nodes.nfv.Vdu.Compute'){
                    nodes.push({ group: 'nodes', data: { id: key1, col: col, parent: key, name: key1, label: key1, weight: 100, faveColor: '#DDDDDD', faveShape: 'ellipse' }, classes: 'bottom-center vdu'});
                    //edges.push({ group: 'edges', data: { source: key1, target: value1['requirements']['virtualStorage'], faveColor: '#706f6f', strength: 70 }});
                }
            });
        }
    });
    col=col+1;
    $.each( nodeTempl, function(key, value){
            if (value['type'] === 'tosca.nodes.nfv.VnfExtCp'){
                nodes.push({ group: 'nodes', data: { id: key, col: col,parent: 'externalCp', name: key, label: key, weight: 50, faveColor: '#fff', faveShape: 'ellipse'},  classes: 'bottom-center extcp'});
        }
    });
    col=col+1;
    $.each( nodeTempl, function(key, value){
            if (value['type'] === 'tosca.nodes.nfv.VnfExtCp'){
            $.each( value['requirements'], function(key2, value2){
                //console.log(key2 + " " + value2);
                //console.log(key);
                nodes.push({ group: 'nodes', data: { id: key2+value2, col: col, name: value2, label: value2, weight: 50, faveColor: '#fff', faveShape: 'ellipse'} , classes: 'bottom-center net '});
                edges.push({ group: 'edges', data: { source: key, target: key2+value2, faveColor: '#706f6f', strength: 70 }});
            });
        }
    });

    var cy = cytoscape({
        container: document.getElementById(params[1]),
        boxSelectionEnabled: false,
        autounselectify: true,

		layout: {
			name: 'grid',
      padding: 1,
      position: function(node) {
          return {
           row: node.data('row'),
           col: node.data('col')
          }
        }
		},

		style: cytoscape.stylesheet()
			.selector('node')
				.css({
          'shape': 'data(faveShape)',
          'height': 80,
          'width': 80,
          'content': 'data(name)',
					'text-valign': 'center',
					'text-outline-width': 0,
					'text-width': 2,
					//'text-outline-color': '#000',
					'background-color': 'data(faveColor)',
					'color': '#000',
					'label': 'data(name)'
        })
      .selector('$node > node')
        .css({
            'shape': 'data(faveShape)',
            'color': '#000',
            'text-pt': 10,
            'text-outline-width': 0,
            //'text-outline-color': '#000',
            'padding-top': '10px',
            'padding-left': '30px',
            'padding-bottom': '10px',
            'padding-right': '30px',
            'text-valign': 'top',
            'text-halign': 'center',
            'background-color': 'data(faveColor)'
        })
			.selector(':selected')
				.css({
					'border-width': 3,
					'border-color': '#333'
				})
			.selector('edge')
				.css({
					'curve-style': 'bezier',
					'opacity': 0.666,
					'width': 'mapData(strength, 70, 100, 2, 6)',
					'target-arrow-shape': 'circle',
          'source-arrow-shape': 'circle',
					'line-color': 'data(faveColor)',
          'source-arrow-color': 'data(faveColor)',
          'label': 'data(label)',
					'target-arrow-color': 'data(faveColor)'
				})
			.selector('edge.questionable')
				.css({
					'line-style': 'dotted',
					'target-arrow-shape': 'diamond',
					'source-arrow-shape': 'diamond'
				})
			.selector('.vdu')
				.css({
					'background-image': '/5gcatalogue/images/vdu_icon_80.png',
					'width': 80,//'mapData(weight, 40, 80, 20, 60)',
					'height': 80
				})
			.selector('.extcp')
				.css({
					'background-image': '/5gcatalogue/images/cp_icon_50.png',
					'width': 50,//'mapData(weight, 40, 80, 20, 60)',
					'height': 50
				})
			.selector('.net')
				.css({
					'background-image': '/5gcatalogue/images/net_icon_50.png',
					'width': 50,//'mapData(weight, 40, 80, 20, 60)',
					'height': 50
				})
			.selector('.faded')
				.css({
					'opacity': 0.25,
					'text-opacity': 0
				})
			.selector('.top-left')
				.css({
					'text-valign': 'top',
					'text-halign': 'left'
				})
			.selector('.top-right')
				.css({
					'text-valign': 'top',
					'text-halign': 'right'
				})
			.selector('.bottom-center')
				.css({
					'text-valign': 'bottom',
					'text-halign': 'center'
				}),

		elements: {
		  nodes: nodes,
		  edges: edges
		},

		ready: function(){
		  window.cy = this;
		}
	});

	cy.minZoom(0.5);
    cy.maxZoom(1.6);

}
