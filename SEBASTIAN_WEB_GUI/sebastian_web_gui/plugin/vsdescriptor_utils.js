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
  getJsonFromURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor',
    callback,
    params
  );
}

function readVSDescriptor(tableId) {
  getVSDescriptor(tableId, createVSDescriptorDetailsTable);
}

function getVSDescriptor(elemId, callback) {
  var id = getURLParameter('Id');
  getJsonFromURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/catalogue/vsdescriptor/' + id,
    callback,
    elemId
  );
}

function deleteVSDescriptor(vsDescriptorId) {
  deleteRequestToURLWithAuth(
    'http://' +
      vsAddr +
      ':' +
      vsPort +
      '/vs/catalogue/vsdescriptor/' +
      vsDescriptorId,
    showResultMessage,
    ['VS descriptor successfully deleted', 'Unable to delete VS descriptor']
  );
}

function readVSInstances(tableId) {
  getVSInstanceIds(tableId, createVSInstancesTable);
}

function getVSInstanceIds(elemId, callback) {
  getJsonFromURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vsId',
    callback,
    elemId
  );
}

function readVSInstance(divId, vsiId, params) {
  params.push(divId);
  getVSInstance(vsiId, params, createVSInstancesTableContent);
}

function getVSInstance(vsiId, params, callback) {
  getJsonFromURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs/' + vsiId,
    callback,
    params
  );
}

function terminateVSInstance(vsiId) {
  postToURLWithAuth(
    'http://' +
      vsAddr +
      ':' +
      vsPort +
      '/vs/basic/vslcm/vs/' +
      vsiId +
      '/terminate',
    showResultMessage,
    [
      'VS instances with id ' + vsiId + ' successfully teminated',
      'Unable to terminate VS instance with id ' + vsiId
    ]
  );
}

function purgeVSInstance(vsiId) {
  deleteRequestToURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs/' + vsiId,
    showResultMessage,
    [
      'VS instances with id ' + vsiId + ' successfully purged',
      'Unable to purge VS instance with id ' + vsiId
    ]
  );
}

function instantiateVSDFromForm(params) {
  var vsdId = document.getElementById(params[0]).value;
  var name = document.getElementById(params[1]).value;
  var tenant = document.getElementById(params[2]).value;
  var description = document.getElementById(params[3]).value;
  var position = document.getElementById(params[4]).value;
  
  var jsonObj = JSON.parse('{}');

  jsonObj.vsdId = vsdId;
  jsonObj.name = name;
  jsonObj.tenantId = tenant;
  jsonObj.description = description;
  if (params.length>5) {  
  	jsonObj.userData=JSON.parse('{}');
  	for (var i=0; i<params[5].length; i++){

    		var paramName = params[5][i];
   		var paramValue = document.getElementById("instVSDId-param_" + paramName).value;
    		jsonObj.userData[paramName]=paramValue;

  	}
  }

  if (position == '5TONIC') {
    var jsonLocationObj = JSON.parse('{}');
    jsonLocationObj.latitude = 40.337;
    jsonLocationObj.longitude = -3.77;
    jsonLocationObj.altitude = 0;
    jsonLocationObj.range = 10;

    jsonObj.locationConstraints = jsonLocationObj;
  }

  if (position == 'CTTC') {
    var jsonLocationObj = JSON.parse('{}');
    jsonLocationObj.latitude = 41.275;
    jsonLocationObj.longitude = 1.988;
    jsonLocationObj.altitude = 0;
    jsonLocationObj.range = 10;

    jsonObj.locationConstraints = jsonLocationObj;
  }

  if (position == 'ARNO') {
    var jsonLocationObj = JSON.parse('{}');
    jsonLocationObj.latitude = 43.718;
    jsonLocationObj.longitude = 10.425;
    jsonLocationObj.altitude = 0;
    jsonLocationObj.range = 10;

    jsonObj.locationConstraints = jsonLocationObj;
  }

  if (position == 'POLITO') {
    var jsonLocationObj = JSON.parse('{}');
    jsonLocationObj.latitude = 45.063;
    jsonLocationObj.longitude = 7.662;
    jsonLocationObj.altitude = 0;
    jsonLocationObj.range = 10;

    jsonObj.locationConstraints = jsonLocationObj;
  }

  if (position == 'CRF') {
    var jsonLocationObj = JSON.parse('{}');
    jsonLocationObj.latitude = 45.013;
    jsonLocationObj.longitude = 7.566;
    jsonLocationObj.altitude = 0;
    jsonLocationObj.range = 10;

    jsonObj.locationConstraints = jsonLocationObj;
  }


  var json = JSON.stringify(jsonObj, null, 4);
  console.log(json);

  postJsonToURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs',
    json,
    showResultMessage,
    [
      'Instatiation request for VS Descriptor ' +
        vsdId +
        ' successfully submitted',
      'Unable to instantiate VS descriptor with id ' + vsdId
    ]
  );
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
    cbacks = [
      'vsd_details.html?Id=',
//      openInstantiateModal,
      'instantiateVSDescriptor',
      'deleteVSDescriptor'
    ];
    names = ['View', 'Instantiate', 'Delete'];
  }

  var btnFlag = true;
  var header = createTableHeaderByValues(
    [
      'Id',
      'Name',
      'Version',
      'Vsb Id',
      'Slice Service Type',
      'Management Type',
      'QoS Parameters'
    ],
    btnFlag,
    false
  );
  var columns = [
    ['vsDescriptorId'],
    ['name'],
    ['version'],
    ['vsBlueprintId'],
    ['sst'],
    ['managementType'],
    ['qosParameters']
  ];

  var conts = '<tbody>';
  for (var i = 0; i < data.length; i++) {
    conts += createVSDescriptorsTableContents(
      data[i],
      btnFlag,
      names,
      cbacks,
      columns
    );
  }
  table.innerHTML = header + conts + '</tbody>';
}

