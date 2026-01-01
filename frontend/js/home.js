const btnDespesa = document.getElementById('btnDespesa');
const navSub = document.getElementById('navSub');
const painelDireito = document.getElementById('painelDireito');

const btnDashboard = document.getElementById('btnDashboard');
const btnHistorico = document.getElementById('btnHistorico');
const btnConfig = document.getElementById('btnConfig');

btnDespesa.addEventListener('click', () => {

    navSub.classList.toggle('ativo');

    const imgArrow = document.getElementById('imgArrow');

     imgArrow.src = navSub.classList.contains('ativo')
        ? '../assets/images/arrow-down-sign-to-navigate2.png'
        : '../assets/images/arrow-down-sign-to-navigate.png';

});

btnDashboard.addEventListener('click' ,() => {

    painelDireito.innerHTML = "<h1>Tela de Dashboard</h1>";

});

btnHistorico.addEventListener('click' ,() => {

    painelDireito.innerHTML = "<h1>Tela de Historico</h1>";

});

btnConfig.addEventListener('click' ,() => {

    painelDireito.innerHTML = "<h1>Tela de Configuração</h1>";

});