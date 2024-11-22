document.getElementById('loginForm').addEventListener('submit', async (event) => {
    event.preventDefault(); //evita sumbit padrao do form
    console.log('botao funcionou nessa bosta');


    const loginData = {
        login: document.getElementById('login').value,
        senha: document.getElementById('senha').value,
    };

    try {
        console.log("init");
        console.log(loginData);

        const response = await fetch('http://localhost:8080/adm/login', {
            method: 'POST',
            headers: { 'Content-type': 'application/json' },
            body: JSON.stringify(loginData)

        })


        if (response.ok) {

            const tokenJWT = await response.json();
            console.log(tokenJWT.tokenJWT)
    
            
           // localStorage.setItem('token', token); // Armazena o token para uso posterior
        } else {
            const tokenJWT = await response.json();       
            alert(tokenJWT.tokenJWT);
        }
    } catch (error) {
        console.error('Erro:', error);
        alert('Erro no processo de login');
    }
});