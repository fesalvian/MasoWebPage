document.getElementById('loginForm').addEventListener('sumbit', async (evento) => {
    evento.preventDefault(); //evita sumbit padrao do form
    console.log('botao funcionou nessa bosta');
    

    const loginData = {
        login: document.getElementById('login').value, 
        senha: document.getElementById('senha').value,
    };

    try{
        const response = await fetch ('http://localhost:8080/adm/login',{
            method:'POST',
            headers:{'Content-type':'application/json'},
            body:JSON.stringify(loginData)

        });

        if (response.ok) {
            const tokenDTO = await response.json();
            const token = tokenDTO.token;
            alert('Login bem-sucedido!');
            localStorage.setItem('token', token); // Armazena o token para uso posterior
        } else {
            alert('Falha na autenticação.');
        }
    }catch (error){
    console.error('Erro:', error);
    alert('Erro no processo de login');
    }
});