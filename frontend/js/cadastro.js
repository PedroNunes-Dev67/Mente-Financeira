const form = document.querySelector('form');

form.addEventListener('submit', async (e) => {

    e.preventDefault();

    const nomeUsuario = document.getElementById('inNome').value;
    const emailUsuario = document.getElementById('inEmail').value;
    const senhaUsuario = document.getElementById('inSenha').value;

    const usuarioDTORequest = {
        nome: nomeUsuario,
        email: emailUsuario,
        senha: senhaUsuario 
    }

    try{
        const response = await axios.post('http://localhost:8080/usuarios/cadastro', usuarioDTORequest);

        window.location.href="envio-de-email.html";
    }
    catch(error){
        console.log('error ao conectar ao servidor.');
    }
})

function voltarALogin(){

    window.location.href="../index.html"
}

const button = document.getElementById('mostrarSenha');

button.addEventListener('click', (e) => {

    const input = document.getElementById('inSenha');

    const olho = document.getElementById('olhoSenha');

    if (input.type === "password"){
        input.type = "text"
        olho.src = '../assets/images/hidden.png'
    }
    else{
        input.type = "password"
        olho.src = '../assets/images/eye.png'
    }

    input.focus();
})