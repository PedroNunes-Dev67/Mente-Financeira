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

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Dashboard</h1>
                              </div>
                              <div class="dashPrincipal">
                                <div class="dashDivEsq">
                                    <div class="divEsqSon"></div>
                                    <div class="divEsqSon"></div>
                                </div>
                                <div class="dashDivDir">
                        
                                </div>
                            </div>`

});

btnHistorico.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Histórico</h1>
                               </div>`

});

btnConfig.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Configurações</h1>
                               </div>`
;

});

btnSuporte.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Suporte</h1>
                               </div>`
;

});

navSub1.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Despesas mensais</h1>
                               </div>`
;

});

navSub2.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Despesas cotidianas</h1>
                               </div>`
;

});


document.querySelectorAll('.links').forEach((e,i) => {

    e.addEventListener('click', () => {

        document.querySelectorAll('.links').forEach((ele, ind) => {
            ele.classList.remove('btnClicado');
        })

        e.classList.add('btnClicado');
        
    })
})


