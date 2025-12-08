const urlBase = process.env.urlBase;
import { setToken } from './auth.js';

document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault(); 

    const loginData = {
        login: document.getElementById('login').value,
        senha: document.getElementById('senha').value,
    };

    try {
        console.log("init");
        console.log(loginData);

        const response = await fetch(urlBase+'/adm/login', {
            method: 'POST',
            headers: { 'Content-type': 'application/json' },
            body: JSON.stringify(loginData)

        })


        if (response.ok) {

            const tokenJWT = await response.json();
            setToken(tokenJWT.tokenJWT)
    
        } 
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro no processo de login');
    }
});