
function readVSDescriptors(tableId, resId) {
    getAllVSDescriptors(tableId, resId, createVSDescriptorsTable);
}

function getAllVSDescriptors(elemId, params, callback) {
    getJsonFromURL('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor', elemId, callback, params, null, null);
}

function deleteVSDescriptor(vsDescriptorId, resId) {
    deleteRequestToURL('http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor/' + vsDescriptorId, resId, "VS descriptor successfully deleted", "Unable to delete VS descriptor", showResultMessage);
}

function createVSDescriptorsTable(tableId, data, resId) {
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
	var btnFlag = true;
	var header = createTableHeaderByValues(['Id', 'Name', 'Version', 'Vsb Id', 'Slice Service Type', 'Management Type'], btnFlag, false);
	var cbacks = ['deleteVSDescriptor'];
	var names = ['Delete'];
    var columns = [['vsDescriptorId'], ['name'], ['version'], ['vsBlueprintId'], ['sst'], ['managementType']];
    var conts = createVSDescriptorsTableContents(data, btnFlag, resId, names, cbacks, columns);
	table.innerHTML = header + conts;
}

function createVSDescriptorsTableContents(data, btnFlag, resId, names, cbacks, columns) {
    //console.log(JSON.stringify(data, null, 4));
    
	var text = '<tbody>';
	
	for (var j in data) {
		
		var btnText = '';
		if (btnFlag) {
			btnText += createActionButton(data[j]['vsDescriptorId'], resId, names, cbacks);
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
	}
	
	text += '</tbody>';
	return text;
}