var wizardCurrentStep = 1;
var nsds = [];

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

function uploadVNFDFromForm(formIds, resId) {
	var jsonObj = JSON.parse('{}');
	jsonObj['name'] = document.getElementById(formIds[0]).value;
	jsonObj['version'] = document.getElementById(formIds[1]).value;
	jsonObj['provider'] = document.getElementById(formIds[2]).value;
	jsonObj['checksum'] = document.getElementById(formIds[3]).value;
	jsonObj['vnfPackagePath'] = document.getElementById(formIds[4]).value;
	var json = JSON.stringify(jsonObj, null, 4);
    
    return json;
}

function uploadAppFromForm(formIds, resId) {
	var jsonObj = JSON.parse('{}');
	jsonObj['name'] = document.getElementById(formIds[0]).value;
	jsonObj['version'] = document.getElementById(formIds[1]).value;
	jsonObj['provider'] = document.getElementById(formIds[2]).value;
	jsonObj['checksum'] = document.getElementById(formIds[3]).value;
	jsonObj['appPackagePath'] = document.getElementById(formIds[4]).value;
	var json = JSON.stringify(jsonObj, null, 4);
    
    return json;
}