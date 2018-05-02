var wizardCurrentStep = 1;
var nsds = [];
var translationRules = [];
var vnfds = [];
var apps = [];

function submitBlueprintCreationRequest(blueprintId, resId) {
    var jsonObj  = JSON.parse('{}');
    
    var blueprint = document.getElementById(blueprintId).innerHTML;
    jsonObj.vsBlueprint = JSON.parse(blueprint);
    
    jsonObj.nsds = nsds;
    
    jsonObj.vnfPackages = vnfds;
    
    jsonObj.mecAppPackages = apps;
    
    jsonObj.translationRules = translationRules;
    
    var json = JSON.stringify(jsonObj, null, 4);
    
    console.log(json);
    
    postJsonToURL('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint', json, resId, 'VS Blueprint successfully onboarded', 'Error while onboarding VS Blueprint', showResultMessage);   
}

function readVSBlueprints(tableId, resId) {
    getVSBlueprints(tableId, resId, createVSBlueprintsTable);    
}

function getVSBlueprints(elemId, resId, callback) {
    getJsonFromURL('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint', elemId, callback, resId, null, null);
}

function readVSBlueprint(tableId, resId) {
    var id = getURLParameter('Id');
    getVSBlueprint(id, tableId, resId, createVSBlueprintDetailsTable);
}

function getVSBlueprint(id, elemId, resId, callback) {
    getJsonFromURL('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint/' + id, elemId, callback, resId, null, null);
}

function deleteVSBlueprint(blueprintId, resId) {
    deleteRequestToURL('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsblueprint/' + blueprintId, resId, "VS Blueprint successfully deleted", "Unable to delete VS Blueprint", showResultMessage);
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
    if (wizardCurrentStep == 5) {
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
    } else if (wizardCurrentStep < 5) {
        document.getElementById('finishBtn').classList.add('buttonDisabled');
        document.getElementById('progressBtn').classList.remove('buttonDisabled');
    }
}

function loadBlueprintFromFileIntoForm(evt, elemId, resId) {
	var type = 'JSON';
	loadFromFile(type, evt, elemId, resId);
}

function loadNSDsFromFileIntoArray(evt, inputId, elemId, resId) {
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

function uploadVNFDFromForm(formId, formIds, resId) {
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
    
    return json;
}

function uploadAppFromForm(formId, formIds, resId) {
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
    return json;
}

function uploadTRFromForm(formId, formIds, paramNum, resId) {
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

function removeParameterForm(paramCounter, paramDiv) {
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

function createVSBlueprintsTable(tableId, data, resId) {
    console.log(JSON.stringify(data, null, 4));
    
    var table = document.getElementById(tableId);
	if (!table) {
		return;
	}
	if (!data || data.length < 1) {
		console.log('No VS Blueprints');
		table.innerHTML = '<tr>No VS Blueprints stored in database</tr>';
		return;
	}
    //console.log(JSON.stringify(data, null, 4));
	var btnFlag = true;
	var header = createTableHeaderByValues(['Id', 'Version', 'Name', 'Description', 'Configurable parameters', 'Vsd Ids'], btnFlag, false);
	var cbacks = [/*'blueprint_details.html?Id=', */'deleteVSBlueprint'];
	var names = [/*'View Blueprint', */'Delete'];
    var columns = [['vsBlueprintId'], ['vsBlueprintVersion'], ['name'], ['vsBlueprint', 'description'], ['vsBlueprint', 'parameters'], ['activeVsdId']];
	var conts = createVSBlueprintsTableContents(data, btnFlag, resId, names, cbacks, columns);
	table.innerHTML = header + conts;
}

function createVSBlueprintDetailsTable(tableId, data, resId) {
    //console.log(JSON.stringify(data, null, 4));
    
    var table = document.getElementById(tableId);
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
	var header = createTableHeaderByValues(['Id', 'Parameters'], btnFlag, false);
	var cbacks = [];
	var names = [];
    var columns = [['vsBlueprintId'], ['vsBlueprint', 'parameters']];
	var conts = createVSBlueprintTableContents(data, btnFlag, resId, names, cbacks, columns);
	table.innerHTML = header + conts;
}

function createVSBlueprintsTableContents(data, btnFlag, resId, names, cbacks, columns) {	
	var text = '<tbody>';
	
	for (var j in data) {
		
		var btnText = '';
		if (btnFlag) {
			btnText += createActionButton(data[j]['vsBlueprintId'], resId, names, cbacks);
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
                        if (values[0][v] instanceof Object){
                            console.log(JSON.stringify(values[0], null, 4));
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
	}
	
	text += '</tbody>';
	return text;
}

function createVSBlueprintTableContents(data, btnFlag, resId, names, cbacks, columns) {	
	var text = '<tbody>';
	
    var btnText = '';
    if (btnFlag) {
        btnText += createActionButton(data[j]['vsBlueprintId'], resId, names, cbacks);
    }
    
    text += '<tr>' + btnText;
    for (var i in columns) {
        var values = [];
        getValuesFromKeyPath(data, columns[i], values);
        //console.log(values);
        
        var subText = '<td>';
        var subTable = '<table class="table table-borderless">';
        
        if (data.hasOwnProperty(columns[i][0])) {
            if(values[0] instanceof Array) {
                for (var v in values[0]) {
                    if (values[0][v] instanceof Object){
                        console.log(JSON.stringify(values[0], null, 4));
                        var value = values[0][v];
                        $.each(value, function(key, val){
                            subTable += '<tr><td><b>' + key + '</b></td><td>' + val + '</td><tr>';
                        });
                    } else {
                        subTable += '<tr><td>' + values[0][v] + '</td><tr>';
                    }
                }
                subText += subTable + '</table>';
            } else {
                if (values[0] instanceof Object){
                    console.log(JSON.stringify(values[0], null, 4));
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
