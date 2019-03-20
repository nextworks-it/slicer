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

class ReadNSParams {
  constructor(containerId, columns) {
    this.containerId = containerId;
    this.columns = columns;
  }
}

function readNSInstances(tableId) {
  getNSInstanceIds(tableId, createNSInstancesTable);
}

function fillNSICounter(data, elemId) {
  var countDiv = document.getElementById(elemId);

  countDiv.innerHTML = data.length;
}

function readNSInstance(nsiId, params) {
  getNSInstance(nsiId, params, createNSInstancesTableContent);
}

function getNSInstanceIds(elemId, callback) {
  // TODO url
  getJsonFromURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/admin/nsmf/networksliceids',
    callback,
    elemId
  );
}

function getNSInstance(nsiId, params, callback) {
  // TODO url
  getJsonFromURLWithAuth(
    'http://' + vsAddr + ':' + vsPort + '/vs/admin/nsmf/networkslice/' + nsiId,
    callback,
    params
  );
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
  var header = createTableHeaderByValues(
    [
      'Id',
      'Name',
      'Description',
      'Network Service Descriptor',
      'Network Service Details',
      'Status',
      'Network Service Instance Id',
      'Sub-slices'
    ],
    btnFlag,
    false
  );
  var columns = [
    ['nsiId'],
    ['name'],
    ['description'],
    ['nsdId'],
    ['dfId'],
    ['status'],
    ['nfvNsId'],
    ['networkSliceSubnetInstances']
  ];
  table.innerHTML = header;
  tBody = document.createElement('tbody');
  table.append(tBody);

  var rowId;
  var r;
  for (var i = 0; i < l; i++) {
    r = document.createElement('tr');
    rowId = `table-row-${String(i)}`;
    r.id = rowId;
    tBody.append(r);
    readNSInstance(data[i], new ReadNSParams(rowId, columns));
  }
}

function createNSInstancesTableContent(data, params) {
  var columns = params.columns;
  var containerId = params.containerId;

  var container = document.getElementById(containerId);

  for (var i of columns) {
    var value = extract(i, data);

    var subEl = document.createElement('td');
    var subText = '';
    var subTableHead = '<table class="table table-borderless">';

    if (data.hasOwnProperty(i[0])) {
      if (i[0] == 'vsdId') {
        // TODO change to nsi details
        subText +=
          '<button type="button" class="btn btn-info btn-xs btn-block" onclick="location.href=\'vsd_details.html?Id=' +
          value +
          '\'">' +
          value +
          '</button>';
      } else if (i[0] == 'dfId') {
        subText += subTableHead.repeat(1); // clone string
        subText += '<tr><td> Deployment flavour: ' + value + '</td></tr>';
        subText +=
          '<tr><td> Instantiation level: ' +
          data.instantiationLevelId +
          '</td></tr>';
        subText += '</table>';
      } else if (i[0] == 'status' && value == 'FAILED') {
        subText += value + '<br>' + data.errorMessage;
      } else if (i[0] == 'networkSliceSubnetInstances') {
        subText += value.join(', ');
      } else if (i[0] === 'nfvNsId') {
        subEl.append(value);
        if (data.hasOwnProperty('nfvNsUrl')) {
          var b = new Button('View', popUp, [data['nfvNsUrl']]);
          subEl.innerHTML += '<br>';
          b.renderIn(subEl);
        }
      } else {
        subText += value;
      }
    }
    if (subText != '') {
      subEl.innerHTML += subText;
    }
    container.append(subEl);
  }
}

function fillVSICounter(data, elemId) {
  var countDiv = document.getElementById(elemId);

  //console.log(JSON.stringify(data, null, 4));
  countDiv.innerHTML = data.length;
}
