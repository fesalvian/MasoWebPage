function pegaParametro() {
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");

    return token;
}

async function enviarValidacao() {
    const token = pegaParametro();

    const resp = await fetch('http://localhost:8080/lead/validarEmail', {
        method: 'POST',
        headers: { 'Content-type': 'application/json' },
        body: token
    });
    if (resp.status == 200) {
        window.location.href = "http://localhost:5500/pages/login.html"
    }

}

enviarValidacao()