function openInstantiateModal(vsdId) {
  makeInstantiateModal(vsdId);
  $('#instantiateVSDescriptor_' + vsdId).modal('toggle');
}

function createVSDescriptorDetailsTable(data, divId) {
  //console.log(JSON.stringify(data, null, 4));

  var div = document.getElementById(divId);
  if (!div) {
    console.error('content div ' + divId + ' not found');
    return;
  }
  if (!data || data.length < 1) {
    console.log('No VS Descriptor');
    table.innerHTML = '<tr>VS Descriptor not found in database</tr>';
    return;
  }

  var vsd = data;
  // Start building tables
  var children = [];
  children.push(
    makeTable('VSD Metadata', {
      Id: vsd.vsDescriptorId,
      Name: vsd.name,
      Version: vsd.version,
      'VSB Id': vsd.vsBlueprintId
    })
  );

  children.push(makeTable('QoS Parameters', vsd.qosParameters));

  children.push(
    makeTable('Slice Parameters', {
      'Slice Service Type': vsd.sst,
      'Management Type': vsd.managementType
    })
  );

  var generalConstraint = null;

  if (vsd.serviceConstraints != null) {
    for (constraint of vsd.serviceConstraints) {
      if (constraint.atomicComponentId == null) {
        generalConstraint = constraint;
        break;
      }
    }
  } else {
    generalConstraint = {
      priority: 'LOW',
      sharable: false,
      canIncludeSharedElements: false
    };
  }

  if (generalConstraint != null) {
    children.push(
      makeTable(
        'Service Constraint',
        _.mapKeys(generalConstraint, (v, k) => _.upperFirst(_.lowerCase(k)))
      )
    );
  }

  var sla = vsd.sla || {
    serviceCreationTime: 'UNDEFINED',
    availabilityCoverage: 'UNDEFINED',
    lowCostRequired: false
  };

  children.push(
    makeTable('SLA', _.mapKeys(sla, (v, k) => _.upperFirst(_.lowerCase(k))))
  );

  for (child of children) {
    div.appendChild(child);
  }
}

function makeTable(title, data) {
  var container = makeChildDiv(null, 'col-md-7', 'col-sm-7', 'col-xs-12');
  var titleMetadata = makeChild(container, 'h3');
  titleMetadata.innerText = title;

  var metadataTable = makeChild(container, 'table', 'table', 'table-hover');

  var tBody = makeChild(metadataTable, 'tBody');
  for (key in data) {
    let row = makeChild(tBody, 'tr');
    makeChild(row, 'th').innerText = key;
    let valueCell = makeChild(row, 'td');
    valueCell.innerText = data[key];
    valueCell.setAttribute('align', 'right');
  }
  return container;
}

