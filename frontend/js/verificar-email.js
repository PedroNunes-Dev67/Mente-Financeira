
const params = new URLSearchParams(window.location.search);


const token = params.get('token');


if (!token){
    window.location.href="cadastro.html"
}

const validarEmailBtn = document.getElementById('inSubmit');

validarEmailBtn.addEventListener('click', async (e) => {

    e.preventDefault();

    try{
        const response = await axios.post('http://localhost:8080/usuarios/cadastro/auth', {token:token});

        const {id_usuario, nome, email} = response.data;

        document.getElementById('respVerificacao').innerText='E-mail verificado com sucesso!💵🚀'

        document.getElementById('respNome').innerText=`Bem-vindo ao <strong class="titulo">Mente Financeira</strong>,  ${nome}`

        validarEmailBtn.disabled = true;
    }
    catch(error){
        console.log("Error ao validar token")
    }   
})