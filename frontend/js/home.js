const btnDespesa = document.getElementById('btnDespesa');
const navSub = document.getElementById('navSub');
const painelDireito = document.getElementById('painelDireito');

const btnDashboard = document.getElementById('btnDashboard');
const btnHistorico = document.getElementById('btnHistorico');
const btnSuporte = document.getElementById('btnSuporte');
const btnConfig = document.getElementById('btnConfig');
const navSub1 = document.getElementById('navSub1');
const navSub2 = document.getElementById('navSub2');

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

btnSuporte.addEventListener('click' ,() => {

    painelDireito.innerHTML = "<h1>Tela de Suporte</h1>";

});

navSub1.addEventListener('click' ,() => {

    painelDireito.innerHTML = "<h1>Tela de Despesas Recorrentes</h1>";

});

navSub2.addEventListener('click' ,() => {

    painelDireito.innerHTML = "<h1>Tela de Despesas não Recorrentes</h1>";

});