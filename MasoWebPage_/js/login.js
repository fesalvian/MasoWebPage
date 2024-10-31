document.getElementById('loginForm').addEventListener('sumbit', async (e) => {
    e.preventDefault(); //evita sumbit padrao do form

    const loginData = {
        login: document.getElementById('login').value, 
        senha: document.getElementById('senha').value,
    }
})