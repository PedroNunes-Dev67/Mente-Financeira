const form = document.querySelector('form');

form.addEventListener('submit', async (e) => {

    e.preventDefault();

    const nomeUsuario = document.getElementById('inNome').value;
    const emailUsuario = document.getElementById('inEmail').value;
    const senhaUsuario = document.getElementById('inSenha').value;

    const usuarioRequestDTO = {
        nome: nomeUsuario,
        email: emailUsuario,
        senha: senhaUsuario 
    }

    try{
        const response = await axios.post('http://localhost:8080/usuarios/cadastro', usuarioRequestDTO);

        window.location.href="envio-de-email.html";
    }
    catch(error){
        console.log('error ao conectar ao servidor.');
    }
})

function voltarALogin(){

    window.location.href="../index.html"
}