function appendAsRow(table, ...cells) {
  var row = document.createElement('tr');
  for (cell of cells) {
    row.appendChild(cell);
  }
  table.appendChild(row);
}

function makeCell(innerText) {
  var cell = document.createElement('td');
  cell.innerText = innerText;
  return cell;
}

function cellFromOptional(obj, optionalProp) {
  if (obj.hasOwnProperty(optionalProp) && obj[optionalProp] != null) {
    let p = obj[optionalProp];
    if (p instanceof Array) {
      return makeCell(p.join(', '));
    } else {
      return makeCell(obj[optionalProp]);
    }
  } else {
    return makeCell('');
  }
}

function createVSDescriptorsTableContents(
  data,
  btnFlag,
  names,
  cbacks,
  columns
) {
  console.log(JSON.stringify(data, null, 4));

  var text = '';

  var btnText = '';
  if (btnFlag) {
    btnText += createActionButton(data.vsDescriptorId, names, cbacks);
    getVSBlueprint(data.vsBlueprintId, [data.vsDescriptorId,"instVSDId-parameters"],createInstantiateVSDModalDialog);
   //createInstantiateVSDModalDialog(data.vsDescriptorId);
  }

  text += '<tr>' + btnText;
  for (var i = 0; i < columns.length; i++) {
    var values = [];
    getValuesFromKeyPath(data, columns[i], values);
    //console.log(values);

    var subText = '<td>';
    var subTable = '<table class="table table-borderless">';

    if (data.hasOwnProperty(columns[i][0])) {
      if (columns[i][0] == 'serviceConstraints') {
        var sT = document.createElement('table'); // SubTable element
        sT.classList.add('table', 'table-borderless');
        var head = document.createElement('thead');
        cells = [
          makeCell('Atomic component ID'),
          makeCell('Priority'),
          makeCell('Sharable'),
          makeCell('Include shared'),
          makeCell('Preferred providers'),
          makeCell('Non preferred providers'),
          makeCell('Prohibited providers')
        ];
        appendAsRow(head, ...cells);
        sT.appendChild(head);

        let body = document.createElement('tBody');

        for (var v in values[0]) {
          //console.log(JSON.stringify(values[0], null, 4));
          var constraint = values[0][v];

          cells = [
            cellFromOptional(constraint, 'atomicComponentId'),
            cellFromOptional(constraint, 'priority'),
            cellFromOptional(constraint, 'sharable'),
            cellFromOptional(constraint, 'canIncludeSharedElements'),
            cellFromOptional(constraint, 'preferredProviders'),
            cellFromOptional(constraint, 'nonPreferredProviders'),
            cellFromOptional(constraint, 'prohibitedProviders')
          ];

          appendAsRow(body, ...cells);
        }

        sT.appendChild(body);

        subText += sT.outerHTML;
      } else if (columns[i][0] == 'sla') {
        var sT = document.createElement('table'); // SubTable element
        sT.classList.add('table', 'table-borderless');

        let body = document.createElement('tBody');
        //console.log(JSON.stringify(values[0], null, 4));
        var sla = values[0];

        if (
          sla.hasOwnProperty('serviceCreationTime') &&
          sla.serviceCreationTime != null
        ) {
          cells = [
            makeCell('Service creation Time'),
            cellFromOptional(sla, 'serviceCreationTime')
          ];
          appendAsRow(body, ...cells);
        }

        if (
          sla.hasOwnProperty('availabilityCoverage') &&
          sla.serviceCreationTime != null
        ) {
          cells = [
            makeCell('Availability coverage'),
            cellFromOptional(sla, 'availabilityCoverage')
          ];
          appendAsRow(body, ...cells);
        }

        if (
          sla.hasOwnProperty('low_cost_required') &&
          sla.serviceCreationTime != null
        ) {
          cells = [
            makeCell('Low cost required'),
            cellFromOptional(sla, 'low_cost_required')
          ];
          appendAsRow(body, ...cells);
        }

        sT.appendChild(body);

        subText += sT.outerHTML;
      } else if (values[0] instanceof Array) {
        for (var v in values[0]) {
          if (values[0][v] instanceof Object) {
            //console.log(JSON.stringify(values[0], null, 4));
            var value = values[0][v];
            subTable += '<tr><td>' + value.parameterId + '</td><tr>';
          } else {
            subTable += '<tr><td>' + values[0][v] + '</td><tr>';
          }
        }
        subText += subTable + '</table>';
      } else {
        if (values[0] instanceof Object) {
          //console.log(JSON.stringify(values[0], null, 4));
          var value = values[0];
          $.each(value, function(key, val) {
            subTable +=
              '<tr><td><b>' + key + '</b></td><td>' + val + '</td><tr>';
          });
          subText += subTable + '</table>';
        } else {
          if (columns[i][0] == 'vsBlueprintId') {
            subText +=
              '<button type="button" class="btn btn-info btn-xs btn-block" onclick="location.href=\'blueprint_details.html?Id=' +
              values[0] +
              '\'">' +
              values[0] +
              '</button>';
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

function makeChildDiv(parent, ...classes) {
  return makeChild(parent, 'div', ...classes);
}

function makeChild(parent, tag, ...classes) {
  var out = document.createElement(tag);
  out.classList.add(...classes);
  if (parent != undefined && parent != null) {
    parent.appendChild(out);
  }
  return out;
}

function makeFormGroup(
  container,
  inputId,
  labelText,
  required,
  type,
  value,
  readOnly
) {
  if (readOnly === undefined) {
    readOnly = false;
  }
  if (value === undefined) {
    value = null;
  }
  if (required === undefined) {
    required = false;
  }
  if (type === undefined) {
    type = 'text';
  }
  var group = makeChildDiv(container, 'form-group');
  var label = makeChild(
    group,
    'label',
    'control-label',
    'col-md-3',
    'col-sm-3',
    'col-xs-12'
  );
  label.setAttribute('for', inputId);
  label.innerText = labelText;

  var inputContainer = makeChildDiv(group, 'col-md-6', 'col-sm-6', 'col-xs-12');
  var fInput = makeChild(
    inputContainer,
    'input',
    'form-control',
    'col-md-7',
    'col-xs-12'
  );
  fInput.setAttribute('id', inputId);
  fInput.setAttribute('type', type);
  if (required) {
    fInput.setAttribute('required', 'required');
  }
  if (value != null) {
    fInput.setAttribute('value', value);
  }
  if (readOnly) {
    fInput.setAttribute('readonly', '');
  }
  return group;
}

function makeScaleModal(vsiId, vsdId) {
  var main = makeChildDiv(null, 'modal', 'fade', 'bs-example-modal-md', 'in');
  main.setAttribute('tabindex', -1);
  main.setAttribute('role', 'dialog');
  main.setAttribute('aria-hidden', true);
  main.setAttribute('id', 'scaleVSI_' + vsiId);

  var inner = makeChildDiv(main, 'modal-dialog', 'modal-md');
  var content = makeChildDiv(inner, 'modal-content');
  var header = makeChildDiv(content, 'modal-header');

  var closeButton = makeChild(header, 'button', 'close');
  closeButton.setAttribute('data-dismiss', 'modal');
  closeButton.setAttribute('aria-label', 'Cancel');
  var buttonSpan = makeChild(closeButton, 'span');
  buttonSpan.innerText = '×';
  var title = makeChild(header, 'h4', 'modal-title');
  title.innerText = 'Scale VS Instance';

  var modalBody = makeChildDiv(content, 'modal-body');
  var formContainer = makeChildDiv(modalBody, 'form-group');
  var form = makeChild(
    formContainer,
    'form',
    'form-horizontal',
    'form-label-left'
  );
  form.setAttribute('data-parsley-validate', '');
  form.setAttribute('novalidate', '');
  form.setAttribute('id', 'scaleVSIForm');

  tenant = getCookie('username');

  // make form group for the new VSD input form
  var group = makeChildDiv(form, 'form-group');
  var label = makeChild(
    group,
    'label',
    'control-label',
    'col-md-3',
    'col-sm-3',
    'col-xs-12'
  );
  label.setAttribute('for', 'scaleVSI-target');
  label.innerText = 'New VSD';

  var inputContainer = makeChildDiv(group, 'col-md-6', 'col-sm-6', 'col-xs-12');
  var targetInput = makeChild(
    inputContainer,
    'select',
    'form-control',
    'col-md-7',
    'col-xs-12'
  );
  targetInput.setAttribute('id', 'scaleVSI-target');
  // End VSD input form

  var parseInstance = function(data, _) {
    var vsdId = data.vsdId;
    getAllVSDescriptors([vsdId], populateTargetInput);
  };

  var populateTargetInput = function(data, params) {
    var vsdId = params[0];
    if (!data instanceof Array) {
      console.error('Unexpected response.');
      JSON.stringify(data);
      throw 'Unexpected response.';
    }

    var vsdsByVsb = {};
    var vsbId = null;

    for (vsd of data) {
      let key = vsd.vsBlueprintId;

      if (vsd.vsDescriptorId == vsdId) {
        vsbId = key;
      } else {
        // Skip so we don't propose to scale to the current vsd
        vsdsByVsb[key] || (vsdsByVsb[key] = []); // Set default empty list value
        vsdsByVsb[key].push([vsd.vsDescriptorId, vsd.name]);
      }
    }

    if (vsbId === null) {
      console.error('Current VSD ' + vsdId + 'not found');
      throw 'Current VSD ' + vsdId + 'not found';
    }
    var vsds = vsdsByVsb[vsbId];

    for (kvpair of vsds) {
      let opt = document.createElement('option');
      opt.setAttribute('value', kvpair[0]);
      opt.innerText = kvpair[1];
      targetInput.appendChild(opt);
    }
  };

  getVSInstance(vsiId, [], parseInstance);

  var modalFooter = makeChildDiv(content, 'modal-footer');
  var cancelButton = makeChild(
    modalFooter,
    'button',
    'btn',
    'btn-default',
    'pull-left'
  );
  cancelButton.setAttribute('data-dismiss', 'modal');
  var cancelCb = function() {
    clearForms('scaleVSI_' + vsiId);
  };
  cancelButton.addEventListener('click', cancelCb);
  cancelButton.innerText = 'Cancel';

  var submitButton = makeChild(
    modalFooter,
    'button',
    'btn',
    'btn-default',
    'pull-left'
  );
  submitButton.setAttribute('data-dismiss', 'modal');
  var submitCb = function() {
    scaleVSIFromForm(vsiId, tenant, targetInput.value);
  };
  submitButton.addEventListener('click', submitCb);
  submitButton.innerText = 'Submit';

  document.getElementById('scaleModalDiv').appendChild(main);
}

function scaleVSIFromForm(vsiId, tenant, newVSD) {
  var request = {
    tenantId: tenant,
    vsiId: vsiId,
    vsdId: newVSD
  };
  sendJsonToURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/basic/vslcm/vs/' + vsiId,
    JSON.stringify(request),
    showResultMessage,
    ['Scale VS instance request sent', 'Unable to scale VS Instance'],
    'PUT'
  );
}

function makeInstantiateModal(vsdId) {
  var main = makeChildDiv(null, 'modal', 'fade', 'bs-example-modal-md', 'in');
  main.setAttribute('tabindex', -1);
  main.setAttribute('role', 'dialog');
  main.setAttribute('aria-hidden', true);
  main.setAttribute('id', 'instantiateVSDescriptor_' + vsdId);

  var inner = makeChildDiv(main, 'modal-dialog', 'modal-md');
  var content = makeChildDiv(inner, 'modal-content');
  var header = makeChildDiv(content, 'modal-header');

  var closeButton = makeChild(header, 'button', 'close');
  closeButton.setAttribute('data-dismiss', 'modal');
  closeButton.setAttribute('aria-label', 'Cancel');
  var buttonSpan = makeChild(closeButton, 'span');
  buttonSpan.innerText = '×';
  var title = makeChild(header, 'h4', 'modal-title');
  title.innerText = 'Instantiate VS Descriptor';

  var modalBody = makeChildDiv(content, 'modal-body');
  var formContainer = makeChildDiv(modalBody, 'form-group');
  var form = makeChild(
    formContainer,
    'form',
    'form-horizontal',
    'form-label-left'
  );
  form.setAttribute('data-parsley-validate', '');
  form.setAttribute('novalidate', '');
  form.setAttribute('id', 'createNSDIdForm');
  makeFormGroup(form, 'instVSD-id', 'Id', true, 'text', vsdId, true);
  makeFormGroup(form, 'instVSD-name', 'Name', true);
  makeFormGroup(form, 'instVSD-tenant', 'Tenant', true);
  makeFormGroup(form, 'instVSD-desc', 'Description');

  var modalFooter = makeChildDiv(content, 'modal-footer');
  var cancelButton = makeChild(
    modalFooter,
    'button',
    'btn',
    'btn-default',
    'pull-left'
  );
  cancelButton.setAttribute('data-dismiss', 'modal');
  var cancelCb = function() {
    clearForms('instantiateVSDescriptor_' + vsdId);
  };
  cancelButton.addEventListener('click', cancelCb);
  cancelButton.innerText = 'Cancel';

  var submitButton = makeChild(
    modalFooter,
    'button',
    'btn',
    'btn-default',
    'pull-left'
  );
  submitButton.setAttribute('data-dismiss', 'modal');
  var submitCb = function() {
    instantiateVSDFromForm([
      'instVSD-id',
      'instVSD-name',
      'instVSD-tenant',
      'instVSD-desc'
    ]);
  };
  submitButton.addEventListener('click', submitCb);
  submitButton.innerText = 'Submit';

  document.getElementById('instantiateModalDiv').appendChild(main);
}

function createInstantiateVSDModalDialog(data, params) {
  /*jshint multistr: true */
   var vsdId=params[0];
    var instanceParametersHtml='<br /> <h5 class="modal-title" id="myModalLabel">Instance parameters</h5> <br /> ';
    for (var i = 0; i < data.vsBlueprint.configurableParameters.length; i++) {

        var name=data.vsBlueprint.configurableParameters[i];
        var paramPrintName = (name.split(".")).slice(-1)[0].replace(/_/g," ");
        instanceParametersHtml += '<div class="form-group">'+
                   '<label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">'+paramPrintName+'<!-- span class="required">*</span --></label>'+
                  '<div class="col-md-6 col-sm-6 col-xs-12">'+
                       '<input type="text" id="instVSDId-param_' + name +'" name="last-name" required="required" class="form-control col-md-7 col-xs-12">'+
                  '</div>'+
                  '<hr style="clear:both;">'+
                  '</div>';
    }
   var text =
    ' <div id="instantiateVSDescriptor_' +
    vsdId +
    '" class="modal fade bs-example-modal-md in" tabindex="-1" role="dialog" aria-hidden="true">\
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
                              <input type="text" id="instVSDId-id_' +
    vsdId +
    '" required="required" class="form-control col-md-7 col-xs-12" value="' +
    vsdId +
    '" readonly>\
                            </div>\
                          </div>\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Name <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input type="text" id="instVSDId-name_' +
    vsdId +
    '" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
                            </div>\
                          </div>\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Tenant <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input type="text" id="instVSDId-tenant_' +
    vsdId +
    '" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
                            </div>\
                          </div>\
                          <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Description <!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
                              <input type="text" id="instVSDId-description_' +
    vsdId +
    '" name="last-name" required="required" class="form-control col-md-7 col-xs-12">\
                            </div>\
                          </div>\
			  <div class="form-group">\
                            <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">Position<!-- span class="required">*</span -->\
                            </label>\
                            <div class="col-md-6 col-sm-6 col-xs-12">\
				<select id="instVSDId-position_' + vsdId +'" autocomplete="off" name="last-name" class="form-control col-md-7 col-xs-12">\
                        		<option value="NONE">NONE</option>\
                        		<option value="5TONIC">5TONIC</option>\
                        		<option value="CTTC">CTTC</option>\
                        		<option value="ARNO">ARNO</option>\
                        		<option value="POLITO">POLITO</option>\
                        		<option value="CRF">CRF</option>\
                    		</select>\
                            </div>\
                          </div>'+
                         instanceParametersHtml+
                        '</form>\
                    </div>\
                  </div>\
                  <div class="modal-footer">\
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick=clearForms("instantiateVSDescriptor_' +
    vsdId +
    '")>Cancel</button>\
                    <button type="submit" class="btn btn-info" data-dismiss="modal"\
                            onclick=instantiateVSDFromForm(["instVSDId-id_' +
    vsdId +
    '","instVSDId-name_' +
    vsdId +
    '","instVSDId-tenant_' +
    vsdId +
    '","instVSDId-description_' +
    vsdId +
    '","instVSDId-position_' +
    vsdId +
    '",["'+data.vsBlueprint.configurableParameters.join('","')+'"]],"response")>Submit</button>\
                  </div>\
                </div>\
              </div>\
            </div>';

  var instantiateModalDiv = document.getElementById('instantiateModalDiv');
  instantiateModalDiv.innerHTML += text;
}

function scaleVSInstanceClick(instanceId) {
  makeScaleModal(instanceId);
  $('#scaleVSI_' + instanceId).modal('toggle');
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
    cbacks = ['terminateVSInstance', 'purgeVSInstance', scaleVSInstanceClick];
    names = ['Terminate', 'Remove Entry', 'Scale'];
  }

  var header = createTableHeaderByValues(
    ['Id', 'Name', 'Description', 'Vsd Id', 'Sap', 'Status', 'More'],
    btnFlag,
    false
  );
  var columns = [
    ['vsiId'],
    ['name'],
    ['description'],
    ['vsdId'],
    ['externalInterconnections'],
    ['status'],
    ['monitoringUrl']
  ];

  table.innerHTML = header;
  tBody = document.createElement('tbody');
  table.append(tBody);

  var row;
  var rowId;
  for (var i = 0; i < l; i++) {
    row = document.createElement('tr');
    rowId = `table-row-${String(i)}`;
    row.id = rowId;
    tBody.append(row);
    readVSInstance(rowId, data[i], [l, i, cbacks, names, columns, btnFlag]);
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
    tableHtml += '<tr><td>';
    // Internal table (sap table)
    tableHtml += tableHead.repeat(1); // clone string
    tableHtml += '<tr><th>' + sap.sapdId + '</th></tr>';
    if (status == 'INSTANTIATED') {
      for (var j in sap.userAccessInfo) {
        var uai = sap.userAccessInfo[j];
        tableHtml +=
          '<tr><td>' + uai.vnfdId + '</td><td>' + uai.address + '</td></tr>';
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
  var row_no = params[1];
  var cbacks = params[2];
  var names = params[3];
  var columns = params[4];
  var btnFlag = params[5];
  var rowId = params[6];

  var row = document.getElementById(rowId);

  var btnText = '';
  if (btnFlag) {
    btnText += createActionButton(data.vsiId, names, cbacks);
  }
  row.innerHTML += btnText;

  for (var i in columns) {
    var values = [];
    getValuesFromKeyPath(data, columns[i], values);

    var subEl = document.createElement('td');
    var subTableHead = '<table class="table table-borderless">';

    if (data.hasOwnProperty(columns[i][0])) {
      if (values instanceof Array && values.length > 1) {
        var subTable = subTableHead.repeat(1); // clone
        for (var v in values) {
          subTable += '<tr><td>' + values[v] + '</td><tr>';
        }
        subEl.innerHTML += subTable + '</table>';
      } else {
        if (columns[i][0] == 'vsdId') {
          subEl.innerHTML +=
            '<button type="button" class="btn btn-info btn-xs btn-block" onclick="location.href=\'vsd_details.html?Id=' +
            values +
            '\'">' +
            values +
            '</button>';
        } else if (columns[i][0] == 'externalInterconnections') {
          var saps = values[0];
          subEl.innerHTML += parseSap(saps, data.status);
        } else if (columns[i][0] == 'monitoringUrl') {
          url = values.pop();
          if (!url.startsWith('http://')) {
            url = 'http://' + url;
          }
          var button = new Button('Monitoring dashboard', popUp, [url]);
          button.renderIn(subEl);
        } else {
          if (columns[i][0] == 'status' && values == 'FAILED') {
            subEl.innerHTML += values + '<br>' + data.errorMessage;
          } else {
            subEl.innerHTML += values;
          }
        }
      }
    }
    row.append(subEl);
  }
}
