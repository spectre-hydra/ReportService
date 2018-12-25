
var jsonOutput = document.getElementById("json");
var statusOutput = document.getElementById("responseStatus");

function click(buttonId, event) {

    event.preventDefault();
    console.log('test');

    var requestMethod = 'GET';

    switch (buttonId)
    {
        case 'putReport': requestMethod = 'PUT'; break;
        case 'getReport': requestMethod = 'GET'; break;
        case 'deleteReport': requestMethod = 'DELETE'; break;
    }

    document.querySelectorAll('button').forEach(elem => elem.setAttribute('disabled', true));

    var payload = {
        characterPhrase: document.getElementById('characterPhrase').value,
        planetName: document.getElementById('planetName').value,
    };

    jsonOutput.innerHTML = '';
    statusOutput.innerHTML = '';

    var url = '/report'
    if(requestMethod === 'PUT')
    {

    }
    else
    {
        url = url + '?characterPhrase=' + payload.characterPhrase + '&planetName=' + payload.planetName;
    }

    fetch(url, {
        body: requestMethod === 'PUT' ? JSON.stringify(payload) : null,
        headers: {
            'content-type': 'application/json',
        },
        method: requestMethod,
    }).then(r => fetchHandler(r))}


function fetchHandler(response) {
    if (response.ok) {
        return response.json().then(json => {
            statusOutput.innerHTML = 'Status: ' + 200;
            jsonOutput.innerHTML = JSON.stringify(json, undefined, 2);
            document.querySelectorAll('button').forEach(elem => elem.removeAttribute('disabled'));
    }).catch(err => {
            statusOutput.innerHTML = 'Status: ' + response.status;
            document.querySelectorAll('button').forEach(elem => elem.removeAttribute('disabled'));
    });

    } else {
        return response.json().catch(err => {
            statusOutput.innerHTML = 'Status: ' + err.status;
            console.log(err);
            document.querySelectorAll('button').forEach(elem => elem.removeAttribute('disabled'));
    }).then(json => {
            console.log(json);
            jsonOutput.innerHTML = JSON.stringify(json, undefined, 2);;
            statusOutput.innerHTML = 'Status: ' + json.status;
            document.querySelectorAll('button').forEach(elem => elem.removeAttribute('disabled'));
    });
    }
}

['putReport', 'getReport', 'deleteReport'].map(id => document.getElementById(id))
  .forEach(elem => elem.addEventListener('click', click.bind(null,elem.getAttribute('id'))));
